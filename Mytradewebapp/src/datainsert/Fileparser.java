package datainsert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.mr.data.HistoricHighLow;
import com.mr.data.StochIndicData;
import com.mr.data.Stockdata;
import com.mr.data.WeekStochIndicData;
import com.mr.data.WeekStockdata;

public class Fileparser {

	
	

	private static HashMap<String, HistoricHighLow> insertdata(String path,Dataconn dataconn, HashMap<String, HistoricHighLow> histHL) throws IOException,  ParseException, SQLException {
		// TODO Auto-generated method stub
		  HashMap <String,HistoricHighLow> finalHistHL = new HashMap <String,HistoricHighLow>();
	    Connection con1 = dataconn.getconn();
	    PreparedStatement stmt = null;
	    String pattern = "yyyyMMdd";
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	    Date tddt = null;
	    
	     
	    BufferedReader br = null;
	    try {
			br = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    String fileRead = br.readLine();
	  
	    while (fileRead!=null)
	    {
	    	String[] tokenize = fileRead.split(",");
	    	tddt = simpleDateFormat.parse(tokenize[1]);
			try {
				stmt = con1.prepareStatement("insert into STOCKPRICE values(?,?,?,?,?,?,?)");
				HistoricHighLow newobj = new HistoricHighLow();
				newobj.setStocksymbol(tokenize[0]);
				newobj.setHighdate(tddt);
				newobj.setHighprice(Double.parseDouble(tokenize[3]));
				newobj.setLowdate(tddt);
				newobj.setLowprice(Double.parseDouble(tokenize[4]));
				
			if (histHL.containsKey(tokenize[0]))
			{
				HistoricHighLow hilo = (HistoricHighLow)histHL.get(tokenize[0]);
				
				
				if (hilo.getLowprice() <= newobj.getLowprice())
				{
					newobj.setLowprice(hilo.getLowprice());
					newobj.setLowdate(hilo.getLowdate());
					
				}
				if (hilo.getHighprice() >= newobj.getHighprice())
				{
					newobj.setHighprice(hilo.getHighprice());
					newobj.setHighdate(hilo.getHighdate());
				}
				
				
				
			}
			finalHistHL.put(newobj.getStocksymbol(), newobj);
	    	stmt.setString(1,tokenize[0]);
	    	
	    	stmt.setDate(2, new java.sql.Date(tddt.getTime())); //date
	    	stmt.setFloat(3, (float) Double.parseDouble(tokenize[2]));
	    	stmt.setFloat(4, (float) Double.parseDouble(tokenize[3])); // high
	    	stmt.setFloat(5, (float) Double.parseDouble(tokenize[4])); //low
	    	stmt.setFloat(6, (float) Double.parseDouble(tokenize[5]));
	    	stmt.setString(7,tokenize[6]);
	    	stmt.executeUpdate();  
	    	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("data already inserted for" + tddt);
			}
	    	fileRead = br.readLine();
	    }
	     br.close();
	     dataconn.closeconn();
	     System.out.println("done" + path);
	//populateDailyIndicatorValues();
	//populateDailyStochastcValues();
	// populateWeeklyIndicatorValues();
	// populateWeeklyStochasticValues();
	     return finalHistHL;
	}

	private static void populateWeeklyStochasticValues() throws SQLException {
		// TODO Auto-generated method stub
		HashMap <String,WeekStochIndicData> stock = new HashMap<String,WeekStochIndicData>();
		Dataconn dataconn = new Dataconn();
		Connection con1 = dataconn.getconn();
		Statement stmt = con1.createStatement();
		ResultSet rs1 = stmt.executeQuery(" select stocksymbol,tradedate,obvvolume,WMALOW_DIFF,WMAHIGH_DIFF,LOWPRICE,TRADEYR,TRADEWEEK from WEEKLYINDICDATA where tradedate in (select max(tradedate) from stockprice)");
		while (rs1.next())
		{
			WeekStochIndicData sk = new WeekStochIndicData();
			sk.setStocksymbol(rs1.getString(1));
			sk.setTradedate(rs1.getDate(2));
			sk.setObvvolume(rs1.getLong(3));
			sk.setLowdiff(rs1.getDouble(4));
			sk.setHighdiff(rs1.getDouble(5));
			sk.setLowprice(rs1.getDouble(6));
			sk.setTradeyr(rs1.getString(7));
			sk.setTradeweek(rs1.getString(8));
			stock.put(sk.getStocksymbol(),sk);			
		}
		rs1.close();
		stmt.close();
		
		
		 Iterator it = stock.entrySet().iterator();
		 while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        PPeachWeekStochasticvalue((WeekStochIndicData)pair.getValue(),con1);
		    }
		
		 con1.close();
		 
	}

	

