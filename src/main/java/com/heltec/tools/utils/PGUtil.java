package com.heltec.tools.utils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.io.FileUtils;

public class PGUtil {
	private static String url = "jdbc:postgresql://30.23.40.1:7515/aiclaim";
	private static String username = "aiclaimdata";
	private static String password = "jz#P6p6I";
	private static Connection connection = null;
	static {
		try {
			Class.forName("org.postgresql.Driver").newInstance();
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
				Class.forName("org.postgresql.Driver").newInstance();
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
			System.out.println("================开始执行SQL================\n"
					+ sql);
			PreparedStatement prepareStatement = connection
					.prepareStatement(sql);
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
	 * @throws IOException
	 */
	public static void main(String[] args) throws SQLException, IOException {
		/*
		 * //查询 ResultSet rs = query(
		 * "explain select * from la_fee_settlement_summary_info where SETTLEMENT_NO='1' and SETTLEMENT_STATUS!='03'"
		 * );
		 * 
		 * while(rs.next()){ System.out.println(rs.getString(1)); }
		 * 
		 * //插入
		 * insert("insert into t1 VALUES(12, CURRENT_TIMESTAMP, 'test10', 12, 12.1)"
		 * );
		 * 
		 * //修改 update("update t1 set n = 12.2 where id = 12");
		 */

		// 执行脚本
		File file = new File("D:\\TANGHAIQIANG\\zTestFiles\\test.sql");
		String sql = FileUtils.readFileToString(file, "UTF-8");
		executeSql(sql);
	}
}
