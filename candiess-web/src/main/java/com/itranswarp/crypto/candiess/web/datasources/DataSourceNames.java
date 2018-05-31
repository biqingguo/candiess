package com.itranswarp.crypto.candiess.web.datasources;

public enum DataSourceNames {
	COMMON("COMMON"), ADMIN("ADMIN"), EX("EX"), MG("MG"), UI("UI");
	private String value;

	DataSourceNames(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}