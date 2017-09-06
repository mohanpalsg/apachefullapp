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
import com.mr.data.Stocktwodaystat;
import com.mr.data.Daypivot;
import com.mr.data.Daysma50;

/**
 * Servlet implementation class DayDayEPSbreakservlet
 */
@WebServlet("/DayEPSbreakservlet")
public class DayEPSbreakservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DayEPSbreakservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true); 
		if (session.getAttribute("DayEPSbreaknse200selection") !=null &&
		session.getAttribute("DayEPSbreakotherselection") !=null &&
		session.getAttribute("DayEPSbreakdiffselection") !=null )
		{
			DecimalFormat df = new DecimalFormat("#.##"); 
			String nseselected = (String) session.getAttribute("DayEPSbreaknse200selection");
			String Otherselected = (String) session.getAttribute("DayEPSbreakotherselection");
			String diffselected = (String) session.getAttribute("DayEPSbreakdiffselection") ;
			String smaselected = (String)session.getAttribute("DayEPSbreakoptionSMAselection");
			String ppselected =(String)session.getAttribute("DayEPSbreakoptionPPselection");
			String s1selected =(String)session.getAttribute("DayEPSbreakoptionS1selection");
			String s2selected =(String)session.getAttribute("DayEPSbreakoptionS2selection");
			String s3selected =(String)session.getAttribute("DayEPSbreakoptionS3selection");
			String s4selected =(String)session.getAttribute("DayEPSbreakoptionS4selection");
			String r1selected =(String)session.getAttribute("DayEPSbreakoptionR1selection");
			String r2selected =(String)session.getAttribute("DayEPSbreakoptionR2selection");
			String r3selected =(String)session.getAttribute("DayEPSbreakoptionR3selection");
			String r4selected =(String)session.getAttribute("DayEPSbreakoptionR4selection");
			
			
			HashMap <String,CPSbreak> fullmap = (HashMap <String,CPSbreak>) session.getAttribute("EODdaypivotsmabreakattr");
			HashMap <String,CPSbreak> finalmap = new HashMap <String,CPSbreak>();
			HashMap <String,String> nsemap = (HashMap <String,String>) getServletContext().getAttribute("nse200");
			Iterator it1 = fullmap.entrySet().iterator();
			while (it1.hasNext()) {
				Map.Entry pair = (Map.Entry)it1.next();
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
			request.setAttribute("EODdaypivotsmabreak",finalmap );
			request.setAttribute("Dayepsbreaknse", nseselected);
			request.setAttribute("Dayepsbreakothers", Otherselected);
			request.setAttribute("Dayepsperslide",diffselected);
			request.setAttribute("DayEPSbreakSMA", smaselected);
			request.setAttribute("DayEPSbreakPP", ppselected);
			request.setAttribute("DayEPSbreakS1", s1selected);
			request.setAttribute("DayEPSbreakS2", s2selected);
			request.setAttribute("DayEPSbreakS3", s3selected);
			request.setAttribute("DayEPSbreakS4", s4selected);
			request.setAttribute("DayEPSbreakR1", r1selected);
			request.setAttribute("DayEPSbreakR2", r2selected);
			request.setAttribute("DayEPSbreakR3", r3selected);
			request.setAttribute("DayEPSbreakR4", r4selected);
			
		}
		else
		{
		
		HashMap <String,Stocktwodaystat> statsmap = (HashMap <String,Stocktwodaystat>) getServletContext().getAttribute("StockDaytwodaystats");
		HashMap <String,Daysma50> sma50map = (HashMap<String, Daysma50>) getServletContext().getAttribute("Daysma50");
		HashMap <String,Daypivot> daypivotmap = (HashMap<String, Daypivot>) getServletContext().getAttribute("Daypivot");
		HashMap <String,CPSbreak> CPSbreakmap = new  HashMap <String,CPSbreak>();
		DecimalFormat df = new DecimalFormat("#.##"); 
		Iterator it = statsmap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
	        String stocksymbol = (String) pair.getKey();
	        if(sma50map.containsKey(stocksymbol))
	        {
	        	Stocktwodaystat sw2 = (Stocktwodaystat) pair.getValue();
	        	Daysma50 sma50 = sma50map.get(stocksymbol);
	        	if(sw2.getPREV_CLOSE() <= sma50.getMovingavg() && sw2.getCURR_CLOSE() >= sma50.getMovingavg() )
	        	{
	        		CPSbreak filterdata = new CPSbreak();
	        		filterdata.setStocksymbol(stocksymbol);
        			filterdata.setCurrclose(sw2.getCURR_CLOSE());
        			filterdata.setPrevclose(sw2.getPREV_CLOSE());
        			filterdata.setPivotlevel("SMA");
        			filterdata.setPivotval("");
        			filterdata.setSmavalue(Double.valueOf(df.format(sma50.getMovingavg())).toString());
        			filterdata.setTradedate(sw2.getCURR_ENDATE());
        			
        			filterdata.setSmadiff(Double.valueOf(df.format(((sw2.getCURR_CLOSE()-sma50.getMovingavg())/sw2.getCURR_CLOSE())*100)).toString());
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
	        if(daypivotmap.containsKey(stocksymbol))
	        {
	        	Stocktwodaystat sw2 = (Stocktwodaystat) pair.getValue();
	        	Daypivot daypv = daypivotmap.get(stocksymbol);
	        	String level = "nothing";
	        	double levelprice=0;
	        	if (sw2.getPREV_LOW() <= daypv.getPP() && sw2.getCURR_CLOSE() >= daypv.getPP())
        		{
        			level="PP";
        			levelprice=daypv.getPP();
        		}
        		else if (sw2.getPREV_LOW() <= daypv.getS1() && sw2.getCURR_CLOSE() >= daypv.getS1())
        		{
        			level="S1";
        			levelprice=daypv.getS1();
        		}
        		else if (sw2.getPREV_LOW() <= daypv.getS2() && sw2.getCURR_CLOSE() >= daypv.getS2())
        		{
        			level="S2";
        			levelprice=daypv.getS2();
        		}
        		else if (sw2.getPREV_LOW() <= daypv.getS3() && sw2.getCURR_CLOSE() >= daypv.getS3())
        		{
        			level="S3";
        			levelprice=daypv.getS3();
        		}
        		else if (sw2.getPREV_LOW() <= daypv.getS4() && sw2.getCURR_CLOSE() >= daypv.getS4())
        		{
        			level="S4";
        			levelprice=daypv.getS4();
        		}
        		else if (sw2.getPREV_LOW() <= daypv.getR1() && sw2.getCURR_CLOSE() >= daypv.getR1())
        		{
        			level="R1";
        			levelprice=daypv.getR1();
        		}
        		else if (sw2.getPREV_LOW() <= daypv.getR2() && sw2.getCURR_CLOSE() >= daypv.getR2())
        		{
        			level="R2";
        			levelprice=daypv.getR2();
        		}
        		else if (sw2.getPREV_LOW() <= daypv.getR3() && sw2.getCURR_CLOSE() >= daypv.getR3())
        		{
        			level="R3";
        			levelprice=daypv.getR3();
        		}
        		else if (sw2.getPREV_LOW() <= daypv.getR4() && sw2.getCURR_CLOSE() >= daypv.getR4())
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
        			filterdata.setCurrclose(sw2.getCURR_CLOSE());
        			filterdata.setPrevclose(sw2.getPREV_CLOSE());
        			filterdata.setPivotlevel(level);
        			filterdata.setPivotval(Double.valueOf(df.format(levelprice)).toString());
        			filterdata.setSmavalue("");
        			filterdata.setTradedate(sw2.getCURR_ENDATE());
        			
        			filterdata.setSmadiff("");
        			filterdata.setPivotdiff(Double.valueOf(df.format(((sw2.getCURR_CLOSE()-levelprice)/sw2.getCURR_CLOSE())*100)).toString());
        			filterdata.setBreakpivot(true);
        			filterdata.setBreaksma(false);
        			CPSbreakmap.put(stocksymbol+"P.V.B", filterdata);
        		}
	        }
		}
		
		session.setAttribute("EODdaypivotsmabreakattr", CPSbreakmap);
		request.setAttribute("EODdaypivotsmabreak",CPSbreakmap );
		request.setAttribute("Dayepsbreaknse", "true");
		request.setAttribute("Dayepsbreakothers", "false");
		request.setAttribute("DayEPSbreakSMA", "false");
		request.setAttribute("DayEPSbreakPP", "false");
		request.setAttribute("DayEPSbreakS1", "true");
		request.setAttribute("DayEPSbreakS2", "true");
		request.setAttribute("DayEPSbreakS3", "true");
		request.setAttribute("DayEPSbreakS4", "true");
		request.setAttribute("DayEPSbreakR1", "false");
		request.setAttribute("DayEPSbreakR2", "false");
		request.setAttribute("DayEPSbreakR3", "false");
		request.setAttribute("DayEPSbreakR4", "false");
		request.setAttribute("Dayepsperslide","2");
		
		
		
		}
		request.getRequestDispatcher("EODDaySMAorPivotbreakV1.jsp").forward(request, response);
	}

	private CPSbreak getresult(CPSbreak cp, String diffselected, String smaselected, String ppselected, String s1selected,
			String s2selected, String s3selected, String s4selected, String r1selected, String r2selected,
			String r3selected, String r4selected) {
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
		session.setAttribute("DayEPSbreaknse200selection", request.getParameter("DayEPSbreaknse200attr"));
		session.setAttribute("DayEPSbreakotherselection", request.getParameter("DayEPSbreakotherattr"));
		session.setAttribute("DayEPSbreakdiffselection", request.getParameter("DayEPSbreakdiffattr"));
		session.setAttribute("DayEPSbreakoptionSMAselection", request.getParameter("DayEPSbreakoptionSMA"));
		session.setAttribute("DayEPSbreakoptionPPselection", request.getParameter("DayEPSbreakoptionPP"));
		session.setAttribute("DayEPSbreakoptionS1selection", request.getParameter("DayEPSbreakoptionS1"));
		session.setAttribute("DayEPSbreakoptionS2selection", request.getParameter("DayEPSbreakoptionS2"));
		session.setAttribute("DayEPSbreakoptionS3selection", request.getParameter("DayEPSbreakoptionS3"));
		session.setAttribute("DayEPSbreakoptionS4selection", request.getParameter("DayEPSbreakoptionS4"));
		session.setAttribute("DayEPSbreakoptionR1selection", request.getParameter("DayEPSbreakoptionR1"));
		session.setAttribute("DayEPSbreakoptionR2selection", request.getParameter("DayEPSbreakoptionR2"));
		session.setAttribute("DayEPSbreakoptionR3selection", request.getParameter("DayEPSbreakoptionR3"));
		session.setAttribute("DayEPSbreakoptionR4selection", request.getParameter("DayEPSbreakoptionR4"));
		
		response.setContentType("text/html;charset=UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    
	    /*
	    String paramtest = "This is paramtest!!!!! \n"+"DayEPSbreaknse200attr: "+(request.getParameter("DayEPSbreaknse200attr"))+"\n"+
	    		"DayEPSbreakotherattr: "+	(request.getParameter("DayEPSbreakotherattr"))+"\n"+
	    		"DayEPSbreakdiffattr:"+		(request.getParameter("DayEPSbreakdiffattr"))+"\n"+
	    			"DayEPSbreakoptionSMA"	+(request.getParameter("DayEPSbreakoptionSMA"))+"\n"+
	    			"DayEPSbreakoptionPP"	+(request.getParameter("DayEPSbreakoptionPP"))+"\n"+
	    			"DayEPSbreakoptionS1"	+(request.getParameter("DayEPSbreakoptionS1"))+"\n"+
	    			"DayEPSbreakoptionS2"	+(request.getParameter("DayEPSbreakoptionS2"))+"\n"+
	    			"DayEPSbreakoptionS3"	+(request.getParameter("DayEPSbreakoptionS3"))+"\n"+
	    			"DayEPSbreakoptionS4"	+(request.getParameter("DayEPSbreakoptionS4"))+"\n"+
	    			"DayEPSbreakoptionR1"	+(request.getParameter("DayEPSbreakoptionR1"))+"\n"+
	    			"DayEPSbreakoptionR2"	+(request.getParameter("DayEPSbreakoptionR2"))+"\n"+
	    			"DayEPSbreakoptionR3"	+(request.getParameter("DayEPSbreakoptionR3"))+"\n"+
	    			"DayEPSbreakoptionR4"	+(request.getParameter("DayEPSbreakoptionR4"))+"\n";
	    
		
	    response.getWriter().write(paramtest);
		*/
		doGet(request, response);
		
	}


}
