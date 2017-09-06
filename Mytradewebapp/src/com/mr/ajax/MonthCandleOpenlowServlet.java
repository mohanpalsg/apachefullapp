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
import com.mr.data.Stocktwomonthstat;

/**
 * Servlet implementation class MonthCandleOpenlowServlet
 */
@WebServlet("/MonthCandleOpenlowServlet")
public class MonthCandleOpenlowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MonthCandleOpenlowServlet() {
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
		
       
		
		if(session.getAttribute("CandleMonthopenlowlistattr")!= null && session.getAttribute("CandleMonthopenlownseselection")!=null)
		{
			String nseselected = (String) session.getAttribute("CandleMonthopenlownseselection");
			String otherselected = (String) session.getAttribute("CandleMonthopenlowOtherselection");
			HashMap <String,Candledata> fullmap = (HashMap <String,Candledata>)session.getAttribute("CandleMonthopenlowlistattr"); 
			HashMap <String,Candledata> resultmap = new HashMap <String,Candledata> ();
			Iterator it = fullmap.entrySet().iterator();
			while (it.hasNext()) {
				
				Map.Entry pair = (Map.Entry)it.next();
		        String stocksymbol = (String) pair.getKey();
		        Candledata cd = (Candledata)pair.getValue();
		        if(nseselected.equals("true") && nsemap.containsKey(cd.getStocksymbol()))
		        	resultmap.put(stocksymbol, cd);
		        if(otherselected.equals("true") && !nsemap.containsKey(cd.getStocksymbol()))
		        	resultmap.put(stocksymbol, cd);
		        
			}
			
			request.setAttribute("openlowStockList",resultmap);
			request.setAttribute("CandleMonthopenlownse",nseselected);
			request.setAttribute("CandleMonthopenlowother",otherselected);
			
			
		}
		else
		{
			HashMap <String,Candledata> resultmap = new HashMap <String,Candledata> ();
			Iterator it = statsmap.entrySet().iterator();
			while (it.hasNext()) {
			
				Map.Entry pair = (Map.Entry)it.next();
		        String stocksymbol = (String) pair.getKey();
		        Stocktwomonthstat currstat = (Stocktwomonthstat)pair.getValue();
		        
		        if(((currstat.getCURR_OPEN()-currstat.getCURR_LOW())/currstat.getCURR_LOW()) <= 0.0025)
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
		        	cd.setMatch("LowOpen");
		        	
		        	resultmap.put(stocksymbol+"L.O", cd);
		        }
		        
		        if(((currstat.getCURR_HIGH()-currstat.getCURR_CLOSE())/currstat.getCURR_CLOSE()) <= 0.0025)
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
				        	cd.setMatch("HighClose");
				        	
				        	resultmap.put(stocksymbol+"H.C", cd);
				        }
		        
		        
		        
		        
			}
			
			request.setAttribute("openlowStockList",resultmap);
			request.setAttribute("CandleMonthopenlownse","true");
			request.setAttribute("CandleMonthopenlowother","true");
			session.setAttribute("CandleMonthopenlowlistattr",resultmap);
			
			
		}
		
		request.getRequestDispatcher("CandleMonthopenlow.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		session.setAttribute("CandleMonthopenlownseselection",request.getParameter("candlemonthopenlownseattr"));
		session.setAttribute("CandleMonthopenlowOtherselection",request.getParameter("candlemonthopenlowotherattr"));
		
		doGet(request, response);
	}

}
