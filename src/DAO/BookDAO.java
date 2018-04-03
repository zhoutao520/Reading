package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import View.BookView;
import bean.Book;

public class BookDAO {

	/**
	 * 添加图书
	 * @param conn
	 * @param book
	 */
	public static void addBook(Connection conn,Book book){
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("insert into book(name,label,authorId,typeId,profile,cover,creationTime) values(?,?,?,?,?,?,?)");
			pst.setString(1, book.getName());
			pst.setString(2, book.getLabel());
			pst.setString(3, book.getAuthorId());
			pst.setInt(4, book.getTypeId());
			pst.setString(5, book.getProfile());
			pst.setString(6, book.getCover());
			String time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			Timestamp creationTime=Timestamp.valueOf(time);
			pst.setTimestamp(7, creationTime);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改图书信息
	 * @param conn
	 * @param book
	 */
	public static void updateBook(Connection conn,Book book){
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("update book set label=?,profile=?,cover=? where id=?");
			pst.setString(1, book.getLabel());
			pst.setString(2, book.getProfile());
			pst.setString(3, book.getCover());
			pst.setInt(4, book.getId());
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改图书字数
	 * @param conn
	 * @param wordNum
	 * @param id
	 */
	public static void updateWordNum(Connection conn,int wordNum,int id){
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("update book set wordNum = wordNum +"+wordNum+" where id="+id);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 图书收藏数自增1
	 * @param conn
	 * @param id
	 */
	public static void updateCollectionNum(Connection conn,int id){
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("update book set collectionNum = collectionNum + 1 where id="+id);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 图书点击数自增1
	 * @param conn
	 * @param id
	 */
	public static void updateClickNum(Connection conn,int id){
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("update book set clickNum = clickNum + 1 where id="+id);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 图书推荐数自增1
	 * @param conn
	 * @param id
	 */
	public static void updateRecommend(Connection conn,int id){
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("update book set recommendNum = recommendNum + 1 where id="+id);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改图书状态
	 * @param conn
	 * @param state
	 * @param id
	 */
	public static void updateState(Connection conn,byte state,int id){
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("update book set state="+state+" where id="+id);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改主编推荐
	 * @param conn
	 * @param state
	 * @param id
	 */
	public static void updateIsRecommend(Connection conn,byte state,int id){
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("update book set isRecommend="+state+" where id="+id);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改图书最新章节
	 * @param conn
	 * @param chapterId
	 * @param id
	 */
	public static void updateChapter(Connection conn,int chapterId,int id){
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("update book set chapterId="+chapterId+" where id="+id);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 删除图书
	 * @param conn
	 * @param id
	 */
	public static void delBook(Connection conn,int id){
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("delete from book where id= "+id);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取查询书籍数
	 * @param conn
	 * @param bookId
	 * @return
	 */
	public static int getrowCount(Connection conn,String search){
		int rowCount=0;
		try {
			Statement sm=conn.createStatement();
			ResultSet rs=sm.executeQuery("select count(*) from book where name LIKE '%"+search+"%' and state!=0");
			if(rs.next()){
				rowCount=rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowCount;
	}
	/**
	 * 获取查询书籍数
	 * @param conn
	 * @param bookId
	 * @return
	 */
	public static int getRowCount(Connection conn,String search){
		int rowCount=0;
		try {
			Statement sm=conn.createStatement();
			ResultSet rs=sm.executeQuery("select count(*) from book where name LIKE '%"+search+"%' and state=0");
			if(rs.next()){
				rowCount=rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowCount;
	}
	/**
	 * 验证是否存在相同书名
	 * @param conn
	 * @param name 书名
	 * @return
	 */
	public static boolean checkBook(Connection conn,String name){
		PreparedStatement pst = null;
		ResultSet rs = null;
		boolean flag=false;
		try {
			pst = conn.prepareStatement("select *from book where name= '"+name+"'");
			rs = pst.executeQuery();
			if(rs.next()){
				flag = true;
			}
			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 获取图书信息
	 * @param conn
	 * @param id
	 */
	public static BookView getBook(Connection conn,int bookId){
		PreparedStatement pst = null;
		ResultSet rs = null;
		BookView book = null;
		try {
			pst = conn.prepareStatement("select *from bookview where bookId= "+bookId);
			rs = pst.executeQuery();
			if(rs.next()){
				book = getBookView(rs);
			}
			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return book;
	}
	/**
	 * 获取多本图书信息
	 * @param conn
	 * @param sql
	 */
	public static List<BookView> getBooks(Connection conn,String sql){
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<BookView> books = new ArrayList<BookView>();
		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()){
				BookView book = getBookView(rs);
				books.add(book);
			}
			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}
//	private static Book getBook(ResultSet rs) {
//		Book book = new Book();
//		try {
//			book.setId(rs.getInt("id"));
//			book.setAuthorId(rs.getString("authorId"));
//			book.setTypeId(rs.getInt("typeId"));
//			book.setTypeId(rs.getInt("typeId"));
//			book.setCollectionNum(rs.getInt("collectionNum"));
//			book.setClickNum(rs.getInt("clickNum"));
//			book.setChapterId(rs.getInt("chapterId"));
//			book.setName(rs.getString("name"));
//			book.setLabel(rs.getString("label"));
//			book.setProfile(rs.getString("profile"));
//			book.setCover(rs.getString("cover"));
//			book.setState(rs.getByte("state"));
//			book.setCreationTime(rs.getString("creationTime"));
//			return book;
//		} catch (SQLException e) {
//			System.out.print("从数据库中提取图书信息出错，请检查字段有无拼写错误");
//			e.printStackTrace();
//			return null;
//		}
//	}
	
	private static BookView getBookView(ResultSet rs) {

		BookView book =new BookView();
		try {
			book.setBookId(rs.getInt("bookId"));
			book.setAuthorId(rs.getString("authorId"));
			book.setCollectionNum(rs.getInt("collectionNum"));
			book.setClickNum(rs.getInt("clickNum"));
			book.setChapterId(rs.getInt("chapterId"));
			book.setAuthorName(rs.getString("authorName"));
			book.setChapterName(rs.getString("chapterName"));
			book.setChapterTime(rs.getString("chapterTime"));
			book.setAuthorProfile(rs.getString("authorProfile"));
			book.setLabel(rs.getString("label"));
			book.setProfile(rs.getString("profile"));
			book.setCover(rs.getString("cover"));
			book.setState(rs.getByte("state"));
			book.setCreationTime(rs.getString("creationTime"));
			book.setFirstId(rs.getInt("firstId"));
			book.setSecondId(rs.getInt("secondId"));
			book.setWordNum(rs.getInt("wordNum"));
			book.setBookName(rs.getString("bookName"));
			book.setFirstName(rs.getString("firstName"));
			book.setSecondName(rs.getString("secondName"));
			book.setHead(rs.getString("head"));
			book.setRecommendNum(rs.getInt("recommendNum"));
			book.setIsRecommend(rs.getByte("isRecommend"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return book;
	}
	
}
