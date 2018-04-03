package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import DAO.AuthorDAO;
import DAO.UserDAO;
import bean.Author;
import bean.User;

import util.DButil;

@SuppressWarnings("serial")
public class AuthorServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String method = request.getParameter("method");
		if(method.endsWith("getAuthor")){
			getAuthor(request,response);
		}else if (method.endsWith("addAuthor")) {
			addAuthor(request,response);
		}else if (method.endsWith("delAuthor")) {
			delAuthor(request,response);
		}else if (method.endsWith("updateAuthor")) {
			updateAuthor(request,response);
		}else if (method.endsWith("exitAuthor")) {
			exitAuthor(request,response);
		}else if (method.endsWith("editPassword")) {
			editPassword(request,response);
		}else if (method.endsWith("login")) {
			login(request,response);
		}else if (method.endsWith("checkPhone")) {
			checkPhone(request,response);
		}
	}

	private void delAuthor(HttpServletRequest request,
			HttpServletResponse response) {
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		String phone=request.getParameter("phone");
		AuthorDAO.delAuthor(conn, phone);
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "该用户已被注销！");
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
		if(AuthorDAO.login(conn, phone)){
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
		if(AuthorDAO.login(conn, phone)){
			Author author = AuthorDAO.getAuthor(conn, phone);
			if(password.equals(author.getPassword())){
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
			HttpServletResponse response) {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		String phone=request.getParameter("phone");
		String password=request.getParameter("password");
		AuthorDAO.updatePassword(conn, password,phone);
	}

	private void exitAuthor(HttpServletRequest request,
			HttpServletResponse response) {
		request.getSession().invalidate();		
	}

	private void updateAuthor(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		String name=request.getParameter("name");
		String phone=request.getParameter("phone");
		String sex=request.getParameter("sex");
		String email=request.getParameter("email");
		String QQ=request.getParameter("QQ");
		String realName=request.getParameter("realName");
		String IDcard=request.getParameter("IDcard");
		String head=request.getParameter("head");
		String profile=request.getParameter("profile");
		Author author = new Author();
		author.setName(name);
		author.setPhone(phone);
		author.setSex(sex);
		author.setHead(head);
		author.setRealName(realName);
		author.setIDcard(IDcard);
		author.setProfile(profile);
		author.setEmail(email);
		author.setQQ(QQ);
		AuthorDAO.updateAuthor(conn, author);
		PrintWriter out = response.getWriter();
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "获取成功！");
		out.print(json);
		out.flush();
		out.close();
	}

	private void addAuthor(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		String phone=request.getParameter("phone");
		String password=request.getParameter("password");
		Author author = new Author();
		author.setPhone(phone);
		author.setPassword(password);
		AuthorDAO.addAuthor(conn, author);
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

	private void getAuthor(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		String phone=request.getParameter("phone");
		Author author = AuthorDAO.getAuthor(conn, phone);
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "获取成功！");
		json.put("rows", author);
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}

}
