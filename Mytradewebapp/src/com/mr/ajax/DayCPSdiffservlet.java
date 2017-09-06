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
import com.mr.data.Stocktwodaystat;
import com.mr.data.Daypivot;
import com.mr.data.Daysma50;
import com.mr.data.StochasticFinalval;
import com.mr.realtimedata.Pricedata;

/**
 * Servlet implementation class DayCPSdiffservlet
 */
@WebServlet("/DayCPSdiffservlet")
public class DayCPSdiffservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DayCPSdiffservlet() {
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
		HashMap <String,Daysma50> sma50map = (HashMap<String, Daysma50>) getServletContext().getAttribute("Daysma50");
		HashMap <String,Daypivot> daypivotmap = (HashMap<String, Daypivot>) getServletContext().getAttribute("Daypivot");
		HashMap <String,CPSdiff> DayCPSdiffmap = new  HashMap <String,CPSdiff>();
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
		        	Stocktwodaystat sw2 = (Stocktwodaystat) pair.getValue();
		        	Daysma50 sma50 = sma50map.get(stocksymbol);
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
	    			filterdata.setStochk(Double.valueOf(df.format(dayeodstoch.get(stocksymbol).getPrecentk())));
	    			filterdata.setStochd(Double.valueOf(df.format(dayeodstoch.get(stocksymbol).getPercentd())));
	    			
