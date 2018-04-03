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

import DAO.BookDAO;
import DAO.ChapterDAO;
import bean.Chapter;

import util.DButil;

@SuppressWarnings("serial")
public class ChapterServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String method = request.getParameter("method");
		if(method.endsWith("getChapters")){
			getChapters(request,response);
		}else if (method.endsWith("getChapter")) {
			getChapter(request,response);
		}else if (method.endsWith("addChapter")) {
			addChapter(request,response);
		}else if (method.endsWith("updateChapter")) {
			updateChapter(request,response);
		}else if (method.endsWith("updateType")) {
			updateType(request,response);
		}else if (method.endsWith("delChapter")) {
			delChapter(request,response);
		}else if (method.endsWith("delChapters")) {
			delChapters(request,response);
		}
	}

	private void getChapter(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int id = Integer.parseInt(request.getParameter("id"));
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		Chapter chapter = ChapterDAO.getChapter(conn, id);
		List<Chapter> Chapters = ChapterDAO.getChaptersId(conn, bookId);
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "获取成功！");
		json.put("chapters", Chapters);
		json.put("rows",  chapter );
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}

	private void delChapters(HttpServletRequest request,
			HttpServletResponse response) {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		ChapterDAO.delChapter(conn, bookId);
	}

	private void delChapter(HttpServletRequest request,
			HttpServletResponse response) {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int id = Integer.parseInt(request.getParameter("id"));
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		int wordNum = Integer.parseInt(request.getParameter("wordNum"));
		ChapterDAO.delChapter(conn, id);
		int cId = ChapterDAO.getNewChapter(conn, bookId);
		BookDAO.updateChapter(conn, cId, bookId);
		BookDAO.updateWordNum(conn, -wordNum, bookId);
	}

	private void updateType(HttpServletRequest request,
			HttpServletResponse response) {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int id = Integer.parseInt(request.getParameter("id"));
		byte state = Byte.parseByte(request.getParameter("state"));
		ChapterDAO.updateType(conn, state, id);
	}

	private void updateChapter(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String content = request.getParameter("content");
		int wordNum = Integer.parseInt(request.getParameter("wordNum"));
		Chapter chapter = new Chapter();
		chapter.setId(id);
		chapter.setContent(content);
		chapter.setName(name);
		chapter.setWordNum(wordNum);
		ChapterDAO.updateChapter(conn, chapter);
		PrintWriter out = response.getWriter();
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "获取成功！");
		out.print(json);
		out.flush();
		out.close();
	}

	private void addChapter(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		String name = request.getParameter("name");
		String content = request.getParameter("content");
		int wordNum = Integer.parseInt(request.getParameter("wordNum"));
		Chapter chapter = new Chapter();
		chapter.setBookId(bookId);
		chapter.setContent(content);
		chapter.setName(name);
		chapter.setWordNum(wordNum);
		ChapterDAO.addChapter(conn, chapter);
		int id = ChapterDAO.getNewChapter(conn, bookId);
		BookDAO.updateChapter(conn, id, bookId);
		BookDAO.updateWordNum(conn, wordNum, bookId);
		PrintWriter out = response.getWriter();
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "获取成功！");
		out.print(json);
		out.flush();
		out.close();
	}

	private void getChapters(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		int page = Integer.parseInt(request.getParameter("page"));
		List<Chapter> chapters = ChapterDAO.getChapters(conn, bookId, page);
		int size = ChapterDAO.getrowCount(conn, bookId);
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "获取成功！");
		json.put("total", size);
		json.put("rows",  chapters );
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}
}
