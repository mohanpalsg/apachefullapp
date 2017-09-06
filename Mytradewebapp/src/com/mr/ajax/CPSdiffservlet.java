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

import com.mr.app.CPSdiff;
import com.mr.app.CPSdiff;
import com.mr.data.StochasticFinalval;
import com.mr.data.Stocktwoweekstat;
import com.mr.data.Weekpivot;
import com.mr.data.Weeksma50;
import com.mr.realtimedata.Pricedata;

/**
 * Servlet implementation class CPSdiffservlet
 */
@WebServlet("/CPSdiffservlet")
public class CPSdiffservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CPSdiffservlet() {
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
		HashMap <String,Weeksma50> sma50map = (HashMap<String, Weeksma50>) getServletContext().getAttribute("weeksma50");
		HashMap <String,Weekpivot> weekpivotmap = (HashMap<String, Weekpivot>) getServletContext().getAttribute("Weekpivot");
		HashMap <String,CPSdiff> CPSdiffmap = new  HashMap <String,CPSdiff>();
		HashMap <String,Pricedata> Currmap = (HashMap <String,Pricedata>) session.getAttribute("rtCurrentpricemap");
		HashMap <String,CPSdiff> finalmap = new HashMap <String,CPSdiff>();
		HashMap <String,String> nsemap = (HashMap <String,String>) getServletContext().getAttribute("nse200");
		
		HashMap <String,StochasticFinalval> dayeodstoch = (HashMap <String,StochasticFinalval>) session.getAttribute("daylivestochastic");
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
		        	 Pricedata currpd = Currmap.get(stocksymbol);
		        	CPSdiff filterdata = new CPSdiff();
		        	filterdata.setStocksymbol(stocksymbol);
	    			filterdata.setCurrclose(currpd.getLastprice());
	    			filterdata.setPrevclose(sw2.getCURR_CLOSE());
	    			filterdata.setPricelevel("SMA");
	    			filterdata.setPriceval(Double.valueOf(df.format(sma50.getMovingavg())).toString());
	    			
	    			if(sw2.getCURR_LOW() <= sma50.getMovingavg() && sw2.getCURR_CLOSE() >= sma50.getMovingavg() )
	    				filterdata.setTestsma(true);
	    			else
	    				filterdata.setTestsma(false);
	    			
	    			filterdata.setTradedate(currpd.getTradedate());
	    			
