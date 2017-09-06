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

import com.mr.candlepattern.Candledata;
import com.mr.candlepattern.Candlepattern;
import com.mr.data.Stocktwomonthstat;

/**
 * Servlet implementation class CandlePatternMonthServlet
 */
@WebServlet("/CandlePatternMonthServlet")
public class CandlePatternMonthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CandlePatternMonthServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		HashMap <String,Stocktwomonthstat> statsmap = (HashMap <String,Stocktwomonthstat>) getServletContext().getAttribute("Stocktwomonthstats");
		HashMap <String,String> nsemap = (HashMap <String,String>) getServletContext().getAttribute("nse200");
		
		Candlepattern cpattern = new Candlepattern();
		
		if(session.getAttribute("CandlePatternmonthlistattr")!= null && session.getAttribute("CandleMonthnseselection")!=null)
		{
			String nseselected = (String) session.getAttribute("CandleMonthnseselection");
			String otherselected = (String) session.getAttribute("CandleMonthOtherselection");
			
			System.out.println(nseselected + ": nseelceted");
			String dojiselected = (String) session.getAttribute("CandleMonthdojiselection");
			String beselected = (String) session.getAttribute("CandleMonthbeselection");
			String hamselected = (String) session.getAttribute("CandleMonthhamselection");
			String bhselected = (String) session.getAttribute("CandleMonthbhselection");
			String bkselected = (String) session.getAttribute("CandleMonthbkselection");
			
			System.out.println(nsemap.size() + ": nsemapsize");
			HashMap <String,Candledata> fullmap = (HashMap <String,Candledata>)session.getAttribute("CandlePatternmonthlistattr"); 
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
			request.setAttribute("CandleMonthnse",nseselected);
			request.setAttribute("CandleMonthother",otherselected);
			request.setAttribute("CandleMonthdoji",dojiselected);
			request.setAttribute("CandleMonthBullishEngulfing",beselected);
			request.setAttribute("CandleMonthHammer",hamselected);
			request.setAttribute("CandleMonthBullishHarami",bhselected);
			request.setAttribute("CandleMonthBullishKicker",bkselected);
			
			
			
			
			
		
			
		}
		else
		{
			HashMap <String,Candledata> resultmap = new HashMap <String,Candledata> ();
			Iterator it = statsmap.entrySet().iterator();
			while (it.hasNext()) {
			
				Map.Entry pair = (Map.Entry)it.next();
		        String stocksymbol = (String) pair.getKey();
		        Stocktwomonthstat currstat = (Stocktwomonthstat)pair.getValue();
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
			session.setAttribute("CandlePatternmonthlistattr",resultmap);
			request.setAttribute("StockList",resultmap);
			request.setAttribute("CandleMonthnse","true");
			request.setAttribute("CandleMonthother","false");
			
			request.setAttribute("CandleMonthdoji","false");
			request.setAttribute("CandleMonthBullishEngulfing","true");
			request.setAttribute("CandleMonthHammer","false");
			request.setAttribute("CandleMonthBullishHarami","false");
			request.setAttribute("CandleMonthBullishKicker","false");
			
			session.setAttribute("CandleMonthnseselection","false");
			session.setAttribute("CandleMonthOtherselection","false");
			
			session.setAttribute("CandleMonthdojiselection","false");
			session.setAttribute("CandleMonthbeselection","false");
			session.setAttribute("CandleMonthhamselection","false");
			session.setAttribute("CandleMonthbhselection","false");
			session.setAttribute("CandleMonthbkselection","false");
			
			
			
			
		}
		
		
		request.getRequestDispatcher("CandlePatternMonth.jsp").forward(request, response);
		
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
		session.setAttribute("CandleMonthnseselection",request.getParameter("candlemonthnseattr"));
		session.setAttribute("CandleMonthOtherselection",request.getParameter("candlemonthotherattr"));
		
		session.setAttribute("CandleMonthdojiselection",request.getParameter("candlemonthdojiattr"));
		session.setAttribute("CandleMonthbeselection",request.getParameter("candlemonthbeattr"));
		session.setAttribute("CandleMonthhamselection",request.getParameter("candlemonthhamattr"));
		session.setAttribute("CandleMonthbhselection",request.getParameter("candlemonthbhattr"));
		session.setAttribute("CandleMonthbkselection",request.getParameter("candlemonthbkeattr"));
		
		
		  
          
          
          
          
         
		doGet(request, response);
	}


}
