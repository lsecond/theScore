package test;

import modules.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import utility.HelperUtility;
import fit.ColumnFixture;

/**
 * @author John L
 * @version 1.0
 * @since 02/01/2016
 *
 *        create a application in Originator system
 */
public class PlayerTest extends ColumnFixture {

	public WebDriver driver;
	private Properties prop;
	private String testEnv;
	private String URL;
	private String browserType;
	private String homeLocation;
	private String sport;
	private Boolean isMainPageURLCurrect = false;
	private Boolean isPlayerInfomationExist = false;
	private Boolean isPlayerHeightFormatRight = false;
	private Boolean isPlayerBirthdayFormatRight = false;

	private static final long PAUSE_TIME = 3000;
	private static final long PAGE_TIME = 50;
	private static final long FINAL_TIME = 5000;
	private int timeOut;

	// define sub script for each page.

	private HelperUtility helper;

	private MainPage mainPage;
	private EPLSoccer eplSoccer;

	private static final String THESCORE = "http://www.thescore.com/";

	public String CHROME_DRIVER;
	public String IE_DRIVER ;	
	static public SortedMap<String, List<String>> parseInMap = new TreeMap<String, List<String>>();
	static public ArrayList<String> detailList = new ArrayList<>();
	static public SortedMap<Integer, ArrayList<String>> riskMap;
	public SortedMap<String, List<String>> currencymap = new TreeMap<String, List<String>>();
	public Logger log = Logger.getRootLogger();

	public void setTestEnv(String url) {
		this.testEnv = url;
		log.info("test env : " + this.testEnv);
	}

	/**
	 * FITNESSE: Set browser type
	 *
	 * @param browserType
	 *            Firefox, Chrome, IE
	 */
	public void setBrowserType(String browserType) {
		this.browserType = browserType;
		log.info(this.browserType);
	}

	public void setHomeLocation(String loc) {
		homeLocation = loc;
	}

	public void setMainPageURL(String mainPageurl) {
		this.URL = mainPageurl;
		log.info(this.URL);
	}
	
	public void setSport(String sport) {
		this.sport = sport;
		log.info(this.sport);
	}
	
	/**
	 * FITNESSE: Set time out
	 * 
	 * @param timeOut
	 *            Given time out
	 */
	public void setTimeOut(String timeOut) {
		if (timeOut != null)
			this.timeOut = Integer.parseInt(timeOut);
		else
			this.timeOut = 0;
	}

	/**
	 * Quit browser driver
	 *
	 */
	public boolean tearDown() {
		if (driver != null) {
			driver.close();
			driver.quit();
		}
		// System.exit(1);
		return true;
	}

	/**
	 * main process for ems test
	 * 
	 * @return
	 */
	public boolean setup() {
		boolean runStatus = false;
		try {
			log.info("test begin");
			helper = new HelperUtility();
			HelperUtility.disableScreenSaver();
			HelperUtility.driverType = browserType;
			CHROME_DRIVER= this.homeLocation+ "/chromedriver.exe";
			IE_DRIVER = this.homeLocation + "/IEDriverServer.exe";
			// Select browser type

			if (this.browserType.equalsIgnoreCase("Firefox")) {
				driver = getFirefoxDriver();
			} else if (this.browserType.equalsIgnoreCase("Chrome")) {
				System.setProperty("webdriver.chrome.driver", CHROME_DRIVER);
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--test-type");
				driver = new ChromeDriver(options);

				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			} else if (this.browserType.equalsIgnoreCase("IE")) {
				System.setProperty("webdriver.ie.driver",IE_DRIVER);
				// Avoid Protected Mode error
				DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
				caps.setJavascriptEnabled(true);
				caps.setPlatform(Platform.WINDOWS);
				caps.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
				caps.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
				caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				// caps.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS,
				// true);
				caps.setCapability("natvieEvents", true);
				caps.setCapability(InternetExplorerDriver.FORCE_CREATE_PROCESS, true);
				caps.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
				driver = new InternetExplorerDriver(caps);
			}

			driver.manage().timeouts().pageLoadTimeout(PAGE_TIME, TimeUnit.SECONDS);
			// Maximize browser size
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			log.info("-----test begin----");
			driver.navigate().to(this.testEnv);

		} catch (org.openqa.selenium.TimeoutException e) {
			System.err.println(e.getMessage());
			if (driver != null) {
				driver.close();
				driver.quit();
			}
			System.exit(1);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			if (driver != null) {
				driver.close();
				driver.quit();
			}
			System.exit(1);
		}
		log.info(" setup done");
		return true;
	}