	private static void PPeachWeekStochasticvalue(WeekStochIndicData value, Connection con1) throws SQLException {
		// TODO Auto-generated method stub
		double lowdiffstoch =0;
		double highdiffstoch=0;
		double pricediffstoch=0;
		double obvstoch = 0;
		
		double lowdiff_LOW=0;
		double lowdiff_HIGH=0;
		double highdiff_LOW=0;
		double highdiff_HIGH=0;
		double pricediff_LOW=0;
		double pricediff_HIGH=0;
		Long obv_LOW=0L;
		Long obv_HIGH=0L;
		PreparedStatement  stmt = con1.prepareStatement("select * from (select LOWPRICE,OBVVOLUME,WMALOW_DIFF,WMAHIGH_DIFF from WEEKLYINDICDATA where stocksymbol= ? order by tradedate desc) where rownum < 15", ResultSet.TYPE_SCROLL_SENSITIVE, 
                ResultSet.CONCUR_UPDATABLE);
		
		stmt.setString(1, value.getStocksymbol());
		ResultSet rs1 = stmt.executeQuery();
		
		rs1.last();
		int rows = rs1.getRow();
		if(rows == 14)
		{
		rs1.beforeFirst();
		while (rs1.next())
		{
			if (pricediff_LOW == 0 || pricediff_LOW > rs1.getDouble(1))
				pricediff_LOW = rs1.getDouble(1);
			
			if (pricediff_HIGH == 0 || pricediff_HIGH < rs1.getDouble(1))
				pricediff_HIGH = rs1.getDouble(1);
			
			if (obv_LOW == 0L || obv_LOW > rs1.getLong(2) )
				obv_LOW = rs1.getLong(2);
			
			if (obv_HIGH == 0L || obv_HIGH < rs1.getLong(2) )
				obv_HIGH = rs1.getLong(2);
			
			
			if (lowdiff_LOW == 0 || lowdiff_LOW > rs1.getDouble(3))
				lowdiff_LOW = rs1.getDouble(3);
			
			if (lowdiff_HIGH == 0 || lowdiff_HIGH < rs1.getDouble(3))
				lowdiff_HIGH = rs1.getDouble(3);
			
			if (highdiff_LOW == 0 || highdiff_LOW > rs1.getDouble(4))
				highdiff_LOW = rs1.getDouble(4);
			
			if (highdiff_HIGH == 0 || highdiff_HIGH < rs1.getDouble(4))
				highdiff_HIGH = rs1.getDouble(4);
			
			
		}
		// startstockcalculation
		
		lowdiffstoch = getstochasticvalue(value.getLowdiff(),lowdiff_LOW,lowdiff_HIGH);
		highdiffstoch =getstochasticvalue(value.getHighdiff(),highdiff_LOW,highdiff_HIGH);
		pricediffstoch=getstochasticvalue(value.getLowprice(),pricediff_LOW,pricediff_HIGH);
		obvstoch =getstochasticvalue(value.getObvvolume(),obv_LOW,obv_HIGH);
		
		
		
		}
		else
		{
			lowdiffstoch =200;
			highdiffstoch =200;
			pricediffstoch=200;
			obvstoch =200;
		}
			
		rs1.close();
		stmt.close();
		
		

		
        PreparedStatement stmt1 = null;
		
		stmt1 = con1.prepareStatement("insert into WEEKLYSTOCHASTICDATA values(?,?,?,?,?,?,?,?)");
		
    	
    	stmt1.setDate(1, new java.sql.Date(value.getTradedate().getTime()));
    	stmt1.setString(2, value.getStocksymbol());
    	stmt1.setFloat(3, (float) lowdiffstoch);
    	stmt1.setFloat(4, (float) highdiffstoch);
    	stmt1.setFloat(5, (float) pricediffstoch);
    	stmt1.setFloat(6, (float) obvstoch);
    	stmt1.setString(7, value.getTradeyr());
    	stmt1.setString(8, value.getTradeweek());
    	
    	
    	stmt1.executeUpdate();  
		stmt1.close();
		
		
		
	}

