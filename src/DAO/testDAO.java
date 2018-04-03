package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class testDAO {
	public static void add(Connection conn, Date time, float Tio, float Me){		
		PreparedStatement pst = null;		
		try {
			pst = conn.prepareStatement("insert into test(tio,me,time) values(?,?,?)");
			pst.setFloat(1, Tio);
			pst.setFloat(2, Me);
			pst.setDate(3, new java.sql.Date(time.getTime()));
			pst.executeUpdate();	
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
