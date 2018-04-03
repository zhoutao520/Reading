package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import util.DButil;

import DAO.testDAO;


@SuppressWarnings("serial")
public class TestServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public TestServlet() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		float tio = Float.parseFloat(request.getParameter("tio"));
		float me = Float.parseFloat(request.getParameter("me"));
		SimpleDateFormat sdf  = new  SimpleDateFormat( "yyyy-MM-dd" ); 
		try {
			Date time = sdf.parse(request.getParameter("time"));
			testDAO.add(conn, time, tio, me);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONObject json=new JSONObject();
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}
}
