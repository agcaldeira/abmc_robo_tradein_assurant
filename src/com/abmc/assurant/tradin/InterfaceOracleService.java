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

		try {
			Connection conexao = con.conexaoBanco(config);
			Statement stmt = conexao.createStatement();

			String sqlUpdate = "  update cll_f189_invoices_interface " +
					"     set invoice_type_code = "+
					"         (select ffvt.description " +
					"            from apps.fnd_flex_value_sets@ranfe_ebs ffvs, " +
					"                 apps.fnd_flex_values@ranfe_ebs     ffv, " +
					"                 apps.fnd_flex_values_tl@ranfe_ebs  ffvt " +
					"           where ffvs.flex_value_set_name = 'XXBRZ0164_TT_PARAMETERS' " +
					"             and FFVS.flex_value_set_id = ffv.flex_value_set_id " +
					"             and ffv.flex_value_id = ffvt.flex_value_id " +
					"             and ffv.enabled_flag = 'Y' " +
					"             and ffv.flex_value = 'INVOICE_TYPE' " +
					"             and nvl(ffv.start_date_active, trunc(sysdate)) >= trunc(sysdate) " +
					"             and nvl(ffv.end_date_active, trunc(sysdate)) <= trunc(sysdate) " +
					"             and language = ('PTB')), " +
					"         invoice_type_id = " +
					"         (select distinct cll.invoice_type_id " +
					"            from apps.fnd_flex_value_sets@ranfe_ebs   ffvs, " +
					"                 apps.fnd_flex_values@ranfe_ebs       ffv, " +
					"                 apps.fnd_flex_values_tl@ranfe_ebs    ffvt, " +
					"                 cll.cll_f189_invoice_types@ranfe_ebs cll " +
					"           where ffvs.flex_value_set_name = 'XXBRZ0164_TT_PARAMETERS' " +
					"             and FFVS.flex_value_set_id = ffv.flex_value_set_id " +
					"             and ffv.flex_value_id = ffvt.flex_value_id " +
					"             and cll.invoice_type_code = ffvt.description " +
					"             and ffv.enabled_flag = 'Y' " +
					"             and ffv.flex_value = 'INVOICE_TYPE' " +
					"             and nvl(ffv.start_date_active, trunc(sysdate)) >= trunc(sysdate) " +
					"             and nvl(ffv.end_date_active, trunc(sysdate)) <= trunc(sysdate) " +
					"             and language = ('PTB')) " +
					"  where interface_invoice_id in " +
					"         (select distinct cfi.interface_invoice_id " +
					"            from cll.cll_f189_invoices_interface@ranfe_ebs cfi, " +
					"                 cll.cll_f189_invoice_lines_iface@ranfe_ebs cfil, " +
					"                 xxbrz_po_iface_stg1@ranfe_ebs xpis, " +
					"                 (select language, " +
					"                         ffvs.flex_value_set_name, " +
					"                         ffv.flex_value, " +
					"                         ffvt.description " +
					"                    from apps.fnd_flex_value_sets@ranfe_ebs ffvs, " +
					"                         apps.fnd_flex_values@ranfe_ebs     ffv, " +
					"                        apps.fnd_flex_values_tl@ranfe_ebs  ffvt " +
					"                   where ffvs.flex_value_set_name = 'XXBRZ0164_TT_PARAMETERS' " +
					"                     and FFVS.flex_value_set_id = ffv.flex_value_set_id " +
					"                     and ffv.flex_value_id = ffvt.flex_value_id " +
					"                     and ffv.enabled_flag = 'Y' " +
					"                     and ffv.flex_value = 'SAMSUNG' " +
					"                     and nvl(ffv.start_date_active, trunc(sysdate)) >= trunc(sysdate) " +
					"                     and nvl(ffv.end_date_active, trunc(sysdate)) <= trunc(sysdate) " +
					"                     and language = ('PTB')) fnd " +
					"           where cfi.interface_invoice_id = cfil.interface_invoice_id " +
					"             and cfi.process_flag = '1' " +
					"             and cfi.organization_id = xpis.organization_id " +
					"             and cfil.purchase_order_num = xpis.doc_num " +
					"             and cfi.document_number = xpis.cpf_cnpj " +
					"             and xpis.tipo_reg01 = '01' " +
					"             and xpis.cnpj_loja = fnd.description " +
					"             and cfi.invoice_type_code = 'WHS_6000' " +
					"             and trunc(cfi.creation_date) >= trunc(sysdate) - 3) ";
			int linhasAfetadas = stmt.executeUpdate(sqlUpdate);

			if (linhasAfetadas > 0) {
				System.out.println("UPDATE bem-sucedido. Linhas afetadas: " + linhasAfetadas);
			} else {
				System.out.println("Nenhuma linha foi atualizada.");
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
