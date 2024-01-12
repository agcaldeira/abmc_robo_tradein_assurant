package com.abmc.assurant.tradin;

import java.io.IOException;
import java.util.ArrayList;

public class TradinClass {

	public static void main(String[] args) throws IOException {
		
		InterfaceOracleService oracleService = new InterfaceOracleService();
		oracleService.processar();
		
		ServiceClass sc = new ServiceClass();
		ArrayList<String> arquivos = sc.lerArquivos();
		if (arquivos.size() > 0) {
			sc.processar(arquivos);
		}
		
	}
}
