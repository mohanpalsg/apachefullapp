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
import com.mr.data.Stocktwoweekstat;
import com.mr.data.Weekpivot;
import com.mr.data.Weeksma50;
import com.mr.realtimedata.Pricedata;

/**
 * Servlet implementation class CPSbreakservlet
 */
@WebServlet("/CPSbreakservlet")
public class CPSbreakservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CPSbreakservlet() {
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
			

					HashMap <String,Stocktwoweekstat> statsmap = (HashMap <String,Stocktwoweekstat>) getServletContext().getAttribute("Stocktwoweekstats");
					HashMap <String,Pricedata> Currmap = (HashMap <String,Pricedata>) session.getAttribute("rtCurrentpricemap");
					HashMap <String,Weeksma50> sma50map = (HashMap<String, Weeksma50>) getServletContext().getAttribute("weeksma50");
					HashMap <String,Weekpivot> weekpivotmap = (HashMap<String, Weekpivot>) getServletContext().getAttribute("Weekpivot");
					HashMap <String,CPSbreak> CPSbreakmap = new  HashMap <String,CPSbreak>();
					DecimalFormat df = new DecimalFormat("#.##"); 
					Iterator it = statsmap.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry pair = (Map.Entry)it.next();
				        String stocksymbol = (String) pair.getKey();
				        if(sma50map.containsKey(stocksymbol) && Currmap.containsKey(stocksymbol))
				        {
				        	Stocktwoweekstat sw2 = (Stocktwoweekstat) pair.getValue();
				        	Weeksma50 sma50 = sma50map.get(stocksymbol);
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
			        			filterdata.setTradevolume(sw2.getCURR_TRADEVOLUME());
			        			filterdata.setSmadiff(Double.valueOf(df.format(((curprice.getHighprice()-sma50.getMovingavg())/sma50.getMovingavg())*100)).toString());
			        			filterdata.setPivotdiff("");
			        			filterdata.setBreakpivot(false);
			        			filterdata.setBreaksma(true);
			        			CPSbreakmap.put(stocksymbol+"S.M.A", filterdata);
				        	}
				        }
					}
					
					Iterator it1 = statsmap.entrySet().iterator();
					while (it1.hasNext()) {
						Map.Entry pair = (Map.Entry)it1.next();
				        String stocksymbol = (String) pair.getKey();
				        if(weekpivotmap.containsKey(stocksymbol) && Currmap.containsKey(stocksymbol))
				        {
				        	Stocktwoweekstat sw2 = (Stocktwoweekstat) pair.getValue();
				        	Weekpivot weekpv = weekpivotmap.get(stocksymbol);
				        	Pricedata curprice = Currmap.get(stocksymbol);
				        	String level = "nothing";
				        	double levelprice=0;
				        	if (sw2.getCURR_CLOSE() <= weekpv.getPP() && curprice.getHighprice() >= weekpv.getPP())
			        		{
			        			level="PP";
			        			levelprice=weekpv.getPP();
			        		}
			        		else if (sw2.getCURR_CLOSE() <= weekpv.getS1() && curprice.getHighprice() >= weekpv.getS1())
			        		{
			        			level="S1";
			        			levelprice=weekpv.getS1();
			        		}
			        		else if (sw2.getCURR_CLOSE() <= weekpv.getS2() && curprice.getHighprice() >= weekpv.getS2())
			        		{
			        			level="S2";
			        			levelprice=weekpv.getS2();
			        		}
			        		else if (sw2.getCURR_CLOSE() <= weekpv.getS3() && curprice.getHighprice() >= weekpv.getS3())
			        		{
			        			level="S3";
			        			levelprice=weekpv.getS3();
			        		}
			        		else if (sw2.getCURR_CLOSE() <= weekpv.getS4() && curprice.getHighprice() >= weekpv.getS4())
			        		{
			        			level="S4";
			        			levelprice=weekpv.getS4();
			        		}
			        		else if (sw2.getCURR_CLOSE() <= weekpv.getR1() && curprice.getHighprice() >= weekpv.getR1())
			        		{
			        			level="R1";
			        			levelprice=weekpv.getR1();
			        		}
			        		else if (sw2.getCURR_CLOSE() <= weekpv.getR2() && curprice.getHighprice() >= weekpv.getR2())
			        		{
			        			level="R2";
			        			levelprice=weekpv.getR2();
			        		}
			        		else if (sw2.getCURR_CLOSE() <= weekpv.getR3() && curprice.getHighprice() >= weekpv.getR3())
			        		{
			        			level="R3";
			        			levelprice=weekpv.getR3();
			        		}
			        		else if (sw2.getCURR_CLOSE() <= weekpv.getR4() && curprice.getHighprice() >= weekpv.getR4())
			        		{
			        			level="R4";
			        			levelprice=weekpv.getR4();
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
			        			filterdata.setTradevolume(curprice.getTradevolume());
			        			filterdata.setSmadiff("");
			        			filterdata.setPivotdiff(Double.valueOf(df.format(((curprice.getHighprice()-levelprice)/levelprice)*100)).toString());
			        			filterdata.setBreakpivot(true);
			        			filterdata.setBreaksma(false);
			        			CPSbreakmap.put(stocksymbol+"P.V.B", filterdata);
			        		}
				        }
					}
					if (session.getAttribute("CPSbreaknse200selection") !=null &&
							session.getAttribute("CPSbreakotherselection") !=null &&
							session.getAttribute("CPSbreakdiffselection") !=null )
							{
								
								String nseselected = (String) session.getAttribute("CPSbreaknse200selection");
								String Otherselected = (String) session.getAttribute("CPSbreakotherselection");
								String diffselected = (String) session.getAttribute("CPSbreakdiffselection") ;
								String smaselected = (String)session.getAttribute("CPSbreakoptionSMAselection");
								String ppselected =(String)session.getAttribute("CPSbreakoptionPPselection");
								String s1selected =(String)session.getAttribute("CPSbreakoptionS1selection");
								String s2selected =(String)session.getAttribute("CPSbreakoptionS2selection");
								String s3selected =(String)session.getAttribute("CPSbreakoptionS3selection");
								String s4selected =(String)session.getAttribute("CPSbreakoptionS4selection");
								String r1selected =(String)session.getAttribute("CPSbreakoptionR1selection");
								String r2selected =(String)session.getAttribute("CPSbreakoptionR2selection");
								String r3selected =(String)session.getAttribute("CPSbreakoptionR3selection");
								String r4selected =(String)session.getAttribute("CPSbreakoptionR4selection");
								
								
								
								HashMap <String,CPSbreak> finalmap = new HashMap <String,CPSbreak>();
								HashMap <String,String> nsemap = (HashMap <String,String>) getServletContext().getAttribute("nse200");
								Iterator it2 = CPSbreakmap.entrySet().iterator();
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
							        		finalmap.put(stocksymbol,newcp );	
							        	}
							        }
							        if (Otherselected.equals("true"))
							        {
							        	if(!nsemap.containsKey(cp.getStocksymbol()))
							        	{
							        		newcp = getresult(cp,diffselected,smaselected,ppselected,s1selected,s2selected,s3selected,s4selected,r1selected,r2selected,r3selected,r4selected);
							        		if (newcp != null)
							        			finalmap.put(stocksymbol,newcp );		
							        	}
							        		// finalmap.put(stocksymbol, getresult(cp,diffselected,smaselected,ppselected,s1selected,s2selected,s3selected,s4selected,r1selected,r2selected,r3selected,r4selected));	
							        }
							        
							        
							     }
								request.setAttribute("currentpricepivotsmabreak",finalmap );
								request.setAttribute("cpsbreaknse", nseselected);
								request.setAttribute("cpsbreakothers", Otherselected);
								request.setAttribute("cpsperslide",diffselected);
								request.setAttribute("CPSbreakSMA", smaselected);
								request.setAttribute("CPSbreakPP", ppselected);
								request.setAttribute("CPSbreakS1", s1selected);
								request.setAttribute("CPSbreakS2", s2selected);
								request.setAttribute("CPSbreakS3", s3selected);
								request.setAttribute("CPSbreakS4", s4selected);
								request.setAttribute("CPSbreakR1", r1selected);
								request.setAttribute("CPSbreakR2", r2selected);
								request.setAttribute("CPSbreakR3", r3selected);
								request.setAttribute("CPSbreakR4", r4selected);
								
							}
					else
					{
			
					request.setAttribute("currentpricepivotsmabreak",CPSbreakmap );
					request.setAttribute("cpsbreaknse", "true");
					request.setAttribute("cpsbreakothers", "false");
					request.setAttribute("CPSbreakSMA", "false");
					request.setAttribute("CPSbreakPP", "false");
					request.setAttribute("CPSbreakS1", "true");
					request.setAttribute("CPSbreakS2", "true");
					request.setAttribute("CPSbreakS3", "true");
					request.setAttribute("CPSbreakS4", "true");
					request.setAttribute("CPSbreakR1", "false");
					request.setAttribute("CPSbreakR2", "false");
					request.setAttribute("CPSbreakR3", "false");
					request.setAttribute("CPSbreakR4", "false");
					request.setAttribute("cpsperslide","10");
					}
					
					
					request.getRequestDispatcher("CURRENTWeekSMAorPivotbreakV1.jsp").forward(request, response);
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
		session.setAttribute("CPSbreaknse200selection", request.getParameter("CPSbreaknse200attr"));
		session.setAttribute("CPSbreakotherselection", request.getParameter("CPSbreakotherattr"));
		session.setAttribute("CPSbreakdiffselection", request.getParameter("CPSbreakdiffattr"));
		session.setAttribute("CPSbreakoptionSMAselection", request.getParameter("CPSbreakoptionSMA"));
		session.setAttribute("CPSbreakoptionPPselection", request.getParameter("CPSbreakoptionPP"));
		session.setAttribute("CPSbreakoptionS1selection", request.getParameter("CPSbreakoptionS1"));
		session.setAttribute("CPSbreakoptionS2selection", request.getParameter("CPSbreakoptionS2"));
		session.setAttribute("CPSbreakoptionS3selection", request.getParameter("CPSbreakoptionS3"));
		session.setAttribute("CPSbreakoptionS4selection", request.getParameter("CPSbreakoptionS4"));
		session.setAttribute("CPSbreakoptionR1selection", request.getParameter("CPSbreakoptionR1"));
		session.setAttribute("CPSbreakoptionR2selection", request.getParameter("CPSbreakoptionR2"));
		session.setAttribute("CPSbreakoptionR3selection", request.getParameter("CPSbreakoptionR3"));
		session.setAttribute("CPSbreakoptionR4selection", request.getParameter("CPSbreakoptionR4"));
		
		response.setContentType("text/html;charset=UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    
	    /*
	    String paramtest = "This is paramtest!!!!! \n"+"CPSbreaknse200attr: "+(request.getParameter("CPSbreaknse200attr"))+"\n"+
	    		"CPSbreakotherattr: "+	(request.getParameter("CPSbreakotherattr"))+"\n"+
	    		"CPSbreakdiffattr:"+		(request.getParameter("CPSbreakdiffattr"))+"\n"+
	    			"CPSbreakoptionSMA"	+(request.getParameter("CPSbreakoptionSMA"))+"\n"+
	    			"CPSbreakoptionPP"	+(request.getParameter("CPSbreakoptionPP"))+"\n"+
	    			"CPSbreakoptionS1"	+(request.getParameter("CPSbreakoptionS1"))+"\n"+
	    			"CPSbreakoptionS2"	+(request.getParameter("CPSbreakoptionS2"))+"\n"+
	    			"CPSbreakoptionS3"	+(request.getParameter("CPSbreakoptionS3"))+"\n"+
	    			"CPSbreakoptionS4"	+(request.getParameter("CPSbreakoptionS4"))+"\n"+
	    			"CPSbreakoptionR1"	+(request.getParameter("CPSbreakoptionR1"))+"\n"+
	    			"CPSbreakoptionR2"	+(request.getParameter("CPSbreakoptionR2"))+"\n"+
	    			"CPSbreakoptionR3"	+(request.getParameter("CPSbreakoptionR3"))+"\n"+
	    			"CPSbreakoptionR4"	+(request.getParameter("CPSbreakoptionR4"))+"\n";
	    
		
	    response.getWriter().write(paramtest);
		*/
		doGet(request, response);
	}

}
