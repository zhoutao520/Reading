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

import DAO.CommentDAO;
import View.CommentView;
import bean.Comment;

import util.DButil;

@SuppressWarnings("serial")
public class CommentServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String method = request.getParameter("method");
		if(method.endsWith("getComments")){
			getComments(request,response);
		}else if (method.endsWith("addComment")) {
			addComment(request,response);
		}else if (method.endsWith("getUserComment")) {
			getUserComment(request,response);
		}else if (method.endsWith("delComment")) {
			delComment(request,response);
		}else if (method.endsWith("updateComment")) {
			updateComment(request,response);
		}
	}

	private void getUserComment(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		String reviewerId = request.getParameter("reviewerId");
		List<CommentView> comments = CommentDAO.getUserComments(conn, reviewerId);
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "获取成功！");
		json.put("total", comments.size());
		json.put("rows",  comments );
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}

	private void updateComment(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int id = Integer.parseInt(request.getParameter("id"));
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		CommentDAO.updateComment(conn, id);
		List<CommentView> comments = CommentDAO.getComments(conn, bookId,0);
		int size = CommentDAO.getrowCount(conn, bookId);
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "获取成功！");
		json.put("total", size);
		json.put("rows",  comments );
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}

	private void delComment(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int id = Integer.parseInt(request.getParameter("id"));
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		CommentDAO.delComment(conn, id);
		List<CommentView> comments = CommentDAO.getComments(conn, bookId,0);
		int size = CommentDAO.getrowCount(conn, bookId);
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "获取成功！");
		json.put("total", size);
		json.put("rows",  comments );
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}

	private void addComment(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		String reviewerId = request.getParameter("reviewerId");
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		String content = request.getParameter("content");
		Comment comment = new Comment();
		comment.setBookId(bookId);
		comment.setReviewerId(reviewerId);
		comment.setContent(content);
		CommentDAO.addComment(conn, comment);
		List<CommentView> comments = CommentDAO.getComments(conn, bookId,0);
		int size = CommentDAO.getrowCount(conn, bookId);
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "获取成功！");
		json.put("total", size);
		json.put("rows",  comments );
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}

	private void getComments(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		int page = Integer.parseInt(request.getParameter("page"));
		List<CommentView> comments = CommentDAO.getComments(conn, bookId,page);
		int size = CommentDAO.getrowCount(conn, bookId);
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "获取成功！");
		json.put("total", size);
		json.put("rows",  comments );
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}
}
