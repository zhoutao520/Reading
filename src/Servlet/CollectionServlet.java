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
import DAO.CollectionDAO;
import View.CollectionView;
import bean.Collection;

import util.DButil;

@SuppressWarnings("serial")
public class CollectionServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String method = request.getParameter("method");
		if(method.endsWith("addCollection")){
			addCollection(request,response);
		}else if (method.endsWith("updateCollection")) {
			updateCollection(request,response);
		}else if (method.endsWith("delCollection")) {
			delCollection(request,response);
		}else if (method.endsWith("check")) {
			check(request,response);
		}else if (method.endsWith("getCollection")) {
			getCollection(request,response);
		}
	}
	private void getCollection(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		String userId = request.getParameter("userId");
		List<CollectionView> collectionViews = CollectionDAO.getCollections(conn, userId);
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "获取成功！");
		json.put("total", collectionViews.size());
		json.put("rows",  collectionViews );
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}


	private void check(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		String userId = request.getParameter("userId");
		boolean flag = CollectionDAO.check(conn, userId, bookId);
		JSONObject json=new JSONObject();
		json.put("flag", flag);
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}


	private void delCollection(HttpServletRequest request,
			HttpServletResponse response) {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int id = Integer.parseInt(request.getParameter("id"));
		CollectionDAO.delCollection(conn, id);		
	}


	private void updateCollection(HttpServletRequest request,
			HttpServletResponse response) {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int id = Integer.parseInt(request.getParameter("id"));
		int historyChapter = Integer.parseInt(request.getParameter("historyChapter"));
		CollectionDAO.updateCollection(conn, historyChapter, id);
	}


	private void addCollection(HttpServletRequest request,
			HttpServletResponse response) {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		String userId = request.getParameter("userId");
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		Collection collection = new Collection();
		collection.setUserId(userId);
		collection.setBookId(bookId);
		CollectionDAO.addCollection(conn, collection);
		BookDAO.updateCollectionNum(conn, bookId);
	}
}
