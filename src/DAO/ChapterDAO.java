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

import bean.Chapter;

public class ChapterDAO {

	/**
	 * 添加章节
	 * @param conn
	 * @param chapter
	 */
	public static void addChapter(Connection conn,Chapter chapter){
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("insert into chapter(bookId,name,content,wordNum,time) values(?,?,?,?,?)");
			pst.setInt(1, chapter.getBookId());
			pst.setString(2, chapter.getName());
			pst.setString(3, chapter.getContent());
			pst.setInt(4, chapter.getWordNum());
			String time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			Timestamp creationTime=Timestamp.valueOf(time);
			pst.setTimestamp(5, creationTime);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改章节
	 * @param conn
	 * @param chapter
	 */
	public static void updateChapter(Connection conn,Chapter chapter){
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("update chapter set name=?,content=?,wordNum=? where id=?");
			pst.setString(1, chapter.getName());
			pst.setString(2, chapter.getContent());
			pst.setInt(3, chapter.getWordNum());
			pst.setInt(4, chapter.getId());
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改章节状态
	 * @param conn
	 * @param state
	 * @param id
	 */
	public static void updateType(Connection conn,byte state,int id){
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("update chapter set state="+state+" where id="+id);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 删除章节
	 * @param conn
	 * @param id
	 */
	public static void delChapter(Connection conn,int id){
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("delete from chapter where id= "+id);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 删除图书所有章节
	 * @param conn
	 * @param bookId
	 */
	public static void delChapters(Connection conn,int bookId){
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("delete from chapter where bookId= "+bookId);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取图书章节数
	 * @param conn
	 * @param bookId
	 * @return
	 */
	public static int getrowCount(Connection conn,int bookId){
		int rowCount=0;
		try {
			Statement sm=conn.createStatement();
			ResultSet rs=sm.executeQuery("select count(*) from chapter where bookId= "+bookId);
			if(rs.next()){
				rowCount=rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowCount;
	}
	/**
	 * 获取图书所有章节
	 * @param conn
	 * @param bookId
	 */
	public static List<Chapter> getChapters(Connection conn,int bookId, int page){
		page*=20;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Chapter> chapters = new ArrayList<Chapter>();
		try {
			pst = conn.prepareStatement("select *from chapter where bookId= "+bookId+" limit "+page+",20");
			rs = pst.executeQuery();
			while(rs.next()){
				Chapter chapter = getChapter(rs);
				chapters.add(chapter);
			}
			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chapters;
	}
	/**
	 * 获取图书所有章节Id
	 * @param conn
	 * @param bookId
	 */
	public static List<Chapter> getChaptersId(Connection conn,int bookId){
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Chapter> chapters = new ArrayList<Chapter>();
		try {
			pst = conn.prepareStatement("select id,name from chapter where bookId= "+bookId);
			rs = pst.executeQuery();
			while(rs.next()){
				Chapter chapter = new Chapter();
				chapter.setId(rs.getInt("id"));
				chapter.setName(rs.getString("name"));
				chapters.add(chapter);
			}
			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chapters;
	}
	/**
	 * 获取图书单章
	 * @param conn
	 * @param id
	 */
	public static Chapter getChapter(Connection conn,int id){
		PreparedStatement pst = null;
		ResultSet rs = null;
		Chapter chapter = new Chapter();
		try {
			pst = conn.prepareStatement("select *from chapter where id= "+id);
			rs = pst.executeQuery();
			while(rs.next()){
				chapter = getChapter(rs);
			}
			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chapter;
	}
	/**
	 * 获取图书最新章节
	 * @param conn
	 * @param bookId
	 */
	public static int getNewChapter(Connection conn,int bookId){
		PreparedStatement pst = null;
		ResultSet rs = null;
		int id = 0;
		try {
			pst = conn.prepareStatement("select *from chapter where bookId= "+bookId+" ORDER BY time DESC limit 1");
			rs = pst.executeQuery();
			if(rs.next()){
				id = rs.getInt("id");
			}
			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	private static Chapter getChapter(ResultSet rs) {
		Chapter chapter = new Chapter();
		try {
			chapter.setId(rs.getInt("id"));
			chapter.setBookId(rs.getInt("bookId"));
			chapter.setWordNum(rs.getInt("wordNum"));
			chapter.setName(rs.getString("name"));
			chapter.setContent(rs.getString("content"));
			chapter.setState(rs.getByte("state"));
			chapter.setTime(rs.getString("time"));
			return chapter;
		} catch (SQLException e) {
			System.out.print("从数据库中提取章节信息出错，请检查字段有无拼写错误");
			e.printStackTrace();
			return null;
		}
	}
	
}
