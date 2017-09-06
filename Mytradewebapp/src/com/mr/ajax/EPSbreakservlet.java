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
import com.mr.data.Stocktwoweekstat;
import com.mr.data.Weekpivot;
import com.mr.data.Weeksma50;

/**
 * Servlet implementation class EPSbreakservlet
 */
@WebServlet("/EPSbreakservlet")
public class EPSbreakservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EPSbreakservlet() {
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
		if (session.getAttribute("EPSbreaknse200selection") !=null &&
		session.getAttribute("EPSbreakotherselection") !=null &&
		session.getAttribute("EPSbreakdiffselection") !=null )
		{
			DecimalFormat df = new DecimalFormat("#.##"); 
			String nseselected = (String) session.getAttribute("EPSbreaknse200selection");
			String Otherselected = (String) session.getAttribute("EPSbreakotherselection");
			String diffselected = (String) session.getAttribute("EPSbreakdiffselection") ;
			String smaselected = (String)session.getAttribute("EPSbreakoptionSMAselection");
			String ppselected =(String)session.getAttribute("EPSbreakoptionPPselection");
			String s1selected =(String)session.getAttribute("EPSbreakoptionS1selection");
			String s2selected =(String)session.getAttribute("EPSbreakoptionS2selection");
			String s3selected =(String)session.getAttribute("EPSbreakoptionS3selection");
			String s4selected =(String)session.getAttribute("EPSbreakoptionS4selection");
			String r1selected =(String)session.getAttribute("EPSbreakoptionR1selection");
			String r2selected =(String)session.getAttribute("EPSbreakoptionR2selection");
			String r3selected =(String)session.getAttribute("EPSbreakoptionR3selection");
			String r4selected =(String)session.getAttribute("EPSbreakoptionR4selection");
			
			
			HashMap <String,CPSbreak> fullmap = (HashMap <String,CPSbreak>) session.getAttribute("EODpivotsmabreakattr");
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
			request.setAttribute("EODpivotsmabreak",finalmap );
			request.setAttribute("epsbreaknse", nseselected);
			request.setAttribute("epsbreakothers", Otherselected);
			request.setAttribute("epsperslide",diffselected);
			request.setAttribute("EPSbreakSMA", smaselected);
			request.setAttribute("EPSbreakPP", ppselected);
			request.setAttribute("EPSbreakS1", s1selected);
			request.setAttribute("EPSbreakS2", s2selected);
			request.setAttribute("EPSbreakS3", s3selected);
			request.setAttribute("EPSbreakS4", s4selected);
			request.setAttribute("EPSbreakR1", r1selected);
			request.setAttribute("EPSbreakR2", r2selected);
			request.setAttribute("EPSbreakR3", r3selected);
			request.setAttribute("EPSbreakR4", r4selected);
			
		}
		else
		{
		
		HashMap <String,Stocktwoweekstat> statsmap = (HashMap <String,Stocktwoweekstat>) getServletContext().getAttribute("Stocktwoweekstats");
		HashMap <String,Weeksma50> sma50map = (HashMap<String, Weeksma50>) getServletContext().getAttribute("weeksma50");
		HashMap <String,Weekpivot> weekpivotmap = (HashMap<String, Weekpivot>) getServletContext().getAttribute("Weekpivot");
		HashMap <String,CPSbreak> CPSbreakmap = new  HashMap <String,CPSbreak>();
		DecimalFormat df = new DecimalFormat("#.##"); 
		Iterator it = statsmap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
	        String stocksymbol = (String) pair.getKey();
	        if(sma50map.containsKey(stocksymbol))
	        {
	        	Stocktwoweekstat sw2 = (Stocktwoweekstat) pair.getValue();
	        	Weeksma50 sma50 = sma50map.get(stocksymbol);
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
        			filterdata.setTradevolume(sw2.getCURR_TRADEVOLUME());
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
	        if(weekpivotmap.containsKey(stocksymbol))
	        {
	        	Stocktwoweekstat sw2 = (Stocktwoweekstat) pair.getValue();
	        	Weekpivot weekpv = weekpivotmap.get(stocksymbol);
	        	String level = "nothing";
	        	double levelprice=0;
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
        			CPSbreak filterdata = new CPSbreak();
	        		filterdata.setStocksymbol(stocksymbol);
        			filterdata.setCurrclose(sw2.getCURR_CLOSE());
        			filterdata.setPrevclose(sw2.getPREV_CLOSE());
        			filterdata.setPivotlevel(level);
        			filterdata.setPivotval(Double.valueOf(df.format(levelprice)).toString());
        			filterdata.setSmavalue("");
        			filterdata.setTradedate(sw2.getCURR_ENDATE());
        			filterdata.setTradevolume(sw2.getCURR_TRADEVOLUME());
        			filterdata.setSmadiff("");
        			filterdata.setPivotdiff(Double.valueOf(df.format(((sw2.getCURR_CLOSE()-levelprice)/sw2.getCURR_CLOSE())*100)).toString());
        			filterdata.setBreakpivot(true);
        			filterdata.setBreaksma(false);
        			CPSbreakmap.put(stocksymbol+"P.V.B", filterdata);
        		}
	        }
		}
		
