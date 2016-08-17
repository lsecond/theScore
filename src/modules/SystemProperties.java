package modules;

import fit.ColumnFixture;


/**
 * for Fitnesse setup. 
 * @author Owner
 *
 */
public class SystemProperties extends ColumnFixture {
	private String key;
	private String chromeDriver;
	private String ie;
	
	/**
	 * setup for fitnesse key
	 * @param key
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * set up chrome driver 
	 * @param chromeDriver
	 */
	public void setChromeDriver(String chromeDriver) {
		this.chromeDriver = chromeDriver;
	}
	/**
	 * setup chrome key
	 * @return
	 */
	public String setChromeKey() {
		System.setProperty(key, chromeDriver);
		return System.getProperty(key);
	}
	/**
	 * setup ie driver
	 * @param browser
	 */
	public void setIEDriver(String browser) {
		this.ie = browser;
	}
	/**
	 * setup IE key
	 * @return
	 */
	public String setIEKey() {
		System.setProperty(key, ie);
		return System.getProperty(key);
	}
}
