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
import com.mr.data.MyindicatorvaluesWeek;
import com.mr.data.StochasticFinalval;
import com.mr.data.Stocktwoweekstat;
import com.mr.data.Weekpivot;
import com.mr.data.Weeksma50;

/**
 * Servlet implementation class EPSdiffservlet
 */
@WebServlet("/EPSdiffservlet")
public class EPSdiffservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EPSdiffservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true); 
		
		if (session.getAttribute("EPSdiffnse200selection") != null)
		{
			String nseselection =  (String) session.getAttribute("EPSdiffnse200selection");
			String otherselection =  		(String)session.getAttribute("EPSdiffotherselection");
			String smaselection =  (String)session.getAttribute("EPSdiffSMAselection");
			String ppselection =  (String)session.getAttribute("EPSdiffPPselection");
			String s1selection =  (String)session.getAttribute("EPSdiffS1selection");
			String s2selection =  (String)session.getAttribute("EPSdiffS2selection");
			String s3selection = (String)session.getAttribute("EPSdiffS3selection");
			String s4selection =  (String)session.getAttribute("EPSdiffS4selection");
			String r1selection = (String)session.getAttribute("EPSdiffR1selection");
			String r2selection =  (String)session.getAttribute("EPSdiffR2selection");
			String r3selection =  (String)session.getAttribute("EPSdiffR3selection");
			String r4selection = (String)session.getAttribute("EPSdiffR4selection");
			String rangemin = (String)session.getAttribute("EPSdiffrangeminselection");
			String rangemax = (String)session.getAttribute("EPSdiffrangemaxselection");
			String stochkselection = (String)session.getAttribute("EPSdiffstochkselection");
			String stochdselection = (String)session.getAttribute("EPSdiffstochDselection");
			String testsmaselection = (String) session.getAttribute("EPSdifftestsmaselection");
			String testpivotselection = (String) session.getAttribute("EPSdifftestpivotselection");
			String st3selection = (String) session.getAttribute("EPSdiffST3selection");
			String st5selection = (String) session.getAttribute("EPSdiffST5selection");
			
			
			HashMap <String,String> nsemap = (HashMap <String,String>) getServletContext().getAttribute("nse200");
			HashMap <String,CPSdiff> CPSdiffmap = (HashMap <String,CPSdiff> ) session.getAttribute("EODpivotsmadiffattr");
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
		        		CPSdiff newcp = (CPSdiff) getresult(cp,smaselection,ppselection,s1selection,s2selection,s3selection,s4selection,r1selection,r2selection,r3selection,r4selection,rangemin,rangemax,stochkselection,stochdselection,st3selection,st5selection);
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
		        		CPSdiff newcp = (CPSdiff) getresult(cp,smaselection,ppselection,s1selection,s2selection,s3selection,s4selection,r1selection,r2selection,r3selection,r4selection,rangemin,rangemax,stochkselection,stochdselection,st3selection,st5selection);
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
			request.setAttribute("EODpivotsmadiff",finalmap );
			
			request.setAttribute("epsdiffnse", nseselection);
			request.setAttribute("epsdiffothers", otherselection);
			request.setAttribute("epsdiffSMA", smaselection);
			request.setAttribute("epsdiffPP", ppselection);
			request.setAttribute("epsdiffS1", s1selection);
			request.setAttribute("epsdiffS2", s2selection);
			request.setAttribute("epsdiffS3", s3selection);
			request.setAttribute("epsdiffS4", s4selection);
			request.setAttribute("epsdiffR1", r1selection);
			request.setAttribute("epsdiffR2", r2selection);
			request.setAttribute("epsdiffR3", r3selection);
			request.setAttribute("epsdiffR4", r4selection);
			request.setAttribute("epsdiffST3", st3selection);
			request.setAttribute("epsdiffST5", st5selection);
			request.setAttribute("epsdiffrangemin", rangemin);
			request.setAttribute("epsdiffrangemax", rangemax);
			request.setAttribute("percentk", stochkselection);
			request.setAttribute("percentd", stochdselection);
			request.setAttribute("epsdifftestsma", testsmaselection);
			request.setAttribute("epsdifftestpivot", testpivotselection);
			
			System.out.println(rangemin +":" + rangemax +":" + stochkselection +":" + stochdselection);
			
		}
		else
		{
		HashMap <String,Stocktwoweekstat> statsmap = (HashMap <String,Stocktwoweekstat>) getServletContext().getAttribute("Stocktwoweekstats");
		HashMap <String,Weeksma50> sma50map = (HashMap<String, Weeksma50>) getServletContext().getAttribute("weeksma50");
		HashMap <String,Weekpivot> weekpivotmap = (HashMap<String, Weekpivot>) getServletContext().getAttribute("Weekpivot");
		HashMap <String,CPSdiff> CPSdiffmap = new  HashMap <String,CPSdiff>();
		HashMap <String,StochasticFinalval> dayeodstoch = (HashMap <String,StochasticFinalval>) getServletContext().getAttribute("Dayneweodstochastic");
		HashMap <String,MyindicatorvaluesWeek> weekstval = (HashMap <String,MyindicatorvaluesWeek>) getServletContext().getAttribute("weekeodstochastic");
		DecimalFormat df = new DecimalFormat("#.##"); 
		Iterator it = statsmap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
	        String stocksymbol = (String) pair.getKey();
	        if(sma50map.containsKey(stocksymbol))
	        {
	        	Stocktwoweekstat sw2 = (Stocktwoweekstat) pair.getValue();
	        	Weeksma50 sma50 = sma50map.get(stocksymbol);
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
    			filterdata.setTradevolume(sw2.getCURR_TRADEVOLUME());
    			filterdata.setPricediff(Double.valueOf(df.format(((sw2.getCURR_CLOSE()-sma50.getMovingavg())/sw2.getCURR_CLOSE())*100)).toString());
    			
    			filterdata.setIspivot(false);
    			filterdata.setIssma(true);
    			if(dayeodstoch.containsKey(stocksymbol))
    			{
    			filterdata.setStochk(Double.valueOf(df.format(dayeodstoch.get(stocksymbol).getPrecentk())));
    			filterdata.setStochd(Double.valueOf(df.format(dayeodstoch.get(stocksymbol).getPercentd())));
    			}
    			else
    			{
    				filterdata.setStochk(200);
    				filterdata.setStochd(200);
    			}
    			
    			double checkprice=Double.valueOf(df.format(((sw2.getCURR_CLOSE()-sma50.getMovingavg())/sw2.getCURR_CLOSE())*100));
    			if(checkprice < 10.1 && checkprice > -10.1)
    			CPSdiffmap.put(stocksymbol+"S.M.A", filterdata);
	        	
	        	
	        }
	        if(weekstval.containsKey(stocksymbol))
	        {
	        	Stocktwoweekstat sw2 = (Stocktwoweekstat) pair.getValue();
	        	MyindicatorvaluesWeek sma50 = weekstval.get(stocksymbol);
	        	CPSdiff filterdata = new CPSdiff();
	        	filterdata.setStocksymbol(stocksymbol);
    			filterdata.setCurrclose(sw2.getCURR_CLOSE());
    			filterdata.setPrevclose(sw2.getPREV_CLOSE());
    			filterdata.setPricelevel("ST3");
    			filterdata.setPriceval(Double.valueOf(df.format(sma50.getTrend3down())).toString());
    			filterdata.setTestsma(false);
    			filterdata.setTradedate(sw2.getCURR_ENDATE());
    			filterdata.setTradevolume(sw2.getCURR_TRADEVOLUME());
    			filterdata.setPricediff(Double.valueOf(df.format(((sw2.getCURR_CLOSE()-sma50.getTrend3down())/sw2.getCURR_CLOSE())*100)).toString());
    			
    			filterdata.setIspivot(false);
    			filterdata.setIssma(false);
    			if(dayeodstoch.containsKey(stocksymbol))
    			{
    			filterdata.setStochk(Double.valueOf(df.format(dayeodstoch.get(stocksymbol).getPrecentk())));
    			filterdata.setStochd(Double.valueOf(df.format(dayeodstoch.get(stocksymbol).getPercentd())));
    			}
    			else
    			{
    				filterdata.setStochk(200);
    				filterdata.setStochd(200);
    			}
    			
    			double checkprice=Double.valueOf(df.format(((sw2.getCURR_CLOSE()-sma50.getTrend3down())/sw2.getCURR_CLOSE())*100));
    			if(checkprice < 10.1 && checkprice > -10.1)
    			CPSdiffmap.put(stocksymbol+"ST3", filterdata);
	        	
	        	
	        }
	        if(weekstval.containsKey(stocksymbol))
	        {
	        	Stocktwoweekstat sw2 = (Stocktwoweekstat) pair.getValue();
	        	MyindicatorvaluesWeek sma50 = weekstval.get(stocksymbol);
	        	CPSdiff filterdata = new CPSdiff();
	        	filterdata.setStocksymbol(stocksymbol);
    			filterdata.setCurrclose(sw2.getCURR_CLOSE());
    			filterdata.setPrevclose(sw2.getPREV_CLOSE());
    			filterdata.setPricelevel("ST5");
    			filterdata.setPriceval(Double.valueOf(df.format(sma50.getTrend5down())).toString());
    			filterdata.setTestsma(false);
    			filterdata.setTradedate(sw2.getCURR_ENDATE());
    			filterdata.setTradevolume(sw2.getCURR_TRADEVOLUME());
    			filterdata.setPricediff(Double.valueOf(df.format(((sw2.getCURR_CLOSE()-sma50.getTrend5down())/sw2.getCURR_CLOSE())*100)).toString());
    			
    			filterdata.setIspivot(false);
    			filterdata.setIssma(false);
    			if(dayeodstoch.containsKey(stocksymbol))
    			{
    			filterdata.setStochk(Double.valueOf(df.format(dayeodstoch.get(stocksymbol).getPrecentk())));
    			filterdata.setStochd(Double.valueOf(df.format(dayeodstoch.get(stocksymbol).getPercentd())));
    			}
    			else
    			{
    				filterdata.setStochk(200);
    				filterdata.setStochd(200);
    			}
    			
    			double checkprice=Double.valueOf(df.format(((sw2.getCURR_CLOSE()-sma50.getTrend5down())/sw2.getCURR_CLOSE())*100));
    			if(checkprice < 10.1 && checkprice > -10.1)
    			CPSdiffmap.put(stocksymbol+"ST5", filterdata);
	        	
	        	
	        }
	        if(weekpivotmap.containsKey(stocksymbol))
	        {
	        	Stocktwoweekstat sw2 = (Stocktwoweekstat) pair.getValue();
	        	Weekpivot weekpv = weekpivotmap.get(stocksymbol);
	        	String level = "nothing";
	        	double levelprice=0;
	        	try {
	        		CPSdiff newfilterdata = new CPSdiff();
	        		newfilterdata = getCPShashmap("PP",sw2,weekpv,stocksymbol,dayeodstoch);
	        		if(newfilterdata != null)
					CPSdiffmap.put(stocksymbol+"P.V.PP",newfilterdata);
	        		
	        		newfilterdata = new CPSdiff();
	        		newfilterdata = getCPShashmap("S1",sw2,weekpv,stocksymbol,dayeodstoch);
	        		if(newfilterdata != null)
	        			CPSdiffmap.put(stocksymbol+"P.V.S1",newfilterdata);
	        		
	        		newfilterdata = new CPSdiff();
	        		newfilterdata = getCPShashmap("S2",sw2,weekpv,stocksymbol,dayeodstoch);
	        		if(newfilterdata != null)
	        			CPSdiffmap.put(stocksymbol+"P.V.S2",newfilterdata);
	        		
	        		newfilterdata = new CPSdiff();
	        		newfilterdata = getCPShashmap("S3",sw2,weekpv,stocksymbol,dayeodstoch);
	        		if(newfilterdata != null)
	        			CPSdiffmap.put(stocksymbol+"P.V.S3",newfilterdata);
	        		
	        		newfilterdata = new CPSdiff();
	        		newfilterdata = getCPShashmap("S4",sw2,weekpv,stocksymbol,dayeodstoch);
	        		if(newfilterdata != null)
	        			CPSdiffmap.put(stocksymbol+"P.V.S4",newfilterdata);
	        		
	        		newfilterdata = new CPSdiff();
	        		newfilterdata = getCPShashmap("R1",sw2,weekpv,stocksymbol,dayeodstoch);
	        		if(newfilterdata != null)
	        			CPSdiffmap.put(stocksymbol+"P.V.R1",newfilterdata);
	        		
	        		newfilterdata = new CPSdiff();
	        		newfilterdata = getCPShashmap("R2",sw2,weekpv,stocksymbol,dayeodstoch);
	        		if(newfilterdata != null)
	        			CPSdiffmap.put(stocksymbol+"P.V.R2",newfilterdata);
	        		
	        		newfilterdata = new CPSdiff();
	        		newfilterdata = getCPShashmap("R3",sw2,weekpv,stocksymbol,dayeodstoch);
	        		if(newfilterdata != null)
	        			CPSdiffmap.put(stocksymbol+"P.V.R3",newfilterdata);
	        		
	        		newfilterdata = new CPSdiff();
	        		newfilterdata = getCPShashmap("R4",sw2,weekpv,stocksymbol,dayeodstoch);
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
		session.setAttribute("EODpivotsmadiffattr", CPSdiffmap);
		request.setAttribute("EODpivotsmadiff",CPSdiffmap );
		
		request.setAttribute("epsdiffnse", "true");
		request.setAttribute("epsdiffothers", "false");
		request.setAttribute("epsdiffSMA", "false");
		request.setAttribute("epsdiffPP", "false");
		request.setAttribute("epsdiffS1", "flse");
		request.setAttribute("epsdiffS2", "false");
		request.setAttribute("epsdiffS3", "false");
		request.setAttribute("epsdiffS4", "false");
		request.setAttribute("epsdiffR1", "false");
		request.setAttribute("epsdiffR2", "false");
		request.setAttribute("epsdiffR3", "false");
		request.setAttribute("epsdiffR4", "false");
		request.setAttribute("epsdiffST3", "true");
		request.setAttribute("epsdiffST5", "true");
		request.setAttribute("epsdiffrangemin", "-10");
		request.setAttribute("epsdiffrangemax", "10");
		request.setAttribute("percentk", "100");
		request.setAttribute("percentd", "100");
		request.setAttribute("epsdifftestsma", "false");
		request.setAttribute("epsdifftestpivot","false");
		
		//request.setAttribute("epssmapivotoption", "ALL");
		//request.setAttribute("epsperslide","10");
		
		
		
		}
		request.getRequestDispatcher("EODWeekSMAPivotDiffV1.jsp").forward(request, response);
		
		}
	

	private CPSdiff getresult(CPSdiff cp, String smaselection, String ppselection, String s1selection,
			String s2selection, String s3selection, String s4selection, String r1selection, String r2selection,
			String r3selection, String r4selection, String rangemin, String rangemax, String stochkselection, String stochdselection, String st3selection, String st5selection) {
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
		if (st3selection.equals("true") && cp.getPricelevel().equals("ST3") && Double.valueOf(cp.getPricediff()) <= Double.valueOf(rangemax)  && Double.valueOf(cp.getPricediff()) >= Double.valueOf(rangemin) && Double.valueOf(cp.getStochk()) <= Double.valueOf(stochkselection) && Double.valueOf(cp.getStochd()) <= Double.valueOf(stochdselection) )
			return cp;
		if (st5selection.equals("true") && cp.getPricelevel().equals("ST5") && Double.valueOf(cp.getPricediff()) <= Double.valueOf(rangemax)  && Double.valueOf(cp.getPricediff()) >= Double.valueOf(rangemin) && Double.valueOf(cp.getStochk()) <= Double.valueOf(stochkselection) && Double.valueOf(cp.getStochd()) <= Double.valueOf(stochdselection) )
			return cp;
		
		
			
		return null;
	}

	private CPSdiff getCPShashmap(String pivotlevel, Stocktwoweekstat sw2, Weekpivot weekpv, String stocksymbol, HashMap<String, StochasticFinalval> dayeodstoch) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
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
		filterdata.setTradevolume(sw2.getCURR_TRADEVOLUME());
		Class cls = Class.forName("com.mr.data.Weekpivot");
		Method method = cls.getDeclaredMethod("get"+pivotlevel, noparams);
		price = (double) method.invoke(weekpv, null);
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
		if(dayeodstoch.containsKey(stocksymbol))
		{
		filterdata.setStochk(Double.valueOf(df.format(dayeodstoch.get(stocksymbol).getPrecentk())));
		filterdata.setStochd(Double.valueOf(df.format(dayeodstoch.get(stocksymbol).getPercentd())));
		}
		else
		{
			filterdata.setStochk(200);
			filterdata.setStochd(200);
		}
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
		 *  {EPSdiffnse200attr : $(EPSdiffnse200check).is(':checked'),
                     EPSdiffotherattr : $(EPSdiffothercheck).is(':checked'),
                     EPSdiffSMAattr : $(EPSdiffSMAcheck).is(':checked'),
                     EPSdiffPPattr : $(EPSdiffPPcheck).is(':checked'),
                     EPSdiffS1attr : $(EPSdiffS1check).is(':checked'),
                     EPSdiffS2attr : $(EPSdiffS2check).is(':checked'),
                     EPSdiffS3attr : $(EPSdiffS3check).is(':checked'),
                     EPSdiffS4attr : $(EPSdiffS4check).is(':checked'),
                     EPSdiffR1attr : $(EPSdiffR1check).is(':checked'),
                     EPSdiffR2attr : $(EPSdiffR2check).is(':checked'),
                     EPSdiffR3attr : $(EPSdiffR3check).is(':checked'),
                     EPSdiffR4attr : $(EPSdiffR4check).is(':checked')
                     
                    
		 */
		session.setAttribute("EPSdiffnse200selection", request.getParameter("EPSdiffnse200attr"));
		session.setAttribute("EPSdiffotherselection", request.getParameter("EPSdiffotherattr"));
		session.setAttribute("EPSdiffSMAselection", request.getParameter("EPSdiffSMAattr"));
		session.setAttribute("EPSdiffPPselection", request.getParameter("EPSdiffPPattr"));
		session.setAttribute("EPSdiffS1selection", request.getParameter("EPSdiffS1attr"));
		session.setAttribute("EPSdiffS2selection", request.getParameter("EPSdiffS2attr"));
		session.setAttribute("EPSdiffS3selection", request.getParameter("EPSdiffS3attr"));
		session.setAttribute("EPSdiffS4selection", request.getParameter("EPSdiffS4attr"));
		session.setAttribute("EPSdiffR1selection", request.getParameter("EPSdiffR1attr"));
		session.setAttribute("EPSdiffR2selection", request.getParameter("EPSdiffR2attr"));
		session.setAttribute("EPSdiffR3selection", request.getParameter("EPSdiffR3attr"));
		session.setAttribute("EPSdiffR4selection", request.getParameter("EPSdiffR4attr"));
		session.setAttribute("EPSdiffST3selection", request.getParameter("EPSdiffST3attr"));
		session.setAttribute("EPSdiffST5selection", request.getParameter("EPSdiffST5attr"));
		session.setAttribute("EPSdiffrangeminselection", request.getParameter("EPSdiffrangemin"));
		session.setAttribute("EPSdiffrangemaxselection", request.getParameter("EPSdiffrangemax"));
		session.setAttribute("EPSdiffstochkselection", request.getParameter("EPSdiffstochk"));
		session.setAttribute("EPSdiffstochDselection", request.getParameter("EPSdiffstochd"));
		session.setAttribute("EPSdifftestsmaselection", request.getParameter("EPSdifftestsmaattr"));
		session.setAttribute("EPSdifftestpivotselection", request.getParameter("EPSdifftestpivotattr"));
		
		
		
		doGet(request, response);
		
		
	}

}
