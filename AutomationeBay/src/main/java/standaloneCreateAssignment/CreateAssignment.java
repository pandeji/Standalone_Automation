package standaloneCreateAssignment;

import org.openqa.selenium.support.PageFactory;
import webutility.method.Webutility;

public class CreateAssignment extends CreateAssignmentOR{
	
	Webutility util;
	
	public CreateAssignment(Webutility wu) {
		PageFactory.initElements(wu.getDriver(), this);
		this.util = wu;
	}
	
	
	public void reloadPage() {
		String url = util.loaderConfigFile().getProperty("devurl");
		util.openUrl(url);
	}
	
	public void TC_01() {
		util.click(createAssignmentBt, "Clicked on Create Assignment Button");
		util.holdOn(2);
		util.click(selectSourceLanguageDD, "Dropdown opened Suggessfully");
		util.holdOn(2);
		util.click(selectEnglish, "English Language Selected as Source");
		util.holdOn(2);
		util.click(selectTargetLanguage, "Dropdown opened Suggessfully");
		util.holdOn(2);
		util.click(selectArabic, "Arabic Language Selected as Target");
		util.holdOn(2);
		util.click(selectFile, "Select File modal opened successfully");
		util.holdOn(2);
//		util.loaderConfigFile();
//		util.holdOn(2);
		util.robotMethod("20241202_1830_Marketing KPIs_NEW (1) (1).pptx");
		util.holdOn(2);
		util.click(uploadFile, "File Uplaoded Successfully");
		util.holdOn(2);
		
		
	}
}
