package Servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.RecommendDAO;
import bean.Recommend;

import util.DButil;

@SuppressWarnings("serial")
public class RecommendServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String method = request.getParameter("method");
		if(method.endsWith("getRecommends")){
			getRecommends(request,response);
		}else if (method.endsWith("addRecommend")) {
			addRecommend(request,response);
		}else if (method.endsWith("delRecommend")) {
			delRecommend(request,response);
		}
	}

	private void delRecommend(HttpServletRequest request,
			HttpServletResponse response) {
		
	}

	private void addRecommend(HttpServletRequest request,
			HttpServletResponse response) {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		String userId = request.getParameter("userId");
		int money = Integer.parseInt(request.getParameter("money"));
		Recommend recommend = new Recommend();
		recommend.setBookId(bookId);
		recommend.setMoney(money);
		recommend.setUserId(userId);
		RecommendDAO.addRecommend(conn, recommend);
	}

	private void getRecommends(HttpServletRequest request,
			HttpServletResponse response) {
		
	}

}
