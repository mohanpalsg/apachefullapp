package com.mr.ajax;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mr.realtimedata.DataDownload;

/**
 * Servlet implementation class LiveupdateServlet
 */
@WebServlet("/LiveupdateServlet")
public class LiveupdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LiveupdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
        DataDownload rtdownload = new DataDownload();
        rtdownload.update();
        DateFormat df = new SimpleDateFormat("dd MMM HH:mm:ss");
		
		session.setAttribute("rtCurrentpricemap", rtdownload.getCurrpricemap());
		session.setAttribute("rtpreviouspricemap", rtdownload.getPrevpricemap());
		
		try {
			session.setAttribute("daylivestochastic", rtdownload.getlivestochastic());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.getWriter().append("  "+df.format(rtdownload.getCurrentime())+" IST");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
