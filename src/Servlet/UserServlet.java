package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DButil;
import DAO.UserDAO;
import bean.User;

import com.alibaba.fastjson.JSONObject;

@SuppressWarnings("serial")
public class UserServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String method = request.getParameter("method");
		if(method.endsWith("getUser")){
			getUser(request,response);
		}else if (method.endsWith("addUser")) {
			addUser(request,response);
		}else if (method.endsWith("delUser")) {
			delUser(request,response);
		}else if (method.endsWith("updateUser")) {
			updateUser(request,response);
		}else if (method.endsWith("exitUser")) {
			exitUser(request,response);
		}else if (method.endsWith("editPassword")) {
			editPassword(request,response);
		}else if (method.endsWith("login")) {
			login(request,response);
		}else if (method.endsWith("checkPhone")) {
			checkPhone(request,response);
		}
	}

	private void delUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		String phone=request.getParameter("phone");
		UserDAO.delUser(conn, phone);
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "该用户已被注销！");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}

	private void checkPhone(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		String phone=request.getParameter("phone");
		JSONObject json=new JSONObject();
		if(UserDAO.login(conn, phone)){
			json.put("success", false);
			json.put("msg", "该手机号已被注册！");
		}else{
			json.put("success", true);
			json.put("msg", "手机号可以使用！");
		}
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		String phone=request.getParameter("phone");
		String password=request.getParameter("password");
		JSONObject json=new JSONObject();
		if(UserDAO.login(conn, phone)){
			User user = UserDAO.getUser(conn, phone);
			if(password.equals(user.getPassword())){
				json.put("success", true);
				json.put("msg", "登录成功！");
			}else{
				json.put("success", false);
				json.put("msg", "密码错误！");
			}
		}else{
			json.put("success", false);
			json.put("msg", "用户名错误！");
		}
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}

	private void editPassword(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		String phone=request.getParameter("phone");
		String password=request.getParameter("password");
		UserDAO.updatePassword(conn, password,phone);
		PrintWriter out = response.getWriter();
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "获取成功！");
		out.print(json);
		out.flush();
		out.close();
	}

	private void exitUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.getSession().invalidate();	
	}

	private void updateUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		String name=request.getParameter("name");
		String phone=request.getParameter("phone");
		String sex=request.getParameter("sex");
		String realName=request.getParameter("realName");
		String IDcard=request.getParameter("IDcard");
		String head=request.getParameter("head");
		String profile=request.getParameter("profile");
		User user = new User();
		user.setName(name);
		user.setPhone(phone);
		user.setSex(sex);
		user.setHead(head);
		user.setRealName(realName);
		user.setIDcard(IDcard);
		user.setProfile(profile);
		UserDAO.updateUser(conn, user);
		PrintWriter out = response.getWriter();
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "获取成功！");
		out.print(json);
		out.flush();
		out.close();
	}

	private void addUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		String phone=request.getParameter("phone");
		String password=request.getParameter("password");
		User user = new User();
		user.setPhone(phone);
		user.setPassword(password);
		UserDAO.addUser(conn, user);
		PrintWriter out = response.getWriter();
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "获取成功！");
		out.print(json);
		out.flush();
		out.close();
	}

	private void getUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		String phone=request.getParameter("phone");
		User user = UserDAO.getUser(conn, phone);
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "获取成功！");
		json.put("rows", user);
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}


}
