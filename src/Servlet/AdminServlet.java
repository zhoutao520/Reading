package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DButil;
import DAO.AdminDAO;

import bean.Admin;

import com.alibaba.fastjson.JSONObject;

@SuppressWarnings("serial")
public class AdminServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String method = request.getParameter("method");
		if(method.endsWith("getAdmins")){
			getAdmins(request,response);
		}else if(method.endsWith("login")){
			login(request,response);
		}else if (method.endsWith("addAdmin")) {
			addAdmin(request,response);
		}else if (method.endsWith("editAdmin")) {
			editAdmin(request,response);
		}else if (method.endsWith("delAdmin")) {
			delAdmin(request,response);
		}
	}

	private void delAdmin(HttpServletRequest request,
			HttpServletResponse response) {
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int id = Integer.parseInt(request.getParameter("id"));
		AdminDAO.delAdmin(conn, id);
	}

	private void addAdmin(HttpServletRequest request,
			HttpServletResponse response) {
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");
		Admin admin = new Admin();
		admin.setId(id);
		admin.setName(name);
		admin.setPassword(password);
		admin.setPhone(phone);
		AdminDAO.addAdmin(conn, admin);
	}

	private void editAdmin(HttpServletRequest request,
			HttpServletResponse response) {
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");
		Admin admin = new Admin();
		admin.setId(id);
		admin.setName(name);
		admin.setPassword(password);
		admin.setPhone(phone);
		AdminDAO.updateAdmin(conn, admin);
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int id=Integer.parseInt(request.getParameter("id"));
		String password=request.getParameter("password");
		JSONObject json=new JSONObject();
		if(AdminDAO.login(conn,id , password)){
				json.put("success", true);
				json.put("msg", "登录成功！");
		}else{
			json.put("success", false);
			json.put("msg", "账号或密码错误！");
		}
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}

	private void getAdmins(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		List<Admin> admins = AdminDAO.getAdimins(conn);
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "获取成功！");
		json.put("admins",  admins );
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}

}
