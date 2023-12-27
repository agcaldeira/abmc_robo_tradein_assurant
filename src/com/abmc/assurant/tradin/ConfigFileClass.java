package com.abmc.assurant.tradin;

public class ConfigFileClass {
	
	private String diretorioLeitura;
	private String diretorioEscritaExtra;
	private String diretorioEscritaYesfurbe;
	private String diretorioEscritaRanfe;
	private String dbHostname;
	private String dbPort;
	private String dbInstanceName;
	private String dbUser;
	private String dbPassword;
	private String diretorioProcessadas;
	private String diretorioErro;
	
	public String getDiretorioLeitura() {
		return diretorioLeitura;
	}
	public void setDiretorioLeitura(String diretorioLeitura) {
		this.diretorioLeitura = diretorioLeitura;
	}
	public String getDiretorioEscritaExtra() {
		return diretorioEscritaExtra;
	}
	public void setDiretorioEscritaExtra(String diretorioEscritaExtra) {
		this.diretorioEscritaExtra = diretorioEscritaExtra;
	}
	public String getDbHostname() {
		return dbHostname;
	}
	public void setDbHostname(String dbHostname) {
		this.dbHostname = dbHostname;
	}
	public String getDbPort() {
		return dbPort;
	}
	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}
	public String getDbInstanceName() {
		return dbInstanceName;
	}
	public void setDbInstanceName(String dbInstanceName) {
		this.dbInstanceName = dbInstanceName;
	}
	public String getDbUser() {
		return dbUser;
	}
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}
	public String getDbPassword() {
		return dbPassword;
	}
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	
	public String getDiretorioProcessadas() {
		return diretorioProcessadas;
	}
	public void setDiretorioProcessadas(String diretorioProcessadas) {
		this.diretorioProcessadas = diretorioProcessadas;
	}
	public String getDiretorioErro() {
		return diretorioErro;
	}
	public void setDiretorioErro(String diretorioErro) {
		this.diretorioErro = diretorioErro;
	}
	
	public String getDiretorioEscritaYesfurbe() {
		return diretorioEscritaYesfurbe;
	}
	public void setDiretorioEscritaYesfurbe(String diretorioEscritaYesfurbe) {
		this.diretorioEscritaYesfurbe = diretorioEscritaYesfurbe;
	}
	public String getDiretorioEscritaRanfe() {
		return diretorioEscritaRanfe;
	}
	public void setDiretorioEscritaRanfe(String diretorioEscritaRanfe) {
		this.diretorioEscritaRanfe = diretorioEscritaRanfe;
	}
	@Override
	public String toString() {
		return "ConfigFileClass [diretorioLeitura=" + diretorioLeitura + ", diretorioEscrita=" + diretorioEscritaExtra
				+ ", bdHostname=" + dbHostname + ", dbPort=" + dbPort + ", dbInstanceName=" + dbInstanceName
				+ ", dbUser=" + dbUser + ", dbPassword=" + dbPassword + "]";
	}
}
