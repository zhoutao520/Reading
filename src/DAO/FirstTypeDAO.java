package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.FirstType;

public class FirstTypeDAO {

	/**
	 * 获取所有一级分类
	 * @param conn
	 * @return
	 */
	public static List<FirstType> getFirstTypes(Connection conn){
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<FirstType> firstTypes = new ArrayList<FirstType>();
		try {
			pst = conn.prepareStatement("select *from firsttype");
			rs = pst.executeQuery();
			while(rs.next()){
				FirstType firstType=new FirstType();
				firstType.setId(rs.getInt("id"));
				firstType.setName(rs.getString("name"));
				firstType.setPhoto(rs.getString("photo"));
				firstTypes.add(firstType);
			}
			rs.close();
			pst.close();
			return firstTypes;
		} catch (SQLException e) {
			System.out.print("从数据库中提取一级分类信息出错，请检查字段有无拼写错误");
			e.printStackTrace();
			return null;
		}
	}
}
