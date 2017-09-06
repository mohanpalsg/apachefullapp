package com.mr.ajax;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mr.app.Alertobject;
import com.mr.data.Daypivot;
import com.mr.data.StockOtherTechnicals;
import com.mr.data.Weekpivot;
import com.mr.hourlydata.Hourdatadownloader;
import  com.mr.app.Alertobject;
/**
 * Servlet implementation class Technicals15minupdateservlet
 */
@WebServlet("/Technicals15minupdateservlet")
public class Technicals15minupdateservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Technicals15minupdateservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true); 
		String ps = (String)session.getAttribute("CTLIVETintervalselection");
		if(ps == null);
		  ps = "300";
		Hourdatadownloader hd = null;
		if (ps!=null)
		 hd = new Hourdatadownloader(ps);
		else
		hd = new Hourdatadownloader();
		HashMap <String,Weekpivot> yrpivot = (HashMap <String,Weekpivot>)getServletContext().getAttribute("Weekpivot");
		HashMap<String, Daypivot>  mntpivot = (HashMap <String,Daypivot>)getServletContext().getAttribute("Daypivot");
		HashMap <String,StockOtherTechnicals> tech15onload = (HashMap <String,StockOtherTechnicals>)getServletContext().getAttribute("nse100liveStoch");
		HashMap <String,StockOtherTechnicals> resultmap = new HashMap <String,StockOtherTechnicals> ();
		Iterator it = tech15onload.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
	        String stocksymbol = (String) pair.getKey();
	        resultmap.put(stocksymbol, hd.getstochdata(stocksymbol));
		}
		session.setAttribute("Live15mindata", resultmap);
		
		boolean newAlert = false;
		// use code YABOVE,YBELOW for yearpivot 
		// use code MABOVE,MBELOW for monthpivot
		//use code SD0 for stochd below 1
		HashMap <String,Alertobject> alert =  (HashMap<String, Alertobject>) session.getAttribute("LiveAlert");
		
		int alertsize= 0;
		if (alert == null)
		{
			alert =  new HashMap <String,Alertobject>();
		}
		else
		{
			alertsize = alert.size();
		}
		it = resultmap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
	        String stocksymbol = (String) pair.getKey();
	        StockOtherTechnicals st = (StockOtherTechnicals) pair.getValue();
	        if(yrpivot.containsKey(stocksymbol))
	        {
	        	Weekpivot wp = (Weekpivot)yrpivot.get(stocksymbol);
	        	if (st.getOpen() < wp.getPP() && st.getClose() >= wp.getPP())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("YABOVEPP");
	        		alobj.setMessage("Breaks above year pivot PP");
	        		
	        		alert.put(stocksymbol+"YABOVEPP", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() < wp.getS1() && st.getClose() >= wp.getS1())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("YABOVES1");
	        		alobj.setMessage("Breaks above year pivot S1");
	        		alert.put(stocksymbol+"YABOVES1", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() < wp.getS2() && st.getClose() >= wp.getS2())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("YABOVES2");
	        		alobj.setMessage("Breaks above year pivot S2");
	        		alert.put(stocksymbol+"YABOVES2", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() < wp.getS3() && st.getClose() >= wp.getS3())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("YABOVES3");
	        		alobj.setMessage("Breaks above year pivot S3");
	        		alert.put(stocksymbol+"YABOVES3", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() < wp.getS4() && st.getClose() >= wp.getS4())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("YABOVES4");
	        		alobj.setMessage("Breaks above year pivot S4");
	        		alert.put(stocksymbol+"YABOVES4", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() < wp.getR1() && st.getClose() >= wp.getR1())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("YABOVER1");
	        		alobj.setMessage("Breaks above year pivot R1");
	        		alert.put(stocksymbol+"YABOVER1", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() < wp.getR2() && st.getClose() >= wp.getR2())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("YABOVER2");
	        		alobj.setMessage("Breaks above year pivot R2");
	        		alert.put(stocksymbol+"YABOVER2", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() < wp.getR3() && st.getClose() >= wp.getR3())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("YABOVER3");
	        		alobj.setMessage("Breaks above year pivot R3");
	        		alert.put(stocksymbol+"YABOVER3", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() < wp.getR4() && st.getClose() >= wp.getR4())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("YABOVER4");
	        		alobj.setMessage("Breaks above year pivot R4");
	        		alert.put(stocksymbol+"YABOVER4", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() > wp.getPP() && st.getClose() < wp.getPP())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("YBELOWPP");
	        		alobj.setMessage("Breaks BELOW year pivot PP");
	        		alert.put(stocksymbol+"YBELOWPP", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() > wp.getS1() && st.getClose() < wp.getS1())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("YBELOWS1");
	        		alobj.setMessage("Breaks BELOW year pivot S1");
	        		alert.put(stocksymbol+"YBELOWS1", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() > wp.getS2() && st.getClose() < wp.getS2())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("YBELOWS2");
	        		alobj.setMessage("Breaks BELOW year pivot S2");
	        		alert.put(stocksymbol+"YBELOWS2", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() > wp.getS3() && st.getClose() < wp.getS3())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("YBELOWS3");
	        		alobj.setMessage("Breaks BELOW year pivot S3");
	        		alert.put(stocksymbol+"YBELOWS3", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() > wp.getS4() && st.getClose() < wp.getS4())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("YBELOWS4");
	        		alobj.setMessage("Breaks BELOW year pivot S4");
	        		alert.put(stocksymbol+"YBELOWS4", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() > wp.getR1() && st.getClose() < wp.getR1())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("YBELOWR1");
	        		alobj.setMessage("Breaks BELOW year pivot R1");
	        		alert.put(stocksymbol+"YBELOWR1", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() > wp.getR2() && st.getClose() < wp.getR2())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("YBELOWR2");
	        		alobj.setMessage("Breaks BELOW year pivot R2");
	        		alert.put(stocksymbol+"YBELOWR2", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() > wp.getR3() && st.getClose() < wp.getR3())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("YBELOWR3");
	        		alobj.setMessage("Breaks BELOW year pivot R3");
	        		alert.put(stocksymbol+"YBELOWR3", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() > wp.getR4() && st.getClose() < wp.getR4())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("YBELOWR4");
	        		alobj.setMessage("Breaks BELOW year pivot R4");
	        		alert.put(stocksymbol+"YBELOWR4", alobj);
	        		newAlert = true;
	        	}
	        	
	        	
	        	
	        }
	        if(mntpivot.containsKey(stocksymbol))
	        {
	        	Daypivot wp = (Daypivot)mntpivot.get(stocksymbol);
	        	
	        	if (st.getOpen() < wp.getPP() && st.getClose() >= wp.getPP())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("MABOVEPP");
	        		alobj.setMessage("Breaks above month pivot PP");
	        		alert.put(stocksymbol+"MABOVEPP", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() < wp.getS1() && st.getClose() >= wp.getS1())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("MABOVES1");
	        		alobj.setMessage("Breaks above month pivot S1");
	        		alert.put(stocksymbol+"MABOVES1", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() < wp.getS2() && st.getClose() >= wp.getS2())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("MABOVES2");
	        		alobj.setMessage("Breaks above month pivot S2");
	        		alert.put(stocksymbol+"MABOVES2", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() < wp.getS3() && st.getClose() >= wp.getS3())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("MABOVES3");
	        		alobj.setMessage("Breaks above month pivot S3");
	        		alert.put(stocksymbol+"MABOVES3", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() < wp.getS4() && st.getClose() >= wp.getS4())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("MABOVES4");
	        		alobj.setMessage("Breaks above month pivot S4");
	        		alert.put(stocksymbol+"MABOVES4", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() < wp.getR1() && st.getClose() >= wp.getR1())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("MABOVER1");
	        		alobj.setMessage("Breaks above month pivot R1");
	        		alert.put(stocksymbol+"MABOVER1", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() < wp.getR2() && st.getClose() >= wp.getR2())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("MABOVER2");
	        		alobj.setMessage("Breaks above month pivot R2");
	        		alert.put(stocksymbol+"MABOVER2", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() < wp.getR3() && st.getClose() >= wp.getR3())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("MABOVER3");
	        		alobj.setMessage("Breaks above month pivot R3");
	        		alert.put(stocksymbol+"MABOVER3", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() < wp.getR4() && st.getClose() >= wp.getR4())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		
	        		alobj.setCategory("MABOVER4");
	        		alobj.setMessage("Breaks above month pivot R4");
	        		alert.put(stocksymbol+"MABOVER4", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() > wp.getPP() && st.getClose() < wp.getPP())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("MBELOWPP");
	        		alobj.setMessage("Breaks BELOW month pivot PP");
	        		alert.put(stocksymbol+"MBELOWPP", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() > wp.getS1() && st.getClose() < wp.getS1())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("MBELOWS1");
	        		alobj.setMessage("Breaks BELOW month pivot S1");
	        		alert.put(stocksymbol+"MBELOWS1", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() > wp.getS2() && st.getClose() < wp.getS2())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("MBELOWS2");
	        		alobj.setMessage("Breaks BELOW month pivot S2");
	        		alert.put(stocksymbol+"MBELOWS2", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() > wp.getS3() && st.getClose() < wp.getS3())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("MBELOWS3");
	        		alobj.setMessage("Breaks BELOW month pivot S3");
	        		alert.put(stocksymbol+"MBELOWS3", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() > wp.getS4() && st.getClose() < wp.getS4())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("MBELOWS4");
	        		alobj.setMessage("Breaks BELOW month pivot S4");
	        		alert.put(stocksymbol+"MBELOWS4", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() > wp.getR1() && st.getClose() < wp.getR1())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("MBELOWR1");
	        		alobj.setMessage("Breaks BELOW month pivot R1");
	        		alert.put(stocksymbol+"MBELOWR1", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() > wp.getR2() && st.getClose() < wp.getR2())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("MBELOWR2");
	        		alobj.setMessage("Breaks BELOW month pivot R2");
	        		alert.put(stocksymbol+"MBELOWR2", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() > wp.getR3() && st.getClose() < wp.getR3())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		alobj.setCategory("MBELOWR3");
	        		alobj.setMessage("Breaks BELOW month pivot R3");
	        		alert.put(stocksymbol+"MBELOWR3", alobj);
	        		newAlert = true;
	        	}
	        	
	        	if (st.getOpen() > wp.getR4() && st.getClose() < wp.getR4())
	        	{
	        		Alertobject alobj = new Alertobject();
	        		alobj.setAlertime(new Date());
	        		alobj.setStocksymbol(stocksymbol);
	        		
	        		alobj.setCategory("MBELOWR4");
	        		alobj.setMessage("Breaks BELOW month pivot R4");
	        		alert.put(stocksymbol+"MBELOWR4", alobj);
	        		newAlert = true;
	        	}
	        	
	        	
	        }
	    
	        
	        if (st.getDayd() < 1)
	        {
	        	Alertobject alobj = new Alertobject();
        		alobj.setAlertime(new Date());
        		alobj.setStocksymbol(stocksymbol);
        		
        		alobj.setCategory("STCHD0");
        		alobj.setMessage(ps+" min Stoch D is less than 1");
        		alert.put(stocksymbol+"STCHD0", alobj);
        		newAlert = true;
	        }
		}
		
		if (alertsize < alert.size())
			newAlert = true;
		else
			newAlert = false;
		session.setAttribute("LiveAlert", alert);
		session.setAttribute("newalertadded", newAlert);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
