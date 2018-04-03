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

import bean.Notice;

public class NoticeDAO {

	/**
	 * 管理员增加活动资讯
	 * @param conn
	 * @param notice
	 */
	public static void addNotice(Connection conn,Notice notice){
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("insert into notice(adminId,title,photo,content,time) values(?,?,?,?,?)");
			pst.setString(1, notice.getAdminId());
			pst.setString(2, notice.getTitle());
			pst.setString(3, notice.getPhoto());
			pst.setString(4, notice.getContent());
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
	 * 管理员删除活动资讯
	 * @param conn
	 * @param id
	 */
	public static void delNotice(Connection conn,int id){
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("delete from notice where id="+id);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取查询资讯数
	 * @param conn
	 * @param bookId
	 * @return
	 */
	public static int getrowCount(Connection conn){
		int rowCount=0;
		try {
			Statement sm=conn.createStatement();
			ResultSet rs=sm.executeQuery("select count(*) from notice");
			if(rs.next()){
				rowCount=rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowCount;
	}
	/**
	 * 获取活动资讯
	 * @param conn
	 * @param id
	 */
	public static List<Notice> getNotices(Connection conn,int size,byte type){
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Notice> notices = new ArrayList<Notice>();
		try {
			pst = conn.prepareStatement("select *from notice where type="+type+" ORDER BY time DESC LIMIT "+size+",10");
			rs = pst.executeQuery();
			while(rs.next()){
				Notice notice = getNotice(rs);
				notices.add(notice);
			}
			pst.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return notices;
	}
	/**
	 * 获取首页活动资讯
	 * @param conn
	 */
	public static List<Notice> getIndexNotices(Connection conn){
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Notice> notices = new ArrayList<Notice>();
		try {
			pst = conn.prepareStatement("select *from notice where type=0 ORDER BY time DESC LIMIT 4");
			rs = pst.executeQuery();
			while(rs.next()){
				Notice notice = getNotice(rs);
				notices.add(notice);
			}
			pst.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return notices;
	}
	/**
	 * 获取单条活动资讯
	 * @param conn
	 * @param id
	 * @return
	 */
	public static Notice getNotice(Connection conn,int id){
		PreparedStatement pst = null;
		ResultSet rs = null;
		Notice notice = null;
		try {
			pst = conn.prepareStatement("select *from notice where id="+id);
			rs = pst.executeQuery();
			if(rs.next()){
				notice = getNotice(rs);
			}
			pst.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return notice;
	}
	private static Notice getNotice(ResultSet rs) {
		Notice notice =new Notice();
		try {
			notice.setId(rs.getInt("id"));
			notice.setAdminId(rs.getString("adminId"));
			notice.setTitle(rs.getString("title"));
			notice.setPhoto(rs.getString("photo"));
			notice.setContent(rs.getString("content"));
			notice.setTime(rs.getString("time"));
			notice.setType(rs.getByte("type"));
			return notice;
		} catch (SQLException e) {
			System.out.print("从数据库中提取活动资讯信息出错，请检查字段有无拼写错误");
			e.printStackTrace();
			return null;
		}
	}
	
}
