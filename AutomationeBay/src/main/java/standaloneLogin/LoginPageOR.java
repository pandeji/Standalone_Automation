package standaloneLogin;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPageOR {

	@FindBy(xpath = "//input[@type='email']")
	WebElement locEmailId;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement SubmitBt;

	@FindBy(xpath = "//input[@type='password']")
	WebElement locShopifyPassword;
	
	@FindBy(xpath="//*[contains(text(),'No User Found associated to given email')]")
	WebElement userNotExist;
}
