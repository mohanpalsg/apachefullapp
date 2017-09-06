package com.mr.ajax;

import java.io.IOException;
import java.net.MalformedURLException;
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

import com.mr.data.StockOtherTechnicals;
import com.mr.data.Technicals15minlivedate;
import com.mr.data.Weekpivot;
import com.mr.hourlydata.Hourdatadownloader;

/**
 * Servlet implementation class Technicals15min
 */
@WebServlet("/Technicals15min_backup")
public class Technicals15min_backup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Technicals15min_backup() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true); 
		HashMap <String,StockOtherTechnicals> tech15onload = (HashMap <String,StockOtherTechnicals>)getServletContext().getAttribute("nse100liveStoch");
		HashMap <String,StockOtherTechnicals> techlivedata = (HashMap <String,StockOtherTechnicals>)session.getAttribute("Live15mindata");
		
		HashMap <String,Weekpivot> weekpivot = (HashMap <String,Weekpivot>)getServletContext().getAttribute("WeekintradayPivot");
		if(session.getAttribute("LIVET15RSIrangeselection")!=null)
		{
			HashMap <String,Technicals15minlivedate> livedata = new HashMap <String,Technicals15minlivedate>();
			Iterator it = techlivedata.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
		        String stocksymbol = (String) pair.getKey();
		        StockOtherTechnicals st = (StockOtherTechnicals)pair.getValue();
		        
		        if (weekpivot.containsKey(stocksymbol))
		        {
		        	Weekpivot wk = weekpivot.get(stocksymbol);
		        	Technicals15minlivedate ttl = getlivehashmap("PP",wk.getPP(),st);
		        	if (ttl!=null)
		        	livedata.put(stocksymbol+"PP",ttl );
		        	
		        	ttl = getlivehashmap("S1",wk.getS1(),st);
		        			if (ttl!=null)
		        	livedata.put(stocksymbol+"S1",ttl );
		        			
		        	ttl =  getlivehashmap("S2",wk.getS2(),st);
		        	if(ttl!=null)
		        	livedata.put(stocksymbol+"S2",ttl);
		        	
		        	ttl =  getlivehashmap("S3",wk.getS3(),st);
		        	if (ttl!=null)
		        	livedata.put(stocksymbol+"S3",ttl);
		        	
		        	ttl = getlivehashmap("S4",wk.getS4(),st);
		        	if (ttl!=null)
		        	livedata.put(stocksymbol+"S4", ttl);
		        	
		        	ttl =  getlivehashmap("R1",wk.getR1(),st);
				        	if (ttl!=null)
		        	livedata.put(stocksymbol+"R1",ttl);
		        	
		        	ttl = getlivehashmap("R2",wk.getR2(),st);
				        	if (ttl!=null)
		        	livedata.put(stocksymbol+"R2", ttl);
		        	
		        	ttl = getlivehashmap("R3",wk.getR3(),st);
				        	if (ttl!=null)
		        	livedata.put(stocksymbol+"R3", ttl);
		        	
		        	ttl = getlivehashmap("R4",wk.getR4(),st);
				        	if (ttl!=null)
		        	livedata.put(stocksymbol+"R4",ttl );
		        }
		       
		        
		       
		        
			}
			
			session.setAttribute("Liveoutputdata", livedata);
			
			
			
			
			HashMap <String,Technicals15minlivedate>   temp = (HashMap  <String,Technicals15minlivedate>) session.getAttribute("Liveoutputdata");
			HashMap <String,Technicals15minlivedate> Finalmap =   new  HashMap <String,Technicals15minlivedate>();
			
			
			
			String rsirange = (String)session.getAttribute("LIVET15RSIrangeselection");
			String wprrange = (String)session.getAttribute("LIVET15WPRrangeselection");
			String krange = (String)session.getAttribute("LIVET15SKrangeselection");
			String drange = (String)session.getAttribute("CTLIVET15SDrangeselection");
			String intervalselect = (String)session.getAttribute("CTLIVETintervalselection");
			String testsmaselecion = (String)session.getAttribute("LIVET15testsmaelection");
			String testpvselection = (String)session.getAttribute("LIVET15testpvselection");
			String breaksmaselection = (String)session.getAttribute("LIVET15breaksmaselection");
			String breakpvselection = (String)session.getAttribute("LIVET15breakpvselection");
			String lowdiffselection = (String)session.getAttribute("LIVET15lowdiffselection");
			String highdiffselection = (String)session.getAttribute("LIVET15highdiffselection");
			
		
			
			
			
			
			
			it = temp.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
		        String stocksymbol = (String) pair.getKey();
		        Technicals15minlivedate t15 = (Technicals15minlivedate) pair.getValue();
		        if(t15.getRsi() <= Double.valueOf(rsirange) && t15.getWpr() <= Double.valueOf(wprrange) && t15.getStochk() <= Double.valueOf(krange) && t15.getStochd() <= Double.valueOf(drange))
		        {
		        	if ((t15.getSmadiff() >= Double.valueOf(lowdiffselection) && t15.getSmadiff() <= Double.valueOf(highdiffselection)) && 
		        			(t15.getPivotdiff() >= Double.valueOf(lowdiffselection) && t15.getPivotdiff() <= Double.valueOf(highdiffselection)))
		        	{
		        	if (testsmaselecion.equals("false") && testpvselection.equals("false") && breaksmaselection.equals("false") && breakpvselection.equals("false"))
		        	Finalmap.put(stocksymbol, t15);
		        	
		        	if (testsmaselecion.equals("true") && testpvselection.equals("true"))
		        	{
		        		if (t15.isTestpivot() && t15.isTestsma())
		        			Finalmap.put(stocksymbol, t15);
		        			
		        	}
		        	else if (testsmaselecion.equals("true"))
		        	{
		        		if(t15.isTestsma())
		        			Finalmap.put(stocksymbol, t15);
		        	}
		        	else if (testpvselection.equals("true"))
		        	{
		        		if(t15.isTestpivot())
		        			Finalmap.put(stocksymbol, t15);
		        	}
		        	
		        	
		        	if (breaksmaselection.equals("true") && breakpvselection.equals("true"))
		        	{
		        		if (t15.isBreaksma()  && t15.isBreakpivot())
		        			Finalmap.put(stocksymbol, t15);
		        	}
		        	else if (breaksmaselection.equals("true"))
		        	{
		        		if (t15.isBreaksma())
		        			Finalmap.put(stocksymbol, t15);
		        	}
		        	else if (breakpvselection.equals("true"))
		        	{
		        		if(t15.isBreakpivot())
		        			Finalmap.put(stocksymbol, t15);
		        	}
		        	
		        	}
		        }
			}
			
			request.setAttribute("stocklist", Finalmap);
			request.setAttribute("Commontechrsirange", rsirange);
			request.setAttribute("Commontechwprrange",wprrange);
			request.setAttribute("Commontechskrange",krange);
			request.setAttribute("Commontechsdrange",drange);
			request.setAttribute("Minselect", intervalselect);
			request.setAttribute("testsmachecked", testsmaselecion);
			request.setAttribute("testpvchecked",testpvselection);
			request.setAttribute("breaksmachecked", breaksmaselection);
			request.setAttribute("breakpvchecked", breakpvselection);
			request.setAttribute("Commontechsmadiff",lowdiffselection );
			request.setAttribute("Commontechpvtdiff", highdiffselection);
			
			
			
			
		}
		else
		{
			HashMap <String,Technicals15minlivedate> livedata = new HashMap <String,Technicals15minlivedate>();
			Iterator it = tech15onload.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
		        String stocksymbol = (String) pair.getKey();
		        StockOtherTechnicals st = (StockOtherTechnicals)pair.getValue();
		        
		        if (weekpivot.containsKey(stocksymbol))
		        {
		        	Weekpivot wk = weekpivot.get(stocksymbol);
		        	Technicals15minlivedate ttl = getlivehashmap("PP",wk.getPP(),st);
		        	if (ttl!=null)
		        	livedata.put(stocksymbol+"PP",ttl );
		        	
		        	ttl = getlivehashmap("S1",wk.getS1(),st);
		        			if (ttl!=null)
		        	livedata.put(stocksymbol+"S1",ttl );
		        			
		        	ttl =  getlivehashmap("S2",wk.getS2(),st);
		        	if(ttl!=null)
		        	livedata.put(stocksymbol+"S2",ttl);
		        	
		        	ttl =  getlivehashmap("S3",wk.getS3(),st);
		        	if (ttl!=null)
		        	livedata.put(stocksymbol+"S3",ttl);
		        	
		        	ttl = getlivehashmap("S4",wk.getS4(),st);
		        	if (ttl!=null)
		        	livedata.put(stocksymbol+"S4", ttl);
		        	
		        	ttl =  getlivehashmap("R1",wk.getR1(),st);
				        	if (ttl!=null)
		        	livedata.put(stocksymbol+"R1",ttl);
		        	
		        	ttl = getlivehashmap("R2",wk.getR2(),st);
				        	if (ttl!=null)
		        	livedata.put(stocksymbol+"R2", ttl);
		        	
		        	ttl = getlivehashmap("R3",wk.getR3(),st);
				        	if (ttl!=null)
		        	livedata.put(stocksymbol+"R3", ttl);
		        	
		        	ttl = getlivehashmap("R4",wk.getR4(),st);
				        	if (ttl!=null)
		        	livedata.put(stocksymbol+"R4",ttl );
		        }
		       
		        
		       
		        
			}
			session.setAttribute("Live15mindata", tech15onload);
			session.setAttribute("Liveoutputdata", livedata);
			request.setAttribute("stocklist", livedata);
			request.setAttribute("Commontechrsirange", "50");
			request.setAttribute("Commontechwprrange", "-50");
			request.setAttribute("Commontechskrange", "20");
			request.setAttribute("Commontechsdrange", "40");
			request.setAttribute("Minselect", "60");
			request.setAttribute("testsmachecked", "false");
			request.setAttribute("testpvchecked", "false");
			request.setAttribute("breaksmachecked", "false");
			request.setAttribute("breakpvchecked", "false");
			request.setAttribute("Commontechsmadiff", "-2.5");
			request.setAttribute("Commontechpvtdiff", "2.5");
			
			
			
			
			
			
		}
		
		request.getRequestDispatcher("Technicallive15min.jsp").forward(request, response);
		
	}

	private Technicals15minlivedate getlivehashmap(String level, double pp, StockOtherTechnicals st) {
		// TODO Auto-generated method stub
		DecimalFormat formatter = new DecimalFormat("#0.00"); 
		Technicals15minlivedate tl = new Technicals15minlivedate();
		tl.setStocksymbol(st.getStocksymbol());
		tl.setCloseprice(st.getClose());
		tl.setHighprice(st.getHigh());
		tl.setLowprice(st.getLow());
		tl.setOpenprice(st.getOpen());
		float smadiff = (float) (((st.getClose() - st.getSma50())/st.getSma50())*100);
		float pivotdiff = (float) ((st.getClose()-pp)/pp)*100;
		tl.setSmadiff(Float.valueOf(String.format("%.2f", smadiff)));
		tl.setPivotdiff(Float.valueOf(String.format("%.2f", pivotdiff)));
		tl.setSma50(Double.valueOf(formatter.format(st.getSma50())));
		if (st.getOpen() < st.getSma50() && st.getClose() > st.getSma50())
		tl.setBreaksma(true);
		else
		tl.setBreaksma(false);
		
		if (st.getOpen() < pp && st.getClose() > pp)
		tl.setBreakpivot(true);
		else
		tl.setBreakpivot(false);
		
		if (st.getOpen() > st.getSma50() && st.getLow() < st.getSma50() && st.getClose() >= st.getSma50())
		tl.setTestsma(true);
		else
		tl.setTestsma(false);
		
		if (st.getOpen() > pp && st.getLow() < pp && st.getClose() >= pp)
		tl.setTestpivot(true);
		else
		tl.setTestpivot(false);
		
		
		tl.setLevel(level);
		tl.setLevelvalue(Double.valueOf(formatter.format(pp)));
		tl.setStochk(st.getDayk());
		tl.setStochd(st.getDayd());
		tl.setWpr(st.getDaywilliamsr());
		tl.setRsi(st.getDayrsi());
		tl.setUpvalue(Double.valueOf(formatter.format(st.getUpvalue())));
		tl.setDownvalue(Double.valueOf(formatter.format(st.getDownvalue())));
		tl.setTrend(st.getTrend());
		if (tl.getPivotdiff() > 10 || tl.getPivotdiff() < -10 )
			return null;
		
		return tl;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		HttpSession session = request.getSession(true); 
		boolean changed = false;
		session.setAttribute("LIVET15RSIrangeselection", request.getParameter("commontechRSPrangeattr"));
		session.setAttribute("LIVET15WPRrangeselection", request.getParameter("commontechWRPrangeattr"));
		session.setAttribute("LIVET15SKrangeselection", request.getParameter("commontechskrangeattr"));
		session.setAttribute("CTLIVET15SDrangeselection", request.getParameter("commontechsdrangeattr"));
		session.setAttribute("LIVET15testsmaelection", request.getParameter("testsmattr"));
		session.setAttribute("LIVET15testpvselection", request.getParameter("testpvattr"));
		session.setAttribute("LIVET15breaksmaselection", request.getParameter("breaksmattr"));
		session.setAttribute("LIVET15breakpvselection", request.getParameter("breakpvattr"));
		session.setAttribute("LIVET15lowdiffselection", request.getParameter("commontechlowdiffattr"));
		
		session.setAttribute("LIVET15highdiffselection", request.getParameter("commontechhighdiffattr"));
		
		
	
		if(request.getParameter("chartinterval").equals(session.getAttribute("CTLIVETintervalselection")))
				{
			
				}
		else
			changed = true;
		
		session.setAttribute("CTLIVETintervalselection", request.getParameter("chartinterval"));
		doGet(request, response,changed);
		
		
		
		
		
	}

	private void doGet(HttpServletRequest request, HttpServletResponse response, boolean changed) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true); 
		if (changed)
		{
			Hourdatadownloader hd = new Hourdatadownloader((String)session.getAttribute("CTLIVETintervalselection"));
			HashMap <String,StockOtherTechnicals> tech15onload = (HashMap <String,StockOtherTechnicals>)getServletContext().getAttribute("nse100liveStoch");
			HashMap <String,StockOtherTechnicals> resultmap = new HashMap <String,StockOtherTechnicals> ();
			
			Iterator it = tech15onload.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
		        String stocksymbol = (String) pair.getKey();
		        try {
					resultmap.put(stocksymbol, hd.getstochdata(stocksymbol));
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			session.setAttribute("Live15mindata", resultmap);
		}
		
			try {
				doGet(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
