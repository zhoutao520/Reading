package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Author;

public class AuthorDAO {

	/**
	 * 注册作者
	 * @param conn
	 * @param author
	 */
	public static void addAuthor(Connection conn,Author author) {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("insert into author(phone,password,head) values(?,?,?)");
			pst.setString(1, author.getPhone());
			pst.setString(2, author.getPassword());
			pst.setString(3, "../img/tx.png");
			pst.executeUpdate();	
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 更新作者信息
	 * @param conn
	 * @param author
	 */
	public static void updateAuthor(Connection conn,Author author){
		PreparedStatement pst = null;	
		try {
			pst = conn.prepareStatement("update author set name=?,sex=?,head=?,realName=?,IDcard=?,profile=?,email=?,QQ=? where phone = ?");
			pst.setString(1, author.getName());
			pst.setString(2, author.getSex());
			pst.setString(3, author.getHead());
			pst.setString(4, author.getRealName());
			pst.setString(5, author.getIDcard());
			pst.setString(6, author.getProfile());
			pst.setString(7, author.getEmail());
			pst.setString(8, author.getQQ());
			pst.setString(9, author.getPhone());
			pst.executeUpdate();	
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改作者密码
	 * @param conn
	 * @param password
	 * @param phone
	 */
	public static void updatePassword(Connection conn,String password,String phone){
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("update author set password="+password+" where phone='"+phone+"'");
			pst.executeUpdate();	
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 注销作者
	 * @param conn 数据库连接
	 * @param id 作者编号
	 */
	public static void delAuthor(Connection conn, String phone) {
		try {
			PreparedStatement pst = conn.prepareStatement("delete from author where phone = '"+phone+"'");
			pst.executeUpdate();	
			pst.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 验证手机号
	 * @param conn
	 * @param phone
	 * @return
	 */
	public static boolean login(Connection conn, String phone) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		boolean flag=false;
		try {
			pst = conn.prepareStatement("select *from author where phone= '"+phone+"'");
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
	 * 获取作者信息
	 * @param conn
	 * @param authorId
	 * @return
	 */
	public static Author getAuthor(Connection conn,String phone){
		PreparedStatement pst = null;
		ResultSet rs = null;
		Author author = null;
		try {
			pst = conn.prepareStatement("select *from author where phone = '"+phone+"'");
			rs = pst.executeQuery();
			if (rs.next()) {
				author = getAuthor(rs);
			}
			rs.close();	
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return author;
	}
	private static Author getAuthor(ResultSet rs) {
		Author author = new Author();
		try {
			author.setName(rs.getString("name"));
			author.setPassword(rs.getString("password"));
			author.setPhone(rs.getString("phone"));
			author.setSex(rs.getString("sex"));
			author.setHead(rs.getString("head"));
			author.setRealName(rs.getString("realName"));
			author.setIDcard(rs.getString("IDcard"));
			author.setProfile(rs.getString("profile"));
			author.setEmail(rs.getString("email"));
			author.setQQ(rs.getString("QQ"));
			return author;
		} catch (SQLException e) {
			System.out.print("从数据库中提取作者信息出错，请检查字段有无拼写错误");
			e.printStackTrace();
			return null;
		}
	}
}
