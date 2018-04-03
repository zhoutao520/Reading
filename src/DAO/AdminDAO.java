package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Admin;

public class AdminDAO {

	/**
	 * 添加管理员
	 * @param admin 管理员
	 */
	public static void addAdmin(Connection conn, Admin admin){		
		PreparedStatement pst = null;		
		try {
			pst = conn.prepareStatement("insert into admin(id,name,password,phone) values(?,?,?,?)");
			pst.setInt(1, admin.getId());
			pst.setString(2, admin.getName());
			pst.setString(3, admin.getPassword());
			pst.setString(4, admin.getPhone());
			pst.executeUpdate();	
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 更新管理员
	 * @param admin 管理员
	 */
	public static void updateAdmin(Connection conn, Admin admin){		
		PreparedStatement pst = null;		
		try {
			pst = conn.prepareStatement("update admin set name=?,password=?,phone=? where id = ?");
			pst.setString(1, admin.getName());
			pst.setString(2, admin.getPassword());
			pst.setString(3, admin.getPhone());
			pst.setInt(4, admin.getId());
			pst.executeUpdate();	
			pst.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改管理员密码
	 * @param conn
	 * @param password
	 * @param id
	 */
	public static void updatePassword(Connection conn,String password,int id){
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("update admin set password="+password+" where id="+id);
			pst.executeUpdate();	
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 验证账号
	 * @param conn
	 * @param phone
	 * @return
	 */
	public static boolean login(Connection conn, int id ,String pwd) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		boolean flag=false;
		try {
			pst = conn.prepareStatement("select *from admin where id = "+id+" and password= '"+pwd+"'");
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
	 * 删除管理员
	 * @param conn 数据库连接
	 * @param id 管理员编号
	 */
	public static void delAdmin(Connection conn, int id) {
		try {
			PreparedStatement pst = conn.prepareStatement("delete from admin where id = "+id);
			pst.executeUpdate();	
			pst.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取指定管理员
	 * @param adminId 管理员号
	 * @return 返回管理员信息
	 */
	public static Admin getAdmin(Connection conn, int adminId){		
		PreparedStatement pst = null;
		ResultSet rs = null;		
		Admin admin = null;
		try {
			pst = conn.prepareStatement("select * from admin where id = "+adminId);
			rs = pst.executeQuery();
			if (rs.next()) {
				admin = getAdmin(rs);
			}
			rs.close();	
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return admin;
	}
	/**
	 * 获取管理员
	 * @return 管理员列表
	 */
	public static List<Admin> getAdimins(Connection conn){
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		List<Admin> admins = new ArrayList<Admin>();		
		try {
			pst = conn.prepareStatement("select * from admin");
			rs = pst.executeQuery();
			while (rs.next()) {
				Admin admin = getAdmin(rs);
				admins.add(admin);
			}
			rs.close();	
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return admins;
	}
	private static Admin getAdmin(ResultSet rs){
		try {
			Admin admin = new Admin();
			admin.setId(rs.getInt("id"));
			admin.setName(rs.getString("name"));
			admin.setPassword(rs.getString("password"));
			admin.setPhone(rs.getString("phone"));
			admin.setRole(rs.getByte("role"));
			return admin;
		} catch (SQLException e) {
			System.out.print("从数据库中提取管理员信息出错，请检查字段有无拼写错误");
			return null;
		}
	}
}
