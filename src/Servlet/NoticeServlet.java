package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;


import DAO.NoticeDAO;
import bean.Notice;

import util.DButil;

@SuppressWarnings("serial")
public class NoticeServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String method = request.getParameter("method");
		if(method.endsWith("getNotices")){
			getNotices(request,response);
		}else if (method.endsWith("addNotice")) {
			addNotice(request,response);
		}else if (method.endsWith("delNotice")) {
			delNotice(request,response);
		}else if (method.endsWith("getNotice")) {
			getNotice(request,response);
		}
	}

	private void getNotice(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int id = Integer.parseInt(request.getParameter("id"));
		Notice notice = NoticeDAO.getNotice(conn, id);
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "获取成功！");
		json.put("rows",  notice);
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}

	private void delNotice(HttpServletRequest request,
			HttpServletResponse response) {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int id = Integer.parseInt(request.getParameter("id"));
		NoticeDAO.delNotice(conn, id);
	}

	private void addNotice(HttpServletRequest request,
			HttpServletResponse response) {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		String adminId = request.getParameter("adminId");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String photo = request.getParameter("photo");
		Notice notice = new Notice();
		notice.setAdminId(adminId);
		notice.setContent(content);
		notice.setTitle(title);
		notice.setPhoto(photo);
		NoticeDAO.addNotice(conn, notice);		
	}

	private void getNotices(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int size = Integer.parseInt(request.getParameter("size"));
		size*=10;
		byte type = 0;
		List<Notice> notices = NoticeDAO.getNotices(conn,size, type);
		int total = NoticeDAO.getrowCount(conn);
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "获取成功！");
		json.put("total", total );
		json.put("rows",  notices );
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}

}
