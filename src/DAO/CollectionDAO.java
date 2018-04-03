package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import View.CollectionView;
import bean.Collection;

public class CollectionDAO {

	/**
	 * 用户添加收藏
	 * @param conn
	 * @param collection
	 */
	public static void addCollection(Connection conn,Collection collection){
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("insert into collection(userId,bookId,time) values(?,?,?)");
			pst.setString(1, collection.getUserId());
			pst.setInt(2, collection.getBookId());
			String time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			Timestamp creationTime=Timestamp.valueOf(time);
			pst.setTimestamp(3, creationTime);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改收藏图书的浏览记录
	 * @param conn
	 * @param historyChapter
	 * @param id
	 */
	public static void updateCollection(Connection conn,int historyChapter,int id){
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("update collection set historyChapter=? where id=?");
			pst.setInt(1, historyChapter);
			pst.setInt(2, id);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 用户移除收藏图书
	 * @param conn
	 * @param id
	 */
	public static void delCollection(Connection conn,int id){
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("delete from collection where id="+id);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 验证收藏书籍
	 * @param conn
	 * @param phone
	 * @return
	 */
	public static boolean check(Connection conn, String userId,int bookId) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		boolean flag=false;
		try {
			pst = conn.prepareStatement("select *from collection where userId= '"+userId+"' and bookId="+bookId);
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
	 * 获取收藏书籍
	 * @param conn
	 * @param userId
	 * @return
	 */
	public static List<CollectionView> getCollections(Connection conn,String userId){
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<CollectionView> collections = new ArrayList<CollectionView>();
		try {
			pst = conn.prepareStatement("select *from collectionview where userId= '"+userId+"'");
			rs = pst.executeQuery();
			while(rs.next()){
				CollectionView collection = getCollection(rs);
				collections.add(collection);
			}
			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return collections;
	}
	private static CollectionView getCollection(ResultSet rs) {

		CollectionView collection = new CollectionView();
		try {
			collection.setId(rs.getInt("id"));
			collection.setUserId(rs.getString("userId"));
			collection.setBookId(rs.getInt("bookId"));
			collection.setHistoryChapter(rs.getInt("historyChapter"));
			collection.setCollectionNum(rs.getInt("collectionNum"));
			collection.setClickNum(rs.getInt("clickNum"));
			collection.setAuthorName(rs.getString("authorName"));
			collection.setChapterName(rs.getString("chapterName"));
			collection.setProfile(rs.getString("profile"));
			collection.setCover(rs.getString("cover"));
			collection.setState(rs.getByte("state"));
			collection.setWordNum(rs.getInt("wordNum"));
			collection.setBookName(rs.getString("bookName"));
			collection.setFirstName(rs.getString("firstName"));
			collection.setSecondName(rs.getString("secondName"));
			return collection;
		} catch (SQLException e) {
			System.out.print("从数据库中提取用户收藏信息出错，请检查字段有无拼写错误");
			e.printStackTrace();
			return null;
		}
	}
	
}
