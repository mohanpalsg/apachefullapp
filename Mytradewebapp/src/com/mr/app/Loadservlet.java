package com.mr.app;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mr.data.Daypivot;
import com.mr.data.Daysma50;
import com.mr.data.Filteredsmapivotdata;
import com.mr.data.Stocktwodaystat;
import com.mr.data.Stocktwoweekstat;
import com.mr.data.Weekpivot;
import com.mr.data.Weeksma50;




/**
 * Servlet implementation class Loadservlet
 */
@WebServlet("/Loadservlet")
public class Loadservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Loadservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//req.setAttribute("WeekSM50Pv",login);  //stockcount closing 
		//req.setAttribute("daySM50Pv",login); // stockcount day
		//req.setAttribute("protfoliopercent",login); // bought stock
		
		
		ServletContext ctx = getServletContext(); 
		DecimalFormat df = new DecimalFormat("#.##"); 
    
		
       
		// Look up our data source
		

		// Allocate and use a connection from the pool
		
		
		//... use this connection to access the database ...
		HashMap <String,Stocktwoweekstat> statsmap = (HashMap <String,Stocktwoweekstat>) getServletContext().getAttribute("Stocktwoweekstats");
		HashMap <String,Weeksma50> sma50map = (HashMap<String, Weeksma50>) getServletContext().getAttribute("weeksma50");
		HashMap <String,Weekpivot> weekpivotmap = (HashMap<String, Weekpivot>) getServletContext().getAttribute("Weekpivot");
		HashMap <String,String> nse = (HashMap <String,String>) getServletContext().getAttribute("nse200");
		Iterator it = statsmap.entrySet().iterator();
		HashMap <String,Filteredsmapivotdata> sm50pvtbreak = new HashMap <String,Filteredsmapivotdata>();
	    while (it.hasNext()) {
	    	String level = "nothing";
	    	double levelprice = 0;
	        Map.Entry pair = (Map.Entry)it.next();
	        String stocksymbol = (String) pair.getKey();
	        if (sma50map.containsKey(stocksymbol)&& weekpivotmap.containsKey(stocksymbol))
	        {
	        	Stocktwoweekstat sw2 = (Stocktwoweekstat) pair.getValue();
	        	Weeksma50 sma50 = sma50map.get(stocksymbol);
	        	Weekpivot weekpv = weekpivotmap.get(stocksymbol);
	        	if(sw2.getPREV_CLOSE() <= sma50.getMovingavg() && sw2.getCURR_CLOSE() >= sma50.getMovingavg() )
	        	{
	        		//sma50 break add
	        		if (sw2.getPREV_LOW() <= weekpv.getPP() && sw2.getCURR_CLOSE() >= weekpv.getPP())
	        		{
	        			level="PP";
	        			levelprice=weekpv.getPP();
	        		}
	        		else if (sw2.getPREV_LOW() <= weekpv.getS1() && sw2.getCURR_CLOSE() >= weekpv.getS1())
	        		{
	        			level="S1";
	        			levelprice=weekpv.getS1();
	        		}
	        		else if (sw2.getPREV_LOW() <= weekpv.getS2() && sw2.getCURR_CLOSE() >= weekpv.getS2())
	        		{
	        			level="S2";
	        			levelprice=weekpv.getS2();
	        		}
	        		else if (sw2.getPREV_LOW() <= weekpv.getS3() && sw2.getCURR_CLOSE() >= weekpv.getS3())
	        		{
	        			level="S3";
	        			levelprice=weekpv.getS3();
	        		}
	        		else if (sw2.getPREV_LOW() <= weekpv.getS4() && sw2.getCURR_CLOSE() >= weekpv.getS4())
	        		{
	        			level="S4";
	        			levelprice=weekpv.getS4();
	        		}
	        		else if (sw2.getPREV_LOW() <= weekpv.getR1() && sw2.getCURR_CLOSE() >= weekpv.getR1())
	        		{
	        			level="R1";
	        			levelprice=weekpv.getR1();
	        		}
	        		else if (sw2.getPREV_LOW() <= weekpv.getR2() && sw2.getCURR_CLOSE() >= weekpv.getR2())
	        		{
	        			level="R2";
	        			levelprice=weekpv.getR2();
	        		}
	        		else if (sw2.getPREV_LOW() <= weekpv.getR3() && sw2.getCURR_CLOSE() >= weekpv.getR3())
	        		{
	        			level="R3";
	        			levelprice=weekpv.getR3();
	        		}
	        		else if (sw2.getPREV_LOW() <= weekpv.getR4() && sw2.getCURR_CLOSE() >= weekpv.getR4())
	        		{
	        			level="R4";
	        			levelprice=weekpv.getR4();
	        		}
	        		
	        		if(level.contains("nothing"))
	        		{
	        			
	        		}
	        		else
	        		{
	        			Filteredsmapivotdata filterdata = new Filteredsmapivotdata();
	        			filterdata.setStocksymbol(stocksymbol);
	        			filterdata.setCurrclose(sw2.getCURR_CLOSE());
	        			filterdata.setPrevclose(sw2.getPREV_CLOSE());
	        			filterdata.setPivotlevel(level);
	        			filterdata.setPivotvalue(Double.valueOf(df.format(levelprice)));
	        			filterdata.setSmavalue(Double.valueOf(df.format(sma50.getMovingavg())));
	        			filterdata.setTradedate(sw2.getCURR_ENDATE());
	        			filterdata.setTradevolume(sw2.getCURR_TRADEVOLUME());
	        			filterdata.setSmadiff(Double.valueOf(df.format(((sw2.getCURR_CLOSE()-sma50.getMovingavg())/sw2.getCURR_CLOSE())*100)));
	        			filterdata.setPivotdiff(Double.valueOf(df.format(((sw2.getCURR_CLOSE()-levelprice)/sw2.getCURR_CLOSE())*100)));
	        			if(nse.containsKey(stocksymbol))
	        			filterdata.setNse200(true);
	        			else
	        			filterdata.setNse200(false);	
	        			sm50pvtbreak.put(stocksymbol,filterdata);
	        		}
	        	}
	        }
	        
	    }
	     
	    ctx.setAttribute("Weeksma50pvbreaklist", sm50pvtbreak);    
		request.setAttribute("WeekSM50Pvsize",sm50pvtbreak.size() );
		
		
		// day break list
		
		HashMap <String,Stocktwodaystat> daystatsmap = (HashMap <String,Stocktwodaystat>) getServletContext().getAttribute("StockDaytwodaystats");
		HashMap <String,Daysma50> daysma50map = (HashMap<String, Daysma50>) getServletContext().getAttribute("Daysma50");
		HashMap <String,Daypivot> daypivotmap = (HashMap<String, Daypivot>) getServletContext().getAttribute("Daypivot");

		Iterator it1 = daystatsmap.entrySet().iterator();
		HashMap <String,Filteredsmapivotdata> daysm50pvtbreak = new HashMap <String,Filteredsmapivotdata>();
	    while (it1.hasNext()) {
	    	String level = "nothing";
	    	double levelprice = 0;
	        Map.Entry pair = (Map.Entry)it1.next();
	        String stocksymbol = (String) pair.getKey();
	        if (daysma50map.containsKey(stocksymbol)&& daypivotmap.containsKey(stocksymbol))
	        {
	        	Stocktwodaystat sw2 = (Stocktwodaystat) pair.getValue();
	        	Daysma50 sma50 = daysma50map.get(stocksymbol);
	        	Daypivot weekpv = daypivotmap.get(stocksymbol);
	        	if(sw2.getPREV_CLOSE() <= sma50.getMovingavg() && sw2.getCURR_CLOSE() >= sma50.getMovingavg() )
	        	{
	        		//sma50 break add
	        		if (sw2.getPREV_LOW() <= weekpv.getPP() && sw2.getCURR_CLOSE() >= weekpv.getPP())
	        		{
	        			level="PP";
	        			levelprice=weekpv.getPP();
	        		}
	        		else if (sw2.getPREV_LOW() <= weekpv.getS1() && sw2.getCURR_CLOSE() >= weekpv.getS1())
	        		{
	        			level="S1";
	        			levelprice=weekpv.getS1();
	        		}
	        		else if (sw2.getPREV_LOW() <= weekpv.getS2() && sw2.getCURR_CLOSE() >= weekpv.getS2())
	        		{
	        			level="S2";
	        			levelprice=weekpv.getS2();
	        		}
	        		else if (sw2.getPREV_LOW() <= weekpv.getS3() && sw2.getCURR_CLOSE() >= weekpv.getS3())
	        		{
	        			level="S3";
	        			levelprice=weekpv.getS3();
	        		}
	        		else if (sw2.getPREV_LOW() <= weekpv.getS4() && sw2.getCURR_CLOSE() >= weekpv.getS4())
	        		{
	        			level="S4";
	        			levelprice=weekpv.getS4();
	        		}
	        		else if (sw2.getPREV_LOW() <= weekpv.getR1() && sw2.getCURR_CLOSE() >= weekpv.getR1())
	        		{
	        			level="R1";
	        			levelprice=weekpv.getR1();
	        		}
	        		else if (sw2.getPREV_LOW() <= weekpv.getR2() && sw2.getCURR_CLOSE() >= weekpv.getR2())
	        		{
	        			level="R2";
	        			levelprice=weekpv.getR2();
	        		}
	        		else if (sw2.getPREV_LOW() <= weekpv.getR3() && sw2.getCURR_CLOSE() >= weekpv.getR3())
	        		{
	        			level="R3";
	        			levelprice=weekpv.getR3();
	        		}
	        		else if (sw2.getPREV_LOW() <= weekpv.getR4() && sw2.getCURR_CLOSE() >= weekpv.getR4())
	        		{
	        			level="R4";
	        			levelprice=weekpv.getR4();
	        		}
	        		
	        		if(level.contains("nothing"))
	        		{
	        			
	        		}
	        		else
	        		{
	        			Filteredsmapivotdata filterdata = new Filteredsmapivotdata();
	        			filterdata.setStocksymbol(stocksymbol);
	        			filterdata.setCurrclose(sw2.getCURR_CLOSE());
	        			filterdata.setPrevclose(sw2.getPREV_CLOSE());
	        			filterdata.setPivotlevel(level);
	        			filterdata.setPivotvalue(Double.valueOf(df.format(levelprice)));
	        			filterdata.setSmavalue(Double.valueOf(df.format(sma50.getMovingavg())));
	        			filterdata.setTradedate(sw2.getCURR_ENDATE());
	        			filterdata.setTradevolume(sw2.getCURR_TRADEVOLUME());
	        			filterdata.setSmadiff(Double.valueOf(df.format(((sw2.getCURR_CLOSE()-sma50.getMovingavg())/sw2.getCURR_CLOSE())*100)));
	        			filterdata.setPivotdiff(Double.valueOf(df.format(((sw2.getCURR_CLOSE()-levelprice)/sw2.getCURR_CLOSE())*100)));
	        			if(nse.containsKey(stocksymbol))
	        			filterdata.setNse200(true);
	        			else
	        			filterdata.setNse200(false);	
	        			daysm50pvtbreak.put(stocksymbol,filterdata);
	        		}
	        	}
	        }
	        
	    }
	     
	    ctx.setAttribute("Daysma50pvbreaklist", daysm50pvtbreak);    
		request.setAttribute("DaySM50Pvsize",daysm50pvtbreak.size() );
		
		// day breaklist end
		request.getRequestDispatcher("./LoginHome/dashboardhome.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
