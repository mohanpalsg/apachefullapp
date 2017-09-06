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

import com.mr.data.AlltimeHL;
import com.mr.data.HistoricHighLow;
import com.mr.data.StochasticFinalval;
import com.mr.data.Stocktwoweekstat;
import com.mr.data.Weeksma50;

/**
 * Servlet implementation class AllTimeHighLowServlet
 */
@WebServlet("/AllTimeHighLowServlet")
public class AllTimeHighLowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AllTimeHighLowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("entered class");
		HttpSession session = request.getSession(true); 
		HashMap <String,Stocktwoweekstat> statsmap = (HashMap <String,Stocktwoweekstat>) getServletContext().getAttribute("Stocktwoweekstats");
		HashMap <String,HistoricHighLow>  HistHLmap = (HashMap<String, HistoricHighLow>) getServletContext().getAttribute("HistoricHL");
		
		HashMap <String,StochasticFinalval> dayeodstoch = (HashMap <String,StochasticFinalval>) getServletContext().getAttribute("Dayneweodstochastic");
		HashMap <String,String> nsemap = (HashMap <String,String>) getServletContext().getAttribute("nse200");
		
		if(session.getAttribute("ATHLdiffnse200selection") != null)
		{
			HashMap <String,AlltimeHL>  finalmap = new HashMap<String, AlltimeHL>();
			HashMap <String,AlltimeHL>  Tempmap = (HashMap<String, AlltimeHL>) session.getAttribute("AlltimeHighLowattr");
			String nselection = (String)session.getAttribute("ATHLdiffnse200selection");
			String otherselection = (String)session.getAttribute("ATHLdiffotherselection");
			String lowdiffselection = (String)session.getAttribute("ATHLlowdiffrangeselection");
			String highdiffdelection = (String)session.getAttribute("ATHLhighdiffrangeselection");
			String stochkselection = (String)session.getAttribute("ATHLdiffstockkselection");
			String stockdseelction = (String)session.getAttribute("ATHLdiffstockdselection");
			Iterator it1 = Tempmap.entrySet().iterator();
			while (it1.hasNext()) {
				Map.Entry pair = (Map.Entry)it1.next();
		        String stocksymbol = (String) pair.getKey();
		        AlltimeHL cp = (AlltimeHL)pair.getValue();
			 if (nselection.equals("true"))
		        {
				 if(nsemap.containsKey(cp.getStocksymbol()))
		        	{
					 AlltimeHL newcp = getresult(cp,lowdiffselection,highdiffdelection,stochkselection,stockdseelction);
					 if (newcp != null)
						 finalmap.put(newcp.getStocksymbol(), newcp);
					 
		        	}
				 
		        }
			 if (otherselection.equals("true"))
		        {
				 
				 if(!nsemap.containsKey(cp.getStocksymbol()))
		        	{
					 
					 AlltimeHL newcp = getresult(cp,lowdiffselection,highdiffdelection,stochkselection,stockdseelction);
					 if (newcp != null)
						 finalmap.put(newcp.getStocksymbol(), newcp);
					 
		        	}
				 
		        }
			}
			
			
			
			
			
			
			request.setAttribute("stocklist",finalmap );
			request.setAttribute("ATHLdiffnse", nselection);
			request.setAttribute("ATHLdiffothers", otherselection);
			request.setAttribute("ATHLdiffrangemax", lowdiffselection);
			request.setAttribute("ATHLdiffrangemin", highdiffdelection);
			request.setAttribute("ATHLpercentk",stochkselection);
			request.setAttribute("ATHLpercentd", stockdseelction);
			
			
			
			
		}
		else
		{
			HashMap <String,AlltimeHL>  AlltimeHighLow = new HashMap <String,AlltimeHL>   ();
			Iterator it1 = statsmap.entrySet().iterator();
			DecimalFormat df = new DecimalFormat("#.##"); 
			while (it1.hasNext()) {
				Map.Entry pair = (Map.Entry)it1.next();
		        String stocksymbol = (String) pair.getKey();
		        if(HistHLmap.containsKey(stocksymbol) && dayeodstoch.containsKey(stocksymbol))
		        {
		        	AlltimeHL AHL = new AlltimeHL();
		        	HistoricHighLow HistHL = (HistoricHighLow)HistHLmap.get(stocksymbol);
		        	Stocktwoweekstat sw2 = (Stocktwoweekstat)pair.getValue();
		        	StochasticFinalval sfl = (StochasticFinalval)dayeodstoch.get(stocksymbol);
		        	AHL.setStocksymbol(HistHL.getStocksymbol());
		        	AHL.setHighDate(HistHL.getHighdate());
		        	AHL.setHighprice(HistHL.getHighprice());
		        	AHL.setLowDate(HistHL.getLowdate());
		        	AHL.setLowprice(HistHL.getLowprice());
		        	AHL.setCurrentlowprice(sw2.getCURR_LOW());
		        	AHL.setCurrenthighprice(sw2.getCURR_HIGH());
		        	AHL.setLowdiff(Double.valueOf(df.format(((AHL.getCurrentlowprice()-AHL.getLowprice())/AHL.getLowprice())*100)));
		        	AHL.setHighdiff(Double.valueOf(df.format(((AHL.getCurrenthighprice()-AHL.getHighprice())/AHL.getHighprice())*100)));
		        	AHL.setStockk(Double.valueOf(df.format(sfl.getPrecentk())));
		        	AHL.setStockd(Double.valueOf(df.format(sfl.getPercentd())));
		        	AlltimeHighLow.put(stocksymbol, AHL);
		        
		        }
			}
			
			session.setAttribute("AlltimeHighLowattr", AlltimeHighLow);
			request.setAttribute("stocklist",AlltimeHighLow );
			request.setAttribute("ATHLdiffnse", "true");
			request.setAttribute("ATHLdiffothers", "false");
			request.setAttribute("ATHLdiffrangemax", "4");
			request.setAttribute("ATHLdiffrangemin", "-4");
			request.setAttribute("ATHLpercentk", "100");
			request.setAttribute("ATHLpercentd", "100");

			
			
		}
		
		
		request.getRequestDispatcher("AllTimeHighLow.jsp").forward(request, response);
		
		
		
	}

	private AlltimeHL getresult(AlltimeHL cp, String lowdiffselection, String highdiffdelection, String stochkselection,
			String stockdseelction) {
		// TODO Auto-generated method stub
		
		if ((cp.getLowdiff() < Double.parseDouble(lowdiffselection) || cp.getHighdiff() > Double.parseDouble(highdiffdelection)) && cp.getStockk() < Double.parseDouble(stochkselection) 
				&& cp.getStockd() < Double.parseDouble(stockdseelction))
			return cp;
		
		return null;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(true); 
		session.setAttribute("ATHLdiffnse200selection", request.getParameter("ATHLdiffnse200attr"));
		session.setAttribute("ATHLdiffotherselection", request.getParameter("ATHLdiffotherattr"));
		session.setAttribute("ATHLlowdiffrangeselection", request.getParameter("ATHLlowdiffrangemax"));
		session.setAttribute("ATHLhighdiffrangeselection", request.getParameter("ATHLhighdiffrangemin"));
		session.setAttribute("ATHLdiffstockkselection", request.getParameter("ATHLdiffstochk"));
		session.setAttribute("ATHLdiffstockdselection", request.getParameter("ATHLdiffstochd"));
		doGet(request, response);
	}

}
