package com.mr.ajax;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mr.app.Alertobject;

/**
 * Servlet implementation class LiveAlertServlet
 */
@WebServlet("/LiveAlertServlet")
public class LiveAlertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LiveAlertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		boolean sendresponse = false;
		if(session.getAttribute("newalertadded")!=null)
			sendresponse = (boolean) session.getAttribute("newalertadded");
		if (request.getParameter("auto")!=null)
		{
		if(sendresponse)
		{
		request.setAttribute("stocklist", session.getAttribute("LiveAlert"));
		request.setAttribute("alarmsound", session.getAttribute("newalertadded"));
		request.getRequestDispatcher("LiveAlertshow.jsp").forward(request, response);
		}
		else
		{
			response.getWriter().append("nothing");
		}
		}
		else
		{
			request.setAttribute("stocklist", session.getAttribute("LiveAlert"));
			request.setAttribute("alarmsound", sendresponse);
			request.getRequestDispatcher("LiveAlertshow.jsp").forward(request, response);
		}
		
		 boolean alarm = false;
		
		session.setAttribute("newalertadded", alarm);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
