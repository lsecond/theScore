package modules;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//import autoitx4java.AutoItX;
import utility.HelperUtility;
/**
 * EPL page function test.
 * @author Owner
 *
 */
public class EPLSoccer {
	public Logger log = Logger.getRootLogger();
	private HelperUtility helper;

	/**
	 * click Lead button	  
	 * @param driver
	 */
	public void clickLeaders(WebDriver driver) {
		WebElement leadersElement = (WebElement) (new WebDriverWait(driver, 60)).until(ExpectedConditions
				.elementToBeClickable(By.xpath("/html/body/div[1]/header/div[2]/div/div/nav/a[4]/span/span/span")));
		// leadersElement.click();
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", leadersElement);
	}
	/**
	 * randomly choose a leader player and click.
	 * @param driver
	 * @param browser
	 * @throws InterruptedException
	 */
	public void clickLeaderRandom(WebDriver driver, String browser) throws InterruptedException {
		ArrayList<WebElement> leaderElements = (ArrayList<WebElement>) (new WebDriverWait(driver, 60))
				.until(ExpectedConditions
						.presenceOfAllElementsLocatedBy(By.xpath("//ul[@class='list-view leaders-list']/li")));
		int i = ThreadLocalRandom.current().nextInt(1, leaderElements.size());	
		// base on different browser, IE and Firefox use javascript perform click();
		if (browser.equalsIgnoreCase("chrome")) {
			leaderElements.get(i).click();			
		} else {			
			WebElement RandomLeader = driver.findElement(
					By.xpath("//ul[@class='list-view leaders-list']/li[" + i + "]/a/div/div[2]/div[1]/div[1]"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", RandomLeader);
			Thread.sleep(2000);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", RandomLeader);
		}
	}

	/**
	 * get information for players and return as a ArrayList
	 * @param driver
	 * @return
	 */
	public ArrayList<String> getPlayerInfo(WebDriver driver) {
		ArrayList<String> playerInfo = new ArrayList();
		ArrayList<WebElement> playerInfoElements = (ArrayList<WebElement>) (new WebDriverWait(driver, 60))
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("info-segment-data")));
		for (int i = 0; i < playerInfoElements.size(); i++) {
			String info = playerInfoElements.get(i).getText();
			playerInfo.add(info);
		}
		return playerInfo;
	}

	/**
	 * player height format check 
	 * @param height
	 * @return
	 */
	public Boolean playerHeightvalidation(String height) {
		Boolean valid = true;
		String pattern = "^(\\d{1,7})\'((\\s?)([0-9]|(1[0-1]))\")?$";
		Pattern r = Pattern.compile(pattern);
		valid = height.matches(pattern);
		return valid;
	}
	/**
	 * player birthday format check 
	 * @param birthday
	 * @return
	 */
	public Boolean playerBirthdayvalidation(String birthday) {
		log.info(birthday);
		Boolean valid = true;
		String pattern = "^((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$";
		valid = birthday.matches(pattern);
		return valid;
	}

}