	private static void populateDailyStochastcValues() throws SQLException {
		// TODO Auto-generated method stub
		
		HashMap <String,StochIndicData> stock = new HashMap<String,StochIndicData>();
		Dataconn dataconn = new Dataconn();
		Connection con1 = dataconn.getconn();
		Statement stmt = con1.createStatement();
		ResultSet rs1 = stmt.executeQuery("select stocksymbol,tradedate,obvvolume,WMALOW_DIFF,WMAHIGH_DIFF,LOWPRICE from Dailyindicdata where tradedate in (select max(tradedate) from Dailyindicdata)");
		while (rs1.next())
		{
			StochIndicData sk = new StochIndicData();
			sk.setStocksymbol(rs1.getString(1));
			sk.setTradedate(rs1.getDate(2));
			sk.setObvvolume(rs1.getLong(3));
			sk.setLowdiff(rs1.getDouble(4));
			sk.setHighdiff(rs1.getDouble(5));
			sk.setLowprice(rs1.getDouble(6));
			stock.put(sk.getStocksymbol(),sk);			
		}
		rs1.close();
		stmt.close();
		
		// Loop through Hashmap-each stock
		 Iterator it = stock.entrySet().iterator();
		 while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        PPeachdayStochasticvalue((StochIndicData)pair.getValue(),con1);
		    }
		
