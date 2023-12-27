package com.abmc.assurant.tradin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

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

	public String ConsultaNF(String chave) {

		String server = "10.100.5.167";
		String port = "1522";
		String database = "v4t4mdfe";
		String user = "msaf_dfe";
		String passwd = "msafdfe123";
		String sql;
		String nomeArquivo = null;

		try {
			String url = "jdbc:oracle:thin:@" + server + ":" + port + ":" + database;

			Connection con = DriverManager.getConnection(url, user, passwd);
			Statement stmt = con.createStatement();

			sql = "select razao_social_dest from dfe_nfe where chave_acesso = " +chave;
			ResultSet res = stmt.executeQuery(sql);
			while (res.next()) {
				nomeArquivo = res.getString("razao_social_dest");
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nomeArquivo;
	}

}
