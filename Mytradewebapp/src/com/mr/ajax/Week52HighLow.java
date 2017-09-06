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

/**
 * Servlet implementation class Week52HighLow
 */
@WebServlet("/Week52HighLow")
public class Week52HighLow extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Week52HighLow() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		
		System.out.println("entered class");
		HttpSession session = request.getSession(true); 
		HashMap <String,Stocktwoweekstat> statsmap = (HashMap <String,Stocktwoweekstat>) getServletContext().getAttribute("Stocktwoweekstats");
		HashMap <String,HistoricHighLow>  HistHLmap = (HashMap<String, HistoricHighLow>) getServletContext().getAttribute("Week52HL");
		
		HashMap <String,StochasticFinalval> dayeodstoch = (HashMap <String,StochasticFinalval>) getServletContext().getAttribute("Dayneweodstochastic");
		HashMap <String,String> nsemap = (HashMap <String,String>) getServletContext().getAttribute("nse200");
		
		if(session.getAttribute("WEEK52diffnse200selection") != null)
		{
			HashMap <String,AlltimeHL>  finalmap = new HashMap<String, AlltimeHL>();
			HashMap <String,AlltimeHL>  Tempmap = (HashMap<String, AlltimeHL>) session.getAttribute("Week52HighLowattr");
			String nselection = (String)session.getAttribute("WEEK52diffnse200selection");
			String otherselection = (String)session.getAttribute("WEEK52diffotherselection");
			String lowdiffselection = (String)session.getAttribute("WEEK52lowdiffrangeselection");
			String highdiffdelection = (String)session.getAttribute("WEEK52highdiffrangeselection");
			String stochkselection = (String)session.getAttribute("WEEK52diffstockkselection");
			String stockdseelction = (String)session.getAttribute("WEEK52diffstockdselection");
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
			request.setAttribute("WEEK52diffnse", nselection);
			request.setAttribute("WEEK52diffothers", otherselection);
			request.setAttribute("WEEK52diffrangemax", lowdiffselection);
			request.setAttribute("WEEK52diffrangemin", highdiffdelection);
			request.setAttribute("WEEK52percentk",stochkselection);
			request.setAttribute("WEEK52percentd", stockdseelction);
			
			
			
			
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
			
			session.setAttribute("Week52HighLowattr", AlltimeHighLow);
			request.setAttribute("stocklist",AlltimeHighLow );
			request.setAttribute("WEEK52diffnse", "true");
			request.setAttribute("WEEK52diffothers", "false");
			request.setAttribute("WEEK52diffrangemax", "4");
			request.setAttribute("WEEK52diffrangemin", "-4");
			request.setAttribute("WEEK52percentk", "100");
			request.setAttribute("WEEK52percentd", "100");

			
			
		}
		
		
		request.getRequestDispatcher("Week52highlow.jsp").forward(request, response);
		
		
		
	
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
		session.setAttribute("WEEK52diffnse200selection", request.getParameter("WEEK52diffnse200attr"));
		session.setAttribute("WEEK52diffotherselection", request.getParameter("WEEK52diffotherattr"));
		session.setAttribute("WEEK52lowdiffrangeselection", request.getParameter("WEEK52lowdiffrangemax"));
		session.setAttribute("WEEK52highdiffrangeselection", request.getParameter("WEEK52highdiffrangemin"));
		session.setAttribute("WEEK52diffstockkselection", request.getParameter("WEEK52diffstochk"));
		session.setAttribute("WEEK52diffstockdselection", request.getParameter("WEEK52diffstochd"));
		doGet(request, response);
		
	}

}
