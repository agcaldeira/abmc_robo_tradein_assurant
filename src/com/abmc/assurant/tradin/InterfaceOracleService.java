package com.abmc.assurant.tradin;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InterfaceOracleService {

	private ConfigFileClass config;

	public void processar() {

		this.config = lerArquivoConfiguracao();
		ConnectionClass con = new ConnectionClass();

		// Update TIPO NF e CODE NF TradeIn Samsung
		try {
			Connection conexao = con.conexaoBanco(config);
			Statement stmt = conexao.createStatement();
			String sqlUpdate = Constants.SCRIPT_UPDATE_SAMSUNG;
			int linhasAfetadas = stmt.executeUpdate(sqlUpdate);
			if (linhasAfetadas > 0) {
				System.out.println("UPDATE TIPO DA NOTA SAMSUNG bem-sucedido. Linhas afetadas: " + linhasAfetadas);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Update TIPO NF e CODE NF TradeIn Não Samsung
		try {
			Connection conexao = con.conexaoBanco(config);
			Statement stmt = conexao.createStatement();
			String sqlUpdate = Constants.SCRIPT_UPDATE_NAO_SAMSUNG;
			int linhasAfetadas = stmt.executeUpdate(sqlUpdate);
			if (linhasAfetadas > 0) {
				System.out.println("UPDATE TIPO DA NOTA NAO SAMSUNG bem-sucedido. Linhas afetadas: " + linhasAfetadas);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Update no CPF sem zeros a esquerda
		try {
			Connection conexao = con.conexaoBanco(config);
			Statement stmt = conexao.createStatement();
			String sqlUpdate = Constants.SCRIPT_UPDATE_CPF_ZEROS_A_ESQUERDA;
			int linhasAfetadas = stmt.executeUpdate(sqlUpdate);
			if (linhasAfetadas > 0) {
				System.out.println("UPDATE CPF bem-sucedido. Linhas afetadas: " + linhasAfetadas);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ConfigFileClass lerArquivoConfiguracao() {

		ConfigFileClass cf = new ConfigFileClass();
		String diretorio;
		String linha = null;
		try {
			diretorio = new File(".").getCanonicalPath() + "\\conf";
			Scanner in = new Scanner(new FileReader(diretorio + "\\config.properties"));
			while (in.hasNextLine()) {
				linha = in.nextLine();
				if (linha.startsWith("directory.name.entrada")) {
					cf.setDiretorioLeitura(linha.replace("directory.name.entrada=", ""));
				}
				if (linha.startsWith("directory.name.processados")) {
					cf.setDiretorioProcessadas(linha.replace("directory.name.processados=", ""));
				}
				if (linha.startsWith("directory.name.erro")) {
					cf.setDiretorioErro(linha.replace("directory.name.erro=", ""));
				}
				if (linha.startsWith("directory.name.gravacaoExtra")) {
					cf.setDiretorioEscritaExtra(linha.replace("directory.name.gravacaoExtra=", ""));
				}
				if (linha.startsWith("directory.name.gravacaoYesfurbe")) {
					cf.setDiretorioEscritaYesfurbe(linha.replace("directory.name.gravacaoYesfurbe=", ""));
				}
				if (linha.startsWith("directory.name.gravacaoRANFE")) {
					cf.setDiretorioEscritaRanfe(linha.replace("directory.name.gravacaoRANFE=", ""));
				}
				if (linha.startsWith("server.db.hostname")) {
					cf.setDbHostname(linha.replace("server.db.hostname=", ""));
				}
				if (linha.startsWith("server.db.port")) {
					cf.setDbPort(linha.replace("server.db.port=", ""));
				}
				if (linha.startsWith("server.db.instance")) {
					cf.setDbInstanceName(linha.replace("server.db.instance=", ""));
				}
				if (linha.startsWith("server.db.ra.user")) {
					cf.setDbUser(linha.replace("server.db.ra.user=", ""));
				}
				if (linha.startsWith("server.db.ra.password")) {
					cf.setDbPassword(linha.replace("server.db.ra.password=", ""));
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return cf;
	}

}
