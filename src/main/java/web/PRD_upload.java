package web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.opencsv.exceptions.CsvException;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PRD_upload extends Over_write_prd {
	static WebDriver driver;
	static String filePath = ".\\csv_file\\flipkart_prd.csv";
	static String absolutePath;
	static WebElement sta;
	static String status;
	static boolean success = false;
//	public static void main(String[] args) throws CsvException, IOException, InterruptedException {

	public static void upload() {
		while (!success) {
			try {
				Over_write_prd.write();
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driver.get(Url);
				driver.findElement(By.xpath(usernamefiledW)).sendKeys(usernameW);
				driver.findElement(By.xpath(passfieldW)).sendKeys(passwordW);
				driver.findElement(By.xpath(signinw)).click();
				driver.findElement(By.xpath(PRD)).click();
				driver.findElement(By.xpath(Create_PRD)).click();
				driver.findElement(By.xpath(Upload)).click();
				driver.findElement(By.xpath(Select_file)).click();
				absolutePath = System.getProperty("user.dir") + "\\" + filePath;
				Runtime.getRuntime().exec("C://autoitfiles/fileupload.exe" + " " + absolutePath);
				Thread.sleep(3000);
				driver.findElement(By.xpath(Finish_btn)).click();
				Thread.sleep(400);
				driver.navigate().refresh();
				Thread.sleep(60000);
				driver.navigate().refresh();
				sta = driver.findElement(By.xpath(File_status));
				status = sta.getText();
				if (status.equals("Completed")) {
					System.out.println(status);
					success = true;
				} else if (status.equals("Pending")) {
					Thread.sleep(3000);
					driver.navigate().refresh();
					if (status.equals("Completed")) {
						System.out.println(status);
						success = true;
					}
				} else {
					System.out.println("Halted");
					tagList.clear();
					System.out.println(tagList);

				}
			} catch (Exception e) {
				e.printStackTrace(); // Print the exception for debugging purposes
				// You may choose to add a delay or other handling for exceptions if needed
			} finally {
				driver.quit();
			}

		}

	}

	public static String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
		Date now = new Date();
		return sdf.format(now);
	}
}