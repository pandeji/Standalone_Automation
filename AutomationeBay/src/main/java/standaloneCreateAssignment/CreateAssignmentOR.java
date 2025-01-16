package standaloneCreateAssignment;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateAssignmentOR {
	
	@FindBy(xpath = "//span[contains(text(),'My Assi')]/following::button[@title='Create Assignment']")
	WebElement createAssignmentBt;
	
	@FindBy(xpath = "//div[@class='dropdown-container assignment-source-language-dropdown1']")
	WebElement selectSourceLanguageDD;
	
	@FindBy(xpath = "//div[text()='english']")
	WebElement selectEnglish;
	
	@FindBy(xpath = "//div[text()='arabic']")
	WebElement selectArabic;
	
	@FindBy(xpath = "//div[@class='dropdown-selected  assignment-target-language-dropdown2 ']")
	WebElement selectTargetLanguage;
	
	@FindBy(xpath = "//label[text()='Select a File']")
	WebElement selectFile;
	
	@FindBy(xpath = "//button[text()='Upload & Count']")
	WebElement uploadFile;
	
	@FindBy(xpath = "//div[text()='Expected Delivery']//following-sibling::div[@class='custom-dropdown-container']")
	WebElement selectExpectedDelivery;
	
	@FindBy(xpath = "//div[text()='31']")
	WebElement selectExpectedDeliveryDate;
	
	@FindBy(xpath = "//div[text()='Expected Timeline']//following-sibling::div[@class='custom-dropdown-container']")
	WebElement selectExpectedTimeline;
	
	@FindBy(xpath = "//div[text()='31']")
	WebElement selectExpectedTimelineDate;
	
	@FindBy(xpath = "//button[text()='View Assignment']")
	WebElement viewAssignment;
	
	
	
}
