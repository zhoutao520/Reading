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

import bean.Recommend;

public class RecommendDAO {

	/**
	 * 用户推荐
	 * @param conn
	 * @param recommend
	 */
	public static void addRecommend(Connection conn,Recommend recommend){
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("insert into recommend(userId,bookId,time,money) values(?,?,?,?)");
			pst.setString(1, recommend.getUserId());
			pst.setInt(2, recommend.getBookId());
			String time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			Timestamp creationTime=Timestamp.valueOf(time);
			pst.setTimestamp(3, creationTime);
			pst.setInt(4, recommend.getMoney());
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 移除书籍所有推荐
	 * @param conn
	 * @param id
	 */
	public static void delRecommend(Connection conn,int bookId){
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("delete from recommend where bookId="+bookId);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取用户推荐
	 * @param conn
	 * @param userId
	 * @return
	 */
	public static List<Recommend> getRecommends(Connection conn,int userId){
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Recommend> recommends = new ArrayList<Recommend>();
		try {
			pst = conn.prepareStatement("select *from recommend where userId="+userId);
			rs = pst.executeQuery();
			while(rs.next()){
				Recommend recommend = getRecommend(rs);
				recommends.add(recommend);
			}
			pst.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recommends;
	}
	private static Recommend getRecommend(ResultSet rs) {
		Recommend recommend = new Recommend();
		try {
			recommend.setId(rs.getInt("id"));
			recommend.setUserId(rs.getString("userId"));
			recommend.setBookId(rs.getInt("bookId"));
			recommend.setTime(rs.getString("time"));
			recommend.setMoney(rs.getInt("money"));
			return recommend;
		} catch (SQLException e) {
			System.out.print("从数据库中提取用户收藏信息出错，请检查字段有无拼写错误");
			e.printStackTrace();
			return null;
		}
	}
	
}
