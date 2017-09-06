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
 * Servlet implementation class DayweeksmapvServlet
 */
@WebServlet("/DayweeksmapvServlet")
public class DayweeksmapvServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DayweeksmapvServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true); 
		HashMap <String,Stocktwoweekstat> statsmap = (HashMap <String,Stocktwoweekstat>) getServletContext().getAttribute("Stocktwoweekstats");
		HashMap <String,Pricedata> Currmap = (HashMap <String,Pricedata>) session.getAttribute("rtCurrentpricemap");
		HashMap <String,Weeksma50> sma50map = (HashMap<String, Weeksma50>) getServletContext().getAttribute("weeksma50");
		HashMap <String,Weekpivot> weekpivotmap = (HashMap<String, Weekpivot>) getServletContext().getAttribute("Weekpivot");
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
		        	Stocktwoweekstat sw2 = (Stocktwoweekstat) pair.getValue();
		        	Weeksma50 sma50 = sma50map.get(stocksymbol);
		        	Pricedata curprice = Currmap.get(stocksymbol);
		        	if(sw2.getCURR_CLOSE() <= sma50.getMovingavg() && curprice.getHighprice() >= sma50.getMovingavg() )
		        	{
		        		if(weekpivotmap.containsKey(stocksymbol) && Currmap.containsKey(stocksymbol))
				        {
				        	
				        	Weekpivot weekpv = weekpivotmap.get(stocksymbol);
				        	
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
			
	 if (session.getAttribute("CurrentWeeksmapvnse200attr")!=null)
	 {
		 String nse200selected = (String) session.getAttribute("CurrentWeeksmapvnse200attr");
		 String otherselected = (String) session.getAttribute("CurrentWeeksmapvotherattr");
		 String 	pivotdiff = (String)session.getAttribute("CurrentWeeksmapvpivotdiffattr");
		 String 	smadiff = (String)session.getAttribute("CurrentWeeksmapvsmadiffattr");
		 String 	ppselected = (String)session.getAttribute("CurrentWeeksmapvppcheckattr");
		 String 	s1selected = (String)session.getAttribute("CurrentWeeksmapvs1checkattr");
		 String 	s2selected = (String)session.getAttribute("CurrentWeeksmapvs2checkattr");
		 String 	s3selected = (String)session.getAttribute("CurrentWeeksmapvs3checkattr");
		 String 	s4selected = (String)session.getAttribute("CurrentWeeksmapvs4checkattr");
		 String 	r1selected = (String)session.getAttribute("CurrentWeeksmapvr1checkattr");
		 String 	r2selected = (String)session.getAttribute("CurrentWeeksmapvr2checkattr");
		 String 	r3selected = (String)session.getAttribute("CurrentWeeksmapvr3checkattr");
		 String 	r4selected = (String)session.getAttribute("CurrentWeeksmapvr4checkattr");
			
		 
		 System.out.println(session.getAttribute("CurrentWeeksmapvnse200attr"));
		 System.out.println(session.getAttribute("CurrentWeeksmapvotherattr"));
		 System.out.println(session.getAttribute("CurrentWeeksmapvpivotdiffattr"));
		 System.out.println((String)session.getAttribute("CurrentWeeksmapvsmadiffattr"));
			 System.out.println( (String)session.getAttribute("CurrentWeeksmapvppcheckattr"));
		 System.out.println((String)session.getAttribute("CurrentWeeksmapvs1checkattr"));
				 System.out.println( (String)session.getAttribute("CurrentWeeksmapvs2checkattr"));
			 System.out.println( (String)session.getAttribute("CurrentWeeksmapvs3checkattr"));
		 System.out.println((String)session.getAttribute("CurrentWeeksmapvs4checkattr"));
			 System.out.println((String)session.getAttribute("CurrentWeeksmapvr1checkattr"));
				 System.out.println((String)session.getAttribute("CurrentWeeksmapvr2checkattr"));
		 System.out.println( (String)session.getAttribute("CurrentWeeksmapvr3checkattr"));
		 		 System.out.println((String)session.getAttribute("CurrentWeeksmapvr4checkattr"));
		 
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
			 request.setAttribute("currentpriceallbreakstocklist",resultmap );
			 request.setAttribute("cnsechecked",nse200selected);
			 request.setAttribute("cotherchecked", otherselected);
			 request.setAttribute("csmadiffval", smadiff);
			 request.setAttribute("cpivotdiffval", pivotdiff);
			 request.setAttribute("cCurrentWeeksma50pvPP", ppselected);
			 request.setAttribute("cCurrentWeeksma50pvS1", s1selected);
			 request.setAttribute("cCurrentWeeksma50pvS2", s2selected);
			 request.setAttribute("cCurrentWeeksma50pvS3", s3selected);
			 request.setAttribute("cCurrentWeeksma50pvS4", s4selected);
			 request.setAttribute("cCurrentWeeksma50pvR1", r1selected);
			 request.setAttribute("cCurrentWeeksma50pvR2", r2selected);
			 request.setAttribute("cCurrentWeeksma50pvR3", r3selected);
			 request.setAttribute("cCurrentWeeksma50pvR4", r4selected);
	 } // param exists end
	 else
	 {
		 request.setAttribute("currentpriceallbreakstocklist",currentpricebreakmap ); //add all entry
			request.setAttribute("cnsechecked", "true");
			 request.setAttribute("cotherchecked", "false");
			 request.setAttribute("csmadiffval", "10");
			 request.setAttribute("cpivotdiffval", "10");
			 request.setAttribute("cCurrentWeeksma50pvPP", "false");
			 request.setAttribute("cCurrentWeeksma50pvS1", "true");
			 request.setAttribute("cCurrentWeeksma50pvS2", "true");
			 request.setAttribute("cCurrentWeeksma50pvS3", "true");
			 request.setAttribute("cCurrentWeeksma50pvS4", "true");
			 request.setAttribute("cCurrentWeeksma50pvR1", "false");
			 request.setAttribute("cCurrentWeeksma50pvR2", "false");
			 request.setAttribute("cCurrentWeeksma50pvR3", "false");
			 request.setAttribute("cCurrentWeeksma50pvR4", "false");
	 } 
	 session.setAttribute("LiveSize", currentpricebreakmap.size());
		request.setAttribute("LiveSizerq",currentpricebreakmap.size());// param not exists
	 request.getRequestDispatcher("CURRENTWeekSMAandPivotbreakV1.jsp").forward(request, response);// else end
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
		System.out.println("request hitting weeksmaservlet");
		
		 HttpSession session = request.getSession(); 

		 session.setAttribute("CurrentWeeksmapvnse200attr",request.getParameter("cnse200attr"));
		 session.setAttribute("CurrentWeeksmapvotherattr",request.getParameter("cotherattr"));
		 session.setAttribute("CurrentWeeksmapvpivotdiffattr",request.getParameter("cpivotdiffattr"));
		 session.setAttribute("CurrentWeeksmapvsmadiffattr",request.getParameter("csmadiffattr"));
		 session.setAttribute("CurrentWeeksmapvppcheckattr",request.getParameter("cppcheckattr"));
		 session.setAttribute("CurrentWeeksmapvs1checkattr",request.getParameter("cs1checkattr"));
		 session.setAttribute("CurrentWeeksmapvs2checkattr",request.getParameter("cs2checkattr"));
		 session.setAttribute("CurrentWeeksmapvs3checkattr",request.getParameter("cs3checkattr"));
		 session.setAttribute("CurrentWeeksmapvs4checkattr",request.getParameter("cs4checkattr"));
		 session.setAttribute("CurrentWeeksmapvr1checkattr",request.getParameter("cr1checkattr"));
		 session.setAttribute("CurrentWeeksmapvr2checkattr",request.getParameter("cr2checkattr"));
		 session.setAttribute("CurrentWeeksmapvr3checkattr",request.getParameter("cr3checkattr"));
		 session.setAttribute("CurrentWeeksmapvr4checkattr",request.getParameter("cr4checkattr"));
		 
		 
		
		 		 
		 
		 doGet(request, response);
		
		 
		 
	}

}
