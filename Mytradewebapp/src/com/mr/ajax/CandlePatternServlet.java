package com.mr.ajax;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mr.data.Stocktwoweekstat;
import com.mr.candlepattern.Candlepattern;
import com.mr.candlepattern.Candledata;

/**
 * Servlet implementation class CandlePatternServlet
 */
@WebServlet("/CandlePatternServlet")
public class CandlePatternServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CandlePatternServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		HashMap <String,Stocktwoweekstat> statsmap = (HashMap <String,Stocktwoweekstat>) getServletContext().getAttribute("Stocktwoweekstats");
		HashMap <String,String> nsemap = (HashMap <String,String>) getServletContext().getAttribute("nse200");
		
		Candlepattern cpattern = new Candlepattern();
		
		if(session.getAttribute("CandlePatternlistattr")!= null && session.getAttribute("CandleWeeknseselection")!=null)
		{
			String nseselected = (String) session.getAttribute("CandleWeeknseselection");
			String otherselected = (String) session.getAttribute("CandleWeekOtherselection");
			
			
			String dojiselected = (String) session.getAttribute("CandleWeekdojiselection");
			String beselected = (String) session.getAttribute("CandleWeekbeselection");
			String hamselected = (String) session.getAttribute("CandleWeekhamselection");
			String bhselected = (String) session.getAttribute("CandleWeekbhselection");
			String bkselected = (String) session.getAttribute("CandleWeekbkselection");
			
			
			HashMap <String,Candledata> fullmap = (HashMap <String,Candledata>)session.getAttribute("CandlePatternlistattr"); 
			HashMap <String,Candledata> resultmap = new HashMap <String,Candledata> ();
			Iterator it = fullmap.entrySet().iterator();
			while (it.hasNext()) {
				
				Map.Entry pair = (Map.Entry)it.next();
		        String stocksymbol = (String) pair.getKey();
		        Candledata cd = (Candledata)pair.getValue();
		        if(nseselected.equals("true") && nsemap.containsKey(stocksymbol))
		        	if(checkselection(cd,dojiselected,beselected,hamselected,bhselected,bkselected))
		        	resultmap.put(stocksymbol, cd);
		        if(otherselected.equals("true") && !nsemap.containsKey(stocksymbol))
		        	if(checkselection(cd,dojiselected,beselected,hamselected,bhselected,bkselected))
		        	resultmap.put(stocksymbol, cd);
		        
			}
			
			request.setAttribute("StockList",resultmap);
			request.setAttribute("CandleWeeknse",nseselected);
			
			request.setAttribute("CandleWeekdoji",dojiselected);
			request.setAttribute("CandleWeekBullishEngulfing",beselected);
			request.setAttribute("CandleWeekHammer",hamselected);
			request.setAttribute("CandleWeekBullishHarami",bhselected);
			request.setAttribute("CandleWeekBullishKicker",bkselected);
			
			
			
			
			
			
			request.setAttribute("CandleWeekother",otherselected);
		}
		else
		{
			HashMap <String,Candledata> resultmap = new HashMap <String,Candledata> ();
			Iterator it = statsmap.entrySet().iterator();
			while (it.hasNext()) {
			
				Map.Entry pair = (Map.Entry)it.next();
		        String stocksymbol = (String) pair.getKey();
		        Stocktwoweekstat currstat = (Stocktwoweekstat)pair.getValue();
		        String pattern = cpattern.getCandlePattern(currstat);
		        if(pattern != null)
		        {
		        	Candledata cd = new Candledata();
		        	cd.setStocksymbol(stocksymbol);
		        	
		        	cd.setPrev_close(currstat.getPREV_CLOSE());
		        	cd.setPrev_open(currstat.getPREV_OPEN());
		        	cd.setPrev_high(currstat.getPREV_HIGH());
		        	cd.setPrev_low(currstat.getPREV_LOW());
		        	
		        	cd.setCurr_open(currstat.getCURR_OPEN());
		        	cd.setCurr_close(currstat.getCURR_CLOSE());
		        	cd.setCurr_high(currstat.getCURR_HIGH());
		        	cd.setCurr_low(currstat.getCURR_LOW());
		        	cd.setCandlepattern(pattern);
		        	
		        	resultmap.put(stocksymbol, cd);
		        }
		        
			}
			session.setAttribute("CandlePatternlistattr",resultmap);
			request.setAttribute("StockList",resultmap);
			request.setAttribute("CandleWeeknse","true");
			request.setAttribute("CandleWeekother","false");
			
			request.setAttribute("CandleWeekdoji","false");
			request.setAttribute("CandleWeekBullishEngulfing","true");
			request.setAttribute("CandleWeekHammer","false");
			request.setAttribute("CandleWeekBullishHarami","false");
			request.setAttribute("CandleWeekBullishKicker","false");
			
			
			
			session.setAttribute("CandleWeeknseselection","false");
			session.setAttribute("CandleWeekOtherselection","false");
			
			session.setAttribute("CandleWeekdojiselection","true");
			session.setAttribute("CandleWeekbeselection","true");
			session.setAttribute("CandleWeekhamselection","true");
			session.setAttribute("CandleWeekbhselection","true");
			session.setAttribute("CandleWeekbkselection","true");
			
		
			
			
		}
		
		
		request.getRequestDispatcher("CandlePatternWeek.jsp").forward(request, response);
		
	}

	private boolean checkselection(Candledata cd, String dojiselected, String beselected, String hamselected,
			String bhselected, String bkselected) {
		// TODO Auto-generated method stub
		
		if(dojiselected.equals("true") && cd.getCandlepattern().equals("doji"))
			return true;
		
		if(beselected.equals("true") && cd.getCandlepattern().equals("BullishEngulfing"))
			return true;
		
		if(hamselected.equals("true") && cd.getCandlepattern().equals("Hammer"))
			return true;
		
		if(bhselected.equals("true") && cd.getCandlepattern().equals("BullishHarami"))
			return true;
		
		if(bkselected.equals("true") && cd.getCandlepattern().equals("BullishKicker"))
			return true;
		
		
		return false;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		session.setAttribute("CandleWeeknseselection",request.getParameter("candleweeknseattr"));
		session.setAttribute("CandleWeekOtherselection",request.getParameter("candleweekotherattr"));
		
		session.setAttribute("CandleWeekdojiselection",request.getParameter("candleweekdojiattr"));
		session.setAttribute("CandleWeekbeselection",request.getParameter("candleweekbeattr"));
		session.setAttribute("CandleWeekhamselection",request.getParameter("candleweekhamattr"));
		session.setAttribute("CandleWeekbhselection",request.getParameter("candleweekbhattr"));
		session.setAttribute("CandleWeekbkselection",request.getParameter("candleweekbkeattr"));
		
		
		  
          
          
          
          
         
		doGet(request, response);
	}

}
