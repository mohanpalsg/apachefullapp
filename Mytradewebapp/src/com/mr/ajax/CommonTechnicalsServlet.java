package com.mr.ajax;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.jdbc.pool.DataSource;

import com.mr.data.MyindicatorvaluesWeek;
import com.mr.data.StochasticFinalval;
import com.mr.data.StockOtherTechnicals;
import com.mr.data.Stockdata;
import com.mr.data.Stocktwodaystat;
import com.mr.data.Stocktwoweekstat;
import com.mr.data.WeekstochasticFinalval;

/**
 * Servlet implementation class CommonTechnicalsServlet
 */
@WebServlet("/CommonTechnicalsServlet")
public class CommonTechnicalsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommonTechnicalsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	doGet(request, response,false);
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response,boolean changed) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true); 
		
		if(session.getAttribute("CTnse200selection")!=null && !changed)
		{
			 HashMap <String,String> finalstklist = new HashMap <String,String>();
			// use existing object and filter based on other selection
			
			HashMap <String,StockOtherTechnicals> temp = (HashMap <String,StockOtherTechnicals>)session.getAttribute("Othertechnicalsmasterlist");
			HashMap <String,StockOtherTechnicals> finalmap = new HashMap <String,StockOtherTechnicals> ();
			HashMap <String,String> nsemap = (HashMap <String,String>) getServletContext().getAttribute("nse200");
			
		
			String nseselection = (String) session.getAttribute("CTnse200selection");
			String otherselection = (String)session.getAttribute("CTOthersselection");
			String daywprp = (String) session.getAttribute("CTDayWRperiodselection");
			String dayrsip = (String) session.getAttribute("CTDayRSIperiodselection");
			String weekwprp = (String) session.getAttribute("CTWeekWRperiodselection");
			String weekrsip = (String) session.getAttribute("CTWeekRSIperiodselection");
			String rsirange = (String) session.getAttribute("CTRSIrangeselection");
			String wprrange = (String) session.getAttribute("CTWPRrangeselection");
			String skrange = (String) session.getAttribute("CTSKrangeselection");
			String sdrange = (String) session.getAttribute("CTSDrangeselection");
			String prevzeroselection = (String) session.getAttribute("CTPrevkzeroselection");
			String uptrendselection = (String) session.getAttribute("CTUptrendselection");
			String weekprevzeroselection = (String) session.getAttribute("CTweekPrevkzeroselection");
			String weekuptrendselection = (String) session.getAttribute("CTweekUptrendselection");
			
			
			
			
			Iterator it = temp.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
				 String stocksymbol = (String) pair.getKey();
				 StockOtherTechnicals st = (StockOtherTechnicals)pair.getValue();
				 
				
				 if (nseselection.equals("true"))
				 {
					 if(nsemap.containsKey(stocksymbol))
					 {
						 if (  Double.valueOf(st.getDayrsi()) <=  Double.valueOf(rsirange) || Double.valueOf(st.getWeekrsi()) <=  Double.valueOf(rsirange))
							 if (  Double.valueOf(st.getDaywilliamsr()) <=  Double.valueOf(wprrange) || Double.valueOf(st.getWeekwilliamsr()) <=  Double.valueOf(wprrange))
								 if (  Double.valueOf(st.getDayk()) <=  Double.valueOf(skrange) && Double.valueOf(st.getDayd()) <=  Double.valueOf(sdrange))
								 { if (prevzeroselection.equals("true") && uptrendselection.equals("true"))
								 {
									 if (st.getPrevdayk() < 1 && st.getDayd() > st.getPrevdayd())
									 {
										 if (checkweeklyselection(st,weekprevzeroselection,weekuptrendselection))
										 {
									 finalstklist.put(st.getStocksymbol(), st.getStocksymbol());
									 finalmap.put(stocksymbol, st);
										 }
									 }
								 }
								 else if (prevzeroselection.equals("true") && st.getPrevdayk() < 1)
								 {
									 if (checkweeklyselection(st,weekprevzeroselection,weekuptrendselection))
									 {
								 finalstklist.put(st.getStocksymbol(), st.getStocksymbol());
								 finalmap.put(stocksymbol, st);
									 }
								 }
								 else if (uptrendselection.equals("true") && st.getDayd() > st.getPrevdayd())
								 {
									 if (checkweeklyselection(st,weekprevzeroselection,weekuptrendselection))
									 {
									 finalstklist.put(st.getStocksymbol(), st.getStocksymbol());
									 finalmap.put(stocksymbol, st);
									 }
								 }
								 else if (!prevzeroselection.equals("true") && !uptrendselection.equals("true"))
								 {
									 if (checkweeklyselection(st,weekprevzeroselection,weekuptrendselection))
									 {
									 finalstklist.put(st.getStocksymbol(), st.getStocksymbol());
									 finalmap.put(stocksymbol, st);
									 }
								 }}
								
					 }
				 }
				 if (otherselection.equals("true"))
				 {
					 if(!nsemap.containsKey(stocksymbol))
					 {
						 if (  Double.valueOf(st.getDayrsi()) <=  Double.valueOf(rsirange) || Double.valueOf(st.getWeekrsi()) <=  Double.valueOf(rsirange))
							 if (  Double.valueOf(st.getDaywilliamsr()) <=  Double.valueOf(wprrange) || Double.valueOf(st.getWeekwilliamsr()) <=  Double.valueOf(wprrange))
								 if (  Double.valueOf(st.getDayk()) <=  Double.valueOf(skrange) && Double.valueOf(st.getDayd()) <=  Double.valueOf(sdrange))
								 { if (prevzeroselection.equals("true") && uptrendselection.equals("true"))
								 {
									 if (st.getPrevdayk() < 1 && st.getDayd() > st.getPrevdayd())
									 {
										 if (checkweeklyselection(st,weekprevzeroselection,weekuptrendselection))
										 {
									 finalstklist.put(st.getStocksymbol(), st.getStocksymbol());
									 finalmap.put(stocksymbol, st);
										 }
									 }
								 }
								 else if (prevzeroselection.equals("true") && st.getPrevdayk() < 1)
								 {
									 if (checkweeklyselection(st,weekprevzeroselection,weekuptrendselection))
									 {
								 finalstklist.put(st.getStocksymbol(), st.getStocksymbol());
								 finalmap.put(stocksymbol, st);
									 }
								 }
								 else if (uptrendselection.equals("true") && st.getDayd() > st.getPrevdayd())
								 {
									 if (checkweeklyselection(st,weekprevzeroselection,weekuptrendselection))
									 {
									 finalstklist.put(st.getStocksymbol(), st.getStocksymbol());
									 finalmap.put(stocksymbol, st);
									 }
								 }
								 else if (!prevzeroselection.equals("true") && !uptrendselection.equals("true"))
								 {
									 if (checkweeklyselection(st,weekprevzeroselection,weekuptrendselection))
									 {
									 finalstklist.put(st.getStocksymbol(), st.getStocksymbol());
									 finalmap.put(stocksymbol, st);
									 }
								 }}
								
					 }
				 }
			}
			
			session.setAttribute("CommTechEODliveshowfiltered", finalstklist);
			session.setAttribute("EODLiveFilterstock", finalmap);
			request.setAttribute("stocklist", finalmap);
			request.setAttribute("Commontechnse", nseselection);
			request.setAttribute("Commontechothers", otherselection);
			request.setAttribute("Commontechrsirange", rsirange);
			request.setAttribute("Commontechwprrange", wprrange);
			request.setAttribute("Commontechdaywprp", daywprp);
			request.setAttribute("Commontechweekwprp", weekwprp);
			request.setAttribute("Commontechdayrsip", dayrsip);
			request.setAttribute("Commontechweekrsip", weekrsip);
			request.setAttribute("Commontechskrange", skrange);
			request.setAttribute("Commontechsdrange", sdrange);
			
			request.setAttribute("Commontechprevkzero", prevzeroselection);
			request.setAttribute("Commontechuptrend", uptrendselection);
			request.setAttribute("Commontechweekprevkzero", weekprevzeroselection);
			request.setAttribute("Commontechweekuptrend", weekuptrendselection);
			
		}
		else if(session.getAttribute("CTnse200selection")!=null && changed)
		{
			 HashMap <String,String> finalstklist = new HashMap <String,String>();
			//compute set session attribute and then filter
			HashMap <String,String> nsemap = (HashMap <String,String>) getServletContext().getAttribute("nse200");
			
			String nseselection = (String) session.getAttribute("CTnse200selection");
			String otherselection = (String)session.getAttribute("CTOthersselection");
			String daywprp = (String) session.getAttribute("CTDayWRperiodselection");
			String dayrsip = (String) session.getAttribute("CTDayRSIperiodselection");
			String weekwprp = (String) session.getAttribute("CTWeekWRperiodselection");
			String weekrsip = (String) session.getAttribute("CTWeekRSIperiodselection");
			String rsirange = (String) session.getAttribute("CTRSIrangeselection");
			String wprrange = (String) session.getAttribute("CTWPRrangeselection");
			String skrange = (String) session.getAttribute("CTSKrangeselection");
			String sdrange = (String) session.getAttribute("CTSDrangeselection");
			String prevzeroselection = (String) session.getAttribute("CTPrevkzeroselection");
			String uptrendselection = (String) session.getAttribute("CTUptrendselection");
			String weekprevzeroselection = (String) session.getAttribute("CTweekPrevkzeroselection");
			String weekuptrendselection = (String) session.getAttribute("CTweekUptrendselection");
			
			HashMap <String,StockOtherTechnicals> finalmap = new HashMap <String,StockOtherTechnicals> ();
			HashMap <String,StockOtherTechnicals> temp = new HashMap <String,StockOtherTechnicals> ();
			
			temp = getresulthashmap();
			
			
			
			Iterator it = temp.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
				 String stocksymbol = (String) pair.getKey();
				 StockOtherTechnicals st = (StockOtherTechnicals)pair.getValue();
				 
				 if (nseselection.equals("true"))
				 {
					 if(nsemap.containsKey(stocksymbol))
					 {
						 if (  Double.valueOf(st.getDayrsi()) <=  Double.valueOf(rsirange) || Double.valueOf(st.getWeekrsi()) <=  Double.valueOf(rsirange))
							 if (  Double.valueOf(st.getDaywilliamsr()) <=  Double.valueOf(wprrange) || Double.valueOf(st.getWeekwilliamsr()) <=  Double.valueOf(wprrange))
								 if (  Double.valueOf(st.getDayk()) <=  Double.valueOf(skrange) && Double.valueOf(st.getDayd()) <=  Double.valueOf(sdrange))
								 { if (prevzeroselection.equals("true") && uptrendselection.equals("true"))
								 {
									 if (st.getPrevdayk() < 1 && st.getDayd() > st.getPrevdayd())
									 {
										 if (checkweeklyselection(st,weekprevzeroselection,weekuptrendselection))
										 {
									 finalstklist.put(st.getStocksymbol(), st.getStocksymbol());
									 finalmap.put(stocksymbol, st);
										 }
									 }
								 }
								 else if (prevzeroselection.equals("true") && st.getPrevdayk() < 1)
								 {
									 if (checkweeklyselection(st,weekprevzeroselection,weekuptrendselection))
									 {
								 finalstklist.put(st.getStocksymbol(), st.getStocksymbol());
								 finalmap.put(stocksymbol, st);
									 }
								 }
								 else if (uptrendselection.equals("true") && st.getDayd() > st.getPrevdayd())
								 {
									 if (checkweeklyselection(st,weekprevzeroselection,weekuptrendselection))
									 {
									 finalstklist.put(st.getStocksymbol(), st.getStocksymbol());
									 finalmap.put(stocksymbol, st);
									 }
								 }
								 else if (!prevzeroselection.equals("true") && !uptrendselection.equals("true"))
								 {
									 if (checkweeklyselection(st,weekprevzeroselection,weekuptrendselection))
									 {
									 finalstklist.put(st.getStocksymbol(), st.getStocksymbol());
									 finalmap.put(stocksymbol, st);
									 }
								 }}
								
					 }
				 }
				 if (otherselection.equals("true"))
				 {
					 if(!nsemap.containsKey(stocksymbol))
					 {
						 if (  Double.valueOf(st.getDayrsi()) <=  Double.valueOf(rsirange) || Double.valueOf(st.getWeekrsi()) <=  Double.valueOf(rsirange))
							 if (  Double.valueOf(st.getDaywilliamsr()) <=  Double.valueOf(wprrange) || Double.valueOf(st.getWeekwilliamsr()) <=  Double.valueOf(wprrange))
								 if (  Double.valueOf(st.getDayk()) <=  Double.valueOf(skrange) && Double.valueOf(st.getDayd()) <=  Double.valueOf(sdrange))
								 { if (prevzeroselection.equals("true") && uptrendselection.equals("true"))
								 {
									 if (st.getPrevdayk() < 1 && st.getDayd() > st.getPrevdayd())
									 {
										 if (checkweeklyselection(st,weekprevzeroselection,weekuptrendselection))
										 {
									 finalstklist.put(st.getStocksymbol(), st.getStocksymbol());
									 finalmap.put(stocksymbol, st);
										 }
									 }
								 }
								 else if (prevzeroselection.equals("true") && st.getPrevdayk() < 1)
								 {
									 if (checkweeklyselection(st,weekprevzeroselection,weekuptrendselection))
									 {
								 finalstklist.put(st.getStocksymbol(), st.getStocksymbol());
								 finalmap.put(stocksymbol, st);
									 }
								 }
								 else if (uptrendselection.equals("true") && st.getDayd() > st.getPrevdayd())
								 {
									 if (checkweeklyselection(st,weekprevzeroselection,weekuptrendselection))
									 {
									 finalstklist.put(st.getStocksymbol(), st.getStocksymbol());
									 finalmap.put(stocksymbol, st);
									 }
								 }
								 else if (!prevzeroselection.equals("true") && !uptrendselection.equals("true"))
								 {
									 if (checkweeklyselection(st,weekprevzeroselection,weekuptrendselection))
									 {
									 finalstklist.put(st.getStocksymbol(), st.getStocksymbol());
									 finalmap.put(stocksymbol, st);
									 }
								 }}
						 
					 }
				 }
			}
			
			session.setAttribute("CommTechEODliveshowfiltered", finalstklist);
			session.setAttribute("EODLiveFilterstock", finalmap);
			request.setAttribute("stocklist", finalmap);
			request.setAttribute("Commontechnse", nseselection);
			request.setAttribute("Commontechothers", otherselection);
			request.setAttribute("Commontechrsirange", rsirange);
			request.setAttribute("Commontechwprrange", wprrange);
			request.setAttribute("Commontechdaywprp", daywprp);
			request.setAttribute("Commontechweekwprp", weekwprp);
			request.setAttribute("Commontechdayrsip", dayrsip);
			request.setAttribute("Commontechweekrsip", weekrsip);
			request.setAttribute("Commontechskrange", skrange);
			request.setAttribute("Commontechsdrange", sdrange);
			request.setAttribute("Commontechprevkzero", prevzeroselection);
			request.setAttribute("Commontechuptrend", uptrendselection);
			request.setAttribute("Commontechweekprevkzero", weekprevzeroselection);
			request.setAttribute("Commontechweekuptrend", weekuptrendselection);
			
			
		}
		else
		{
			/*
			 * 
			 * 
			 */
			// query Strings
			HashMap <String,StockOtherTechnicals> othertech = new HashMap <String,StockOtherTechnicals> ();
			
			othertech = getresulthashmap();
			session.setAttribute("Othertechnicalsmasterlist", othertech);
			request.setAttribute("stocklist", othertech);
			request.setAttribute("Commontechnse", "true");
			request.setAttribute("Commontechothers", "true");
			request.setAttribute("Commontechrsirange", "100");
			request.setAttribute("Commontechwprrange", "0");
			request.setAttribute("Commontechdaywprp", "21");
			request.setAttribute("Commontechweekwprp", "21");
			request.setAttribute("Commontechdayrsip", "14");
			request.setAttribute("Commontechweekrsip", "14");
			request.setAttribute("Commontechskrange", "100");
			request.setAttribute("Commontechsdrange", "100");
			request.setAttribute("Commontechprevkzero", "false");
			request.setAttribute("Commontechuptrend", "false");
			request.setAttribute("Commontechweekprevkzero", "false");
			request.setAttribute("Commontechweekuptrend", "false");
			  
              
			
		}
		
		
		request.getRequestDispatcher("OtherTechnicals.jsp").forward(request, response);
		
	}

	private boolean checkweeklyselection(StockOtherTechnicals st, String weekprevzeroselection,
			String weekuptrendselection) {
		 if (weekprevzeroselection.equals("true") && weekuptrendselection.equals("true"))
		 {
			 if (st.getWeekprevk() < 1 && st.getWeekd() > st.getWeekprevd())
			 {
				return true;
			 }
			 else
			 {
				 return false;
			 }
		 }
		 else if (weekprevzeroselection.equals("true") && st.getWeekprevk() < 1)
		 {
			 return true;
		 }
		 else if (weekuptrendselection.equals("true") && st.getWeekd() > st.getWeekprevd())
		 {
			 return true;
		 }
		 else if (!weekprevzeroselection.equals("true") && !weekuptrendselection.equals("true"))
		 {
			 return true;
		 }
		 
		 return false;
	}

	private HashMap<String, StockOtherTechnicals> getresulthashmap() {
		// TODO Auto-generated method stub
		DecimalFormat df = new DecimalFormat("#.##"); 
		Connection conn = getconnection();
		HashMap <String,StockOtherTechnicals> othertech = new HashMap <String,StockOtherTechnicals> ();
		
		HashMap <String,Stocktwodaystat> daystatsmap = (HashMap <String,Stocktwodaystat>) getServletContext().getAttribute("StockDaytwodaystats");
		HashMap <String,Stocktwoweekstat> weekstatsmap = (HashMap <String,Stocktwoweekstat>) getServletContext().getAttribute("Stocktwoweekstats");
		HashMap  <String,StochasticFinalval> dayedostoch =(HashMap  <String,StochasticFinalval>) getServletContext().getAttribute("Dayneweodstochastic"); 
		HashMap  <String,StochasticFinalval> pvdayedostoch =(HashMap  <String,StochasticFinalval>) getServletContext().getAttribute("Dayneweodstochastic"); 
		HashMap  <String,MyindicatorvaluesWeek> weekeodstoch =(HashMap  <String,MyindicatorvaluesWeek>) getServletContext().getAttribute("weekeodstochastic"); 
		
		HashMap <String,Double> dayWRdata = getdayWR(conn,21,daystatsmap);
		HashMap <String,Double> WeekWRdata = getweekWR(conn,21,weekstatsmap);
		HashMap <String,Double> dayRsidata = getdayRSI(conn, 14,daystatsmap);
		HashMap <String,Double> weekRsidata = getweekRSI(conn,14,weekstatsmap);
		//HashMap <String,WeekstochasticFinalval> weekstochkd = getweekstochkd(conn,weekstatsmap);
		
		
		
		
		
		 Iterator it = daystatsmap.entrySet().iterator();
		 while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        String stocksymbol = (String) pair.getKey();
		        
		        StockOtherTechnicals st = new StockOtherTechnicals();
		        st.setStocksymbol(stocksymbol);
		        st.setDayrsi(dayRsidata.get(stocksymbol));
		        st.setWeekrsi(weekRsidata.get(stocksymbol));
		        st.setDaywilliamsr(dayWRdata.get(stocksymbol));
		        st.setWeekwilliamsr(WeekWRdata.get(stocksymbol));
		        
		        // set day osc values later
		        if(dayedostoch.containsKey(stocksymbol))
		        {
		        st.setDayk(Double.valueOf(df.format(dayedostoch.get(stocksymbol).getPrecentk())));
		        st.setDayd(Double.valueOf(df.format(dayedostoch.get(stocksymbol).getPercentd())));
		        }
		        else
		        {
		        	st.setDayk(50);
			        st.setDayd(50);
		        }
		        
		        if(pvdayedostoch.containsKey(stocksymbol))
		        {
		        st.setPrevdayk(Double.valueOf(df.format(pvdayedostoch.get(stocksymbol).getPrev_precentk())));
		        st.setPrevdayd(Double.valueOf(df.format(pvdayedostoch.get(stocksymbol).getPrev_precentd())));
		        }
		        else
		        {
		        	st.setPrevdayk(50);
			        st.setPrevdayd(50);
		        }
		        
		        // set week oscialtor values later
		        
		        // put weekstoh here
		        
		        if(weekeodstoch.containsKey(stocksymbol))
		        {
		        st.setWeekk(Double.valueOf(df.format(weekeodstoch.get(stocksymbol).getPercentK())));
		        st.setWeekd(Double.valueOf(df.format(weekeodstoch.get(stocksymbol).getPercentd())));
		        st.setWeekprevk(Double.valueOf(df.format(weekeodstoch.get(stocksymbol).getPrevPercentK())));
		        st.setWeekprevd(Double.valueOf(df.format(weekeodstoch.get(stocksymbol).getPrevPercentd())));
		        
		        }
		        else
		        {
		        	st.setWeekk(50);
			        st.setWeekd(50);
		        }
		        
		        othertech.put(stocksymbol, st);
		    }
		 
		 try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return othertech;
		 
	}

	private HashMap<String, WeekstochasticFinalval> getweekstochkd(Connection conn,
			HashMap<String, Stocktwoweekstat> weekstatsmap) {
		// TODO Auto-generated method stub
		HashMap<String, WeekstochasticFinalval> resultmap = new HashMap<String, WeekstochasticFinalval>();
		Iterator it = weekstatsmap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
	        String stocksymbol = (String) pair.getKey();
	        
	        ArrayList<Float> low = new ArrayList<Float>();
	        ArrayList<Float> high = new ArrayList<Float>();
	        ArrayList<Float> obvvol = new ArrayList<Float>();
	        ArrayList<Float> close = new ArrayList<Float>();
	        
	     // use WeekStockdata
	       
		}
		
		return null;
	}

	private HashMap<String, Double> getweekWR(Connection conn, int i, HashMap<String, Stocktwoweekstat> weekstatsmap) {
		// TODO Auto-generated method stub
		DecimalFormat df = new DecimalFormat("#.##"); 
	    HashMap <String, Double> temp = new  HashMap <String, Double>();
		String query = "select max(WEEKHIGHHIGH) hp ,min(WEEKLOWLOW) lp,stocksymbol from WEEK_STATS where to_number(TRADEYR||tradeweek) >= (select min(tdyr) from (select distinct to_number(TRADEYR||tradeweek) tdyr from WEEK_STATS order by to_number(TRADEYR||tradeweek) desc) where rownum < ?) group by stocksymbol";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, i+1);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next())
			{  
				Double val=(double) 50;
				String sksymbol = rs.getString(3);
				if (weekstatsmap.containsKey(sksymbol))
				{
				
				
				Float high = (float) rs.getDouble(1);
				Float low = (float) rs.getDouble(2);
				
				Float result = (float) (((high-(weekstatsmap.get(sksymbol).getCURR_CLOSE()))/(high-low))*(-100));
				
				val = Double.valueOf(df.format(result));
				}
				
				temp.put(sksymbol, val);
			}
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}

	private HashMap<String, Double> getweekRSI(Connection conn, int period, HashMap<String, Stocktwoweekstat> weekstatsmap) {
		// TODO Auto-generated method stub
		DecimalFormat df = new DecimalFormat("#.##"); 
	    HashMap <String, Double> temp = new  HashMap <String, Double>();
		String query=" select stocksymbol,listagg(weekclose,',') within group (order by tdyw asc) weekcloses from (select stocksymbol,weekclose,to_number(tradeyr||tradeweek) tdyw from WEEK_STATS where to_number(TRADEYR||tradeweek) >= (select min(tdyr) from (select distinct to_number(TRADEYR||tradeweek) tdyr from WEEK_STATS order by to_number(TRADEYR||tradeweek) desc) where rownum < 250)) group by stocksymbol";
		
		try {
			Statement ps = conn.createStatement();	
			
			ResultSet rs = ps.executeQuery(query);
			
			while (rs.next())
			{ 
			    Float rsi=0f;
			    Double finalrsi;
				Double val=(double) 50;
				
				String stocksymbol = rs.getString(1);
				String priceagg = rs.getString(2);
				if(priceagg != null)
				{
					
					Float up=(float) 0,down =(float) 0;
					String[] prices = priceagg.split(",");
					for(int j=0; j< (prices.length-1) ; j++)
					{
						Float gain=0f,loss=0f;
						Float lp = Float.valueOf(prices[j+1]);
						Float np = Float.valueOf(prices[j]);
						if (lp > np)
						gain = lp-np;
						else
						loss = np-lp;
						up=getup(gain,up,period,j);
						down = getdown(loss,down,period,j);
					}
					
					rsi = up/down;
					finalrsi = Double.valueOf(Float.toString(100-(100/(1+rsi))));
					val = Double.valueOf(df.format(finalrsi));
					
				}
				temp.put(stocksymbol, val);
			}
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return temp;
		
	}

	private HashMap<String, Double> getdayRSI(Connection conn, int period, HashMap<String, Stocktwodaystat> daystatsmap) {
		// TODO Auto-generated method stub
		DecimalFormat df = new DecimalFormat("#.##"); 
	    HashMap <String, Double> temp = new  HashMap <String, Double>();
		String query=" select stocksymbol,listagg(closeprice,',') within group (order by tradedate asc) closeprices from (select stocksymbol,closeprice,tradedate from stockprice where tradedate >= (select min(tradedate) from (select distinct tradedate from stockprice order by tradedate desc) where rownum < 250)) group by stocksymbol";
		
		
		try {
			Statement ps = conn.createStatement();	
			
			ResultSet rs = ps.executeQuery(query);
			
			while (rs.next())
			{ 
			    Float rsi=0f;
			    Double finalrsi;
				Double val=(double) 50;
				
				String stocksymbol = rs.getString(1);
				String priceagg = rs.getString(2);
				if(priceagg != null)
				{
					
					Float up=(float) 0,down =(float) 0;
					String[] prices = priceagg.split(",");
					for(int j=0; j< (prices.length-1) ; j++)
					{
						Float gain=0f,loss=0f;
						Float lp = Float.valueOf(prices[j+1]);
						Float np = Float.valueOf(prices[j]);
						if (lp > np)
						gain = lp-np;
						else
						loss = np-lp;
						up=getup(gain,up,period,j);
						down = getdown(loss,down,period,j);
					}
					
					rsi = up/down;
					finalrsi = Double.valueOf(Float.toString(100-(100/(1+rsi))));
					val = Double.valueOf(df.format(finalrsi));
					
				}
				temp.put(stocksymbol, val);
			}
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return temp;
		
	}

	private Float getdown(Float loss, Float down, int period, int j) {
		// TODO Auto-generated method stub
		Float result=0f;
		if (j+1<period)
		{
			result = loss+down;
		}
		if(j+1 == period)
		{
			result = (loss+down)/period;
		}
		if (j+1 > period)
		{
			result = (down*(period-1)+loss)/period;
		}
		return result;
	}

	private Float getup(Float gain, Float up, int period, int j) {
		// TODO Auto-generated method stub
		Float result=0f;
		if (j+1<period)
		{
			result = gain+up;
		}
		if(j+1 == period)
		{
			result = (gain+up)/period;
		}
		if (j+1 > period)
		{
			result = (up*(period-1)+gain)/period;
		}
		return result;
	}

	private HashMap<String, Double> getdayWR(Connection conn, int i, HashMap<String, Stocktwodaystat> daystock) {
		// TODO Auto-generated method stub
		DecimalFormat df = new DecimalFormat("#.##"); 
	    HashMap <String, Double> temp = new  HashMap <String, Double>();
		String query = "select max(highprice) hp ,min(lowprice) lp,stocksymbol from stockprice where tradedate >= (select min(tradedate) from (select distinct tradedate from stockprice order by tradedate desc) where rownum < ?) group by stocksymbol";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, i+1);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next())
			{  
				Double val=(double) 50;
				String sksymbol = rs.getString(3);
				if (daystock.containsKey(sksymbol))
				{
				
				
				Float high = (float) rs.getDouble(1);
				Float low = (float) rs.getDouble(2);
				
				Float result = (float) (((high-(daystock.get(sksymbol).getCURR_CLOSE()))/(high-low))*(-100));
				
				val = Double.valueOf(df.format(result));
				}
				
				temp.put(sksymbol, val);
			}
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}

	private double getweekWR(Connection conn, String stocksymbol, String weekhighlow, int i, Stockdata sk) {
		// TODO Auto-generated method stub
		DecimalFormat df = new DecimalFormat("#.##"); 
		Double val=(double) 0;
		
		
		try {
			PreparedStatement ps = conn.prepareStatement(weekhighlow);
			ps.setInt(1, i);
			ps.setString(2, stocksymbol);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				Float high = (float) rs.getDouble(1);
				Float low = (float) rs.getDouble(2);
				
				Float result = (float) (((high-sk.getCloseprice())/(high-low))*(-100));
				
				val = Double.valueOf(df.format(result));
				
			}
			
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return val;
	}

	private double getdayWR(Connection conn, String stocksymbol, String dayhighlow, int i, Stockdata sk) {
		// TODO Auto-generated method stub
		DecimalFormat df = new DecimalFormat("#.##"); 
		Double val=(double) 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
		 ps = conn.prepareStatement(dayhighlow);
			ps.setInt(1, i);
			ps.setString(2, stocksymbol);
			
		 rs = ps.executeQuery();
			if(rs.next())
			{
				Float high = (float) rs.getDouble(1);
				Float low = (float) rs.getDouble(2);
				
				Float result = (float) (((high-sk.getCloseprice())/(high-low))*(-100));
				
				val = Double.valueOf(df.format(result));
				
			}
			
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		
		return val;
	}

	private double getweekrsi(Connection conn, String stocksymbol, String weekrsi, int i ,Stockdata sk) {
		// TODO Auto-generated method stub
		return 0;
	}

	private double getdayrsi(Connection conn, String stocksymbol, String dayrsi, int i, Stockdata sk) {
		// TODO Auto-generated method stub
		return 0;
	}

	private HashMap<String, Stockdata> getstocklist(Connection conn) {
		// TODO Auto-generated method stub
		HashMap <String,Stockdata> stock = new HashMap<String,Stockdata>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return stock;
	}

	private Connection getconnection() {
		// TODO Auto-generated method stub
		//ServletContext ctx = servletContextEvent.getServletContext();
		Connection conn = null;
		Context envCtx;
		try {
			Context initCtx = (Context) new InitialContext();
			envCtx = (Context) ((Context) initCtx).lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup("jdbc/UsersDB");
			conn = ds.getConnection();
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
            
		boolean changed = false;
		HttpSession session = request.getSession(true); 
		session.setAttribute("CTnse200selection", request.getParameter("Commontechnse200attr"));
		session.setAttribute("CTOthersselection", request.getParameter("Commontechotherattr"));
		
		if (session.getAttribute("CTDayWRperiodselection")!=null)
		{
		  if (!session.getAttribute("CTDayWRperiodselection").equals(request.getParameter("commontechDWRPattr")))
				  {
			  changed = true;
				  }
		  if (!session.getAttribute("CTDayRSIperiodselection").equals(request.getParameter("commontechDRSIPattr")))
		  {
	  changed = true;
		  }
		  if (!session.getAttribute("CTWeekWRperiodselection").equals(request.getParameter("commontechWWRPattr")))
		  {
	  changed = true;
		  }
		  if (!session.getAttribute("CTWeekRSIperiodselection").equals(request.getParameter("commontechWRSPIattr")))
		  {
	  changed = true;
		  }
		  
			
		}
		
		session.setAttribute("CTDayWRperiodselection", request.getParameter("commontechDWRPattr"));
		session.setAttribute("CTDayRSIperiodselection", request.getParameter("commontechDRSIPattr"));
		session.setAttribute("CTWeekWRperiodselection", request.getParameter("commontechWWRPattr"));
		session.setAttribute("CTWeekRSIperiodselection", request.getParameter("commontechWRSPIattr"));
		session.setAttribute("CTRSIrangeselection", request.getParameter("commontechRSPrangeattr"));
		session.setAttribute("CTWPRrangeselection", request.getParameter("commontechWRPrangeattr"));
		session.setAttribute("CTSKrangeselection", request.getParameter("commontechskrangeattr"));
		session.setAttribute("CTSDrangeselection", request.getParameter("commontechsdrangeattr"));
		session.setAttribute("CTPrevkzeroselection", request.getParameter("commontechprevzeroattr"));
		session.setAttribute("CTUptrendselection", request.getParameter("commontechuptrendattr"));
		session.setAttribute("CTweekPrevkzeroselection", request.getParameter("commontechweekprevzeroattr"));
		session.setAttribute("CTweekUptrendselection", request.getParameter("commontechweekuptrendattr"));
		
		
		
		
        
		doGet(request, response,changed);
	}

}
