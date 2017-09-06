package com.mr.realtimedata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.mr.data.StochasticFinalval;

import datainsert.Dataconn;

public class DataDownload {

	private  String datas[];
	public  HashMap<String,Pricedata> Currpricemap = new HashMap<String,Pricedata>();
	public  HashMap<String,Pricedata> Prevpricemap = new HashMap<String,Pricedata>();
    public  Date Currentime;
 
    
	public HashMap<String, Pricedata> getCurrpricemap() {
		return Currpricemap;
	}

	public HashMap<String, Pricedata> getPrevpricemap() {
		return Prevpricemap;
	}

	public Date getCurrentime() {
		return Currentime;
	}

	

	
	public  void update() throws MalformedURLException {
		// TODO Auto-generated method stub
		 Currentime = new Date();
		URL tdlink = new URL("http://www.traderscockpit.com/?pageView=nse-indices-stock-watch&index=NIFTY+500");
		URLConnection conn1 =null;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
		
			System.out.println("lastrun: " + new Date());
		try {
			conn1 = tdlink.openConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//System.out.println("Processing :" + string);
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(conn1.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		        StringBuilder response = new StringBuilder();
		        String newLine = System.getProperty("line.separator");
		        
		        String inputLine;
		        try {
					while ((inputLine = in.readLine()) != null)
						response.append(inputLine + newLine);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        inputLine = response.toString();

		        try {
		        	String dt = inputLine.substring(inputLine.indexOf("Last Updated On:")+17,inputLine.indexOf("Last Updated On:")+36);
		        	System.out.println(dt+"::"+dt.length());
					 Currentime = df.parse(inputLine.substring(inputLine.indexOf("Last Updated On:")+17,inputLine.indexOf("Last Updated On:")+36));
				    System.out.println(Currentime);
		        } catch (ParseException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
		        inputLine = (inputLine.substring(inputLine.indexOf("<table class=\"greenTable\">"),inputLine.length()));
		        //System.out.println(inputLine.substring(0,inputLine.indexOf("<script type=\"text/javascript\">")));
		        datas = inputLine.split("</tr>");
		       
		        
		        for (int i=0; i< datas.length;i++)
		        {
		        	if (datas[i].isEmpty())
                     continue;
		        	if (datas[i].contains("<tr class=\"odd\">") || datas[i].contains("<tr class=\"even\">"))
		        	{
		        		parseeachdata(datas[i],Currentime);
		        	}
		        		

		        }
		        
		        System.out.println("completed");
		        
		        	/* System.out.println("Sleeping for 10 seconds");
		        	 
		        	 System.out.println(Currpricemap.size()+":"+Currpricemap.get("ITC").getTradedate());
				        System.out.print(Prevpricemap.size());
				        if(!Prevpricemap.isEmpty())
				        	System.out.println(Prevpricemap.get("ITC").getTradedate());
				        else
				        	System.out.println(":null");
				        
				        */
				        
					
				
		        System.out.println("===============");
		     
		
		        
	}

	private  void parseeachdata(String string, Date currentime) {
		// TODO Auto-generated method stub
		String stocksymbol = null,Lastprice = null,Open = null,High = null,Low = null,Volume = null;
		String segments[] = string.split("</td>");
		for (int i=0 ; i< segments.length;i++)
		{
			if(segments[i].isEmpty())
			continue;
			segments[i]= segments[i].replaceAll(" ","");
			segments[i]= segments[i].replaceAll("\r\n","");
			if(segments[i].startsWith("<trclass")) // stocksymbol
			{
				stocksymbol = segments[i].substring(segments[i].indexOf("symbolFilter=")+13,segments[i].indexOf("\"><imgsrc="));
			}
			if(segments[i].startsWith("<td>")) // stockdata
			{
				String val = segments[i].replaceAll("<td>","");
					// Lastprice,Open,High,Low,Volume
				switch(i)
				{
				  case 1 :    Lastprice = val;       //ltp
				    break;
				  case 3:  Open = val; //open
					  break;
				  case 4:  High=val;// high
					  break;
				case 5:  Low=val;// low
					break;
				case 7: Volume=val;//volume
					break;
					
			 
				
				}
			}
		}
		//System.out.println("Sym: "+stocksymbol);
		try {
			
			createhashmap(stocksymbol.replace("%26", "&"),Lastprice,Open,High,Low,Volume,currentime);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private  void createhashmap(String stocksymbol, String lastprice, String open, String high,
			String low, String volume, Date currentime) {
		// TODO Auto-generated method stub
		Pricedata pd = new Pricedata(stocksymbol,Double.valueOf(lastprice),Double.valueOf(open),Double.valueOf(high),Double.valueOf(low),Long.parseLong(volume),currentime);
		if (!Currpricemap.containsKey(stocksymbol))
		{
			Currpricemap.put(stocksymbol, pd);
		}
		else
		{
			Pricedata oldpd=Currpricemap.get(stocksymbol);
			Pricedata currpd = new Pricedata(oldpd.getStocksymbol(),oldpd.getLastprice(),oldpd.getOpenprice(),oldpd.getHighprice(),oldpd.getLowprice(),oldpd.getTradevolume(),oldpd.getTradedate());
			Prevpricemap.put(stocksymbol, currpd);
			Currpricemap.put(stocksymbol, pd);
		}
			
	}

	public HashMap <String,StochasticFinalval> getlivestochastic() throws SQLException {
		// TODO Auto-generated method stub
		HashMap <String,StochasticFinalval> finalmap = new HashMap <String,StochasticFinalval> ();
		Dataconn dataconn = new Dataconn();
		Connection conn = dataconn.getconn();
		Iterator it = this.getCurrpricemap().entrySet().iterator();
		//62-1213 combination
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			Pricedata curr = (Pricedata) pair.getValue();
			PreparedStatement  stmt = conn.prepareStatement("select * from (select openprice,highprice,lowprice,lastprice,OBVVOLUME,WMA50_HIGH,WMA_LOW,WMALOW_DIFF,WMAHIGH_DIFF from DAILYINDICDATA where STOCKSYMBOL= ? and to_char(tradedate,'YYYYMMDD') < to_char(?,'YYYYMMDD') order by tradedate desc) where ROWNUM < 14", ResultSet.TYPE_SCROLL_SENSITIVE, 
	                ResultSet.CONCUR_UPDATABLE);
			
			stmt.setString(1, curr.getStocksymbol());
			stmt.setDate(2, new java.sql.Date(curr.getTradedate().getTime()));
			ResultSet rs1 = stmt.executeQuery();
			
			Float CurWMA50_high=0f;
			Float CurWMA50_low=0f;
			Long CurOBVVOL =0L;
			Float CurLowdiff = 0f;
			Float CurHighdiff = 0f;
			
			Float Lowdiff_low=0f;
			Float Lowdiff_high=0f;
			
			Float Highdiff_low=0f;
			Float Highdiff_high=0f;
			
			Float Price_low=0f;
			Float Price_high=0f;
			
			Long Obv_low = 0L;
			Long Obv_high=0L;
			
			boolean initial = true;
			while (rs1.next())
			{
				if (initial)
				{
					if (curr.getLowprice() < rs1.getDouble(3))
					{
						Price_low = (float) curr.getLowprice();
								Price_high = (float) rs1.getDouble(3);
					}
					else
					{
						Price_high = (float) curr.getLowprice();
						Price_low = (float) rs1.getDouble(3);
					}
					
					
					CurOBVVOL = getObvvol (curr.getLastprice(),rs1.getDouble(4),rs1.getLong(5),curr.getTradevolume(),(curr.getHighprice()+curr.getLowprice())/2);
					
					if (CurOBVVOL < rs1.getLong(5))
					{
						Obv_low = CurOBVVOL;
						Obv_high = 	rs1.getLong(5);	
					}
					else
					{
						Obv_high = CurOBVVOL;
						Obv_low = 	rs1.getLong(5);
					}
					
					CurWMA50_high = (float) (((curr.getHighprice()*62)/1275)+ ((rs1.getDouble(6)*1213)/1275));
					CurWMA50_low = (float) (((curr.getLowprice()*62)/1275)+ ((rs1.getDouble(7)*1213)/1275));
					CurLowdiff = (float) (curr.getLowprice() - CurWMA50_low);
					CurHighdiff = (float) (curr.getHighprice() - CurWMA50_high);
					
				
					
					if (CurLowdiff < rs1.getDouble(8))
					{
						Lowdiff_low = CurLowdiff;
						Lowdiff_high = (float) rs1.getDouble(8);		
					}
					else
					{
						Lowdiff_high = CurLowdiff;
						Lowdiff_low = (float) rs1.getDouble(8);	
					}
					
					if (CurHighdiff < rs1.getDouble(9))
					{
						Highdiff_low = CurHighdiff;
						Highdiff_high = (float) rs1.getDouble(9);		
					}
					else
					{
						Highdiff_high = CurHighdiff;
						Highdiff_low = (float) rs1.getDouble(9);	
					}
					
					
					
					
					
					
					initial = false;
				}
				else
				{
					if (Price_low > rs1.getDouble(3))
					{
						Price_low = (float) rs1.getDouble(3);
							
					}
					
					if (Price_high < rs1.getDouble(3))
					{
						Price_high = (float) rs1.getDouble(3);
							
					}
					
					
					
					if (Obv_low > rs1.getLong(5))
					{
						Obv_low = rs1.getLong(5);
						
					}
					
					if (Obv_high < rs1.getLong(5))
					{
						Obv_high= rs1.getLong(5);
						
					}
					
					if (Lowdiff_low > rs1.getDouble(8))
					{
						Lowdiff_low = (float) rs1.getDouble(8);
								
					}
					
					if (Lowdiff_high < rs1.getDouble(8))
					{
						Lowdiff_high = (float) rs1.getDouble(8);
								
					}
					
					if (Highdiff_low > rs1.getDouble(9))
					{
						Highdiff_low = (float) rs1.getDouble(9);
								
					}
					
					if (Highdiff_high < rs1.getDouble(9))
					{
						Highdiff_high = (float) rs1.getDouble(9);
								
					}
					
					
					
					
					
				}
			}
			rs1.close();
			stmt.close();
			// all data found for stochastics.
			
			double lowdiffstoch = 0;
			double highdiffstoch = 0;
			double obvstoch = 0;
			double pricestoch = 0;
			
			/*
			Long CurOBVVOL =0L;
			Float CurLowdiff = 0f;
			Float CurHighdiff = 0f;
			
			*/
			
			obvstoch = getstochasticvalue(CurOBVVOL,Obv_low,Obv_high);
			lowdiffstoch = getstochasticvalue(CurLowdiff,Lowdiff_low,Lowdiff_high);
			highdiffstoch = getstochasticvalue(CurHighdiff,Highdiff_low,Highdiff_high);
			pricestoch =  getstochasticvalue((float) curr.getLowprice(),Price_low,Price_high);
			
			
			StochasticFinalval sfv = new StochasticFinalval();
			sfv = getfinalstoch(curr,obvstoch,lowdiffstoch,highdiffstoch,pricestoch,conn);
		
			finalmap.put((String) pair.getKey(), sfv);
		}
		dataconn.closeconn();
		return finalmap;
		
	}

	private StochasticFinalval getfinalstoch(Pricedata curr, double obvstoch, double lowdiffstoch, double highdiffstoch,
			double pricestoch, Connection conn) throws SQLException {
		
		
		Float WMAlowdiffstoch = (float) (lowdiffstoch*((float)3/(float)6));
		Float WMAhighdiffstoch = (float) (highdiffstoch*((float)3/(float)6));
		Float WMApricestoch = (float)(pricestoch*((float)5/(float)15));
		Float WMAobvvol = (float) (obvstoch*((float)3/(float)6));
		
		PreparedStatement stmt = conn.prepareStatement("select * from ((select lowdiffstoch,highdiffstoch,obvstoch from DAILYSTOCHASTICDATA where stocksymbol= ? and to_char(tradedate,'YYYYMMDD') < to_char(?,'YYYYMMDD') order by tradedate desc)) where rownum < 3", ResultSet.TYPE_SCROLL_SENSITIVE, 
                ResultSet.CONCUR_UPDATABLE);
		stmt.setString(1,curr.getStocksymbol());
		stmt.setDate(2, new java.sql.Date(curr.getTradedate().getTime()));
		ResultSet rs1 = stmt.executeQuery();
		Float i = (float) 2;
		while (rs1.next())
		{
			WMAlowdiffstoch = (float) (WMAlowdiffstoch + ((i/6)*rs1.getDouble(1)));
			WMAhighdiffstoch = (float) (WMAhighdiffstoch + ((i/6)*rs1.getDouble(2)));
			WMAobvvol = (float) (WMAobvvol + ((i/6)*rs1.getDouble(3)));
			i--;
			
		}
		rs1.close();
		stmt.close();
		
		
		PreparedStatement stmt1 = conn.prepareStatement("select * from ((select pricestoch from DAILYSTOCHASTICDATA where stocksymbol= ? and to_char(tradedate,'YYYYMMDD') < to_char(?,'YYYYMMDD') order by tradedate desc)) where rownum < 5", ResultSet.TYPE_SCROLL_SENSITIVE, 
                ResultSet.CONCUR_UPDATABLE);
		stmt1.setString(1,curr.getStocksymbol());
		stmt1.setDate(2, new java.sql.Date(curr.getTradedate().getTime()));
		
		ResultSet rs2 = stmt1.executeQuery();
		
		Float j = (float) 4;
		while (rs2.next())
		{
			
			WMApricestoch = (float) (WMApricestoch + ((j/15)*rs2.getDouble(1)));
			j--;
			
		}
		
		rs2.close();
		stmt1.close();
		
		/*
		 
		 Float WMAlowdiffstoch = (float) (lowdiffstoch*(3/6));
		Float WMAhighdiffstoch = (float) (highdiffstoch*(3/6));
		Float WMApricestoch = (float)(pricestoch*(5/15));
		Float WMAobvvol = (float) (obvstoch*(3/6));
		
		 */
		double val = (lowdiffstoch+highdiffstoch+pricestoch+WMAlowdiffstoch+WMAhighdiffstoch+WMApricestoch)/6;
		double finalk = (pricestoch+obvstoch)/2;
		double finald = (val+WMAobvvol)/2;		
		

		StochasticFinalval sfv = new StochasticFinalval();
		sfv.setTradedate(curr.getTradedate());
		sfv.setStocksymbol(curr.getStocksymbol());
		sfv.setPrecentk(finalk);
		sfv.setPercentd(finald);
		
	//	System.out.println(sfv.getStocksymbol()+"::"+sfv.getTradedate()+"::"+sfv.getPrecentk()+"::"+sfv.getPercentd());
		return sfv;
		
	}

	private double getstochasticvalue(Float currentval, Float LOW, Float HIGH) {
		// TODO Auto-generated method stub
		DecimalFormat df = new DecimalFormat("#.##"); 
		double stochval = ((currentval - LOW)/(HIGH-LOW))*100;
		if(Double.isNaN(stochval) || Double.isInfinite(stochval))
			stochval= 0;
		
		return Double.valueOf(df.format(stochval));
	}

	private double getstochasticvalue(Long currentval, Long LOW, Long HIGH) {
		// TODO Auto-generated method stub
		DecimalFormat df = new DecimalFormat("#.##"); 
		Float stochval = (float) ((currentval - LOW)*100L)/(HIGH-LOW);
		if(Double.isNaN(stochval) || Double.isInfinite(stochval))
			stochval= (float) 0;
		
		return Double.valueOf(df.format(stochval));
	}

	private Long getObvvol(double currprice, double eodprice, long eodobv,long currvol,double range) {
		// TODO Auto-generated method stub
		Long result = currvol;
		
		
		if (currprice >= eodprice)
		{
			if (currprice >= range)
				result= eodobv + currvol;
			else
				result= eodobv;
				
		}
		else
		{
			if (currprice >= range)
				result= eodobv;
                 
				else
					
					result= eodobv - currvol;
		}
		
		
		
		return result;
	}

}
