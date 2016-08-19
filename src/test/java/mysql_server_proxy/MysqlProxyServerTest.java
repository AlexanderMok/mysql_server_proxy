package mysql_server_proxy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Before;
import org.junit.Test;

import io.vertx.core.Vertx;

public class MysqlProxyServerTest {
	
	@Before
	public void InitialContext(){
		Vertx.vertx().deployVerticle(new MysqlProxyServerVerticle());
	}
	
	@Test
	public void testMysqlProxyServer() {
		
		String name = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://192.489.15:3306/XXX?characterEncoding=utf8";
		String user = "root";
		String password = "1234";
		String sql = "select * from K_admin;";
		try {
			Class.forName(name);// connnection type
			Connection conn = DriverManager.getConnection(url, user, password);// proxy server url
			PreparedStatement pst = conn.prepareStatement(sql);// SQL
			ResultSet resultSet = pst.executeQuery();
			while (resultSet.next()) {
				System.out.println(resultSet.getLong(1) + " : " + resultSet.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
