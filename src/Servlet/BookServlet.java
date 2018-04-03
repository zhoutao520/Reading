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
import DAO.NoticeDAO;
import View.BookView;
import bean.Book;
import bean.Notice;

import util.DButil;

@SuppressWarnings("serial")
public class BookServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String method = request.getParameter("method");
		if(method.endsWith("getSearchBooks")){
			getSearchBooks(request,response);
		}else if (method.endsWith("addBooks")) {
			addBook(request,response);
		}else if (method.endsWith("updateBook")) {
			updateBook(request,response);
		}else if (method.endsWith("updateState")) {
			updateState(request,response);
		}else if (method.endsWith("updateCollectionNum")) {
			updateCollectionNum(request,response);
		}else if (method.endsWith("updateClickNum")) {
			updateClickNum(request,response);
		}else if (method.endsWith("updateRecommendNum")) {
			updateRecommendNum(request,response);
		}else if (method.endsWith("updateIsRecommend")) {
			updateIsRecommend(request,response);
		}else if (method.endsWith("delBook")) {
			delBook(request,response);
		}else if (method.endsWith("getBook")) {
			getBook(request,response);
		}else if (method.endsWith("getBooks")) {
			getBooks(request,response);
		}else if (method.endsWith("checkBook")) {
			checkBook(request,response);
		}else if (method.endsWith("indexBook")) {
			indexBook(request,response);
		}else if (method.endsWith("randBook")) {
			randBook(request,response);
		}else if (method.endsWith("adminBook")) {
			adminBook(request,response);
		}else if (method.endsWith("recommendBook")) {
			recommendBook(request,response);
		}
	}

	private void updateIsRecommend(HttpServletRequest request,
			HttpServletResponse response) {
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int id = Integer.parseInt(request.getParameter("bookId"));
		byte state = Byte.parseByte(request.getParameter("state"));
		BookDAO.updateIsRecommend(conn,state, id);
	}


	private void recommendBook(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int size = Integer.parseInt(request.getParameter("size"));
		size*=10;
		String search = request.getParameter("search");
		int sort = Integer.parseInt(request.getParameter("sort"));
		String sql = "";
		switch (sort) {
		case 1:
			sql="SELECT *from bookview WHERE bookName LIKE '%"+search+"%' and state!=0  ORDER BY collectionNum DESC LIMIT "+size+",10";
			break;
		case 2:
			sql="SELECT *from bookview WHERE bookName LIKE '%"+search+"%' and state!=0  ORDER BY clickNum DESC LIMIT "+size+",10";
			break;
		case 3:
			sql="SELECT *from bookview WHERE bookName LIKE '%"+search+"%' and state!=0  ORDER BY recommendNum DESC LIMIT "+size+",10";
			break;
		default:
			sql="SELECT *from bookview WHERE bookName LIKE '%"+search+"%' and state!=0 LIMIT "+size+",10";
			break;
		}
		List<BookView> books = BookDAO.getBooks(conn,sql );
		int total = BookDAO.getrowCount(conn, search);
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "获取成功！");
		json.put("total", total);
		json.put("rows",  books );
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}


	private void delBook(HttpServletRequest request,
			HttpServletResponse response) {
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int id = Integer.parseInt(request.getParameter("bookId"));
		BookDAO.delBook(conn, id);
	}


	private void adminBook(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int size = Integer.parseInt(request.getParameter("size"));
		size*=10;
		String search = request.getParameter("search");
		List<BookView> books = BookDAO.getBooks(conn, "SELECT *from bookview WHERE bookName LIKE '%"+search+"%' and state=0 LIMIT "+size+",10");
		int total = BookDAO.getRowCount(conn, search);
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "获取成功！");
		json.put("total", total);
		json.put("rows",  books );
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}


	private void randBook(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		List<BookView> randbooks = BookDAO.getBooks(conn, "SELECT *from bookview WHERE state != 0 ORDER BY RAND() LIMIT 6");
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "获取成功！");
		json.put("randbooks",  randbooks );
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}


	private void getBooks(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		String sql = request.getParameter("sql");
		List<BookView> books = BookDAO.getBooks(conn, sql);
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "获取成功！");
		json.put("total", books.size());
		json.put("rows",  books );
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}


	private void indexBook(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		List<BookView> editbooks = BookDAO.getBooks(conn, "SELECT *from bookview WHERE isRecommend != 0 and state != 0 LIMIT 6");
		List<BookView> userbooks = BookDAO.getBooks(conn, "SELECT *from bookview WHERE state != 0 ORDER BY recommendNum DESC LIMIT 10");
		List<BookView> hotbooks = BookDAO.getBooks(conn, "SELECT *from bookview WHERE state != 0 ORDER BY collectionNum DESC LIMIT 6");
		List<BookView> clickbooks = BookDAO.getBooks(conn, "SELECT *from bookview WHERE state != 0 ORDER BY clickNum DESC LIMIT 10");
		List<BookView> newbooks = BookDAO.getBooks(conn, "SELECT *from bookview WHERE state != 0 ORDER BY creationTime DESC LIMIT 10");
		List<BookView> randbooks = BookDAO.getBooks(conn, "SELECT *from bookview WHERE state != 0 ORDER BY RAND() LIMIT 6");
		List<Notice> notices = NoticeDAO.getIndexNotices(conn);
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "获取成功！");
		json.put("editbooks",  editbooks );
		json.put("userbooks",  userbooks );
		json.put("hotbooks",  hotbooks );
		json.put("clickbooks",  clickbooks );
		json.put("newbooks",  newbooks );
		json.put("randbooks",  randbooks );
		json.put("notices",  notices );
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();		
	}


	private void updateRecommendNum(HttpServletRequest request,
			HttpServletResponse response) {
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int id = Integer.parseInt(request.getParameter("bookId"));
		BookDAO.updateRecommend(conn, id);
	}


	private void updateState(HttpServletRequest request,
			HttpServletResponse response) {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int id = Integer.parseInt(request.getParameter("id"));
		byte state = Byte.parseByte(request.getParameter("state"));
		BookDAO.updateState(conn, state, id);
	}


	private void checkBook(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}	
		String name = request.getParameter("name");
		JSONObject json=new JSONObject();
		boolean flag = BookDAO.checkBook(conn, name);
		if(flag){
			json.put("success", true);
			json.put("msg", "书名已存在！");
		}else {
			json.put("success", false);
			json.put("msg", "书名不存在！");
		}
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}


	private void getBook(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		BookView book = BookDAO.getBook(conn, bookId);
		BookDAO.updateClickNum(conn, bookId);
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "获取成功！");
		json.put("rows", book);
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}


	private void updateClickNum(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int id = Integer.parseInt(request.getParameter("id"));
		BookDAO.updateClickNum(conn, id);
		PrintWriter out = response.getWriter();
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "获取成功！");
		out.print(json);
		out.flush();
		out.close();
	}


	private void updateCollectionNum(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int id = Integer.parseInt(request.getParameter("id"));
		BookDAO.updateCollectionNum(conn, id);
		
	}


	private void updateBook(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		String label = request.getParameter("label");
		String profile = request.getParameter("profile");
		String cover = request.getParameter("cover");
		int id = Integer.parseInt(request.getParameter("id"));
		Book book = new Book();
		book.setLabel(label);
		book.setProfile(profile);
		book.setCover(cover);
		book.setId(id);
		BookDAO.updateBook(conn, book);
		PrintWriter out = response.getWriter();
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "获取成功！");
		out.print(json);
		out.flush();
		out.close();
	}


	private void addBook(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		String name = request.getParameter("name");
		String label = request.getParameter("label");
		String authorId = request.getParameter("authorId");
		int typeId = Integer.parseInt(request.getParameter("typeId"));
		String profile = request.getParameter("profile");
		String cover = request.getParameter("cover");
		
		Book book = new Book();
		book.setName(name);
		book.setLabel(label);
		book.setAuthorId(authorId);
		book.setTypeId(typeId);
		book.setProfile(profile);
		book.setCover(cover);
		
		BookDAO.addBook(conn, book);
		PrintWriter out = response.getWriter();
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "获取成功！");
		out.print(json);
		out.flush();
		out.close();
	}


	private void getSearchBooks(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//获取数据库连接，统一在这里获取连接，减少创建连接的次数
		Connection conn = DButil.getConnection();
		if (conn == null) {
			return;
		}
		int size = Integer.parseInt(request.getParameter("size"));
		String search = request.getParameter("search");
		List<BookView> books = BookDAO.getBooks(conn, "SELECT *from bookview WHERE bookName LIKE '%"+search+"%' and state!=0 LIMIT "+size+",20");
		int total = BookDAO.getrowCount(conn, search);
		JSONObject json=new JSONObject();
		json.put("success", true);
		json.put("msg", "获取成功！");
		json.put("total", total);
		json.put("rows",  books );
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}

}
