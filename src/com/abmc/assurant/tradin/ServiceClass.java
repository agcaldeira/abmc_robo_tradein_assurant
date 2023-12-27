package com.abmc.assurant.tradin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class ServiceClass {
	
	private ConfigFileClass config;

	public void processar(ArrayList<String> arquivos) {
		config = lerArquivoConfiguracao();
		ConnectionClass con = new ConnectionClass();
		Connection conexao = con.conexaoBanco(config);
		String sql;
		String chave;
		String nnf;
		String natop;
		String serie;
		String xped = "";
		String arquivoOrigem;
		String arquivoDestinoExtra;
		String arquivoDestinoYesfurbe;
		String arquivoDestinoRanfe;
		String arquivoProcessado;
		String arquivoErro;
		String nomeArquivo;
		try {
			Statement stmt = conexao.createStatement();
			
			for (String arquivo : arquivos) {
				chave = arquivo.replace("NFE", "").replace(".pdf", "").replace(".xml", "").replace("-nfe", "");
				
				sql = "select replace(replace(extract(xmltype(doc), '/NFe/infNFe/ide/nNF', 'xmlns=\"http://www.portalfiscal.inf.br/nfe\"').getStringVal(),'<nNF xmlns=\"http://www.portalfiscal.inf.br/nfe\">',''),'</nNF>','') nnf,";
				sql = sql + " replace(replace(extract(xmltype(doc), '/NFe/infNFe/ide/natOp', 'xmlns=\"http://www.portalfiscal.inf.br/nfe\"').getStringVal(),'<natOp xmlns=\"http://www.portalfiscal.inf.br/nfe\">',''),'</natOp>','') natop,";
				sql = sql + " replace(replace(extract(xmltype(doc), '/NFe/infNFe/ide/serie', 'xmlns=\"http://www.portalfiscal.inf.br/nfe\"').getStringVal(),'<serie xmlns=\"http://www.portalfiscal.inf.br/nfe\">',''),'</serie>','') serie,";
				sql = sql + " replace(replace(extract(xmltype(doc), '/NFe/infNFe/compra/xPed', 'xmlns=\"http://www.portalfiscal.inf.br/nfe\"').getStringVal(),'<xPed xmlns=\"http://www.portalfiscal.inf.br/nfe\">',''),'</xPed>','') xped";
				sql = sql + " from DFE_LOB";
				sql = sql + " WHERE ID = (select max(dfe_lob_id) from dfe_historico_nfe t WHERE STATUS_DESCRICAO LIKE 'Remontando e Assinando %'";
				sql = sql + " and NFE_ID = (SELECT id FROM DFE_NFE WHERE chave_acesso = '" + chave + "' AND CNPJ_EMISSOR = '22725405000200'))";
				ResultSet res = stmt.executeQuery(sql);
				arquivoOrigem = config.getDiretorioLeitura() +"\\\\"+ arquivo;
				arquivoErro = config.getDiretorioErro() +"\\\\"+ arquivo;
				arquivoProcessado = config.getDiretorioProcessadas() +"\\\\"+ arquivo;
				
				if (!res.isBeforeFirst()) {
					copiarArquivo(arquivoOrigem, arquivoErro, true);
				}
				
				while (res.next()) {
					
					nnf = res.getString("nnf");
					natop = res.getString("natop");
					serie = res.getString("serie");
					xped = res.getString("xped");
					nomeArquivo = xped + "_" + nnf;
					String extencao = arquivo.substring(arquivo.length()-4);
					arquivoDestinoExtra = config.getDiretorioEscritaExtra() +"\\\\"+ nomeArquivo + extencao;
					arquivoDestinoYesfurbe = config.getDiretorioEscritaYesfurbe() +"\\\\"+ nomeArquivo + extencao;
					arquivoDestinoRanfe = config.getDiretorioEscritaRanfe() +"\\\\"+ nomeArquivo + extencao;
					
					// Processo Extra
					/*
					if (xped == "" || xped == null || (!xped.contains("EXT") && !xped.contains("YB"))) {
						copiarArquivo(arquivoOrigem, arquivoErro, true);
					}
					*/
					if (xped == "" || xped == null || (!xped.startsWith("EXT") && !xped.startsWith("YB"))) {
						copiarArquivo(arquivoOrigem, arquivoErro, true);
					}
					
					if (xped != null) {
						// Processo Extra
						if (xped.startsWith("EXT")) {
							if (arquivoOrigem.contains(".xml")) {
								copiarArquivo(arquivoOrigem, arquivoDestinoYesfurbe, false);
								copiarArquivo(arquivoOrigem, arquivoDestinoRanfe, false);
								copiarArquivo(arquivoOrigem, arquivoProcessado, true);
								continue;
							}
							if (serie.equals("4") && natop.equals("Compra de End-User - Trade_IN")) {
								copiarArquivo(arquivoOrigem, arquivoDestinoExtra, false);
								copiarArquivo(arquivoOrigem, arquivoDestinoYesfurbe, false);
								copiarArquivo(arquivoOrigem, arquivoProcessado, true);
							} else {
								copiarArquivo(arquivoOrigem, arquivoErro, true);
							}
						}
						
						// Processo Yesfurbe
						if (xped.startsWith("YB")) {
							if (serie.equals("4") && natop.equals("Compra de End-User - Trade_IN")) {
								copiarArquivo(arquivoOrigem, arquivoDestinoYesfurbe, false);
								if (arquivoOrigem.contains(".xml")) {
									copiarArquivo(arquivoOrigem, arquivoDestinoRanfe, false);
								}
								copiarArquivo(arquivoOrigem, arquivoProcessado, true);
							} else {
								copiarArquivo(arquivoOrigem, arquivoErro, true);
							}
						}
						// Processo Samsung
						if (xped.startsWith("SAN")) {
							if (serie.equals("4") && natop.equals("Compra de End-User - Trade_IN")) {
								//copiarArquivo(arquivoOrigem, arquivoDestinoYesfurbe, false);
								if (arquivoOrigem.contains(".xml")) {
									copiarArquivo(arquivoOrigem, arquivoDestinoRanfe, false);
								}
								copiarArquivo(arquivoOrigem, arquivoProcessado, true);
							} else {
								copiarArquivo(arquivoOrigem, arquivoErro, true);
							}
						}
					}
				}
			}
			conexao.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void copiarArquivo(String origem, String destino, boolean apagaOrigem) {

        try {
        	
        	File file = new File(destino);
            file.getParentFile().mkdirs();

            FileInputStream in = new FileInputStream(origem);
            FileOutputStream out = new FileOutputStream(destino);
            
            byte[] buf = new byte[1024];
            int len;
            
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            
            out.close();
            in.close();
            if (apagaOrigem) {
	            Path path = Paths.get(origem);
	            InputStream fileStream = Files.newInputStream(path, StandardOpenOption.DELETE_ON_CLOSE);
	            fileStream.close();
            }
            
        } catch (IOException e) {
            System.out.println(e.toString());;
        }
    }

	public ArrayList<String> lerArquivos() {

		File f = new File(lerArquivoConfiguracao().getDiretorioLeitura());
		String[] nomes = f.list();
		ArrayList<String> arquivos = new ArrayList<String>();
		for (String s : nomes) {
			if (s.contains(".pdf") || s.contains(".xml")) {
				arquivos.add(s);
			}
		}
		return arquivos;
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

				if (linha.startsWith("server.db.user")) {
					cf.setDbUser(linha.replace("server.db.user=", ""));
				}

				if (linha.startsWith("server.db.user")) {
					cf.setDbUser(linha.replace("server.db.user=", ""));
				}

				if (linha.startsWith("server.db.password")) {
					cf.setDbPassword(linha.replace("server.db.password=", ""));
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return cf;
	}

}
