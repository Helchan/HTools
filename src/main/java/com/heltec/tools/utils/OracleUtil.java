package com.heltec.tools.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OracleUtil {
	private static String url = "jdbc:oracle:thin:@localhost:1521:hdb";
	private static String username = "hdbuser";
	private static String password = "hdbuser";
	private static Connection connection = null;
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			connection = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			System.out.println("获取连接出错：" + e);
		}
	}

	/**
	 * 获取连接
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				connection = DriverManager.getConnection(url, username,
						password);
			}
		} catch (Exception e) {
			System.out.println("获取连接出错：" + e);
		}
		return connection;
	}

	/**
	 * 关闭连接
	 */
	public static void close() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			System.out.println("关闭连接出错：" + e);
		}
	}

	/**
	 * 执行查询语句
	 * 
	 * @param conn
	 * @param sql
	 * @return
	 */
	public static ResultSet query(String sql) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			System.out.println("执行SQL出错：" + sql + ", " + e);
		}
		return rs;
	}

	/**
	 * 执行update语句
	 * 
	 * @param conn
	 * @param sql
	 * @return
	 */
	public static boolean update(String sql) {
		PreparedStatement ps = null;
		int rs = 0;
		try {
			ps = connection.prepareStatement(sql);
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("执行SQL出错：" + sql + ", " + e);
		}
		if (rs > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 执行插入语句
	 * 
	 * @param sql
	 */
	public static void insert(String sql) {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.execute();
		} catch (SQLException e) {
			System.out.println("执行SQL出错：" + sql + ", " + e);
		}
	}

	/**
	 * 执行sql（可用于执行DDL脚本）
	 * 
	 * @param sql
	 * @return
	 */
	public static boolean executeSql(String sql) {
		try {
			System.out.println("================开始执行SQL================\n" + sql);
			PreparedStatement prepareStatement = connection.prepareStatement(sql);
			boolean executeFlag = prepareStatement.execute();
			System.out.println("执行SQL成功!");
			return executeFlag;
		} catch (SQLException e) {
			System.out.println("执行SQL异常:" + e);
		}
		return false;
	}

	/**
	 * 测试代码
	 * 
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		ResultSet rs = query("select 1 from dual");
		rs.next();
		System.out.println(rs.getString(1));
	}
}