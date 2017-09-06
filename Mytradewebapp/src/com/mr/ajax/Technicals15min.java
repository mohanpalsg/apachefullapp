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
@WebServlet("/Technicals15min")
public class Technicals15min extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Technicals15min() {
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
		if(session.getAttribute("LIVET15SKrangeselection")!=null)
		{
			HashMap <String,Technicals15minlivedate> livedata = new HashMap <String,Technicals15minlivedate>();
			HashMap <String,Technicals15minlivedate> outputdata = new HashMap <String,Technicals15minlivedate>();
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
		        	{
		        	livedata.put(stocksymbol+"PP",ttl );
		        	outputdata.put(stocksymbol+"PP",ttl );
		        	}
		        	
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
			session.setAttribute("presentdata", outputdata);
			
			
			
			
			HashMap <String,Technicals15minlivedate>   temp = (HashMap  <String,Technicals15minlivedate>) session.getAttribute("presentdata");
			HashMap <String,Technicals15minlivedate> Finalmap =   new  HashMap <String,Technicals15minlivedate>();
			
			

			String krange = (String)session.getAttribute("LIVET15SKrangeselection");
			String drange = (String)session.getAttribute("CTLIVET15SDrangeselection");
			String intervalselect = (String)session.getAttribute("CTLIVETintervalselection");
			
			String diffselection = (String)session.getAttribute("LIVET15diffselection");
		
			
		
			
			
			
			
			
			it = temp.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
		        String stocksymbol = (String) pair.getKey();
		        Technicals15minlivedate t15 = (Technicals15minlivedate) pair.getValue();
		        if(t15.getStochk() <= Double.valueOf(krange) && t15.getStochd() <= Double.valueOf(drange)
		        		&& ((t15.getDowndiff() > -1 && t15.getDowndiff() <= Double.valueOf(diffselection) ) || 
		        		(t15.getUpdiff() > -1 && t15.getUpdiff() <= Double.valueOf(diffselection) ) 
		        		|| (t15.getDown7diff() > -1 && t15.getDown7diff() <= Double.valueOf(diffselection) ) || 
		        		(t15.getUp7diff() > -1 && t15.getUp7diff() <= Double.valueOf(diffselection) )))
		        {
		        	Finalmap.put(t15.getStocksymbol(), t15);
		        }
			}
			
			request.setAttribute("stocklist", Finalmap);
			
			request.setAttribute("Commontechskrange",krange);
			request.setAttribute("Commontechsdrange",drange);
			request.setAttribute("Minselect", intervalselect);
			
			request.setAttribute("Commontechdiff",diffselection );
		
			
			
			
			
		}
		else
		{
			HashMap <String,Technicals15minlivedate> livedata = new HashMap <String,Technicals15minlivedate>();
			HashMap <String,Technicals15minlivedate> outputdata = new HashMap <String,Technicals15minlivedate>();
		    HashMap <String,StockOtherTechnicals> newcreated = new HashMap <String,StockOtherTechnicals> ();
			Iterator it = tech15onload.entrySet().iterator();
			Hourdatadownloader hd =new Hourdatadownloader("300");
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
		        String stocksymbol = (String) pair.getKey();
		        StockOtherTechnicals st = hd.getstochdata(stocksymbol);
		        newcreated.put(stocksymbol, st);
		        if (weekpivot.containsKey(stocksymbol))
		        {
		        	Weekpivot wk = weekpivot.get(stocksymbol);
		        	Technicals15minlivedate ttl = getlivehashmap("PP",wk.getPP(),st);
		        	if (ttl!=null)
		        	{
		        	livedata.put(stocksymbol+"PP",ttl );
		        	outputdata.put(stocksymbol+"PP",ttl );
		        	}
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
			session.setAttribute("Live15mindata", newcreated);
			session.setAttribute("Liveoutputdata", livedata);
			session.setAttribute("CTLIVETintervalselection","300");
			session.setAttribute("LIVET15SKrangeselection", "100");
			session.setAttribute("CTLIVET15SDrangeselection","100");
			
			session.setAttribute("LIVET15diffselection","5");
			request.setAttribute("stocklist", outputdata);
			
			request.setAttribute("Commontechskrange", "100");
			request.setAttribute("Commontechsdrange", "100");
			request.setAttribute("Minselect", "300");
			
			request.setAttribute("Commontechdiff", "5");
			
			
			
			
			
			
			
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
		float updiff = (float) (((st.getUpvalue()-st.getHigh())/st.getUpvalue())*100);
		tl.setUpdiff(Double.valueOf(formatter.format(updiff)));
		tl.setDownvalue(Double.valueOf(formatter.format(st.getDownvalue())));
		float downdiff = (float) (((st.getLow() - st.getDownvalue())/st.getDownvalue())*100);
		tl.setDowndiff(Double.valueOf(formatter.format(downdiff)));
		tl.setTrend(st.getTrend());
		tl.setUpvalue7(Double.valueOf(formatter.format(st.getUpvalue7())));
		tl.setDownvalue7(Double.valueOf(formatter.format(st.getDownvalue7())));
		updiff = (float) (((st.getUpvalue()-st.getClose())/st.getUpvalue())*100);
		tl.setUp7diff(Double.valueOf(formatter.format(updiff)));
		downdiff = (float) (((st.getClose() - st.getDownvalue())/st.getDownvalue())*100);
		tl.setDown7diff(Double.valueOf(formatter.format(downdiff)));
		
		System.out.println("Symbol:"+tl.getStocksymbol()+" "+"Low: "+tl.getLowprice() +" "+"High:" + tl.getHighprice()+" "+"close:" + "tl.getCloseprice()"+"  Stdown:" + tl.getDownvalue() + "sthigh" + tl.getUpvalue());
		return tl;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		HttpSession session = request.getSession(true); 
		boolean changed = false;
		session.setAttribute("LIVET15SKrangeselection", request.getParameter("commontechskrangeattr"));
		session.setAttribute("CTLIVET15SDrangeselection", request.getParameter("commontechsdrangeattr"));
		session.setAttribute("LIVET15diffselection", request.getParameter("commontechdiffattr"));
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
