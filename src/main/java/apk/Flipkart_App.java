package apk;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.remote.MobileCapabilityType;
import web.Over_write_prd;
import web.PRD_upload;

public class Flipkart_App extends PRD_upload {
	static String ird;
	static AndroidDriver<WebElement> driver;
	static ArrayList<String> tag_id = Over_write_prd.tagList;
	static String Tag_id;
	static WebElement Tag_field;
	static boolean permissionHandled = false;
	static WebElement capture;
	static int i;
	static WebElement IRD_msg;
	static WebElement GRN_msg;
	static String timeDevice1;

	public static void Launch() throws MalformedURLException, InterruptedException {
//	public static void main(String[] args) throws MalformedURLException, InterruptedException {
//		Properties.pro();
		PRD_upload.upload();
//		WebDriverWait wait = new WebDriverWait(driver, 10);
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium");
		dc.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		dc.setCapability(MobileCapabilityType.PLATFORM_VERSION, "13");
		dc.setCapability(MobileCapabilityType.DEVICE_NAME, "chetan");
		System.out.println();
		dc.setCapability("appPackage", "com.blubirch.rims.flipkart");
		dc.setCapability("appActivity", "com.blubirch.commons.presentation.login.LoginActivity");
		URL url = new URL("http://127.0.0.1:4723/wd/hub");
		driver = new AndroidDriver<WebElement>(url, dc);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(By.id(APK_userfield)).sendKeys(APK_user);
		timeDevice1 = getCurrentTime();
		System.out.println("App is visible on Device at: "+ timeDevice1);
		driver.findElement(By.id(APK_passfield)).sendKeys(APK_pass);
		driver.findElement(By.id(APK_signin)).click();
		driver.findElement(By.xpath(Item_inward)).click();
		ird = web.Over_write_prd.getIRD();
		System.out.println(ird);
		driver.findElement(By.id(Search_field)).sendKeys(ird);
		driver.pressKey(new io.appium.java_client.android.nativekey.KeyEvent(AndroidKey.ENTER));
		driver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", "search"));
		for (i = 1; i < tag_id.size(); i++) {
			try {
				driver.pressKey(new io.appium.java_client.android.nativekey.KeyEvent(AndroidKey.TAB));
				driver.pressKey(new io.appium.java_client.android.nativekey.KeyEvent(AndroidKey.TAB));
				Tag_id = tag_id.get(i);
				System.out.println(Tag_id);
				Tag_field = driver.findElement(By.xpath(Tag_id_field));
				Tag_field.click();
				Tag_field.sendKeys(Tag_id);
				driver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", "search"));
				driver.findElement(By.id(Grade_btn)).click();
				driver.findElement(By.xpath(Check_box1)).click();
				Thread.sleep(500);
				driver.findElement(By.xpath(Check_box2)).click();
				Thread.sleep(500);
				driver.findElement(By.xpath(Check_box3)).click();
				Thread.sleep(500);
				if (i == 1 && !permissionHandled) {
					capture = driver.findElement(By.id(Redmi_permission));
					capture.click();
					permissionHandled = true;
				}
				driver.findElement(By.id(Capture_btn)).click();
				driver.findElement(By.id(Confirm_btn)).click();
				driver.findElement(By.id(Next_btn)).click();
				driver.findElement(By.id(Proceed_btn)).click();
				driver.findElement(By.xpath(Toat_field)).sendKeys("2456");
				driver.findElement(By.id(AddItem_btn)).click();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Thread.sleep(200);
		driver.findElement(By.id(Complete_IRD)).click();
		IRD_msg = driver.findElement(By.id(IRD_message));
		System.out.println(IRD_msg.getText());
		Thread.sleep(300);
		driver.findElement(By.id(Generate_GRN)).click();
		GRN_msg = driver.findElement(By.id(GRN_message));
		System.out.println(GRN_msg.getText());
		timeDevice1 = getCurrentTime();
		System.out.println("App is visible on Device at: "+ timeDevice1);
	}
}
