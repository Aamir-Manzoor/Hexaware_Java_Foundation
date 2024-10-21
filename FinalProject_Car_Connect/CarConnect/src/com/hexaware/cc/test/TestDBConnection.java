/*
 * Author: Venkatesh Pai & Amir Manzoor
 * Desc: Carconnect (DB Connection Test)
 * Date: 21/10/2024
 */

package com.hexaware.cc.test;

import com.hexaware.cc.exception.DatabaseConnectionException;
import com.hexaware.cc.util.DBConnUtil;
import java.sql.Connection;

public class TestDBConnection {

	public static void main(String[] args) throws DatabaseConnectionException {
		// TODO Auto-generated method stub
		Connection connection = DBConnUtil.getConnection();
		if(connection != null) {
			System.out.println("Database Connected Successfully");
		}else {
			System.out.println("Failed to connect to database");
		}
	}

}
