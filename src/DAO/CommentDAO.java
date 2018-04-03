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

import View.CommentView;
import bean.Comment;

public class CommentDAO {

	/**
	 * 用户添加评论
	 * @param conn
	 * @param comment
	 */
	public static void addComment(Connection conn,Comment comment){
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("insert into comment(reviewerId,bookId,content,time) values(?,?,?,?)");
			pst.setString(1, comment.getReviewerId());
			pst.setInt(2, comment.getBookId());
			pst.setString(3, comment.getContent());
			String time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			Timestamp creationTime=Timestamp.valueOf(time);
			pst.setTimestamp(4, creationTime);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 用户删除评论
	 * @param conn
	 * @param id
	 */
	public static void delComment(Connection conn,int id){
		PreparedStatement pst =null;
		try {
			pst = conn.prepareStatement("delete from comment where id="+id);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 用户点赞
	 * @param conn
	 * @param id
	 */
	public static void updateComment(Connection conn,int id){
		PreparedStatement pst =null;
		try {
			pst = conn.prepareStatement("update comment set praiseNum=praiseNum+1 where id="+id);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取图书评论数
	 * @param conn
	 * @param bookId
	 * @return
	 */
	public static int getrowCount(Connection conn,int bookId){
		int rowCount=0;
		try {
			Statement sm=conn.createStatement();
			ResultSet rs=sm.executeQuery("select count(*) from comment where bookId= "+bookId);
			if(rs.next()){
				rowCount=rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowCount;
	}
	/**
	 * 获取评论
	 * @param conn
	 * @param sql
	 * @return
	 */
	public static List<CommentView> getComments(Connection conn,int bookId,int page){
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<CommentView> comments = new ArrayList<CommentView>();
		try {
			pst = conn.prepareStatement("select *from commentview where bookId= "+bookId+" limit "+page+",10");
			rs = pst.executeQuery();
			while(rs.next()){
				CommentView comment = getComment(rs);
				comments.add(comment);
			}
			pst.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return comments;
	}
	/**
	 * 获取用户评论
	 * @param conn
	 * @param sql
	 * @return
	 */
	public static List<CommentView> getUserComments(Connection conn,String reviewerId){
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<CommentView> comments = new ArrayList<CommentView>();
		try {
			pst = conn.prepareStatement("select *from commentview where reviewerId= '"+reviewerId+"'");
			rs = pst.executeQuery();
			while(rs.next()){
				CommentView comment = getComment(rs);
				comments.add(comment);
			}
			pst.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return comments;
	}
	private static CommentView getComment(ResultSet rs) {
		CommentView comment = new CommentView();
		try {
			comment.setId(rs.getInt("id"));
			comment.setReviewerId(rs.getString("reviewerId"));
			comment.setBookId(rs.getInt("bookId"));
			comment.setPraiseNum(rs.getInt("praiseNum"));
			comment.setContent(rs.getString("content"));
			comment.setTime(rs.getString("time"));
			comment.setName(rs.getString("name"));
			comment.setHead(rs.getString("head"));
			comment.setBookName(rs.getString("bookName"));
			return comment;
		} catch (SQLException e) {
			System.out.print("从数据库中提取用户评论信息出错，请检查字段有无拼写错误");
			e.printStackTrace();
			return null;
		}
	}
}
