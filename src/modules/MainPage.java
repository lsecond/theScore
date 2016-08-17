package modules;


import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//import autoitx4java.AutoItX;
import utility.HelperUtility;
/**
 * main page function test
 * @author Owner
 *
 */
public class MainPage {
	public Logger log = Logger.getRootLogger();
	private HelperUtility helper;
	
	/**
	 * mainpage URL validation
	 * @param driver
	 * @param url
	 * @return
	 */
	public Boolean urlValid(WebDriver driver,String url) {
		Boolean valid= false;
		String mainPageURL = driver.getCurrentUrl();
		if(mainPageURL.equalsIgnoreCase(url)){
			valid = true;
		}
		return valid;
	}
	
	/**
	 * click main menu button
	 * @param driver
	 */
	public void clickMainMenu(WebDriver driver) {
		WebElement mainMenuElement = (WebElement) (new WebDriverWait(driver, 60))
				.until(ExpectedConditions.elementToBeClickable(By
						.xpath("/html/body/div[1]/header/div[1]/div/div/div[1]/div/button")));
		mainMenuElement.click();
	}
    
	
	
	public void chooseSport(WebDriver driver,String sport) {
		ArrayList<WebElement> sportsElements =  (ArrayList<WebElement>) (new WebDriverWait(driver, 60))
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
						.xpath("/html/body/div[1]/header/div[1]/div/div/div[1]/div/nav/div/section[3]/div/a/span")));
		for(int i=0; i<sportsElements.size();i++){
			
			if(sportsElements.get(i).getText().contains(sport)){
				log.info(sportsElements.get(i).getText());
				sportsElements.get(i).click();
				
				break;
			}
		}
	
	}
	
	
}
