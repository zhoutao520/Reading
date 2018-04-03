package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.SecondType;

public class SecondTypeDAO {
	
	/**
	 * 根据一级分类显示所属二级分类
	 * @param conn
	 * @param id
	 * @return
	 */
	public static List<SecondType> getSecondTypes(Connection conn,int id){
		PreparedStatement pst=null;
		ResultSet rs=null;
		List<SecondType> secondTypes=new ArrayList<SecondType>();
		try {
			pst=conn.prepareStatement("select *from secondtype where firstTypeId="+id);
			rs=pst.executeQuery();
			while(rs.next()){
				SecondType secondType=new SecondType();
				secondType.setId(rs.getInt("id"));
				secondType.setFirstTypeId(rs.getInt("firstTypeId"));
				secondType.setName(rs.getString("name"));
				secondType.setPhoto(rs.getString("photo"));
				secondTypes.add(secondType);
			}
			rs.close();
			pst.close();
			return secondTypes;
		} catch (SQLException e) {
			System.out.print("从数据库中提取二级分类信息出错，请检查字段有无拼写错误");
			e.printStackTrace();
			return null;
		}
	}
}