	public boolean playerInfoValid() throws InterruptedException {
		ArrayList<String> playerInfo = new ArrayList<>();
		mainPage = new MainPage();
		eplSoccer = new EPLSoccer();
		isMainPageURLCurrect = mainPage.urlValid(driver, "http://www.thescore.com/trending");
		mainPage.clickMainMenu(driver);
		helper.sleep(2000);
		mainPage.chooseSport(driver, sport);
		helper.sleep(2000);
		eplSoccer.clickLeaders(driver);
		helper.sleep(5000);
		eplSoccer.clickLeaderRandom(driver, this.browserType);
		helper.sleep(2000);
		playerInfo = eplSoccer.getPlayerInfo(driver);
		this.isPlayerInfomationExist = playerInfo.size() >2;
		log.info(playerInfo);

		String height = playerInfo.get(0).split("/")[0].trim();
		String birthday = playerInfo.get(1).split("\\(")[0].trim();
		log.info("birthday:" + birthday);
		this.isPlayerHeightFormatRight = (eplSoccer.playerHeightvalidation(height));
		this.isPlayerBirthdayFormatRight = (eplSoccer.playerBirthdayvalidation(birthday));
		helper.sleep(2000);
		log.info(" test end without error");
		return true;
	}
     
	public boolean close() {
		driver.close();
		driver.quit();		
		return true;
	}
	
	public Boolean isPlayerInfomationExist() {
		return this.isPlayerInfomationExist;
	}

	public Boolean isPlayerHeightFormatRight() {
		return this.isPlayerHeightFormatRight;

	}

	public Boolean isPlayerBirthdayFormatRight() {
		return this.isPlayerBirthdayFormatRight;

	}

	public Boolean isMainPageURLCurrect() {
		return this.isMainPageURLCurrect;

	}

	/**
	 * Return Firefox driver with custom profile
	 *
	 * @return firefox driver
	 */
	public WebDriver getFirefoxDriver() {

		File profileDir = new File(this.homeLocation+ "\\firefoxprofile");
		FirefoxProfile fxProfile = new FirefoxProfile(profileDir);		
		return new FirefoxDriver(fxProfile);
	}

	public static void main(String[] args) throws Exception {
		Logger log = Logger.getRootLogger();
		// "firefox", "IE", "Chrome"
		String[] jsonStrings = new String[] {    "firefox", "IE", "Chrome"};
		for (int i = 0; i < jsonStrings.length; i++) {
			log.info("theScore testing begining");
			PlayerTest fixture = new PlayerTest();
			fixture.setTestEnv(THESCORE);
			fixture.setSport("EPL Soccer");
			fixture.setHomeLocation("c:\\workspace\\DemoTest");
			fixture.setBrowserType(jsonStrings[i]);
			fixture.setMainPageURL("http://www.thescore.com/trending");
			fixture.setup();
			fixture.playerInfoValid();
			fixture.tearDown();
			log.info("is main page URL correct ?   " + fixture.isMainPageURLCurrect());
			log.info("is player inforamtion exist on ? : " + fixture.isPlayerInfomationExist());
			log.info("is player's height format right ? : " + fixture.isPlayerHeightFormatRight());
			log.info("is player's birthday format right ? : " + fixture.isPlayerBirthdayFormatRight());

		}
	}
}