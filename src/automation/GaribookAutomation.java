package automation;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GaribookAutomation {

	public static void main(String[] args) {
        // Set the EdgeDriver path
        System.setProperty("webdriver.edge.driver", "F:\\DOWNLOADS\\now edge\\edgedriver_win64\\msedgedriver.exe");
        EdgeDriver driver = new EdgeDriver();
        driver.manage().window().maximize();

        try {
            // Step 1: Open Garibook website
            driver.get("http://fe.garibook.com/");

            // Step 2: Click on the "Log in" link
            driver.findElement(By.linkText("Log in")).click();
            System.out.println("Login link clicked successfully");

            // Step 3: Create WebDriverWait object
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Step 4: Enter the phone number
            WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("mobile")));
            phoneInput.sendKeys("01680305178");

            // Step 5: Click on 'Send Code' button
            WebElement sendCodeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[4]/div/div/div[2]/form/div[2]/button")));
            sendCodeButton.click();

            // Step 6: Wait for the OTP to appear
            WebElement otpElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Your OTP is')]")));

            // Extract OTP digits using regex
            String otpText = otpElement.getText();
            String otp = otpText.replaceAll("\\D+", ""); // Extract only digits
            System.out.println("Extracted OTP: " + otp);

            // Step 7: Enter OTP into the four fields
            driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/form/div[1]/div[2]/div/input[1]")).sendKeys(String.valueOf(otp.charAt(0)));
            driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/form/div[1]/div[2]/div/input[2]")).sendKeys(String.valueOf(otp.charAt(1)));
            driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/form/div[1]/div[2]/div/input[3]")).sendKeys(String.valueOf(otp.charAt(2)));
            driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/form/div[1]/div[2]/div/input[4]")).sendKeys(String.valueOf(otp.charAt(3)));

            // Step 8: Click "Continue" button
            WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[4]/div/div/div[2]/form/div[2]/button")));
            continueButton.click();

            // Step 9: Confirmation message
            System.out.println("Login Test Completed Successfully.");

            // Step 10: Wait until redirected to home page after login
            wait.until(ExpectedConditions.urlContains("?login-success"));
            
            // Step 11: Wait for page to fully load
            Thread.sleep(2000);

            // Step 12: Find the target button with improved waiting and scroll
            WebElement targetButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("/html/body/div[1]/div[1]/div[2]/section[1]/div/div/div[2]/div/div[2]/div/div[1]/form/div[2]/div/div[7]/div/button")));
            
            // Step 13: Scroll to the button to ensure it's in view
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", targetButton);
            
            // Step 14: Wait for any animations to complete
            Thread.sleep(1000);
            
            // Step 15: Click using JavaScript to avoid interception
            js.executeScript("arguments[0].click();", targetButton);
            
            // Step 16: Wait until redirected to the trip request page
            wait.until(ExpectedConditions.urlToBe("http://fe.garibook.com/trip/trip-request"));
            System.out.println("Successfully landed on trip request page!");
            
            // Step 17: Enter Pickup location
            WebElement pickupInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("pickup_location")));
            pickupInput.clear();
            pickupInput.sendKeys("Savar, Bangladesh");
            Thread.sleep(1000); // Wait for suggestions to appear if any
            
            // Step 18: Enter Drop-off location
            WebElement dropoffInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("drop_off_location")));
            dropoffInput.clear();
            dropoffInput.sendKeys("Mirpur-1, Dhaka, Bangladesh");
            Thread.sleep(1000); // Wait for suggestions to appear if any
            
            // Step 19: Select Date & Time
            WebElement dateTimeInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/div[1]/div[1]/div[2]/section[2]/div/div/div/div/div[2]/form/div[6]/div/div[1]/div/input")));
            
            // Clear any existing value first
            dateTimeInput.clear();
            dateTimeInput.sendKeys("02/20/2025, 10:00 AM");
            Thread.sleep(1000); // Wait for date picker to close if it appears
            
            // Step 20: Apply Promo Code
            WebElement promoCodeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("promo_code")));
            promoCodeInput.clear();
            promoCodeInput.sendKeys("DISCOUNT50");
            
            // Step 21: Scroll to the submit button
            WebElement requestTripButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("/html/body/div[1]/div[1]/div[2]/section[2]/div/div/div/div/div[2]/form/div[9]")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", requestTripButton);
            Thread.sleep(1000); // Wait for scroll to complete
            
            // Step 22: Submit Trip Request using JavaScript click
            System.out.println("Clicking the submit button...");
            js.executeScript("arguments[0].click();", requestTripButton);
            System.out.println("Submit button clicked successfully!");
            
            // Step 23: Wait briefly to observe the result
            System.out.println("Waiting 3 seconds to observe the result...");
            Thread.sleep(3000);
            
            System.out.println("Trip request completed. Final URL: " + driver.getCurrentUrl());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close browser
            System.out.println("Test completed. Closing browser...");
            driver.quit();
        }
    }
}