		 con1.close();
		 
	}

	
	
	
	private static void PPeachdayStochasticvalue(StochIndicData value, Connection con1) throws SQLException {
		// TODO Auto-generated method stub
		double lowdiffstoch =0;
		double highdiffstoch=0;
		double pricediffstoch=0;
		double obvstoch = 0;
		
		double lowdiff_LOW=0;
		double lowdiff_HIGH=0;
		double highdiff_LOW=0;
		double highdiff_HIGH=0;
		double pricediff_LOW=0;
		double pricediff_HIGH=0;
		Long obv_LOW=0L;
		Long obv_HIGH=0L;
		PreparedStatement  stmt = con1.prepareStatement("select * from (select LOWPRICE,OBVVOLUME,WMALOW_DIFF,WMAHIGH_DIFF from Dailyindicdata where stocksymbol= ? order by tradedate desc) where rownum < 15", ResultSet.TYPE_SCROLL_SENSITIVE, 
                ResultSet.CONCUR_UPDATABLE);
		
		stmt.setString(1, value.getStocksymbol());
		ResultSet rs1 = stmt.executeQuery();
		
		rs1.last();
		int rows = rs1.getRow();
		if(rows == 14)
		{
		rs1.beforeFirst();
		while (rs1.next())
		{
			if (pricediff_LOW == 0 || pricediff_LOW > rs1.getDouble(1))
				pricediff_LOW = rs1.getDouble(1);
			
			if (pricediff_HIGH == 0 || pricediff_HIGH < rs1.getDouble(1))
				pricediff_HIGH = rs1.getDouble(1);
			
			if (obv_LOW == 0L || obv_LOW > rs1.getLong(2) )
				obv_LOW = rs1.getLong(2);
			
			if (obv_HIGH == 0L || obv_HIGH < rs1.getLong(2) )
				obv_HIGH = rs1.getLong(2);
			
			
			if (lowdiff_LOW == 0 || lowdiff_LOW > rs1.getDouble(3))
				lowdiff_LOW = rs1.getDouble(3);
			
			if (lowdiff_HIGH == 0 || lowdiff_HIGH < rs1.getDouble(3))
				lowdiff_HIGH = rs1.getDouble(3);
			
			if (highdiff_LOW == 0 || highdiff_LOW > rs1.getDouble(4))
				highdiff_LOW = rs1.getDouble(4);
			
			if (highdiff_HIGH == 0 || highdiff_HIGH < rs1.getDouble(4))
				highdiff_HIGH = rs1.getDouble(4);
			
			
		}
		// startstockcalculation
		
		lowdiffstoch = getstochasticvalue(value.getLowdiff(),lowdiff_LOW,lowdiff_HIGH);
		highdiffstoch =getstochasticvalue(value.getHighdiff(),highdiff_LOW,highdiff_HIGH);
		pricediffstoch=getstochasticvalue(value.getLowprice(),pricediff_LOW,pricediff_HIGH);
		obvstoch =getstochasticvalue(value.getObvvolume(),obv_LOW,obv_HIGH);
		
		
		
		}
		else
		{
			lowdiffstoch =200;
			highdiffstoch =200;
			pricediffstoch=200;
			obvstoch =200;
		}
			
		rs1.close();
		stmt.close();
        PreparedStatement stmt1 = null;
		
		stmt1 = con1.prepareStatement("insert into DailystochasticData values(?,?,?,?,?,?)");
		
    	
    	stmt1.setDate(1, new java.sql.Date(value.getTradedate().getTime()));
    	stmt1.setString(2, value.getStocksymbol());
    	stmt1.setFloat(3, (float) lowdiffstoch);
    	stmt1.setFloat(4, (float) highdiffstoch);
    	stmt1.setFloat(5, (float) pricediffstoch);
    	stmt1.setFloat(6, (float) obvstoch);
    	
    	
    	stmt1.executeUpdate();  
		stmt1.close();
		
		
		
		
	}

	private static double getstochasticvalue(double currentval, double LOW, double HIGH) {
		// TODO Auto-generated method stub
		DecimalFormat df = new DecimalFormat("#.##"); 
		double stochval = ((currentval - LOW)/(HIGH-LOW))*100;
		if(Double.isNaN(stochval) || Double.isInfinite(stochval))
			stochval= 0;
		
		return Double.valueOf(df.format(stochval));
	}

	
	private static void populateWeeklyIndicatorValues() throws SQLException {
		// TODO Auto-generated method stub
		HashMap <String,WeekStockdata> stock = new HashMap<String,WeekStockdata>();
		Dataconn dataconn = new Dataconn();
		Connection con1 = dataconn.getconn();
		Statement stmt = con1.createStatement();
		ResultSet rs1 = stmt.executeQuery("select * from WEEK_STATS where ENDDATE in (select max(tradedate) from stockprice)");
		while (rs1.next())
		{
			WeekStockdata sk = new WeekStockdata();
			sk.setStocksymbol(rs1.getString(1));
			sk.setOpenprice(rs1.getDouble(2));	
			sk.setHighprice(rs1.getDouble(3));
			sk.setLowprice(rs1.getDouble(4));
			sk.setCloseprice(rs1.getDouble(5));
			sk.setTradeyr(rs1.getString(6));
			sk.setTradeweek(rs1.getString(7));
			sk.setTradevolume(rs1.getLong(8));
			sk.setTradedate(rs1.getDate(10));
			stock.put(sk.getStocksymbol(), sk);
		}
		rs1.close();
		stmt.close();
		
		Iterator it = stock.entrySet().iterator();
		 while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        PPeachWeekIndicvalue((WeekStockdata)pair.getValue(),con1);
		    }
		
		 con1.close();
		 
		
	}
	
	

	private static void populateDailyIndicatorValues() throws SQLException {
		// TODO Auto-generated method stub
		HashMap <String,Stockdata> stock = new HashMap<String,Stockdata>();
		Dataconn dataconn = new Dataconn();
		Connection con1 = dataconn.getconn();
		Statement stmt = con1.createStatement();
		ResultSet rs1 = stmt.executeQuery("select * from stockprice where tradedate in (select max(tradedate) from stockprice)");
		while (rs1.next())
		{
			Stockdata sk = new Stockdata();
			sk.setStocksymbol(rs1.getString(1));
			sk.setTradedate(rs1.getDate(2));
			sk.setOpenprice(rs1.getDouble(3));
			sk.setHighprice(rs1.getDouble(4));
			sk.setLowprice(rs1.getDouble(5));
			sk.setCloseprice(rs1.getDouble(6));
			sk.setTradevolume(rs1.getLong(7));
			stock.put(sk.getStocksymbol(),sk);			
		}
		rs1.close();
		stmt.close();
		
		// Loop through Hashmap-each stock
		 Iterator it = stock.entrySet().iterator();
		 while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        PPeachdayIndicvalue((Stockdata)pair.getValue(),con1);
		    }
		
		 con1.close();
		
	}

	
	private static void PPeachWeekIndicvalue(WeekStockdata stockdata, Connection con1) throws SQLException {
		// TODO Auto-generated method stub
		Long OBVVOL = stockdata.getTradevolume();
		Float WMA50CLOSE = (float) 0;
		Float WMA50HIGH=(float) 0;
		Float WMA50LOW=(float) 0;
		Float lowdiff = (float) 0;
		Float highdiff = (float) 0;
		try {
			OBVVOL = getWeekOBVVOL(stockdata,con1);
			WMA50CLOSE = getWeeklyWMA50CLOSE(stockdata,con1);
			WMA50HIGH = getWeeklyWMA50HIGH(stockdata,con1);
			WMA50LOW =  getWeeklyWMA50LOW(stockdata,con1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		lowdiff = (float) (stockdata.getLowprice() - WMA50LOW);
		highdiff = (float) (stockdata.getHighprice() - WMA50HIGH);
		
		
		PreparedStatement stmt1 = null;
		
		stmt1 = con1.prepareStatement("delete from WEEKLYINDICDATA where stocksymbol = ? and tradeyr= ? and tradeweek = ?" );
		
		stmt1.setString(1, stockdata.getStocksymbol());
		stmt1.setString(2, stockdata.getTradeyr());
    	stmt1.setString(3, stockdata.getTradeweek());
    	stmt1.executeUpdate();
		stmt1.close();
		
PreparedStatement stmt = null;
		
		stmt = con1.prepareStatement("insert into WEEKLYINDICDATA values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		
    	
    	stmt.setDate(1, new java.sql.Date(stockdata.getTradedate().getTime()));
    	stmt.setString(2, stockdata.getStocksymbol());
    	stmt.setFloat(3, (float) stockdata.getOpenprice());
    	stmt.setFloat(4, (float) stockdata.getHighprice());
    	stmt.setFloat(5, (float) stockdata.getLowprice());
    	stmt.setFloat(6, (float) stockdata.getCloseprice());
    	stmt.setString(7,Long.toString(stockdata.getTradevolume()));
    	stmt.setString(8,Long.toString(OBVVOL));
    	stmt.setFloat(9, (float) WMA50CLOSE);
    	stmt.setFloat(10, (float) WMA50HIGH);
    	stmt.setFloat(11, (float) WMA50LOW);
    	stmt.setFloat(12, (float) lowdiff);
    	stmt.setFloat(13, (float) highdiff);
    	stmt.setString(14, stockdata.getTradeyr());
    	stmt.setString(15, stockdata.getTradeweek());
    	
    	stmt.executeUpdate();  
		stmt.close();
		
		
	}
	
	private static Float getWeeklyWMA50CLOSE(WeekStockdata stockdata, Connection con1) throws SQLException {
		// TODO Auto-generated method stub
		Float finalval=(float) 0;
		DecimalFormat df = new DecimalFormat("#.##"); 
		
		PreparedStatement  stmt = con1.prepareStatement("select * from (select WEEKCLOSE from WEEK_STATS where stocksymbol= ? and to_number(tradeyr||tradeweek) < to_number(?) order by enddate desc) where rownum < 51", ResultSet.TYPE_SCROLL_SENSITIVE, 
                ResultSet.CONCUR_UPDATABLE);
		
		stmt.setString(1, stockdata.getStocksymbol());
		stmt.setString(2, stockdata.getTradeyr()+stockdata.getTradeweek());
		
		ResultSet rs1 = stmt.executeQuery();
		Float i=(float) 50;
		
		rs1.last();
		int rows = rs1.getRow();
		if(rows == 50)
		{
		rs1.beforeFirst();
		
		while(rs1.next())
		{
			Float data = (float) rs1.getDouble(1);
            Float val = (i/1275)*data;
			
			finalval = finalval + val ;
			i--;
		}
		}
		else
			finalval = (float) stockdata.getCloseprice();
		
		rs1.close();
		stmt.close();
		return finalval;
	}

	private static Long getWeekOBVVOL(WeekStockdata stockdata, Connection con1) throws SQLException {
		// TODO Auto-generated method stub
		Long Finalvol;
		Statement stmt = con1.createStatement();
		ResultSet rs1 = stmt.executeQuery("select * from (select LASTPRICE,OBVVOLUME from WEEKLYINDICDATA where to_number(tradeyr||tradeweek) < to_number('"+stockdata.getTradeyr()+stockdata.getTradeweek()+"') and STOCKSYMBOL='"+stockdata.getStocksymbol()+"' order by tradedate desc) where rownum <2");
		if(rs1.next())
		{
			double prevclose =rs1.getDouble(1);
			Long Lastobvval = rs1.getLong(2);
			double range = 	(stockdata.getHighprice()+stockdata.getLowprice())/2;
			
			if (stockdata.getCloseprice() >= prevclose)
			{
				if (stockdata.getCloseprice() >= range)
					Finalvol= (Lastobvval+stockdata.getTradevolume());
				else
					Finalvol= Lastobvval;
					
			}
			else
			{
				if (stockdata.getCloseprice() >= range)
					Finalvol= Lastobvval;
	                 
					else
						
						Finalvol= (Lastobvval-stockdata.getTradevolume());
			}
		}
		else
		Finalvol= stockdata.getTradevolume();
		
		rs1.close();
		stmt.close();
		return Finalvol;
	}

	private static Float getWeeklyWMA50LOW(WeekStockdata stockdata, Connection con1) throws SQLException {
		// TODO Auto-generated method stub
		Float finalval=(float) 0;
		DecimalFormat df = new DecimalFormat("#.##"); 
		
		PreparedStatement  stmt = con1.prepareStatement("select * from (select WEEKLOWLOW from WEEK_STATS where stocksymbol= ? and to_number(tradeyr||tradeweek) < to_number(?) order by enddate desc) where rownum < 51", ResultSet.TYPE_SCROLL_SENSITIVE, 
                ResultSet.CONCUR_UPDATABLE);
		
		stmt.setString(1, stockdata.getStocksymbol());
		stmt.setString(2, stockdata.getTradeyr()+stockdata.getTradeweek());
		
		ResultSet rs1 = stmt.executeQuery();
		Float i=(float) 50;
		
		rs1.last();
		int rows = rs1.getRow();
		if(rows == 50)
		{
		rs1.beforeFirst();
		
		while(rs1.next())
		{
			Float data = (float) rs1.getDouble(1);
            Float val = (i/1275)*data;
			
			finalval = finalval + val ;
			i--;
		}
		}
		else
			finalval = (float) stockdata.getLowprice();
		
		rs1.close();
		stmt.close();
		return finalval;
	}

	private static Float getWeeklyWMA50HIGH(WeekStockdata stockdata, Connection con1) throws SQLException {
		// TODO Auto-generated method stub
		Float finalval=(float) 0;
		DecimalFormat df = new DecimalFormat("#.##"); 
		
		PreparedStatement  stmt = con1.prepareStatement("select * from (select WEEKHIGHHIGH from WEEK_STATS where stocksymbol= ? and to_number(tradeyr||tradeweek) < to_number(?) order by enddate desc) where rownum < 51", ResultSet.TYPE_SCROLL_SENSITIVE, 
                ResultSet.CONCUR_UPDATABLE);
		
		stmt.setString(1, stockdata.getStocksymbol());
		stmt.setString(2, stockdata.getTradeyr()+stockdata.getTradeweek());
		
		ResultSet rs1 = stmt.executeQuery();
		Float i=(float) 50;
		
		rs1.last();
		int rows = rs1.getRow();
		if(rows == 50)
		{
		rs1.beforeFirst();
		
		while(rs1.next())
		{
			Float data = (float) rs1.getDouble(1);
            Float val = (i/1275)*data;
			
			finalval = finalval + val ;
			i--;
		}
		}
		else
			finalval = (float) stockdata.getHighprice();
		
		rs1.close();
		stmt.close();
		return finalval;
	}

	


	private static void PPeachdayIndicvalue(Stockdata stockdata, Connection con1) throws SQLException {
		// TODO Auto-generated method stub
		Long OBVVOL = stockdata.getTradevolume();
		Float WMA50CLOSE = (float) 0;
		Float WMA50HIGH=(float) 0;
		Float WMA50LOW=(float) 0;
		Float lowdiff = (float) 0;
		Float highdiff = (float) 0;
		try {
			OBVVOL = getOBVVOL(stockdata,con1);
			WMA50CLOSE = getDailyWMA50CLOSE(stockdata,con1);
			WMA50HIGH = getDailyWMA50HIGH(stockdata,con1);
			WMA50LOW =  getDailyWMA50LOW(stockdata,con1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		lowdiff = (float) (stockdata.getLowprice() - WMA50LOW);
		highdiff = (float) (stockdata.getHighprice() - WMA50HIGH);
		 
		
		PreparedStatement stmt = null;
		
		stmt = con1.prepareStatement("insert into Dailyindicdata values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
		
    	
    	stmt.setDate(1, new java.sql.Date(stockdata.getTradedate().getTime()));
    	stmt.setString(2, stockdata.getStocksymbol());
    	stmt.setFloat(3, (float) stockdata.getOpenprice());
    	stmt.setFloat(4, (float) stockdata.getHighprice());
    	stmt.setFloat(5, (float) stockdata.getLowprice());
    	stmt.setFloat(6, (float) stockdata.getCloseprice());
    	stmt.setString(7,Long.toString(stockdata.getTradevolume()));
    	stmt.setString(8,Long.toString(OBVVOL));
    	stmt.setFloat(9, (float) WMA50CLOSE);
    	stmt.setFloat(10, (float) WMA50HIGH);
    	stmt.setFloat(11, (float) WMA50LOW);
    	stmt.setFloat(12, (float) lowdiff);
    	stmt.setFloat(13, (float) highdiff);
    	
    	stmt.executeUpdate();  
		stmt.close();
	}

	private static Float getDailyWMA50LOW(Stockdata stockdata, Connection con1) throws SQLException {
		// TODO Auto-generated method stub
		Float finalval=(float) 0;
		DecimalFormat df = new DecimalFormat("#.##"); 
		
		PreparedStatement  stmt = con1.prepareStatement("select * from (select lowprice from stockprice where stocksymbol= ? order by tradedate desc) where rownum < 51", ResultSet.TYPE_SCROLL_SENSITIVE, 
                ResultSet.CONCUR_UPDATABLE);
		
		stmt.setString(1, stockdata.getStocksymbol());
		
		
		ResultSet rs1 = stmt.executeQuery();
		Float i=(float) 50;
		
		rs1.last();
		int rows = rs1.getRow();
		if(rows == 50)
		{
		rs1.beforeFirst();
		
		while(rs1.next())
		{
			Float data = (float) rs1.getDouble(1);
            Float val = (i/1275)*data;
			
			finalval = finalval + val ;
			i--;
		}
		}
		else
			finalval = (float) stockdata.getLowprice();
		
		rs1.close();
		stmt.close();
		return finalval;
	}

	private static Float getDailyWMA50HIGH(Stockdata stockdata, Connection con1) throws SQLException {
		// TODO Auto-generated method stub
		Float finalval=(float) 0;
		DecimalFormat df = new DecimalFormat("#.##"); 
		
		PreparedStatement  stmt = con1.prepareStatement("select * from (select highprice from stockprice where stocksymbol= ? order by tradedate desc) where rownum < 51", ResultSet.TYPE_SCROLL_SENSITIVE, 
                ResultSet.CONCUR_UPDATABLE);
		
		stmt.setString(1, stockdata.getStocksymbol());
		
		
		ResultSet rs1 = stmt.executeQuery();
		Float i=(float) 50;
		
		rs1.last();
		int rows = rs1.getRow();
		if(rows == 50)
		{
		rs1.beforeFirst();
		
		while(rs1.next())
		{
			Float data = (float) rs1.getDouble(1);
            Float val = (i/1275)*data;
			
			finalval = finalval + val ;
			i--;
		}
		}
		else
			finalval = (float) stockdata.getHighprice();
		
		rs1.close();
		stmt.close();
		return finalval;
	}

	private static Float getDailyWMA50CLOSE(Stockdata stockdata, Connection con1) throws SQLException {
		// TODO Auto-generated method stub
		Float finalval=(float) 0;
		DecimalFormat df = new DecimalFormat("#.##"); 
		
		PreparedStatement  stmt = con1.prepareStatement("select * from (select closeprice from stockprice where stocksymbol= ? order by tradedate desc) where rownum < 51", ResultSet.TYPE_SCROLL_SENSITIVE, 
                ResultSet.CONCUR_UPDATABLE);
		
		stmt.setString(1, stockdata.getStocksymbol());
		
		
		ResultSet rs1 = stmt.executeQuery();
		Float i=(float) 50;
		
		rs1.last();
		int rows = rs1.getRow();
		if(rows == 50)
		{
		rs1.beforeFirst();
		
		while(rs1.next())
		{
			Float data = (float) rs1.getDouble(1);
            Float val = (i/1275)*data;
			
			finalval = finalval + val ;
			i--;
		}
		}
		else
			finalval = (float) stockdata.getCloseprice();
		
		rs1.close();
		stmt.close();
		return finalval;
	}

	private static Long getOBVVOL(Stockdata stockdata, Connection con1) throws SQLException {
		// TODO Auto-generated method stub
		Long Finalvol;
		Statement stmt = con1.createStatement();
		ResultSet rs1 = stmt.executeQuery("select * from (select LASTPRICE,OBVVOLUME from Dailyindicdata where tradedate < (select max(tradedate) from stockprice) and STOCKSYMBOL='"+stockdata.getStocksymbol()+"' order by tradedate desc) where rownum <2");
		if(rs1.next())
		{
			double prevclose =rs1.getDouble(1);
			Long Lastobvval = rs1.getLong(2);
			double range = 	(stockdata.getHighprice()+stockdata.getLowprice())/2;
			
			if (stockdata.getCloseprice() >= prevclose)
			{
				if (stockdata.getCloseprice() >= range)
					Finalvol= (Lastobvval+stockdata.getTradevolume());
				else
					Finalvol= Lastobvval;
					
			}
			else
			{
				if (stockdata.getCloseprice() >= range)
					Finalvol= Lastobvval;
	                 
					else
						
						Finalvol= (Lastobvval-stockdata.getTradevolume());
			}
		}
		else
		Finalvol= stockdata.getTradevolume();
		
		rs1.close();
		stmt.close();
		return Finalvol;
		
	}

	public HashMap<String, HistoricHighLow> startdbinsert(String string, HashMap<String, HistoricHighLow> histHL) {
		// TODO Auto-generated method stub
		Dataconn dataconn = new Dataconn();
		if (string.endsWith(".txt") && string.contains("EQ"))
		 {
		 try {
			try {
				return insertdata(string,dataconn,histHL);
			} catch (IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 }
		return null;
	}

}
