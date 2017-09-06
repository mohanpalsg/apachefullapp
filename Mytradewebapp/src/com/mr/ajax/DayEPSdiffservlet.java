package com.mr.ajax;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
import com.mr.app.CPSdiff;
import com.mr.data.Stocktwodaystat;
import com.mr.data.Daypivot;
import com.mr.data.Daysma50;
import com.mr.data.StochasticFinalval;

/**
 * Servlet implementation class DayEPSdiffservlet
 */
@WebServlet("/DayEPSdiffservlet")
public class DayEPSdiffservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DayEPSdiffservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true); 
		
		if (session.getAttribute("DayEPSdiffnse200selection") != null)
		{
			String nseselection =  (String) session.getAttribute("DayEPSdiffnse200selection");
			String otherselection =  		(String)session.getAttribute("DayEPSdiffotherselection");
			String smaselection =  (String)session.getAttribute("DayEPSdiffSMAselection");
			String ppselection =  (String)session.getAttribute("DayEPSdiffPPselection");
			String s1selection =  (String)session.getAttribute("DayEPSdiffS1selection");
			String s2selection =  (String)session.getAttribute("DayEPSdiffS2selection");
			String s3selection = (String)session.getAttribute("DayEPSdiffS3selection");
			String s4selection =  (String)session.getAttribute("DayEPSdiffS4selection");
			String r1selection = (String)session.getAttribute("DayEPSdiffR1selection");
			String r2selection =  (String)session.getAttribute("DayEPSdiffR2selection");
			String r3selection =  (String)session.getAttribute("DayEPSdiffR3selection");
			String r4selection = (String)session.getAttribute("DayEPSdiffR4selection");
			String rangemin = (String)session.getAttribute("DayEPSdiffrangeminselection");
			String rangemax = (String)session.getAttribute("DayEPSdiffrangemaxselection");
			String stochkselection = (String)session.getAttribute("DayEPSdiffstochkselection");
			String stochdselection = (String)session.getAttribute("DayEPSdiffstochDselection");
			String testsmaselection = (String) session.getAttribute("DayEPSdifftestsmaselection");
			String testpivotselection = (String) session.getAttribute("DayEPSdifftestpivotselection");
			
			HashMap <String,String> nsemap = (HashMap <String,String>) getServletContext().getAttribute("nse200");
			HashMap <String,CPSdiff> CPSdiffmap = (HashMap <String,CPSdiff> ) session.getAttribute("EODdaypivotsmadiffattr");
			HashMap <String,CPSdiff> finalmap = new HashMap <String,CPSdiff>();
			Iterator it = CPSdiffmap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
		        String stocksymbol = (String) pair.getKey();
		        CPSdiff cp = (CPSdiff) pair.getValue();
		        if (nseselection.equals("true"))
		        {
		        	if(nsemap.containsKey(cp.getStocksymbol()))
		        	{
		        		CPSdiff newcp = (CPSdiff) getresult(cp,smaselection,ppselection,s1selection,s2selection,s3selection,s4selection,r1selection,r2selection,r3selection,r4selection,rangemin,rangemax,stochkselection,stochdselection);
		        		if (newcp != null)
		        		{
		        		if(testsmaselection.equals("true") && newcp.isTestsma() == true)
		        		finalmap.put(stocksymbol,newcp );	
		        		if(testpivotselection.equals("true") && newcp.isTestpivot() == true)
			        	finalmap.put(stocksymbol,newcp );	
		        		if(testsmaselection.equals("false") && testpivotselection.equals("false"))
		        				finalmap.put(stocksymbol,newcp );
		        		}
		        	}
		        }
		        if (otherselection.equals("true"))
		        {
		        	if(!nsemap.containsKey(cp.getStocksymbol()))
		        	{
		        		CPSdiff newcp = (CPSdiff) getresult(cp,smaselection,ppselection,s1selection,s2selection,s3selection,s4selection,r1selection,r2selection,r3selection,r4selection,rangemin,rangemax,stochkselection,stochdselection);
		        		if (newcp != null)
		        		{
		        			
		        			if(testsmaselection.equals("true") && newcp.isTestsma() == true)
				        		finalmap.put(stocksymbol,newcp );	
				        		if(testpivotselection.equals("true") && newcp.isTestpivot() == true)
					        	finalmap.put(stocksymbol,newcp );	
				        		if(testsmaselection.equals("false") && testpivotselection.equals("false"))
				        				finalmap.put(stocksymbol,newcp );
		        		}
		        	}
		        		// finalmap.put(stocksymbol, getresult(cp,diffselected,smaselected,ppselected,s1selected,s2selected,s3selected,s4selected,r1selected,r2selected,r3selected,r4selected));	
		        }
		        
			}
			request.setAttribute("EODdaypivotsmadiff",finalmap );
			
			request.setAttribute("Dayepsdiffnse", nseselection);
			request.setAttribute("Dayepsdiffothers", otherselection);
			request.setAttribute("DayepsdiffSMA", smaselection);
			request.setAttribute("DayepsdiffPP", ppselection);
			request.setAttribute("DayepsdiffS1", s1selection);
			request.setAttribute("DayepsdiffS2", s2selection);
			request.setAttribute("DayepsdiffS3", s3selection);
			request.setAttribute("DayepsdiffS4", s4selection);
			request.setAttribute("DayepsdiffR1", r1selection);
			request.setAttribute("DayepsdiffR2", r2selection);
			request.setAttribute("DayepsdiffR3", r3selection);
			request.setAttribute("DayepsdiffR4", r4selection);
			request.setAttribute("Dayepsdiffrangemin", rangemin);
			request.setAttribute("Dayepsdiffrangemax", rangemax);
			request.setAttribute("percentk", stochkselection);
			request.setAttribute("percentd", stochdselection);
			request.setAttribute("Dayepsdifftestsma", testsmaselection);
			request.setAttribute("Dayepsdifftestpivot", testpivotselection);
			
			System.out.println(rangemin +":" + rangemax +":" + stochkselection +":" + stochdselection);
			
			
		}
		else
		{
		HashMap <String,Stocktwodaystat> statsmap = (HashMap <String,Stocktwodaystat>) getServletContext().getAttribute("StockDaytwodaystats");
		HashMap <String,Daysma50> sma50map = (HashMap<String, Daysma50>) getServletContext().getAttribute("Daysma50");
		HashMap <String,Daypivot> daypivotmap = (HashMap<String, Daypivot>) getServletContext().getAttribute("Daypivot");
		HashMap <String,CPSdiff> CPSdiffmap = new  HashMap <String,CPSdiff>();
		HashMap <String,StochasticFinalval> dayeodstoch = (HashMap <String,StochasticFinalval>) getServletContext().getAttribute("Dayneweodstochastic");
		DecimalFormat df = new DecimalFormat("#.##"); 
		Iterator it = statsmap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
	        String stocksymbol = (String) pair.getKey();
	        if(sma50map.containsKey(stocksymbol))
	        {
	        	Stocktwodaystat sw2 = (Stocktwodaystat) pair.getValue();
	        	Daysma50 sma50 = sma50map.get(stocksymbol);
	        	CPSdiff filterdata = new CPSdiff();
	        	filterdata.setStocksymbol(stocksymbol);
    			filterdata.setCurrclose(sw2.getCURR_CLOSE());
    			filterdata.setPrevclose(sw2.getPREV_CLOSE());
    			filterdata.setPricelevel("SMA");
    			filterdata.setPriceval(Double.valueOf(df.format(sma50.getMovingavg())).toString());
    			if(sw2.getCURR_LOW() <= sma50.getMovingavg() && sw2.getCURR_CLOSE() >= sma50.getMovingavg() )
    				filterdata.setTestsma(true);
    			else
    				filterdata.setTestsma(false);
    			filterdata.setTradedate(sw2.getCURR_ENDATE());
    			
    			filterdata.setPricediff(Double.valueOf(df.format(((sw2.getCURR_CLOSE()-sma50.getMovingavg())/sw2.getCURR_CLOSE())*100)).toString());
    			
    			filterdata.setIspivot(false);
    			filterdata.setIssma(true);
    			System.out.println(stocksymbol);
    			filterdata.setStochk(Double.valueOf(df.format(dayeodstoch.get(stocksymbol).getPrecentk())));
    			filterdata.setStochd(Double.valueOf(df.format(dayeodstoch.get(stocksymbol).getPercentd())));
    			
    			double checkprice=Double.valueOf(df.format(((sw2.getCURR_CLOSE()-sma50.getMovingavg())/sw2.getCURR_CLOSE())*100));
    			if(checkprice < 10.1 && checkprice > -10.1)
    			CPSdiffmap.put(stocksymbol+"S.M.A", filterdata);
	        	
	        	
	        }
	        if(daypivotmap.containsKey(stocksymbol))
	        {
	        	Stocktwodaystat sw2 = (Stocktwodaystat) pair.getValue();
	        	Daypivot daypv = daypivotmap.get(stocksymbol);
	        	String level = "nothing";
	        	double levelprice=0;
	        	try {
	        		CPSdiff newfilterdata = new CPSdiff();
	        		newfilterdata = getCPShashmap("PP",sw2,daypv,stocksymbol,dayeodstoch);
	        		if(newfilterdata != null)
					CPSdiffmap.put(stocksymbol+"P.V.PP",newfilterdata);
	        		
	        		newfilterdata = new CPSdiff();
	        		newfilterdata = getCPShashmap("S1",sw2,daypv,stocksymbol,dayeodstoch);
	        		if(newfilterdata != null)
	        			CPSdiffmap.put(stocksymbol+"P.V.S1",newfilterdata);
	        		
	        		newfilterdata = new CPSdiff();
	        		newfilterdata = getCPShashmap("S2",sw2,daypv,stocksymbol,dayeodstoch);
	        		if(newfilterdata != null)
	        			CPSdiffmap.put(stocksymbol+"P.V.S2",newfilterdata);
	        		
	        		newfilterdata = new CPSdiff();
	        		newfilterdata = getCPShashmap("S3",sw2,daypv,stocksymbol,dayeodstoch);
	        		if(newfilterdata != null)
	        			CPSdiffmap.put(stocksymbol+"P.V.S3",newfilterdata);
	        		
	        		newfilterdata = new CPSdiff();
	        		newfilterdata = getCPShashmap("S4",sw2,daypv,stocksymbol,dayeodstoch);
	        		if(newfilterdata != null)
	        			CPSdiffmap.put(stocksymbol+"P.V.S4",newfilterdata);
	        		
	        		newfilterdata = new CPSdiff();
	        		newfilterdata = getCPShashmap("R1",sw2,daypv,stocksymbol,dayeodstoch);
	        		if(newfilterdata != null)
	        			CPSdiffmap.put(stocksymbol+"P.V.R1",newfilterdata);
	        		
	        		newfilterdata = new CPSdiff();
	        		newfilterdata = getCPShashmap("R2",sw2,daypv,stocksymbol,dayeodstoch);
	        		if(newfilterdata != null)
	        			CPSdiffmap.put(stocksymbol+"P.V.R2",newfilterdata);
	        		
	        		newfilterdata = new CPSdiff();
	        		newfilterdata = getCPShashmap("R3",sw2,daypv,stocksymbol,dayeodstoch);
	        		if(newfilterdata != null)
	        			CPSdiffmap.put(stocksymbol+"P.V.R3",newfilterdata);
	        		
	        		newfilterdata = new CPSdiff();
	        		newfilterdata = getCPShashmap("R4",sw2,daypv,stocksymbol,dayeodstoch);
	        		if(newfilterdata != null)
	        			CPSdiffmap.put(stocksymbol+"P.V.R4",newfilterdata);
	        		
	        		
	        		
		        	
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
	        	
	        	
	        	
	        }
	        }
		session.setAttribute("EODdaypivotsmadiffattr", CPSdiffmap);
		request.setAttribute("EODdaypivotsmadiff",CPSdiffmap );
		
		request.setAttribute("Dayepsdiffnse", "true");
		request.setAttribute("Dayepsdiffothers", "false");
		request.setAttribute("DayepsdiffSMA", "false");
		request.setAttribute("DayepsdiffPP", "false");
		request.setAttribute("DayepsdiffS1", "true");
		request.setAttribute("DayepsdiffS2", "true");
		request.setAttribute("DayepsdiffS3", "true");
		request.setAttribute("DayepsdiffS4", "true");
		request.setAttribute("DayepsdiffR1", "false");
		request.setAttribute("DayepsdiffR2", "false");
		request.setAttribute("DayepsdiffR3", "false");
		request.setAttribute("DayepsdiffR4", "false");
		request.setAttribute("Dayepsdiffrangemin", "-2.5");
		request.setAttribute("Dayepsdiffrangemax", "2.5");
		request.setAttribute("percentk", "100");
		request.setAttribute("percentd", "100");
		request.setAttribute("Dayepsdifftestsma", "false");
		request.setAttribute("Dayepsdifftestpivot", "false");
		//request.setAttribute("Dayepssmapivotoption", "ALL");
		//request.setAttribute("Dayepsperslide","10");
		
		
		
		}
		request.getRequestDispatcher("EODDaySMAPivotDiffV1.jsp").forward(request, response);
		
		}
	

	private CPSdiff getresult(CPSdiff cp, String smaselection, String ppselection, String s1selection,
			String s2selection, String s3selection, String s4selection, String r1selection, String r2selection,
			String r3selection, String r4selection, String rangemin, String rangemax, String stochkselection, String stochdselection) {
		// TODO Auto-generated method stub
		if (smaselection.equals("true") && cp.getPricelevel().equals("SMA") && Double.valueOf(cp.getPricediff()) <= Double.valueOf(rangemax)  && Double.valueOf(cp.getPricediff()) >= Double.valueOf(rangemin) && Double.valueOf(cp.getStochk()) <= Double.valueOf(stochkselection) && Double.valueOf(cp.getStochd()) <= Double.valueOf(stochdselection))  
			return cp;
		if (ppselection.equals("true") && cp.getPricelevel().equals("PP") && Double.valueOf(cp.getPricediff()) <= Double.valueOf(rangemax)  && Double.valueOf(cp.getPricediff()) >= Double.valueOf(rangemin) && Double.valueOf(cp.getStochk()) <= Double.valueOf(stochkselection) && Double.valueOf(cp.getStochd()) <= Double.valueOf(stochdselection))
			return cp;
		if (s1selection.equals("true") && cp.getPricelevel().equals("S1") && Double.valueOf(cp.getPricediff()) <= Double.valueOf(rangemax)  && Double.valueOf(cp.getPricediff()) >= Double.valueOf(rangemin) && Double.valueOf(cp.getStochk()) <= Double.valueOf(stochkselection) && Double.valueOf(cp.getStochd()) <= Double.valueOf(stochdselection))
			return cp;
		if (s2selection.equals("true") && cp.getPricelevel().equals("S2") && Double.valueOf(cp.getPricediff()) <= Double.valueOf(rangemax)  && Double.valueOf(cp.getPricediff()) >= Double.valueOf(rangemin) && Double.valueOf(cp.getStochk()) <= Double.valueOf(stochkselection) && Double.valueOf(cp.getStochd()) <= Double.valueOf(stochdselection) )
			return cp;
		if (s3selection.equals("true") && cp.getPricelevel().equals("S3") && Double.valueOf(cp.getPricediff()) <= Double.valueOf(rangemax)  && Double.valueOf(cp.getPricediff()) >= Double.valueOf(rangemin) && Double.valueOf(cp.getStochk()) <= Double.valueOf(stochkselection) && Double.valueOf(cp.getStochd()) <= Double.valueOf(stochdselection))
			return cp;
		if (s4selection.equals("true") && cp.getPricelevel().equals("S4") && Double.valueOf(cp.getPricediff()) <= Double.valueOf(rangemax)  && Double.valueOf(cp.getPricediff()) >= Double.valueOf(rangemin) && Double.valueOf(cp.getStochk()) <= Double.valueOf(stochkselection) && Double.valueOf(cp.getStochd()) <= Double.valueOf(stochdselection))
			return cp;
		if (r1selection.equals("true") && cp.getPricelevel().equals("R1") && Double.valueOf(cp.getPricediff()) <= Double.valueOf(rangemax)  && Double.valueOf(cp.getPricediff()) >= Double.valueOf(rangemin) && Double.valueOf(cp.getStochk()) <= Double.valueOf(stochkselection) && Double.valueOf(cp.getStochd()) <= Double.valueOf(stochdselection))
			return cp;
		if (r2selection.equals("true") && cp.getPricelevel().equals("R2") && Double.valueOf(cp.getPricediff()) <= Double.valueOf(rangemax)  && Double.valueOf(cp.getPricediff()) >= Double.valueOf(rangemin) && Double.valueOf(cp.getStochk()) <= Double.valueOf(stochkselection) && Double.valueOf(cp.getStochd()) <= Double.valueOf(stochdselection) )
			return cp;
		if (r3selection.equals("true") && cp.getPricelevel().equals("R3") && Double.valueOf(cp.getPricediff()) <= Double.valueOf(rangemax)  && Double.valueOf(cp.getPricediff()) >= Double.valueOf(rangemin)&& Double.valueOf(cp.getStochk()) <= Double.valueOf(stochkselection) && Double.valueOf(cp.getStochd()) <= Double.valueOf(stochdselection) )
			return cp;
		if (r4selection.equals("true") && cp.getPricelevel().equals("R4") && Double.valueOf(cp.getPricediff()) <= Double.valueOf(rangemax)  && Double.valueOf(cp.getPricediff()) >= Double.valueOf(rangemin) && Double.valueOf(cp.getStochk()) <= Double.valueOf(stochkselection) && Double.valueOf(cp.getStochd()) <= Double.valueOf(stochdselection) )
			return cp;
		
		
			
		return null;
			
	
	}

	private CPSdiff getCPShashmap(String pivotlevel, Stocktwodaystat sw2, Daypivot daypv, String stocksymbol, HashMap<String, StochasticFinalval> dayeodstoch) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
		DecimalFormat df = new DecimalFormat("#.##"); 
		Class noparams[] = {};
		double price = 0;
		CPSdiff filterdata = new CPSdiff();
		filterdata.setStocksymbol(stocksymbol);
		filterdata.setCurrclose(sw2.getCURR_CLOSE());
		filterdata.setPrevclose(sw2.getPREV_CLOSE());
		filterdata.setPricelevel(pivotlevel);
		filterdata.setIspivot(true);
		filterdata.setIssma(false);
		filterdata.setTradedate(sw2.getCURR_ENDATE());
		
		
		Class cls = Class.forName("com.mr.data.Daypivot");
		Method method = cls.getDeclaredMethod("get"+pivotlevel, noparams);
		price = (double) method.invoke(daypv, null);
		filterdata.setPriceval(Double.valueOf(df.format(price)).toString());
		
		if (sw2.getCURR_LOW() <= Double.valueOf(filterdata.getPriceval()) && sw2.getCURR_CLOSE() >=  Double.valueOf(filterdata.getPriceval()) )
		{
			filterdata.setTestpivot(true);
		}
		else
		{
			filterdata.setTestpivot(false);
		}
		filterdata.setPricediff(Double.valueOf(df.format(((sw2.getCURR_CLOSE()-price)/sw2.getCURR_CLOSE())*100)).toString());
		double checkprice=Double.valueOf(filterdata.getPricediff());
		filterdata.setStochk(Double.valueOf(df.format(dayeodstoch.get(stocksymbol).getPrecentk())));
		filterdata.setStochd(Double.valueOf(df.format(dayeodstoch.get(stocksymbol).getPercentd())));
		if(checkprice < 10.1 && checkprice > -10.1)
		return filterdata;
		else
		return null;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		HttpSession session = request.getSession(true); 
		/*
		 *  {DayEPSdiffnse200attr : $(DayEPSdiffnse200check).is(':checked'),
                     DayEPSdiffotherattr : $(DayEPSdiffothercheck).is(':checked'),
                     DayEPSdiffSMAattr : $(DayEPSdiffSMAcheck).is(':checked'),
                     DayEPSdiffPPattr : $(DayEPSdiffPPcheck).is(':checked'),
                     DayEPSdiffS1attr : $(DayEPSdiffS1check).is(':checked'),
                     DayEPSdiffS2attr : $(DayEPSdiffS2check).is(':checked'),
                     DayEPSdiffS3attr : $(DayEPSdiffS3check).is(':checked'),
                     DayEPSdiffS4attr : $(DayEPSdiffS4check).is(':checked'),
                     DayEPSdiffR1attr : $(DayEPSdiffR1check).is(':checked'),
                     DayEPSdiffR2attr : $(DayEPSdiffR2check).is(':checked'),
                     DayEPSdiffR3attr : $(DayEPSdiffR3check).is(':checked'),
                     DayEPSdiffR4attr : $(DayEPSdiffR4check).is(':checked')
                     
                    
		 */
		session.setAttribute("DayEPSdiffnse200selection", request.getParameter("DayEPSdiffnse200attr"));
		session.setAttribute("DayEPSdiffotherselection", request.getParameter("DayEPSdiffotherattr"));
		session.setAttribute("DayEPSdiffSMAselection", request.getParameter("DayEPSdiffSMAattr"));
		session.setAttribute("DayEPSdiffPPselection", request.getParameter("DayEPSdiffPPattr"));
		session.setAttribute("DayEPSdiffS1selection", request.getParameter("DayEPSdiffS1attr"));
		session.setAttribute("DayEPSdiffS2selection", request.getParameter("DayEPSdiffS2attr"));
		session.setAttribute("DayEPSdiffS3selection", request.getParameter("DayEPSdiffS3attr"));
		session.setAttribute("DayEPSdiffS4selection", request.getParameter("DayEPSdiffS4attr"));
		session.setAttribute("DayEPSdiffR1selection", request.getParameter("DayEPSdiffR1attr"));
		session.setAttribute("DayEPSdiffR2selection", request.getParameter("DayEPSdiffR2attr"));
		session.setAttribute("DayEPSdiffR3selection", request.getParameter("DayEPSdiffR3attr"));
		session.setAttribute("DayEPSdiffR4selection", request.getParameter("DayEPSdiffR4attr"));
		session.setAttribute("DayEPSdiffrangeminselection", request.getParameter("DayEPSdiffrangemin"));
		
		session.setAttribute("DayEPSdiffrangemaxselection", request.getParameter("DayEPSdiffrangemax"));
		session.setAttribute("DayEPSdiffstochkselection", request.getParameter("DayEPSdiffstochk"));
		session.setAttribute("DayEPSdiffstochDselection", request.getParameter("DayEPSdiffstochd"));
		session.setAttribute("DayEPSdifftestsmaselection", request.getParameter("DayEPSdifftestsmaattr"));
		session.setAttribute("DayEPSdifftestpivotselection", request.getParameter("DayEPSdifftestpivotattr"));
		
		
		
		doGet(request, response);
		
		
	}

}
