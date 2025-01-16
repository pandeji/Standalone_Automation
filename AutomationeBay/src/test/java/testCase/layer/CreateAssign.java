package testCase.layer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import standaloneCreateAssignment.CreateAssignment;
import standaloneLogin.LoginPage;
import webutility.method.Baseclass;

public class CreateAssign extends Baseclass {

	@DataProvider
	public Object[][] getData() throws IOException {
		List<HashMap<String, String>> data = wu.getJsonDataToHashmap("Template");
		return new Object[][] { { data.get(0) } };
	}
	LoginPage ste = new LoginPage(wu);
	CreateAssignment ca = new CreateAssignment(wu);
	

	@Test(priority = 1)
	public void Verify_user_login_with_vaild_credentials() {
//		CreateAssignment ste11 = new CreateAssignment(wu);
		ste.login_TC_01();
		ca.TC_01();
		wu.quiteBrowser();
	}
	
//	@Test(priority = 2)
//	public void verify_user_login_with_invalid_credentials() {
//		wu.initializeBrowser();
//		ste.login_TC_02();
//	}
	
	

}