	    			double checkprice=Double.valueOf(df.format(((currpd.getLastprice()-sma50.getMovingavg())/sma50.getMovingavg())*100));
	    			if(checkprice < 10.1 && checkprice > -10.1)
	    			DayCPSdiffmap.put(stocksymbol+"S.M.A", filterdata);
		        	
		        	
		        }
		        if(daypivotmap.containsKey(stocksymbol) && Currmap.containsKey(stocksymbol))
		        {
		        	Stocktwodaystat sw2 = (Stocktwodaystat) pair.getValue();
		        	Daypivot daypv = daypivotmap.get(stocksymbol);
		        	Pricedata currpd = Currmap.get(stocksymbol);
		        	String level = "nothing";
		        	double levelprice=0;
		        	try {
		        		CPSdiff newfilterdata = new CPSdiff();
		        		newfilterdata = getDayCPShashmap("PP",sw2.getCURR_CLOSE(),daypv,stocksymbol,currpd,dayeodstoch,sw2.getCURR_LOW());
		        		if (newfilterdata != null)
		        			DayCPSdiffmap.put(stocksymbol+"P.V.PP",newfilterdata);
		        		
		        	     newfilterdata = new CPSdiff();
		        		newfilterdata = getDayCPShashmap("S1",sw2.getCURR_CLOSE(),daypv,stocksymbol,currpd,dayeodstoch,sw2.getCURR_LOW());
		        		if (newfilterdata != null)
		        			DayCPSdiffmap.put(stocksymbol+"P.V.S1",newfilterdata);
		        		
		        		  newfilterdata = new CPSdiff();
			        		newfilterdata = getDayCPShashmap("S2",sw2.getCURR_CLOSE(),daypv,stocksymbol,currpd,dayeodstoch,sw2.getCURR_LOW());
			        		if (newfilterdata != null)
			        			DayCPSdiffmap.put(stocksymbol+"P.V.S2",newfilterdata);
			        		
			        		  newfilterdata = new CPSdiff();
				        		newfilterdata = getDayCPShashmap("S3",sw2.getCURR_CLOSE(),daypv,stocksymbol,currpd,dayeodstoch,sw2.getCURR_LOW());
				        		if (newfilterdata != null)
				        			DayCPSdiffmap.put(stocksymbol+"P.V.S3",newfilterdata);
				        		
				        		  newfilterdata = new CPSdiff();
					        		newfilterdata = getDayCPShashmap("S4",sw2.getCURR_CLOSE(),daypv,stocksymbol,currpd,dayeodstoch,sw2.getCURR_LOW());
					        		if (newfilterdata != null)
					        			DayCPSdiffmap.put(stocksymbol+"P.V.S4",newfilterdata);
		
					        		  newfilterdata = new CPSdiff();
						        		newfilterdata = getDayCPShashmap("R1",sw2.getCURR_CLOSE(),daypv,stocksymbol,currpd,dayeodstoch,sw2.getCURR_LOW());
						        		if (newfilterdata != null)
						        			DayCPSdiffmap.put(stocksymbol+"P.V.R1",newfilterdata);
						        		

						        		  newfilterdata = new CPSdiff();
							        		newfilterdata = getDayCPShashmap("R2",sw2.getCURR_CLOSE(),daypv,stocksymbol,currpd,dayeodstoch,sw2.getCURR_LOW());
							        		if (newfilterdata != null)
							        			DayCPSdiffmap.put(stocksymbol+"P.V.R2",newfilterdata);
							        		

							        		  newfilterdata = new CPSdiff();
								        		newfilterdata = getDayCPShashmap("R3",sw2.getCURR_CLOSE(),daypv,stocksymbol,currpd,dayeodstoch,sw2.getCURR_LOW());
								        		if (newfilterdata != null)
								        			DayCPSdiffmap.put(stocksymbol+"P.V.R3",newfilterdata);
								        		

								        		  newfilterdata = new CPSdiff();
									        		newfilterdata = getDayCPShashmap("R4",sw2.getCURR_CLOSE(),daypv,stocksymbol,currpd,dayeodstoch,sw2.getCURR_LOW());
									        		if (newfilterdata != null)
									        			DayCPSdiffmap.put(stocksymbol+"P.V.R4",newfilterdata);
									        		
									        		
					        		
		        		
		        		
		        		
		        		
						
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			}
			
		        if (session.getAttribute("DayCPSdiffnse200selection") != null)
				{
					String nseselection =  (String) session.getAttribute("DayCPSdiffnse200selection");
					String otherselection =  		(String)session.getAttribute("DayCPSdiffotherselection");
					String smaselection =  (String)session.getAttribute("DayCPSdiffSMAselection");
					String ppselection =  (String)session.getAttribute("DayCPSdiffPPselection");
					String s1selection =  (String)session.getAttribute("DayCPSdiffS1selection");
					String s2selection =  (String)session.getAttribute("DayCPSdiffS2selection");
					String s3selection = (String)session.getAttribute("DayCPSdiffS3selection");
					String s4selection =  (String)session.getAttribute("DayCPSdiffS4selection");
					String r1selection = (String)session.getAttribute("DayCPSdiffR1selection");
					String r2selection =  (String)session.getAttribute("DayCPSdiffR2selection");
					String r3selection =  (String)session.getAttribute("DayCPSdiffR3selection");
					String r4selection = (String)session.getAttribute("DayCPSdiffR4selection");
					String rangemin = (String)session.getAttribute("DayCPSdiffrangeminselection");
					String rangemax = (String)session.getAttribute("DayCPSdiffrangemaxselection");
					String stochkselection = (String)session.getAttribute("DayCPSdiffstochkselection");
					String stochdselection = (String)session.getAttribute("DayCPSdiffstochDselection");
					String testsmaselection = (String) session.getAttribute("DayCPSdifftestsmaselection");
					String testpivotselection = (String) session.getAttribute("DayCPSdifftestpivotselection");
					
					
					Iterator it1 = DayCPSdiffmap.entrySet().iterator();
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
					request.setAttribute("daycurrentpricesmadiff",finalmap );
					
					request.setAttribute("Daycpsdiffnse", nseselection);
					request.setAttribute("Daycpsdiffothers", otherselection);
					request.setAttribute("DaycpsdiffSMA", smaselection);
					request.setAttribute("DaycpsdiffPP", ppselection);
					request.setAttribute("DaycpsdiffS1", s1selection);
					request.setAttribute("DaycpsdiffS2", s2selection);
					request.setAttribute("DaycpsdiffS3", s3selection);
					request.setAttribute("DaycpsdiffS4", s4selection);
					request.setAttribute("DaycpsdiffR1", r1selection);
					request.setAttribute("DaycpsdiffR2", r2selection);
					request.setAttribute("DaycpsdiffR3", r3selection);
					request.setAttribute("DaycpsdiffR4", r4selection);
					request.setAttribute("Daycpsdiffrangemin", rangemin);
					request.setAttribute("Daycpsdiffrangemax", rangemax);
					request.setAttribute("percentk", stochkselection);
					request.setAttribute("percentd", stochdselection);
					request.setAttribute("Daycpsdifftestsma", testsmaselection);
					request.setAttribute("Daycpsdifftestpivot", testpivotselection);
				}
		        else
		        {
		        	request.setAttribute("daycurrentpricesmadiff",DayCPSdiffmap );
		    		
		    		request.setAttribute("Daycpsdiffnse", "true");
		    		request.setAttribute("Daycpsdiffothers", "false");
		    		request.setAttribute("DaycpsdiffSMA", "false");
		    		request.setAttribute("DaycpsdiffPP", "false");
		    		request.setAttribute("DaycpsdiffS1", "true");
		    		request.setAttribute("DaycpsdiffS2", "true");
		    		request.setAttribute("DaycpsdiffS3", "true");
		    		request.setAttribute("DaycpsdiffS4", "true");
		    		request.setAttribute("DaycpsdiffR1", "false");
		    		request.setAttribute("DaycpsdiffR2", "false");
		    		request.setAttribute("DaycpsdiffR3", "false");
		    		request.setAttribute("DaycpsdiffR4", "false");
		    		request.setAttribute("Daycpsdiffrangemin", "-2.5");
					request.setAttribute("Daycpsdiffrangemax", "2.5");
					request.setAttribute("percentk", "100");
					request.setAttribute("percentd", "100");
					request.setAttribute("Daycpsdifftestsma", "false");
					request.setAttribute("Daycpsdifftestpivot", "false");
					
		        }
			
			
		    	request.getRequestDispatcher("CURRENTDaySMAPivotdiffV1.jsp").forward(request, response);
		
	}
	
	}
	
	
	private CPSdiff getDayCPShashmap(String pivotlevel, double curr_CLOSE, Daypivot daypv, String stocksymbol,
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
			Class cls = Class.forName("com.mr.data.Daypivot");
			Method method = cls.getDeclaredMethod("get"+pivotlevel, noparams);
			price = (double) method.invoke(daypv, null);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		filterdata.setStochk(Double.valueOf(df.format(dayeodstoch.get(stocksymbol).getPrecentk())));
		filterdata.setStochd(Double.valueOf(df.format(dayeodstoch.get(stocksymbol).getPercentd())));
		
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
		if (smaselection.equals("true") && cp.getPricelevel().equals("SMA") && Double.valueOf(cp.getPricediff()) <= Double.valueOf(rangemax)  && Double.valueOf(cp.getPricediff()) >= Double.valueOf(rangemin) && Double.valueOf(cp.getStochk()) <= Double.valueOf(stochkselection) && Double.valueOf(cp.getStochd()) <= Double.valueOf(stochdselection)  )
			return cp;
		if (ppselection.equals("true") && cp.getPricelevel().equals("PP") && Double.valueOf(cp.getPricediff()) <= Double.valueOf(rangemax)  && Double.valueOf(cp.getPricediff()) >= Double.valueOf(rangemin) && Double.valueOf(cp.getStochk()) <= Double.valueOf(stochkselection) && Double.valueOf(cp.getStochd()) <= Double.valueOf(stochdselection) )
			return cp;
		if (s1selection.equals("true") && cp.getPricelevel().equals("S1") && Double.valueOf(cp.getPricediff()) <= Double.valueOf(rangemax)  && Double.valueOf(cp.getPricediff()) >= Double.valueOf(rangemin) && Double.valueOf(cp.getStochk()) <= Double.valueOf(stochkselection) && Double.valueOf(cp.getStochd()) <= Double.valueOf(stochdselection) )
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
		session.setAttribute("DayCPSdiffnse200selection", request.getParameter("DayCPSdiffnse200attr"));
		session.setAttribute("DayCPSdiffotherselection", request.getParameter("DayCPSdiffotherattr"));
		session.setAttribute("DayCPSdiffSMAselection", request.getParameter("DayCPSdiffSMAattr"));
		session.setAttribute("DayCPSdiffPPselection", request.getParameter("DayCPSdiffPPattr"));
		session.setAttribute("DayCPSdiffS1selection", request.getParameter("DayCPSdiffS1attr"));
		session.setAttribute("DayCPSdiffS2selection", request.getParameter("DayCPSdiffS2attr"));
		session.setAttribute("DayCPSdiffS3selection", request.getParameter("DayCPSdiffS3attr"));
		session.setAttribute("DayCPSdiffS4selection", request.getParameter("DayCPSdiffS4attr"));
		session.setAttribute("DayCPSdiffR1selection", request.getParameter("DayCPSdiffR1attr"));
		session.setAttribute("DayCPSdiffR2selection", request.getParameter("DayCPSdiffR2attr"));
		session.setAttribute("DayCPSdiffR3selection", request.getParameter("DayCPSdiffR3attr"));
		session.setAttribute("DayCPSdiffR4selection", request.getParameter("DayCPSdiffR4attr"));
		session.setAttribute("DayCPSdiffrangeminselection", request.getParameter("DayCPSdiffrangemin"));
		session.setAttribute("DayCPSdiffrangemaxselection", request.getParameter("DayCPSdiffrangemax"));
		session.setAttribute("DayCPSdiffstochkselection", request.getParameter("DayCPSdiffstochk"));
		session.setAttribute("DayCPSdiffstochDselection", request.getParameter("DayCPSdiffstochd"));
		session.setAttribute("DayCPSdifftestsmaselection", request.getParameter("DayCPSdifftestsmaattr"));
		session.setAttribute("DayCPSdifftestpivotselection", request.getParameter("DayCPSdifftestpivotattr"));
		
		
		doGet(request, response);
	}

}
