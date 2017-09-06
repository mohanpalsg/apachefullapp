package com.mr.ajax;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mr.data.Filteredsmapivotdata;

/**
 * Servlet implementation class WeeksmapvServlet
 */
@WebServlet("/WeeksmapvServlet")
public class WeeksmapvServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WeeksmapvServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		DecimalFormat df = new DecimalFormat("#.##"); 
		HashMap <String,Filteredsmapivotdata> Map = (HashMap <String,Filteredsmapivotdata>) getServletContext().getAttribute("Weeksma50pvbreaklist");
		HttpSession session = request.getSession(); 
		if (session.getAttribute("Weeksmapvnse200attr")!=null)
		{
			doPost(request, response);
		}
		else
		{
		request.setAttribute("stocklist",Map );
		request.setAttribute("nsechecked", "true");
		 request.setAttribute("otherchecked", "false");
		 request.setAttribute("smadiffval", "10");
		 request.setAttribute("pivotdiffval", "10");
		 request.setAttribute("Weeksma50pvPP", "false");
		 request.setAttribute("Weeksma50pvS1", "true");
		 request.setAttribute("Weeksma50pvS2", "true");
		 request.setAttribute("Weeksma50pvS3", "true");
		 request.setAttribute("Weeksma50pvS4", "true");
		 request.setAttribute("Weeksma50pvR1", "false");
		 request.setAttribute("Weeksma50pvR2", "false");
		 request.setAttribute("Weeksma50pvR3", "false");
		 request.setAttribute("Weeksma50pvR4", "false");
		
		//request.getRequestDispatcher("weeksma50pv.jsp").forward(request, response);
		 request.getRequestDispatcher("EODWeekSMAandPivotbreakV1.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		 //HttpSession session = request.getSession();

		 System.out.println("request hitting weeksmaservlet");
	
		 HttpSession session = request.getSession(true); 
		 String nse200selected = request.getParameter("nse200attr");
		 String otherselected = request.getParameter("otherattr");
		 String pivotdiff = request.getParameter("pivotdiffattr");
		 String smadiff = request.getParameter("smadiffattr");
		 String ppselected = request.getParameter("ppcheckattr");
		 String s1selected = request.getParameter("s1checkattr");
		 String s2selected = request.getParameter("s2checkattr");
		 String s3selected = request.getParameter("s3checkattr");
		 String s4selected = request.getParameter("s4checkattr");
		 String r1selected = request.getParameter("r1checkattr");
		 String r2selected = request.getParameter("r2checkattr");
		 String r3selected = request.getParameter("r3checkattr");
		 String r4selected = request.getParameter("r4checkattr");
		 
		 System.out.println("nseselected "+ nse200selected);
		 System.out.println("otherselected "+ otherselected);
		 System.out.println("PPselected "+ ppselected);
		 System.out.println("R1selected "+ r1selected);
		// System.out.println(ppselected + ":" + s1selected + ":"+ r1selected);
		if (nse200selected!=null)
		{
		 session.setAttribute("Weeksmapvnse200attr",request.getParameter("nse200attr"));
		 session.setAttribute("Weeksmapvotherattr",request.getParameter("otherattr"));
		 session.setAttribute("Weeksmapvpivotdiffattr",request.getParameter("pivotdiffattr"));
		 session.setAttribute("Weeksmapvsmadiffattr",request.getParameter("smadiffattr"));
		 session.setAttribute("Weeksmapvppcheckattr",request.getParameter("ppcheckattr"));
		 session.setAttribute("Weeksmapvs1checkattr",request.getParameter("s1checkattr"));
		 session.setAttribute("Weeksmapvs2checkattr",request.getParameter("s2checkattr"));
		 session.setAttribute("Weeksmapvs3checkattr",request.getParameter("s3checkattr"));
		 session.setAttribute("Weeksmapvs4checkattr",request.getParameter("s4checkattr"));
		 session.setAttribute("Weeksmapvr1checkattr",request.getParameter("r1checkattr"));
		 session.setAttribute("Weeksmapvr2checkattr",request.getParameter("r2checkattr"));
		 session.setAttribute("Weeksmapvr3checkattr",request.getParameter("r3checkattr"));
		 session.setAttribute("Weeksmapvr4checkattr",request.getParameter("r4checkattr"));
		}
		else
		{
			nse200selected = (String) session.getAttribute("Weeksmapvnse200attr");
			otherselected = (String) session.getAttribute("Weeksmapvotherattr");
			pivotdiff = (String)session.getAttribute("Weeksmapvpivotdiffattr");
			smadiff = (String)session.getAttribute("Weeksmapvsmadiffattr");
			ppselected = (String)session.getAttribute("Weeksmapvppcheckattr");
			s1selected = (String)session.getAttribute("Weeksmapvs1checkattr");
			s2selected = (String)session.getAttribute("Weeksmapvs2checkattr");
			s3selected = (String)session.getAttribute("Weeksmapvs3checkattr");
			s4selected = (String)session.getAttribute("Weeksmapvs4checkattr");
			r1selected = (String)session.getAttribute("Weeksmapvr1checkattr");
			r2selected = (String)session.getAttribute("Weeksmapvr2checkattr");
			r3selected = (String)session.getAttribute("Weeksmapvr3checkattr");
			r4selected = (String)session.getAttribute("Weeksmapvr4checkattr");
		}
		 
		 HashMap <String,Filteredsmapivotdata> Map = (HashMap <String,Filteredsmapivotdata>) getServletContext().getAttribute("Weeksma50pvbreaklist");
		 HashMap <String,String> nsemap = (HashMap <String,String>) getServletContext().getAttribute("nse200");
		 HashMap <String,Filteredsmapivotdata> resultmap = new  HashMap <String,Filteredsmapivotdata>();
		 Iterator it = Map.entrySet().iterator();
		 while (it.hasNext()) {
			 Map.Entry pair = (Map.Entry)it.next();
			 Filteredsmapivotdata fv = (Filteredsmapivotdata) pair.getValue();
			if (nse200selected.equals("true"))
			 {
				 if(fv.getSmadiff() <= new Double(smadiff).doubleValue() && fv.getPivotdiff() <= new Double(pivotdiff).doubleValue())
				 {
					 if(nsemap.containsKey(fv.getStocksymbol()))
					 {
						 Filteredsmapivotdata newfv = getselectedlevel(ppselected,s1selected,s2selected,s3selected,s4selected,r1selected,r2selected,r3selected,r4selected,fv);
					 if (newfv != null)	 
					 resultmap.put(fv.getStocksymbol(), newfv);
					 }
				 }
				 
			 }
			 if (otherselected.equals("true"))
			 {
				 if(fv.getSmadiff() <= new Double(smadiff).doubleValue() && fv.getPivotdiff() <= new Double(pivotdiff).doubleValue())
				 {
					 if(! nsemap.containsKey(fv.getStocksymbol()))
					 {
						 Filteredsmapivotdata newfv = getselectedlevel(ppselected,s1selected,s2selected,s3selected,s4selected,r1selected,r2selected,r3selected,r4selected,fv);
				     if (newfv != null)	 
					 resultmap.put(fv.getStocksymbol(), newfv);
					 }
				 }
			 }
			
			 
		 }
		 request.setAttribute("stocklist",resultmap );
		 request.setAttribute("nsechecked",nse200selected);
		 request.setAttribute("otherchecked", otherselected);
		 request.setAttribute("smadiffval", smadiff);
		 request.setAttribute("pivotdiffval", pivotdiff);
		 request.setAttribute("Weeksma50pvPP", ppselected);
		 request.setAttribute("Weeksma50pvS1", s1selected);
		 request.setAttribute("Weeksma50pvS2", s2selected);
		 request.setAttribute("Weeksma50pvS3", s3selected);
		 request.setAttribute("Weeksma50pvS4", s4selected);
		 request.setAttribute("Weeksma50pvR1", r1selected);
		 request.setAttribute("Weeksma50pvR2", r2selected);
		 request.setAttribute("Weeksma50pvR3", r3selected);
		 request.setAttribute("Weeksma50pvR4", r4selected);
		
		 // request.getRequestDispatcher("weeksma50pv.jsp").forward(request, response);
		 request.getRequestDispatcher("EODWeekSMAandPivotbreakV1.jsp").forward(request, response);
		 

}

	private Filteredsmapivotdata getselectedlevel(String ppselected, String s1selected, String s2selected,
			String s3selected, String s4selected, String r1selected, String r2selected, String r3selected,
			String r4selected, Filteredsmapivotdata fv) {
		
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
}
