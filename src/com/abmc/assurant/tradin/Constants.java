package com.abmc.assurant.tradin;

public class Constants {
	
	public static final String SCRIPT_UPDATE_SAMSUNG = "  update cll_f189_invoices_interface " +
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
	
	public static final String SCRIPT_UPDATE_NAO_SAMSUNG = " update cll_f189_invoices_interface a " +
			"    set a.invoice_type_id = " +
			"        (select ffvt.description " +
			"           from apps.fnd_flex_value_sets@ranfe_ebs ffvs, " +
			"                apps.fnd_flex_values@ranfe_ebs ffv, " +
			"                apps.fnd_flex_values_tl@ranfe_ebs ffvt " +
			"          where ffvs.flex_value_set_name = 'XXBRZ0164_TT_PARAMETERS' " +
			"            and FFVS.flex_value_set_id = ffv.flex_value_set_id " +
			"            and ffv.flex_value_id = ffvt.flex_value_id " +
			"            and ffv.enabled_flag = 'Y' " +
			"            and ffv.flex_value = 'INVOICE_TYPE_ID2' " +
			"            and nvl(ffv.start_date_active, trunc(sysdate)) >= trunc(sysdate) " +
			"            and nvl(ffv.end_date_active, trunc(sysdate)) <= trunc(sysdate) " +
			"            and language = ('PTB')), " +
			"        a.invoice_type_code = " +
			"        (select ffvt.description " +
			"           from apps.fnd_flex_value_sets@ranfe_ebs ffvs, " +
			"                apps.fnd_flex_values@ranfe_ebs ffv, " +
			"                apps.fnd_flex_values_tl@ranfe_ebs ffvt " +
			"          where ffvs.flex_value_set_name = 'XXBRZ0164_TT_PARAMETERS' " +
			"            and FFVS.flex_value_set_id = ffv.flex_value_set_id " +
			"            and ffv.flex_value_id = ffvt.flex_value_id " +
			"            and ffv.enabled_flag = 'Y' " +
			"            and ffv.flex_value = 'INVOICE_TYPE_ID2' " +
			"            and nvl(ffv.start_date_active, trunc(sysdate)) >= trunc(sysdate) " +
			"            and nvl(ffv.end_date_active, trunc(sysdate)) <= trunc(sysdate) " +
			"            and language = ('PTB')) " +
			"  where a.interface_invoice_id in " +
			"        (select distinct cfil.interface_invoice_id " +
			"           from cll_f189_invoice_lines_iface cfil, " +
			"                cll_f189_invoices_interface cfi, " +
			"                cll_f189_invoice_types cfit, " +
			"                (select xpis.doc_num, " +
			"                        xpis.cpf_cnpj, " +
			"                        xpis.cnpj_loja, " +
			"                        xpis.cnpj, " +
			"                        xpis.org_id, " +
			"                        xpis.organization_id " +
			"                   from xxbrz_po_iface_stg1@ranfe_ebs xpis " +
			"                  where xpis.status_process = '00001' " +
			"                    and trunc(xpis.creation_date) >= trunc(sysdate)-3 " +
			"                    and xpis.cnpj_loja <> " +
			"                        (select ffvt.description " +
			"                           from apps.fnd_flex_value_sets@ranfe_ebs ffvs, " +
			"                                apps.fnd_flex_values@ranfe_ebs ffv, " +
			"                                apps.fnd_flex_values_tl@ranfe_ebs ffvt " +
			"                          where ffvs.flex_value_set_name = " +
			"                                'XXBRZ0164_TT_PARAMETERS' " +
			"                            and FFVS.flex_value_set_id = ffv.flex_value_set_id " +
			"                            and ffv.flex_value_id = ffvt.flex_value_id " +
			"                            and ffv.enabled_flag = 'Y' " +
			"                            and ffv.flex_value = 'SAMSUNG' " +
			"                            and nvl(ffv.start_date_active, trunc(sysdate)) >= trunc(sysdate) " +
			"                            and nvl(ffv.end_date_active, trunc(sysdate)) <= trunc(sysdate) " +
			"                            and language = ('PTB'))) x " +
			"          where cfi.organization_id = 164 " +
			"            and cfi.organization_code = 'WHS' " +
			"            and cfi.process_flag = '1' " +
			"            and trunc(cfil.creation_date) >= trunc(sysdate)-3 " +
			"            and cfil.interface_invoice_id = cfi.interface_invoice_id " +
			"            and cfi.invoice_type_code = cfit.invoice_type_code " +
			"            and cfi.organization_id = cfit.organization_id " +
			"            and cfi.invoice_type_code = 'WHS_6000' " +
			"            and substr(cfil.purchase_order_num, 1, length(cfil.purchase_order_num) - 1) = x.doc_num " +
			"            and cfi.document_number = x.cpf_cnpj " +
			"            and cfi.organization_id = x.organization_id) " ;

	public static final String SCRIPT_UPDATE_CPF_ZEROS_A_ESQUERDA = "  UPDATE cll_f189_invoices_interface " +
			" SET DOCUMENT_NUMBER =  LPAD(DOCUMENT_NUMBER,11,'0') " +
			" WHERE CREATION_DATE >= TRUNC(SYSDATE)- 3 " +
			" AND DOCUMENT_NUMBER IS NOT NULL AND LENGTH(DOCUMENT_NUMBER) <= 10 ";
}
