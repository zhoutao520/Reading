package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import DAO.SecondTypeDAO;
import bean.SecondType;

import util.DButil;

@SuppressWarnings("serial")
public class SecondTypeServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String method = request.getParameter("method");
		if(method.endsWith("getSecondTypes")){
			getSecondTypes(request,response);
		}
	}

	private void getSecondTypes(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int firstTypeId=Integer.parseInt(request.getParameter("firstTypeId"));
		List<SecondType> secondTypes = new ArrayList<SecondType>();
		secondTypes = SecondTypeDAO.getSecondTypes(conn, firstTypeId);
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "获取成功！");
		json.put("total", secondTypes.size());
		json.put("rows",  secondTypes );
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}


}
