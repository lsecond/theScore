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
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import utility.HelperUtility;
import fit.ColumnFixture;

/**
 * @author John L
 * @version 1.0
 * @since 08/17/2016
 *      
 * this is main 
 * setup environment, run the test, close browser.  
 * @author Owner
 *
 */
public class PlayerTest extends ColumnFixture {

	public WebDriver driver;
	private Properties prop;
	private String testEnv;
	private String testPlatform;
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
	private int timeOut;
	private HelperUtility helper;
	private MainPage mainPage;
	private EPLSoccer eplSoccer;
	private static final String THESCORE = "http://www.thescore.com/";

	public String CHROME_DRIVER;
	public String IE_DRIVER;
	static public SortedMap<String, List<String>> parseInMap = new TreeMap<String, List<String>>();
	static public ArrayList<String> detailList = new ArrayList();
	static public SortedMap<Integer, ArrayList<String>> riskMap;
	public SortedMap<String, List<String>> currencymap = new TreeMap<String, List<String>>();
	public Logger log = Logger.getRootLogger();

	/**
	 * setup init URL
	 * 
	 * @param url
	 * the web address begin with.
	 */
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

	/**
	 * setup workspace
	 * 
	 * @param loc
	 */
	public void setHomeLocation(String loc) {
		this.homeLocation = loc;
	}

	/**
	 * setup running platform e.g.: mac or windows
	 * 
	 * @param testPlatform
	 */
	public void setTestPlatform(String testPlatform) {
		this.testPlatform = testPlatform;
	}

	/**
	 * setup main page url
	 * 
	 * @param mainPageurl
	 */
	public void setMainPageURL(String mainPageurl) {
		this.URL = mainPageurl;
		log.info(this.URL);
	}

	/**
	 * setup sport game
	 * 
	 * @param sport
	 */
	public void setSport(String sport) {
		this.sport = sport;
		log.info(this.sport);
	}

	/**
	 * Set time out
	 * 
	 * @param timeOut
	 * 
	 */
	public void setTimeOut(String timeOut) {
		if (timeOut != null)
			this.timeOut = Integer.parseInt(timeOut);
		else
			this.timeOut = 0;
	}

	/**
	 * close webdriver
	 * 
	 * @return
	 */
	public boolean tearDown() {
		if (driver != null) {
			driver.close();
			driver.quit();
		}
		return true;
	}

	/**
	 * environment setup
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
			if (this.testPlatform.equalsIgnoreCase("windows")) {
				CHROME_DRIVER = this.homeLocation + "/chromedriver.exe";
			} else {
				CHROME_DRIVER = this.homeLocation + "/chromedriverMac";
			}
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
				System.setProperty("webdriver.ie.driver", IE_DRIVER);
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
			} else {
				driver = new SafariDriver();
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

	/**
	 * test steps.
	 * 1. Navigate to http://www.thescore.com
	 * 
	 * 2. Verify the URL is correct
	 * 
	 * 3. Click on the main menu button
	 * 
	 * 4. Click on ¡°EPL Soccer¡±
	 * 
	 * 5. Click on ¡°Leaders¡±
	 * 
	 * 6. Click on a random player
	 * 
	 * 7. Verify that the height and birthdate display correctly (fields exist,
	 * proper format, etc)
	 * 
	 * @return
	 * @throws InterruptedException
	 */

	public boolean playerInfoValid() throws InterruptedException {
		ArrayList<String> playerInfo = new ArrayList();
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
		this.isPlayerInfomationExist = playerInfo.size() > 2;
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
    /**
     * close browser
     * @return
     */
	public boolean close() {
		driver.close();
		driver.quit();
		return true;
	}
	/**
	 * URL check
	 * @return
	 */
	public Boolean isMainPageURLCurrect() {
		return this.isMainPageURLCurrect;

	}
	/**
	 * player information exist?
	 * @return
	 */
	public Boolean isPlayerInfomationExist() {
		return this.isPlayerInfomationExist;
	}
	/**
	 * player height format check
	 * @return
	 */
	public Boolean isPlayerHeightFormatRight() {
		return this.isPlayerHeightFormatRight;

	}
	/**
	 * player birthday format check
	 * @return
	 */
	public Boolean isPlayerBirthdayFormatRight() {
		return this.isPlayerBirthdayFormatRight;

	}

	/**
	 * Return Firefox driver with custom profile
	 * 
	 * @return firefox driver
	 */
	public WebDriver getFirefoxDriver() {

		File profileDir = new File(this.homeLocation + "\\firefoxprofile");
		FirefoxProfile fxProfile = new FirefoxProfile(profileDir);
		return new FirefoxDriver(fxProfile);
	}
	
	public static void main(String[] args) throws Exception {
		Logger log = Logger.getRootLogger();
		// "firefox", "IE", "Chrome"
		String[] jsonStrings = new String[] { "Chrome" };// can add more browsers inside array.
		for (int i = 0; i < jsonStrings.length; i++) {
			log.info("theScore testing begining");
			PlayerTest fixture = new PlayerTest();
			fixture.setTestEnv(THESCORE);
			fixture.setSport("EPL Soccer");
			fixture.setHomeLocation("c:\\workspace\\theScore");
			// in mac should like : fixture.setHomeLocation("/Users/jiangliu/Documents/workspace/theScore");
			fixture.setBrowserType(jsonStrings[i]);
			fixture.setTestPlatform("windows");
			fixture.setMainPageURL("http://www.thescore.com/trending");
			fixture.setup();
			fixture.playerInfoValid();
			fixture.tearDown();
			log.info("is main page URL correct ?   " + fixture.isMainPageURLCurrect());
			log.info("is player inforamtion exist ? : " + fixture.isPlayerInfomationExist());
			log.info("is player's height format right ? : " + fixture.isPlayerHeightFormatRight());
			log.info("is player's birthday format right ? : " + fixture.isPlayerBirthdayFormatRight());

		}
	}
}