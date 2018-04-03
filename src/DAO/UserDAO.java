package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.User;

public class UserDAO {
	/**
	 * 注册用户
	 * @param conn
	 * @param user
	 */
	public static void addUser(Connection conn,User user) {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("insert into user(phone,password,head) values(?,?,?)");
			pst.setString(1, user.getPhone());
			pst.setString(2, user.getPassword());
			pst.setString(3, "../img/tx.png");
			pst.executeUpdate();	
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 更新用户信息
	 * @param conn
	 * @param user
	 */
	public static void updateUser(Connection conn,User user){
		PreparedStatement pst = null;	
		try {
			pst = conn.prepareStatement("update user set name=?,sex=?,head=?,realName=?,IDcard=?,profile=? where phone=?");
			pst.setString(1, user.getName());
			pst.setString(2, user.getSex());
			pst.setString(3, user.getHead());
			pst.setString(4, user.getRealName());
			pst.setString(5, user.getIDcard());
			pst.setString(6, user.getProfile());
			pst.setString(7, user.getPhone());
			pst.executeUpdate();	
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改用户密码
	 * @param conn
	 * @param password
	 * @param userId
	 */
	public static void updatePassword(Connection conn,String password,String phone){
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("update user set password=? where phone=?");
			pst.setString(1, password);
			pst.setString(2, phone);
			pst.executeUpdate();	
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 注销用户
	 * @param conn 数据库连接
	 * @param phone 用户手机号
	 */
	public static void delUser(Connection conn, String phone) {
		try {
			PreparedStatement pst = conn.prepareStatement("delete from user where phone = '"+phone+"'");
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
			pst = conn.prepareStatement("select *from user where phone= '"+phone+"'");
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
	 * 获取用户信息
	 * @param conn
	 * @param userId
	 * @return
	 */
	public static User getUser(Connection conn,String phone){
		PreparedStatement pst = null;
		ResultSet rs = null;
		User user = null;
		try {
			pst = conn.prepareStatement("select *from user where phone = ?");
			pst.setString(1, phone);
			rs = pst.executeQuery();
			if (rs.next()) {
				user = getUser(rs);
			}
			rs.close();	
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	private static User getUser(ResultSet rs) {
		User user = new User();
		try {
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setPhone(rs.getString("phone"));
			user.setSex(rs.getString("sex"));
			user.setHead(rs.getString("head"));
			user.setRealName(rs.getString("realName"));
			user.setIDcard(rs.getString("IDcard"));
			user.setProfile(rs.getString("profile"));
			user.setRole(rs.getByte("role"));
			return user;
		} catch (SQLException e) {
			System.out.print("从数据库中提取用户信息出错，请检查字段有无拼写错误");
			e.printStackTrace();
			return null;
		}
	}
}
