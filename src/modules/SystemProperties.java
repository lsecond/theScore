package modules;

import fit.ColumnFixture;

public class SystemProperties extends ColumnFixture {
	private String key;
	private String chromeDriver;
	private String ie;

	public void setKey(String key) {
		this.key = key;
	}

	public void setChromeDriver(String chromeDriver) {
		this.chromeDriver = chromeDriver;
	}

	public String setChromeKey() {
		System.setProperty(key, chromeDriver);
		return System.getProperty(key);
	}

	public void setIEDriver(String browser) {
		this.ie = browser;
	}

	public String setIEKey() {
		System.setProperty(key, ie);
		return System.getProperty(key);
	}
}