	    			filterdata.setPricediff(Double.valueOf(df.format(((currpd.getLastprice()-sma50.getMovingavg())/sma50.getMovingavg())*100)).toString());
	    			
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
	    			double checkprice=Double.valueOf(df.format(((currpd.getLastprice()-sma50.getMovingavg())/sma50.getMovingavg())*100));
	    			if(checkprice < 10.1 && checkprice > -10.1)
	    			CPSdiffmap.put(stocksymbol+"S.M.A", filterdata);
		        	
		        	
		        }
		        if(weekpivotmap.containsKey(stocksymbol) && Currmap.containsKey(stocksymbol))
		        {
		        	Stocktwoweekstat sw2 = (Stocktwoweekstat) pair.getValue();
		        	Weekpivot weekpv = weekpivotmap.get(stocksymbol);
		        	Pricedata currpd = Currmap.get(stocksymbol);
		        	String level = "nothing";
		        	double levelprice=0;
		        	try {
		        		CPSdiff newfilterdata = new CPSdiff();
		        		newfilterdata = getCPShashmap("PP",sw2.getCURR_CLOSE(),weekpv,stocksymbol,currpd,dayeodstoch,sw2.getCURR_LOW());
		        		if (newfilterdata != null)
		        			CPSdiffmap.put(stocksymbol+"P.V.PP",newfilterdata);
		        		
		        	     newfilterdata = new CPSdiff();
		        		newfilterdata = getCPShashmap("S1",sw2.getCURR_CLOSE(),weekpv,stocksymbol,currpd,dayeodstoch,sw2.getCURR_LOW());
		        		if (newfilterdata != null)
		        			CPSdiffmap.put(stocksymbol+"P.V.S1",newfilterdata);
		        		
		        		  newfilterdata = new CPSdiff();
			        		newfilterdata = getCPShashmap("S2",sw2.getCURR_CLOSE(),weekpv,stocksymbol,currpd,dayeodstoch,sw2.getCURR_LOW());
			        		if (newfilterdata != null)
			        			CPSdiffmap.put(stocksymbol+"P.V.S2",newfilterdata);
			        		
			        		  newfilterdata = new CPSdiff();
				        		newfilterdata = getCPShashmap("S3",sw2.getCURR_CLOSE(),weekpv,stocksymbol,currpd,dayeodstoch,sw2.getCURR_LOW());
				        		if (newfilterdata != null)
				        			CPSdiffmap.put(stocksymbol+"P.V.S3",newfilterdata);
				        		
				        		  newfilterdata = new CPSdiff();
					        		newfilterdata = getCPShashmap("S4",sw2.getCURR_CLOSE(),weekpv,stocksymbol,currpd,dayeodstoch,sw2.getCURR_LOW());
					        		if (newfilterdata != null)
					        			CPSdiffmap.put(stocksymbol+"P.V.S4",newfilterdata);
		
					        		  newfilterdata = new CPSdiff();
						        		newfilterdata = getCPShashmap("R1",sw2.getCURR_CLOSE(),weekpv,stocksymbol,currpd,dayeodstoch,sw2.getCURR_LOW());
						        		if (newfilterdata != null)
						        			CPSdiffmap.put(stocksymbol+"P.V.R1",newfilterdata);
						        		

						        		  newfilterdata = new CPSdiff();
							        		newfilterdata = getCPShashmap("R2",sw2.getCURR_CLOSE(),weekpv,stocksymbol,currpd,dayeodstoch,sw2.getCURR_LOW());
							        		if (newfilterdata != null)
							        			CPSdiffmap.put(stocksymbol+"P.V.R2",newfilterdata);
							        		

							        		  newfilterdata = new CPSdiff();
								        		newfilterdata = getCPShashmap("R3",sw2.getCURR_CLOSE(),weekpv,stocksymbol,currpd,dayeodstoch,sw2.getCURR_LOW());
								        		if (newfilterdata != null)
								        			CPSdiffmap.put(stocksymbol+"P.V.R3",newfilterdata);
								        		

								        		  newfilterdata = new CPSdiff();
									        		newfilterdata = getCPShashmap("R4",sw2.getCURR_CLOSE(),weekpv,stocksymbol,currpd,dayeodstoch,sw2.getCURR_LOW());
									        		if (newfilterdata != null)
									        			CPSdiffmap.put(stocksymbol+"P.V.R4",newfilterdata);
									        		
									        		
					        		
		        		
		        		
		        		
		        		
						
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			}
			
		        if (session.getAttribute("CPSdiffnse200selection") != null)
				{
					String nseselection =  (String) session.getAttribute("CPSdiffnse200selection");
					String otherselection =  		(String)session.getAttribute("CPSdiffotherselection");
					String smaselection =  (String)session.getAttribute("CPSdiffSMAselection");
					String ppselection =  (String)session.getAttribute("CPSdiffPPselection");
					String s1selection =  (String)session.getAttribute("CPSdiffS1selection");
					String s2selection =  (String)session.getAttribute("CPSdiffS2selection");
					String s3selection = (String)session.getAttribute("CPSdiffS3selection");
					String s4selection =  (String)session.getAttribute("CPSdiffS4selection");
					String r1selection = (String)session.getAttribute("CPSdiffR1selection");
					String r2selection =  (String)session.getAttribute("CPSdiffR2selection");
					String r3selection =  (String)session.getAttribute("CPSdiffR3selection");
					String r4selection = (String)session.getAttribute("CPSdiffR4selection");
					String rangemin = (String)session.getAttribute("CPSdiffrangeminselection");
					String rangemax = (String)session.getAttribute("CPSdiffrangemaxselection");
					String stochkselection = (String)session.getAttribute("CPSdiffstochkselection");
					String stochdselection = (String)session.getAttribute("CPSdiffstochDselection");
					String testsmaselection = (String) session.getAttribute("CPSdifftestsmaselection");
					String testpivotselection = (String) session.getAttribute("CPSdifftestpivotselection");
					
					HashMap <String,String> finaliststk = new HashMap <String,String>();
					Iterator it1 = CPSdiffmap.entrySet().iterator();
					while (it1.hasNext()) {
						Map.Entry pair = (Map.Entry)it1.next();
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
				        		{
				        		finalmap.put(stocksymbol,newcp );	
				        		finaliststk.put(newcp.getStocksymbol(), newcp.getStocksymbol());
				        		}
				        		if(testpivotselection.equals("true") && newcp.isTestpivot() == true)
				        		{
				        			finalmap.put(stocksymbol,newcp );	
					        		finaliststk.put(newcp.getStocksymbol(), newcp.getStocksymbol());
				        		}
				        		if(testsmaselection.equals("false") && testpivotselection.equals("false"))
				        		{
				        			finalmap.put(stocksymbol,newcp );	
					        		finaliststk.put(newcp.getStocksymbol(), newcp.getStocksymbol());
				        		}
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
					        		{
					        		finalmap.put(stocksymbol,newcp );	
					        		finaliststk.put(newcp.getStocksymbol(), newcp.getStocksymbol());
					        		}
					        		if(testpivotselection.equals("true") && newcp.isTestpivot() == true)
					        		{
					        			finalmap.put(stocksymbol,newcp );	
						        		finaliststk.put(newcp.getStocksymbol(), newcp.getStocksymbol());
					        		}
					        		if(testsmaselection.equals("false") && testpivotselection.equals("false"))
					        		{
					        			finalmap.put(stocksymbol,newcp );	
						        		finaliststk.put(newcp.getStocksymbol(), newcp.getStocksymbol());
					        		}
				        		}	
				        	}
				        		// finalmap.put(stocksymbol, getresult(cp,diffselected,smaselected,ppselected,s1selected,s2selected,s3selected,s4selected,r1selected,r2selected,r3selected,r4selected));	
				        }
				        
					}
					session.setAttribute("Weekdiifservfilterstock", finaliststk);
					request.setAttribute("currentpricesmadiff",finalmap );
					
					request.setAttribute("cpsdiffnse", nseselection);
					request.setAttribute("cpsdiffothers", otherselection);
					request.setAttribute("cpsdiffSMA", smaselection);
					request.setAttribute("cpsdiffPP", ppselection);
					request.setAttribute("cpsdiffS1", s1selection);
					request.setAttribute("cpsdiffS2", s2selection);
					request.setAttribute("cpsdiffS3", s3selection);
					request.setAttribute("cpsdiffS4", s4selection);
					request.setAttribute("cpsdiffR1", r1selection);
					request.setAttribute("cpsdiffR2", r2selection);
					request.setAttribute("cpsdiffR3", r3selection);
					request.setAttribute("cpsdiffR4", r4selection);
					request.setAttribute("cpsdiffrangemin", rangemin);
					request.setAttribute("cpsdiffrangemax", rangemax);
					request.setAttribute("percentk", stochkselection);
					request.setAttribute("percentd", stochdselection);
					request.setAttribute("cpsdifftestsma", testsmaselection);
					request.setAttribute("cpsdifftestpivot", testpivotselection);
					
				}
		        else
		        {
		        	request.setAttribute("currentpricesmadiff",CPSdiffmap );
		    		
		    		request.setAttribute("cpsdiffnse", "true");
		    		request.setAttribute("cpsdiffothers", "false");
		    		request.setAttribute("cpsdiffSMA", "false");
		    		request.setAttribute("cpsdiffPP", "false");
		    		request.setAttribute("cpsdiffS1", "true");
		    		request.setAttribute("cpsdiffS2", "true");
		    		request.setAttribute("cpsdiffS3", "true");
		    		request.setAttribute("cpsdiffS4", "true");
		    		request.setAttribute("cpsdiffR1", "false");
		    		request.setAttribute("cpsdiffR2", "false");
		    		request.setAttribute("cpsdiffR3", "false");
		    		request.setAttribute("cpsdiffR4", "false");
		    		request.setAttribute("cpsdiffrangemin", "-10");
					request.setAttribute("cpsdiffrangemax", "10");
					request.setAttribute("percentk", "100");
					request.setAttribute("percentd", "100");
					request.setAttribute("cpsdifftestsma", "false");
					request.setAttribute("cpsdifftestpivot", "false");
					
		        }
			
			
		    	request.getRequestDispatcher("CURRENTWeekSMAPivotdiffV1.jsp").forward(request, response);
		
	}
	
	}
	
	
	private CPSdiff getCPShashmap(String pivotlevel, double curr_CLOSE, Weekpivot weekpv, String stocksymbol,
			Pricedata cp, HashMap<String, StochasticFinalval> dayeodstoch ,double curr_low) {
		// TODO Auto-generated method stub
		DecimalFormat df = new DecimalFormat("#.##"); 
		Class noparams[] = {};
		double price = 0;
		CPSdiff filterdata = new CPSdiff();
		filterdata.setStocksymbol(stocksymbol);
		filterdata.setCurrclose(cp.getLastprice());
		filterdata.setPrevclose(curr_CLOSE);
		filterdata.setPricelevel(pivotlevel);
		filterdata.setIspivot(true);
		filterdata.setIssma(false);
		filterdata.setTradedate(cp.getTradedate());
		
		
		try {
			Class cls = Class.forName("com.mr.data.Weekpivot");
			Method method = cls.getDeclaredMethod("get"+pivotlevel, noparams);
			price = (double) method.invoke(weekpv, null);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		filterdata.setPriceval(Double.valueOf(df.format(price)).toString());
		filterdata.setPriceval(Double.valueOf(df.format(price)).toString());
		if (curr_low <= Double.valueOf(filterdata.getPriceval()) && curr_CLOSE >=  Double.valueOf(filterdata.getPriceval()) )
		{
			filterdata.setTestpivot(true);
		}
		else
		{
			filterdata.setTestpivot(false);
		}
		
		filterdata.setPricediff(Double.valueOf(df.format(((cp.getLastprice()-price)/price)*100)).toString());
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
		
		double  pricecheck = Double.valueOf(filterdata.getPricediff());
		if (pricecheck < 10.1 && pricecheck > -10.1)
		return filterdata;
		else
	    return null;
	}

	/**
	 * @param rangemax 
	 * @param rangemin 
	 * @param stochdselection 
	 * @param stochkselection 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	private CPSdiff getresult(CPSdiff cp, String smaselection, String ppselection, String s1selection,
			String s2selection, String s3selection, String s4selection, String r1selection, String r2selection,
			String r3selection, String r4selection, String rangemin, String rangemax, String stochkselection, String stochdselection) {
		// TODO Auto-generated method stub
		if (smaselection.equals("true") && cp.getPricelevel().equals("SMA") && Double.valueOf(cp.getPricediff()) <= Double.valueOf(rangemax)  && Double.valueOf(cp.getPricediff()) >= Double.valueOf(rangemin) && Double.valueOf(cp.getStochk()) <= Double.valueOf(stochkselection) && Double.valueOf(cp.getStochd()) <= Double.valueOf(stochdselection) )
			return cp;
		if (ppselection.equals("true") && cp.getPricelevel().equals("PP") && Double.valueOf(cp.getPricediff()) <= Double.valueOf(rangemax)  && Double.valueOf(cp.getPricediff()) >= Double.valueOf(rangemin) && Double.valueOf(cp.getStochk()) <= Double.valueOf(stochkselection) && Double.valueOf(cp.getStochd()) <= Double.valueOf(stochdselection))
			return cp;
		if (s1selection.equals("true") && cp.getPricelevel().equals("S1") && Double.valueOf(cp.getPricediff()) <= Double.valueOf(rangemax)  && Double.valueOf(cp.getPricediff()) >= Double.valueOf(rangemin) && Double.valueOf(cp.getStochk()) <= Double.valueOf(stochkselection) && Double.valueOf(cp.getStochd()) <= Double.valueOf(stochdselection))
			return cp;
		if (s2selection.equals("true") && cp.getPricelevel().equals("S2") && Double.valueOf(cp.getPricediff()) <= Double.valueOf(rangemax)  && Double.valueOf(cp.getPricediff()) >= Double.valueOf(rangemin) && Double.valueOf(cp.getStochk()) <= Double.valueOf(stochkselection) && Double.valueOf(cp.getStochd()) <= Double.valueOf(stochdselection))
			return cp;
		if (s3selection.equals("true") && cp.getPricelevel().equals("S3") && Double.valueOf(cp.getPricediff()) <= Double.valueOf(rangemax)  && Double.valueOf(cp.getPricediff()) >= Double.valueOf(rangemin) && Double.valueOf(cp.getStochk()) <= Double.valueOf(stochkselection) && Double.valueOf(cp.getStochd()) <= Double.valueOf(stochdselection))
			return cp;
		if (s4selection.equals("true") && cp.getPricelevel().equals("S4") && Double.valueOf(cp.getPricediff()) <= Double.valueOf(rangemax)  && Double.valueOf(cp.getPricediff()) >= Double.valueOf(rangemin) && Double.valueOf(cp.getStochk()) <= Double.valueOf(stochkselection) && Double.valueOf(cp.getStochd()) <= Double.valueOf(stochdselection))
			return cp;
		if (r1selection.equals("true") && cp.getPricelevel().equals("R1") && Double.valueOf(cp.getPricediff()) <= Double.valueOf(rangemax)  && Double.valueOf(cp.getPricediff()) >= Double.valueOf(rangemin) && Double.valueOf(cp.getStochk()) <= Double.valueOf(stochkselection) && Double.valueOf(cp.getStochd()) <= Double.valueOf(stochdselection))
			return cp;
		if (r2selection.equals("true") && cp.getPricelevel().equals("R2") && Double.valueOf(cp.getPricediff()) <= Double.valueOf(rangemax)  && Double.valueOf(cp.getPricediff()) >= Double.valueOf(rangemin) && Double.valueOf(cp.getStochk()) <= Double.valueOf(stochkselection) && Double.valueOf(cp.getStochd()) <= Double.valueOf(stochdselection))
			return cp;
		if (r3selection.equals("true") && cp.getPricelevel().equals("R3") && Double.valueOf(cp.getPricediff()) <= Double.valueOf(rangemax)  && Double.valueOf(cp.getPricediff()) >= Double.valueOf(rangemin) && Double.valueOf(cp.getStochk()) <= Double.valueOf(stochkselection) && Double.valueOf(cp.getStochd()) <= Double.valueOf(stochdselection))
			return cp;
		if (r4selection.equals("true") && cp.getPricelevel().equals("R4") && Double.valueOf(cp.getPricediff()) <= Double.valueOf(rangemax)  && Double.valueOf(cp.getPricediff()) >= Double.valueOf(rangemin) && Double.valueOf(cp.getStochk()) <= Double.valueOf(stochkselection) && Double.valueOf(cp.getStochd()) <= Double.valueOf(stochdselection))
			return cp;
		
		
			
		return null;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true); 
		session.setAttribute("CPSdiffnse200selection", request.getParameter("CPSdiffnse200attr"));
		session.setAttribute("CPSdiffotherselection", request.getParameter("CPSdiffotherattr"));
		session.setAttribute("CPSdiffSMAselection", request.getParameter("CPSdiffSMAattr"));
		session.setAttribute("CPSdiffPPselection", request.getParameter("CPSdiffPPattr"));
		session.setAttribute("CPSdiffS1selection", request.getParameter("CPSdiffS1attr"));
		session.setAttribute("CPSdiffS2selection", request.getParameter("CPSdiffS2attr"));
		session.setAttribute("CPSdiffS3selection", request.getParameter("CPSdiffS3attr"));
		session.setAttribute("CPSdiffS4selection", request.getParameter("CPSdiffS4attr"));
		session.setAttribute("CPSdiffR1selection", request.getParameter("CPSdiffR1attr"));
		session.setAttribute("CPSdiffR2selection", request.getParameter("CPSdiffR2attr"));
		session.setAttribute("CPSdiffR3selection", request.getParameter("CPSdiffR3attr"));
		session.setAttribute("CPSdiffR4selection", request.getParameter("CPSdiffR4attr"));
		session.setAttribute("CPSdiffrangeminselection", request.getParameter("CPSdiffrangemin"));
		session.setAttribute("CPSdiffrangemaxselection", request.getParameter("CPSdiffrangemax"));
		session.setAttribute("CPSdiffstochkselection", request.getParameter("CPSdiffstochk"));
		session.setAttribute("CPSdiffstochDselection", request.getParameter("CPSdiffstochd"));
		session.setAttribute("CPSdifftestsmaselection", request.getParameter("CPSdifftestsmaattr"));
		session.setAttribute("CPSdifftestpivotselection", request.getParameter("CPSdifftestpivotattr"));
		
		
		doGet(request, response);
	}

}
