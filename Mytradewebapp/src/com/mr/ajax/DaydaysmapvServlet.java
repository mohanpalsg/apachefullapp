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
 * Servlet implementation class DaydaysmapvServlet
 */
@WebServlet("/DaydaysmapvServlet")
public class DaydaysmapvServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DaydaysmapvServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true); 
		HashMap <String,Stocktwodaystat> statsmap = (HashMap <String,Stocktwodaystat>) getServletContext().getAttribute("StockDaytwodaystats");
		HashMap <String,Pricedata> Currmap = (HashMap <String,Pricedata>) session.getAttribute("rtCurrentpricemap");
		HashMap <String,Daysma50> sma50map = (HashMap<String, Daysma50>) getServletContext().getAttribute("Daysma50");
		HashMap <String,Daypivot> daypivotmap = (HashMap<String, Daypivot>) getServletContext().getAttribute("Daypivot");
		HashMap <String,Filteredsmapivotdata> currentpricebreakmap = new HashMap <String,Filteredsmapivotdata>();
		HashMap <String,Filteredsmapivotdata> resultmap = new  HashMap <String,Filteredsmapivotdata>();
		 HashMap <String,String> nsemap = (HashMap <String,String>) getServletContext().getAttribute("nse200");
		 
		System.out.println("entered currentday break class");
		if(session.getAttribute("rtCurrentpricemap") == null)
		{
			String text = "Enable LiveData to use this feature";

		    response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
		    response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
		    response.getWriter().write(text);
		}
		else
		{
		
			
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
		        		if(daypivotmap.containsKey(stocksymbol) && Currmap.containsKey(stocksymbol))
				        {
				        	
				        	Daypivot daypv = daypivotmap.get(stocksymbol);
				        	
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
			        			Filteredsmapivotdata filterdata = new Filteredsmapivotdata();
			        			filterdata.setStocksymbol(stocksymbol);
			        			filterdata.setCurrclose(curprice.getLastprice());
			        			filterdata.setPrevclose(sw2.getCURR_CLOSE());
			        			filterdata.setPivotlevel(level);
			        			filterdata.setPivotvalue(Double.valueOf(df.format(levelprice)));
			        			filterdata.setSmavalue(Double.valueOf(df.format(sma50.getMovingavg())));
			        			filterdata.setTradedate(curprice.getTradedate());
			        			filterdata.setTradevolume(curprice.getTradevolume());
			        			filterdata.setSmadiff(Double.valueOf(df.format(((curprice.getHighprice()-sma50.getMovingavg())/sma50.getMovingavg())*100)));
			        			filterdata.setPivotdiff(Double.valueOf(df.format(((curprice.getHighprice()-levelprice)/levelprice)*100)));
			        			if(nsemap.containsKey(stocksymbol))
			        			filterdata.setNse200(true);
			        			else
			        			filterdata.setNse200(false);	
			        			currentpricebreakmap.put(stocksymbol,filterdata);
			        		}
				        }
		        	}
		        }
			} // primary entry creation whileloop;
			
	 if (session.getAttribute("CurrentDaysmapvnse200attr")!=null)
	 {
		 String nse200selected = (String) session.getAttribute("CurrentDaysmapvnse200attr");
		 String otherselected = (String) session.getAttribute("CurrentDaysmapvotherattr");
		 String 	pivotdiff = (String)session.getAttribute("CurrentDaysmapvpivotdiffattr");
		 String 	smadiff = (String)session.getAttribute("CurrentDaysmapvsmadiffattr");
		 String 	ppselected = (String)session.getAttribute("CurrentDaysmapvppcheckattr");
		 String 	s1selected = (String)session.getAttribute("CurrentDaysmapvs1checkattr");
		 String 	s2selected = (String)session.getAttribute("CurrentDaysmapvs2checkattr");
		 String 	s3selected = (String)session.getAttribute("CurrentDaysmapvs3checkattr");
		 String 	s4selected = (String)session.getAttribute("CurrentDaysmapvs4checkattr");
		 String 	r1selected = (String)session.getAttribute("CurrentDaysmapvr1checkattr");
		 String 	r2selected = (String)session.getAttribute("CurrentDaysmapvr2checkattr");
		 String 	r3selected = (String)session.getAttribute("CurrentDaysmapvr3checkattr");
		 String 	r4selected = (String)session.getAttribute("CurrentDaysmapvr4checkattr");
			
		 
		 System.out.println(session.getAttribute("CurrentDaysmapvnse200attr"));
		 System.out.println(session.getAttribute("CurrentDaysmapvotherattr"));
		 System.out.println(session.getAttribute("CurrentDaysmapvpivotdiffattr"));
		 System.out.println((String)session.getAttribute("CurrentDaysmapvsmadiffattr"));
			 System.out.println( (String)session.getAttribute("CurrentDaysmapvppcheckattr"));
		 System.out.println((String)session.getAttribute("CurrentDaysmapvs1checkattr"));
				 System.out.println( (String)session.getAttribute("CurrentDaysmapvs2checkattr"));
			 System.out.println( (String)session.getAttribute("CurrentDaysmapvs3checkattr"));
		 System.out.println((String)session.getAttribute("CurrentDaysmapvs4checkattr"));
			 System.out.println((String)session.getAttribute("CurrentDaysmapvr1checkattr"));
				 System.out.println((String)session.getAttribute("CurrentDaysmapvr2checkattr"));
		 System.out.println( (String)session.getAttribute("CurrentDaysmapvr3checkattr"));
		 		 System.out.println((String)session.getAttribute("CurrentDaysmapvr4checkattr"));
		 
			Iterator it1 = currentpricebreakmap.entrySet().iterator();
			System.out.println(currentpricebreakmap.size());
			 while (it1.hasNext()) {
				 Map.Entry pair = (Map.Entry)it1.next();
				 Filteredsmapivotdata fv = (Filteredsmapivotdata) pair.getValue();
				 System.out.println(fv.getStocksymbol());
				if (nse200selected.equals("true"))
				 {
					 System.out.println(fv.getStocksymbol());
					 if(fv.getSmadiff() <= new Double(smadiff).doubleValue() && fv.getPivotdiff() <= new Double(pivotdiff).doubleValue())
					 {
						 if(nsemap.containsKey(fv.getStocksymbol()))
						 {   Filteredsmapivotdata newfv = new Filteredsmapivotdata();
							  newfv = getselectedlevel(ppselected,s1selected,s2selected,s3selected,s4selected,r1selected,r2selected,r3selected,r4selected,fv);
						 if (newfv != null)	 
						 resultmap.put(fv.getStocksymbol(), newfv);
						 }
					 }
					 
					 
					 
				 }
				 if (otherselected.equals("true"))
				 {
					 System.out.println(fv.getStocksymbol());
					 if(fv.getSmadiff() <= new Double(smadiff).doubleValue() && fv.getPivotdiff() <= new Double(pivotdiff).doubleValue())
					 {
						 if(! nsemap.containsKey(fv.getStocksymbol()))
						 { 
							 Filteredsmapivotdata newfv = new Filteredsmapivotdata();
							 newfv = getselectedlevel(ppselected,s1selected,s2selected,s3selected,s4selected,r1selected,r2selected,r3selected,r4selected,fv);
					     if (newfv != null)	 
						 resultmap.put(fv.getStocksymbol(), newfv);
						 }
					 }
				 }
				
				 
			 }
			 
			 System.out.println(resultmap.size());
			 request.setAttribute("daycurrentpriceallbreakstocklist",resultmap );
			 request.setAttribute("cnsechecked",nse200selected);
			 request.setAttribute("cotherchecked", otherselected);
			 request.setAttribute("csmadiffval", smadiff);
			 request.setAttribute("cpivotdiffval", pivotdiff);
			 request.setAttribute("cCurrentDaysma50pvPP", ppselected);
			 request.setAttribute("cCurrentDaysma50pvS1", s1selected);
			 request.setAttribute("cCurrentDaysma50pvS2", s2selected);
			 request.setAttribute("cCurrentDaysma50pvS3", s3selected);
			 request.setAttribute("cCurrentDaysma50pvS4", s4selected);
			 request.setAttribute("cCurrentDaysma50pvR1", r1selected);
			 request.setAttribute("cCurrentDaysma50pvR2", r2selected);
			 request.setAttribute("cCurrentDaysma50pvR3", r3selected);
			 request.setAttribute("cCurrentDaysma50pvR4", r4selected);
	 } // param exists end
	 else
	 {
		 request.setAttribute("daycurrentpriceallbreakstocklist",currentpricebreakmap ); //add all entry
			request.setAttribute("cnsechecked", "true");
			 request.setAttribute("cotherchecked", "false");
			 request.setAttribute("csmadiffval", "10");
			 request.setAttribute("cpivotdiffval", "10");
			 request.setAttribute("cCurrentDaysma50pvPP", "false");
			 request.setAttribute("cCurrentDaysma50pvS1", "true");
			 request.setAttribute("cCurrentDaysma50pvS2", "true");
			 request.setAttribute("cCurrentDaysma50pvS3", "true");
			 request.setAttribute("cCurrentDaysma50pvS4", "true");
			 request.setAttribute("cCurrentDaysma50pvR1", "false");
			 request.setAttribute("cCurrentDaysma50pvR2", "false");
			 request.setAttribute("cCurrentDaysma50pvR3", "false");
			 request.setAttribute("cCurrentDaysma50pvR4", "false");
	 } 
	 session.setAttribute("DayLiveSize", currentpricebreakmap.size());
		request.setAttribute("DayLiveSizerq",currentpricebreakmap.size());// param not exists
	 request.getRequestDispatcher("CURRENTDaySMAandPivotbreakV1.jsp").forward(request, response);// else end
	 	}
		
	}

	private Filteredsmapivotdata getselectedlevel(String ppselected, String s1selected, String s2selected,
			String s3selected, String s4selected, String r1selected, String r2selected, String r3selected,
			String r4selected, Filteredsmapivotdata fv) {
		// TODO Auto-generated method stub
		if (ppselected.equals("true") && fv.getPivotlevel().equals("PP"))
			return fv;
		if (s1selected.equals("true") && fv.getPivotlevel().equals("S1"))
			return fv;
		if (s2selected.equals("true") && fv.getPivotlevel().equals("S2"))
			return fv;
		if (s3selected.equals("true") && fv.getPivotlevel().equals("S3"))
			return fv;
		if (s4selected.equals("true") && fv.getPivotlevel().equals("S4"))
			return fv;
		if (r1selected.equals("true") && fv.getPivotlevel().equals("R1"))
			return fv;
		if (r2selected.equals("true") && fv.getPivotlevel().equals("R2"))
			return fv;
		if (r3selected.equals("true") && fv.getPivotlevel().equals("R3"))
			return fv;
		if (r4selected.equals("true") && fv.getPivotlevel().equals("R4"))
			return fv;
		
		
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("request hitting daysmaservlet");
		
		 HttpSession session = request.getSession(); 

		 session.setAttribute("CurrentDaysmapvnse200attr",request.getParameter("cnse200attr"));
		 session.setAttribute("CurrentDaysmapvotherattr",request.getParameter("cotherattr"));
		 session.setAttribute("CurrentDaysmapvpivotdiffattr",request.getParameter("cpivotdiffattr"));
		 session.setAttribute("CurrentDaysmapvsmadiffattr",request.getParameter("csmadiffattr"));
		 session.setAttribute("CurrentDaysmapvppcheckattr",request.getParameter("cppcheckattr"));
		 session.setAttribute("CurrentDaysmapvs1checkattr",request.getParameter("cs1checkattr"));
		 session.setAttribute("CurrentDaysmapvs2checkattr",request.getParameter("cs2checkattr"));
		 session.setAttribute("CurrentDaysmapvs3checkattr",request.getParameter("cs3checkattr"));
		 session.setAttribute("CurrentDaysmapvs4checkattr",request.getParameter("cs4checkattr"));
		 session.setAttribute("CurrentDaysmapvr1checkattr",request.getParameter("cr1checkattr"));
		 session.setAttribute("CurrentDaysmapvr2checkattr",request.getParameter("cr2checkattr"));
		 session.setAttribute("CurrentDaysmapvr3checkattr",request.getParameter("cr3checkattr"));
		 session.setAttribute("CurrentDaysmapvr4checkattr",request.getParameter("cr4checkattr"));
		 
		 
		
		 		 
		 
		 doGet(request, response);
		
		 
		 
	}

}
