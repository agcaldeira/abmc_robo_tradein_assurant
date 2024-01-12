package com.abmc.assurant.tradin;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionClass {
	
	public Connection conexaoBanco(ConfigFileClass config) {
		Connection conexao = null;
		try {
			String url = "jdbc:oracle:thin:@" + config.getDbHostname() + ":" + config.getDbPort() + ":" + config.getDbInstanceName();
			conexao = DriverManager.getConnection(url, config.getDbUser(), config.getDbPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conexao;
	}
}
