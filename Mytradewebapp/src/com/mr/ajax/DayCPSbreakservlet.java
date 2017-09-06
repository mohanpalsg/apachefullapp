package com.mr.ajax;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mr.app.CPSbreak;
import com.mr.data.Filteredsmapivotdata;
import com.mr.data.Stocktwodaystat;
import com.mr.data.Daypivot;
import com.mr.data.Daysma50;
import com.mr.realtimedata.Pricedata;

/**
 * Servlet implementation class DayCPSbreakservlet
 */
@WebServlet("/DayCPSbreakservlet")
public class DayCPSbreakservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DayCPSbreakservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		if(session.getAttribute("rtCurrentpricemap") == null)
		{
			String text = "Enable LiveData to use this feature";

		    response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
		    response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
		    response.getWriter().write(text);
			
		}
		else
		{
			

					HashMap <String,Stocktwodaystat> statsmap = (HashMap <String,Stocktwodaystat>) getServletContext().getAttribute("StockDaytwodaystats");
					HashMap <String,Pricedata> Currmap = (HashMap <String,Pricedata>) session.getAttribute("rtCurrentpricemap");
					HashMap <String,Daysma50> sma50map = (HashMap<String, Daysma50>) getServletContext().getAttribute("Daysma50");
					HashMap <String,Daypivot> daypivotmap = (HashMap<String, Daypivot>) getServletContext().getAttribute("Daypivot");
					HashMap <String,CPSbreak> DayCPSbreakmap = new  HashMap <String,CPSbreak>();
					DecimalFormat df = new DecimalFormat("#.##"); 
					Iterator it = statsmap.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry pair = (Map.Entry)it.next();
				        String stocksymbol = (String) pair.getKey();
				        if(sma50map.containsKey(stocksymbol) && Currmap.containsKey(stocksymbol))
				        {
				        	Stocktwodaystat sw2 = (Stocktwodaystat) pair.getValue();
				        	Daysma50 sma50 = sma50map.get(stocksymbol);
				        	Pricedata curprice = Currmap.get(stocksymbol);
				        	if(sw2.getCURR_CLOSE() <= sma50.getMovingavg() && curprice.getHighprice() >= sma50.getMovingavg() )
				        	{
				        		CPSbreak filterdata = new CPSbreak();
				        		filterdata.setStocksymbol(stocksymbol);
			        			filterdata.setCurrclose(curprice.getLastprice());
			        			filterdata.setPrevclose(sw2.getCURR_CLOSE());
			        			filterdata.setPivotlevel("SMA");
			        			filterdata.setPivotval("");
			        			filterdata.setSmavalue(Double.valueOf(df.format(sma50.getMovingavg())).toString());
			        			filterdata.setTradedate(sw2.getCURR_ENDATE());
			        			
			        			filterdata.setSmadiff(Double.valueOf(df.format(((curprice.getHighprice()-sma50.getMovingavg())/sma50.getMovingavg())*100)).toString());
			        			filterdata.setPivotdiff("");
			        			filterdata.setBreakpivot(false);
			        			filterdata.setBreaksma(true);
			        			DayCPSbreakmap.put(stocksymbol+"S.M.A", filterdata);
				        	}
				        }
					}
					
					Iterator it1 = statsmap.entrySet().iterator();
					while (it1.hasNext()) {
						Map.Entry pair = (Map.Entry)it1.next();
				        String stocksymbol = (String) pair.getKey();
				        if(daypivotmap.containsKey(stocksymbol) && Currmap.containsKey(stocksymbol))
				        {
				        	Stocktwodaystat sw2 = (Stocktwodaystat) pair.getValue();
				        	Daypivot daypv = daypivotmap.get(stocksymbol);
				        	Pricedata curprice = Currmap.get(stocksymbol);
				        	String level = "nothing";
				        	double levelprice=0;
				        	if (sw2.getCURR_CLOSE() <= daypv.getPP() && curprice.getHighprice() >= daypv.getPP())
			        		{
			        			level="PP";
			        			levelprice=daypv.getPP();
			        		}
			        		else if (sw2.getCURR_CLOSE() <= daypv.getS1() && curprice.getHighprice() >= daypv.getS1())
			        		{
			        			level="S1";
			        			levelprice=daypv.getS1();
			        		}
			        		else if (sw2.getCURR_CLOSE() <= daypv.getS2() && curprice.getHighprice() >= daypv.getS2())
			        		{
			        			level="S2";
			        			levelprice=daypv.getS2();
			        		}
			        		else if (sw2.getCURR_CLOSE() <= daypv.getS3() && curprice.getHighprice() >= daypv.getS3())
			        		{
			        			level="S3";
			        			levelprice=daypv.getS3();
			        		}
			        		else if (sw2.getCURR_CLOSE() <= daypv.getS4() && curprice.getHighprice() >= daypv.getS4())
			        		{
			        			level="S4";
			        			levelprice=daypv.getS4();
			        		}
			        		else if (sw2.getCURR_CLOSE() <= daypv.getR1() && curprice.getHighprice() >= daypv.getR1())
			        		{
			        			level="R1";
			        			levelprice=daypv.getR1();
			        		}
			        		else if (sw2.getCURR_CLOSE() <= daypv.getR2() && curprice.getHighprice() >= daypv.getR2())
			        		{
			        			level="R2";
			        			levelprice=daypv.getR2();
			        		}
			        		else if (sw2.getCURR_CLOSE() <= daypv.getR3() && curprice.getHighprice() >= daypv.getR3())
			        		{
			        			level="R3";
			        			levelprice=daypv.getR3();
			        		}
			        		else if (sw2.getCURR_CLOSE() <= daypv.getR4() && curprice.getHighprice() >= daypv.getR4())
			        		{
			        			level="R4";
			        			levelprice=daypv.getR4();
			        		}
			        		
			        		if(level.contains("nothing"))
			        		{
			        			
			        		}
			        		else
			        		{
			        			CPSbreak filterdata = new CPSbreak();
				        		filterdata.setStocksymbol(stocksymbol);
			        			filterdata.setCurrclose(curprice.getLastprice());
			        			filterdata.setPrevclose(sw2.getCURR_CLOSE());
			        			filterdata.setPivotlevel(level);
			        			filterdata.setPivotval(Double.valueOf(df.format(levelprice)).toString());
			        			filterdata.setSmavalue("");
			        			filterdata.setTradedate(curprice.getTradedate());
			        			
			        			filterdata.setSmadiff("");
			        			filterdata.setPivotdiff(Double.valueOf(df.format(((curprice.getHighprice()-levelprice)/levelprice)*100)).toString());
			        			filterdata.setBreakpivot(true);
			        			filterdata.setBreaksma(false);
			        			DayCPSbreakmap.put(stocksymbol+"P.V.B", filterdata);
			        		}
				        }
					}
					if (session.getAttribute("DayCPSbreaknse200selection") !=null &&
							session.getAttribute("DayCPSbreakotherselection") !=null &&
							session.getAttribute("DayCPSbreakdiffselection") !=null )
							{
								
								String nseselected = (String) session.getAttribute("DayCPSbreaknse200selection");
								String Otherselected = (String) session.getAttribute("DayCPSbreakotherselection");
								String diffselected = (String) session.getAttribute("DayCPSbreakdiffselection") ;
								String smaselected = (String)session.getAttribute("DayCPSbreakoptionSMAselection");
								String ppselected =(String)session.getAttribute("DayCPSbreakoptionPPselection");
								String s1selected =(String)session.getAttribute("DayCPSbreakoptionS1selection");
								String s2selected =(String)session.getAttribute("DayCPSbreakoptionS2selection");
								String s3selected =(String)session.getAttribute("DayCPSbreakoptionS3selection");
								String s4selected =(String)session.getAttribute("DayCPSbreakoptionS4selection");
								String r1selected =(String)session.getAttribute("DayCPSbreakoptionR1selection");
								String r2selected =(String)session.getAttribute("DayCPSbreakoptionR2selection");
								String r3selected =(String)session.getAttribute("DayCPSbreakoptionR3selection");
								String r4selected =(String)session.getAttribute("DayCPSbreakoptionR4selection");
								
								
								HashMap <String,String> finallist = new HashMap <String,String>();
								HashMap <String,CPSbreak> finalmap = new HashMap <String,CPSbreak>();
								HashMap <String,String> nsemap = (HashMap <String,String>) getServletContext().getAttribute("nse200");
								Iterator it2 = DayCPSbreakmap.entrySet().iterator();
								while (it2.hasNext()) {
									Map.Entry pair = (Map.Entry)it2.next();
							        String stocksymbol = (String) pair.getKey();
							        CPSbreak cp = (CPSbreak) pair.getValue();
							        CPSbreak newcp = new CPSbreak();
							        if (nseselected.equals("true"))
							        {
							        	if(nsemap.containsKey(cp.getStocksymbol()))
							        	{
							        		newcp = getresult(cp,diffselected,smaselected,ppselected,s1selected,s2selected,s3selected,s4selected,r1selected,r2selected,r3selected,r4selected);
							        		if (newcp != null)
							        		{
							        		finalmap.put(stocksymbol,newcp );	
							        		finallist.put(newcp.getStocksymbol(), newcp.getStocksymbol());
							        		}
							        	}
							        }
							        if (Otherselected.equals("true"))
							        {
							        	if(!nsemap.containsKey(cp.getStocksymbol()))
							        	{
							        		newcp = getresult(cp,diffselected,smaselected,ppselected,s1selected,s2selected,s3selected,s4selected,r1selected,r2selected,r3selected,r4selected);
							        		if (newcp != null)
							        		{
							        		finalmap.put(stocksymbol,newcp );	
							        		finallist.put(newcp.getStocksymbol(), newcp.getStocksymbol());
							        		}	
							        	}
							        		// finalmap.put(stocksymbol, getresult(cp,diffselected,smaselected,ppselected,s1selected,s2selected,s3selected,s4selected,r1selected,r2selected,r3selected,r4selected));	
							        }
							        
							        
							     }
								session.setAttribute("WeekFilterLiveFilterstock", finallist);
								request.setAttribute("daycurrentpricepivotsmabreak",finalmap );
								request.setAttribute("Daycpsbreaknse", nseselected);
								request.setAttribute("Daycpsbreakothers", Otherselected);
								request.setAttribute("Daycpsperslide",diffselected);
								request.setAttribute("DayCPSbreakSMA", smaselected);
								request.setAttribute("DayCPSbreakPP", ppselected);
								request.setAttribute("DayCPSbreakS1", s1selected);
								request.setAttribute("DayCPSbreakS2", s2selected);
								request.setAttribute("DayCPSbreakS3", s3selected);
								request.setAttribute("DayCPSbreakS4", s4selected);
								request.setAttribute("DayCPSbreakR1", r1selected);
								request.setAttribute("DayCPSbreakR2", r2selected);
								request.setAttribute("DayCPSbreakR3", r3selected);
								request.setAttribute("DayCPSbreakR4", r4selected);
								
							}
					else
					{
			
					request.setAttribute("daycurrentpricepivotsmabreak",DayCPSbreakmap );
					request.setAttribute("Daycpsbreaknse", "true");
					request.setAttribute("Daycpsbreakothers", "false");
					request.setAttribute("DayCPSbreakSMA", "false");
					request.setAttribute("DayCPSbreakPP", "false");
					request.setAttribute("DayCPSbreakS1", "true");
					request.setAttribute("DayCPSbreakS2", "true");
					request.setAttribute("DayCPSbreakS3", "true");
					request.setAttribute("DayCPSbreakS4", "true");
					request.setAttribute("DayCPSbreakR1", "false");
					request.setAttribute("DayCPSbreakR2", "false");
					request.setAttribute("DayCPSbreakR3", "false");
					request.setAttribute("DayCPSbreakR4", "false");
					request.setAttribute("Daycpsperslide","2");
					}
					
					
					request.getRequestDispatcher("CURRENTDaySMAorPivotbreakV1.jsp").forward(request, response);
	}
		
		
	}
	
	private CPSbreak getresult(CPSbreak cp, String diffselected, String smaselected, String ppselected,
			String s1selected, String s2selected, String s3selected, String s4selected, String r1selected,
			String r2selected, String r3selected, String r4selected) {
		// TODO Auto-generated method stub
		if (smaselected.equals("true") && cp.isBreaksma() && new Double(cp.getSmadiff()).doubleValue() <= new Double(diffselected).doubleValue())
			return cp;
				
			
			if (ppselected.equals("true") && cp.isBreakpivot() && cp.getPivotlevel().equals("PP") && new Double(cp.getPivotdiff()).doubleValue() <= new Double(diffselected).doubleValue())
				return cp;
			
			if (s1selected.equals("true") && cp.isBreakpivot() && cp.getPivotlevel().equals("S1") && new Double(cp.getPivotdiff()).doubleValue() <= new Double(diffselected).doubleValue())
				return cp;
			
			if (s2selected.equals("true") && cp.isBreakpivot() && cp.getPivotlevel().equals("S2") && new Double(cp.getPivotdiff()).doubleValue() <= new Double(diffselected).doubleValue())
				return cp;
			
			if (s3selected.equals("true") && cp.isBreakpivot() && cp.getPivotlevel().equals("S3") && new Double(cp.getPivotdiff()).doubleValue() <= new Double(diffselected).doubleValue())
				return cp;
			
			if (s4selected.equals("true") && cp.isBreakpivot() && cp.getPivotlevel().equals("S4") && new Double(cp.getPivotdiff()).doubleValue() <= new Double(diffselected).doubleValue())
				return cp;
			
			if (r1selected.equals("true") && cp.isBreakpivot() && cp.getPivotlevel().equals("R1") && new Double(cp.getPivotdiff()).doubleValue() <= new Double(diffselected).doubleValue())
				return cp;
			
			if (r2selected.equals("true") && cp.isBreakpivot() && cp.getPivotlevel().equals("R2") && new Double(cp.getPivotdiff()).doubleValue() <= new Double(diffselected).doubleValue())
				return cp;
			
			if (r3selected.equals("true") && cp.isBreakpivot() && cp.getPivotlevel().equals("R3") && new Double(cp.getPivotdiff()).doubleValue() <= new Double(diffselected).doubleValue())
				return cp;
			
			if (r4selected.equals("true") && cp.isBreakpivot() && cp.getPivotlevel().equals("R4") && new Double(cp.getPivotdiff()).doubleValue() <= new Double(diffselected).doubleValue())
				return cp;
			
			return null;
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true); 
		session.setAttribute("DayCPSbreaknse200selection", request.getParameter("DayCPSbreaknse200attr"));
		session.setAttribute("DayCPSbreakotherselection", request.getParameter("DayCPSbreakotherattr"));
		session.setAttribute("DayCPSbreakdiffselection", request.getParameter("DayCPSbreakdiffattr"));
		session.setAttribute("DayCPSbreakoptionSMAselection", request.getParameter("DayCPSbreakoptionSMA"));
		session.setAttribute("DayCPSbreakoptionPPselection", request.getParameter("DayCPSbreakoptionPP"));
		session.setAttribute("DayCPSbreakoptionS1selection", request.getParameter("DayCPSbreakoptionS1"));
		session.setAttribute("DayCPSbreakoptionS2selection", request.getParameter("DayCPSbreakoptionS2"));
		session.setAttribute("DayCPSbreakoptionS3selection", request.getParameter("DayCPSbreakoptionS3"));
		session.setAttribute("DayCPSbreakoptionS4selection", request.getParameter("DayCPSbreakoptionS4"));
		session.setAttribute("DayCPSbreakoptionR1selection", request.getParameter("DayCPSbreakoptionR1"));
		session.setAttribute("DayCPSbreakoptionR2selection", request.getParameter("DayCPSbreakoptionR2"));
		session.setAttribute("DayCPSbreakoptionR3selection", request.getParameter("DayCPSbreakoptionR3"));
		session.setAttribute("DayCPSbreakoptionR4selection", request.getParameter("DayCPSbreakoptionR4"));
		
		response.setContentType("text/html;charset=UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    
	    /*
	    String paramtest = "This is paramtest!!!!! \n"+"DayCPSbreaknse200attr: "+(request.getParameter("DayCPSbreaknse200attr"))+"\n"+
	    		"DayCPSbreakotherattr: "+	(request.getParameter("DayCPSbreakotherattr"))+"\n"+
	    		"DayCPSbreakdiffattr:"+		(request.getParameter("DayCPSbreakdiffattr"))+"\n"+
	    			"DayCPSbreakoptionSMA"	+(request.getParameter("DayCPSbreakoptionSMA"))+"\n"+
	    			"DayCPSbreakoptionPP"	+(request.getParameter("DayCPSbreakoptionPP"))+"\n"+
	    			"DayCPSbreakoptionS1"	+(request.getParameter("DayCPSbreakoptionS1"))+"\n"+
	    			"DayCPSbreakoptionS2"	+(request.getParameter("DayCPSbreakoptionS2"))+"\n"+
	    			"DayCPSbreakoptionS3"	+(request.getParameter("DayCPSbreakoptionS3"))+"\n"+
	    			"DayCPSbreakoptionS4"	+(request.getParameter("DayCPSbreakoptionS4"))+"\n"+
	    			"DayCPSbreakoptionR1"	+(request.getParameter("DayCPSbreakoptionR1"))+"\n"+
	    			"DayCPSbreakoptionR2"	+(request.getParameter("DayCPSbreakoptionR2"))+"\n"+
	    			"DayCPSbreakoptionR3"	+(request.getParameter("DayCPSbreakoptionR3"))+"\n"+
	    			"DayCPSbreakoptionR4"	+(request.getParameter("DayCPSbreakoptionR4"))+"\n";
	    
		
	    response.getWriter().write(paramtest);
		*/
		doGet(request, response);
	}

}