		session.setAttribute("EODpivotsmabreakattr", CPSbreakmap);
		request.setAttribute("EODpivotsmabreak",CPSbreakmap );
		request.setAttribute("epsbreaknse", "true");
		request.setAttribute("epsbreakothers", "false");
		request.setAttribute("EPSbreakSMA", "false");
		request.setAttribute("EPSbreakPP", "false");
		request.setAttribute("EPSbreakS1", "true");
		request.setAttribute("EPSbreakS2", "true");
		request.setAttribute("EPSbreakS3", "true");
		request.setAttribute("EPSbreakS4", "true");
		request.setAttribute("EPSbreakR1", "false");
		request.setAttribute("EPSbreakR2", "false");
		request.setAttribute("EPSbreakR3", "false");
		request.setAttribute("EPSbreakR4", "false");
		request.setAttribute("epsperslide","10");
		
		
		
		}
		request.getRequestDispatcher("EODWeekSMAorPivotbreakV1.jsp").forward(request, response);
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
		session.setAttribute("EPSbreaknse200selection", request.getParameter("EPSbreaknse200attr"));
		session.setAttribute("EPSbreakotherselection", request.getParameter("EPSbreakotherattr"));
		session.setAttribute("EPSbreakdiffselection", request.getParameter("EPSbreakdiffattr"));
		session.setAttribute("EPSbreakoptionSMAselection", request.getParameter("EPSbreakoptionSMA"));
		session.setAttribute("EPSbreakoptionPPselection", request.getParameter("EPSbreakoptionPP"));
		session.setAttribute("EPSbreakoptionS1selection", request.getParameter("EPSbreakoptionS1"));
		session.setAttribute("EPSbreakoptionS2selection", request.getParameter("EPSbreakoptionS2"));
		session.setAttribute("EPSbreakoptionS3selection", request.getParameter("EPSbreakoptionS3"));
		session.setAttribute("EPSbreakoptionS4selection", request.getParameter("EPSbreakoptionS4"));
		session.setAttribute("EPSbreakoptionR1selection", request.getParameter("EPSbreakoptionR1"));
		session.setAttribute("EPSbreakoptionR2selection", request.getParameter("EPSbreakoptionR2"));
		session.setAttribute("EPSbreakoptionR3selection", request.getParameter("EPSbreakoptionR3"));
		session.setAttribute("EPSbreakoptionR4selection", request.getParameter("EPSbreakoptionR4"));
		
		response.setContentType("text/html;charset=UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    
	    /*
	    String paramtest = "This is paramtest!!!!! \n"+"EPSbreaknse200attr: "+(request.getParameter("EPSbreaknse200attr"))+"\n"+
	    		"EPSbreakotherattr: "+	(request.getParameter("EPSbreakotherattr"))+"\n"+
	    		"EPSbreakdiffattr:"+		(request.getParameter("EPSbreakdiffattr"))+"\n"+
	    			"EPSbreakoptionSMA"	+(request.getParameter("EPSbreakoptionSMA"))+"\n"+
	    			"EPSbreakoptionPP"	+(request.getParameter("EPSbreakoptionPP"))+"\n"+
	    			"EPSbreakoptionS1"	+(request.getParameter("EPSbreakoptionS1"))+"\n"+
	    			"EPSbreakoptionS2"	+(request.getParameter("EPSbreakoptionS2"))+"\n"+
	    			"EPSbreakoptionS3"	+(request.getParameter("EPSbreakoptionS3"))+"\n"+
	    			"EPSbreakoptionS4"	+(request.getParameter("EPSbreakoptionS4"))+"\n"+
	    			"EPSbreakoptionR1"	+(request.getParameter("EPSbreakoptionR1"))+"\n"+
	    			"EPSbreakoptionR2"	+(request.getParameter("EPSbreakoptionR2"))+"\n"+
	    			"EPSbreakoptionR3"	+(request.getParameter("EPSbreakoptionR3"))+"\n"+
	    			"EPSbreakoptionR4"	+(request.getParameter("EPSbreakoptionR4"))+"\n";
	    
		
	    response.getWriter().write(paramtest);
		*/
		doGet(request, response);
		
	}

}
