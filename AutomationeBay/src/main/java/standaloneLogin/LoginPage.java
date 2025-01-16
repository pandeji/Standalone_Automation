package standaloneLogin;

import org.openqa.selenium.support.PageFactory;
import webutility.method.Webutility;

public class LoginPage extends LoginPageOR {

	Webutility util;

	public LoginPage(Webutility wu) {
		PageFactory.initElements(wu.getDriver(), this);
		this.util = wu;
	}
//	WebDriver driver = new ChromeDriver();
	
	
	//Verify user login with vaild credentials
//	LoginPage lp = new LoginPage(util);
	
	public void reloadPage() {
		String url = util.loaderConfigFile().getProperty("devurl");
		util.openUrl(url);
	}
	
	public void login_TC_01() { 
		String url = util.loaderConfigFile().getProperty("devurl");
		util.openUrl(url);
		util.holdOn(3);
		String emailid = util.loaderConfigFile().getProperty("emailid");
		util.sendkeys(locEmailId, emailid, "Input successfully shopify email id in email box");
		util.holdOn(2);
		String emailpwd = util.loaderConfigFile().getProperty("emailpwd");
		util.sendkeys(locShopifyPassword, emailpwd, "Input successfully shopify password in password box");
		util.holdOn(2);
		util.click(SubmitBt, "Click successfully on Continue with Email button");
		util.holdOn(3);
		String currentUrl = util.getUrl();
	    if (currentUrl.equals("https://cat.ezlab.in/assignments")) {
	    	System.out.println("User is logged in.");
	    } else if (currentUrl.equals("https://cat.ezlab.in/")) {
	    	System.out.println("User is not logged in.");
	    }
	}
	
//	verify user login with invalid credentials(Invaild email & valid password)
	
	public void login_TC_02() {
		reloadPage();
		util.holdOn(3);
		String emailid = util.loaderConfigFile().getProperty("invalidEmail");
		util.sendkeys(locEmailId, emailid, "Input successfully email id in email box");
		util.holdOn(2);
		String emailpwd = util.loaderConfigFile().getProperty("invalidPWD");
		util.sendkeys(locShopifyPassword, emailpwd, "Input successfully password in password box");
		util.holdOn(2);
		util.click(SubmitBt, "Click successfully on Submit button");
		util.holdOn(2);
		String currentUrl = util.getUrl();
	    if (currentUrl.equals("https://cat.ezlab.in/assignments")) {
	    	System.out.println("User is logged in.");
	    } else if (currentUrl.equals("https://cat.ezlab.in/")) {
	    	System.out.println("User is not logged in.");
	    }
		
	}
	
}
