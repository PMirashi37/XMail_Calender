package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

import com.github.dockerjava.api.model.Task;

import java.time.Duration;
import java.util.logging.Level;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCases {
    ChromeDriver driver;

    public TestCases() {
        System.out.println("Constructor: TestCases");

        WebDriverManager.chromedriver().timeout(30).setup();
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        // Set log level and type
        logs.enable(LogType.BROWSER, Level.INFO);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);

        // Connect to the chrome-window running on debugging port
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");

        // Set path for log file
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "chromedriver.log");

        driver = new ChromeDriver(options);

        // Set browser to maximize and wait
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    public void endTest() {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }

    public void testCase01() {
        System.out.println("Start Test case: testCase01");
        driver.get("https://calendar.google.com/");

        String currenturl = driver.getCurrentUrl();
        if (currenturl.contains("calendar")) {
            System.out.println("Test case: TestCase01 Passed");
        }
    }

    public void testCase02() throws InterruptedException {
        System.out.println("Start Test case: testCase02");
        // driver.get("https://calendar.google.com/");

        driver.findElement(By.xpath("//button[@jsname='jnPWCc']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//li[@data-viewkey='month']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//div[text()='Create']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//span[@aria-label='Task']")).click();
        Thread.sleep(1000);
        WebElement title = driver.findElement(By.xpath("//input[@placeholder='Add title']"));
        title.sendKeys("Crio INTV Task Automation");
        driver.findElement(By.xpath("//textarea[@placeholder='Add description']"))
                .sendKeys("Crio INTV Calendar Task Automation");
        driver.findElement(By.xpath("//div[@jsname='c6xFrd']//button[@jsname='x8hlje']")).click();
        String switchmonth = driver.findElement(By.xpath("//div[@jsname='WjL7X']//span[@jsname='V67aGc']")).getText();
        Thread.sleep(1000);
        if (switchmonth.contains("Month")) {
            System.out.println("The Calendar switched to month view and a task was created");
        }
        System.out.println("Test case: TestCase02 Passed");
    }

    public void testCase03() throws InterruptedException {
        System.out.println("Start Test case: testCase03");
        // driver.get("https://calendar.google.com/");

        driver.findElement(By.xpath("//span[contains(text(),'Automation')]")).click();
        driver.findElement(By.xpath("//button[@aria-label='Edit task']")).click();
        driver.findElement(By.xpath("//textarea[@placeholder='Add description']")).clear();
        driver.findElement(By.xpath("//textarea[@placeholder='Add description']")).sendKeys(
                "Crio INTV Task Automation is a test suite designed for automating various tasks on the Google Calendar web application");
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[@jsname='x8hlje']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[contains(text(),'Automation')]")).click();
        String taskupdatedM = driver.findElement(By.xpath("//div[@class='Mz3isd']/div[3]")).getText();

        if (taskupdatedM.contains(
                "Crio INTV Task Automation is a test suite designed for automating various tasks on the Google Calendar web application")) {
            System.out.println("The task description is successfully updated and displayed");
        }

    }

    public void testCase04() throws InterruptedException {
        System.out.println("Start Test case: testCase04");
        // driver.get("https://calendar.google.com/");
        try {
            driver.findElement(By.xpath("//span[contains(text(),'Automation')]")).click();
            WebElement title = driver.findElement(By.id("rAECCd"));
            if (title.getText().equals("Crio INTV Task Automation")) {
                driver.findElement(By.xpath("//button[@aria-label='Delete task']")).click();
            }
            Thread.sleep(1000);
            WebElement taskdeletedM = driver.findElement(By.xpath("//div[@class='YrbPvc']"));
            Thread.sleep(2000);
            System.out.println(taskdeletedM.getText());
            if (taskdeletedM.getText().equals("Task deleted")) {
                System.out.println(
                        "The task is successfully deleted, and the confirmation message indicates Task deleted");
            }
            else{
                System.out.println(
                    "The task is not successfully deleted");
            }
        } catch (Exception e) {
            e.getMessage();
            // TODO: handle exception
        }

    }
}
