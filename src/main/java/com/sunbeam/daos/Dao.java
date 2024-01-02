package com.sunbeam.daos;

import java.sql.Connection;

import com.sunbeam.util.DbUtil;

public class Dao implements AutoCloseable {
	protected Connection con;
	public Dao() throws Exception {
		con = DbUtil.getConnection();
	}
	public void close() throws Exception {
		if(con != null)
			con.close();
	}
}
