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

import com.mr.data.StockOtherTechnicals;
import com.mr.data.Weekpivot;
import com.mr.hourlydata.Hourdatadownloader;

/**
 * Servlet implementation class EODtechnicalfilershowliveServlet
 */
@WebServlet("/EODtechnicalfilershowliveServlet")
public class EODtechnicalfilershowliveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EODtechnicalfilershowliveServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		HashMap <String,String> stklist = (HashMap <String,String>)session.getAttribute("CommTechEODliveshowfiltered");
		HashMap <String,StockOtherTechnicals> stochresult = new HashMap <String,StockOtherTechnicals>();
		HashMap <String,Weekpivot> weekpivot = (HashMap <String,Weekpivot>)getServletContext().getAttribute("WeekintradayPivot");
		String livetimeselection = (String)session.getAttribute("EODTECHLivetimeselection");
		Hourdatadownloader hd = null;
		if(stklist == null)
		{
			String text = " Filter in Common Tech EOD to use this feature";

		    response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
		    
		    response.getWriter().write(text);
		}
		else {
		if (livetimeselection != null)
		 hd = new Hourdatadownloader(livetimeselection);
		else
		 hd =  new Hourdatadownloader();
		
				Iterator it = stklist.entrySet().iterator();
		 while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        stochresult.put((String) pair.getKey(), hd.getstochdata((String) pair.getKey()));
		     
		        
		 }
		 
	     if (livetimeselection != null)
	     {
	    	 request.setAttribute("Minselect", livetimeselection);
	     }
	     else
	     {
	    	 request.setAttribute("Minselect", "60");
	     }
		 request.setAttribute("stocklist", stochresult);
		 request.getRequestDispatcher("EODTechEODLive.jsp").forward(request, response);
	
			
		}
			
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true); 
		session.setAttribute("EODTECHLivetimeselection", request.getParameter("chartinterval"));
		
		
		doGet(request, response);
	}

}
