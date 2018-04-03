package util;

import java.sql.Connection;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class DButil {
	private static String driver = "com.mysql.jdbc.Driver";
	
	private final static String URL = "jdbc:mysql://localhost:3306/bookreading?useUnicode=true&characterEncoding=utf-8";
	
	private final static String USER = "root";
	
	private final static String PASSWORD = "456";
	
	private static DataSource datasource = null;
	
	private static void init(){
		PoolProperties p = new PoolProperties();
	    p.setUrl(URL);
	    p.setDriverClassName(driver );
	    p.setUsername(USER);
	    p.setPassword(PASSWORD);
	    p.setJmxEnabled(true);
	    p.setTestWhileIdle(false);
	    p.setTestOnBorrow(true);
	    p.setValidationQuery("SELECT 1");
	    p.setTestOnReturn(false);
	    p.setValidationInterval(30000);
	    p.setTimeBetweenEvictionRunsMillis(30000);
	    p.setMaxActive(500);
	    p.setInitialSize(50);
	    p.setMaxWait(10000);
	    p.setRemoveAbandonedTimeout(30);
	    p.setMinEvictableIdleTimeMillis(30000);
	    p.setMaxIdle(500);
	   // p.setMinIdle(30);
	    p.setRemoveAbandoned(true);
	    p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
	      "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
	    
	    datasource = new DataSource();
	    datasource.setPoolProperties(p);
	    System.out.println("初始化数据源");
	}
	
	public static Connection getConnection(){
		
		if (datasource == null) {
			init();
		}
		
		Connection conn = null;
		try {			
			conn = datasource.getConnection();
		} catch (Exception e) {
			System.out.print("数据库连接错误");
		}
		return conn;
	}
}