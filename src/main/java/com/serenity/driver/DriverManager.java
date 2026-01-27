package com.serenity.driver;

import com.serenity.exceptions.DriverException;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.webdriver.DriverSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.serenity.utilities.SerenityConfigReader;

/**
 * DriverManager class responsible for WebDriver creation, configuration, and lifecycle management.
 * This class handles browser initialization with appropriate options and capabilities.
 */
public class DriverManager implements DriverSource {

    private static final Logger LOGGER = Logger.getLogger(DriverManager.class.getName());
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    /**
     * Get the current WebDriver instance for the current thread
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver == null) {
            driver = Serenity.getDriver();
            driverThreadLocal.set(driver);
        }
        return driver;
    }

	/**
	 * Create a new WebDriver instance based on configuration
	 * @return WebDriver instance
	 * @throws DriverException if driver creation fails
	 */
	@Override
	public WebDriver newDriver() {
		try {
			String browser = SerenityConfigReader.getBrowser().toLowerCase();
			boolean headless = Boolean.parseBoolean(SerenityConfigReader.getProperty("headless.mode", "false"));

			LOGGER.info("Initializing WebDriver for browser: " + browser + " (Headless: " + headless + ")");

			WebDriver driver;
			switch (browser) {
				case "chrome":
					driver = createChromeDriver(headless);
					break;
				case "firefox":
					driver = createFirefoxDriver(headless);
					break;
				case "edge":
					driver = createEdgeDriver(headless);
					break;
				default:
					LOGGER.warning("Browser '" + browser + "' not recognized. Defaulting to Chrome.");
					driver = createChromeDriver(headless);
			}

			configureDriver(driver);
			driverThreadLocal.set(driver);

			LOGGER.info("WebDriver initialized successfully");
			return driver;
		} catch (Exception e) {
			throw new DriverException("Failed to create WebDriver instance", e);
		}
	}

    /**
     * Create ChromeDriver with configured options
     * @param headless - whether to run in headless mode
     * @return ChromeDriver instance
     */
    private WebDriver createChromeDriver(boolean headless) {
        ChromeOptions options = new ChromeOptions();

        // Add arguments from configuration
        String chromeSwitches = SerenityConfigReader.getProperty("chrome.switches", "");
        if (!chromeSwitches.isEmpty()) {
            for (String arg : chromeSwitches.split(",")) {
                options.addArguments(arg.trim());
            }
        }

        // Common Chrome arguments for stability
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");

        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        } else {
            options.addArguments("--start-maximized");
        }

        // SSL and certificate handling
        boolean acceptSSL = Boolean.parseBoolean(
            SerenityConfigReader.getProperty("chrome.capabilities.acceptSslCerts", "true")
        );
        if (acceptSSL) {
            options.setAcceptInsecureCerts(true);
        }

		// Auto-download configuration with cross-platform path
		if (Boolean.parseBoolean(SerenityConfigReader.getProperty("webdriver.autodownload", "true"))) {
			Map<String, Object> prefs = new HashMap<>();
			prefs.put("download.prompt_for_download", false);
			
			// Create downloads directory if it doesn't exist (cross-platform)
			Path downloadPath = Paths.get(System.getProperty("user.dir"), "downloads");
			try {
				Files.createDirectories(downloadPath);
				LOGGER.info("Download directory ensured: " + downloadPath.toAbsolutePath());
			} catch (Exception e) {
				LOGGER.warning("Could not create downloads directory: " + e.getMessage());
			}
			
			prefs.put("download.default_directory", downloadPath.toAbsolutePath().toString());
			prefs.put("safebrowsing.enabled", false);
			options.setExperimentalOption("prefs", prefs);
		}

        // Exclude logging options for cleaner console output
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-logging"});

        LOGGER.info("Creating ChromeDriver with options: " + options.asMap());
        return new ChromeDriver(options);
    }

    /**
     * Create FirefoxDriver with configured options
     * @param headless - whether to run in headless mode
     * @return FirefoxDriver instance
     */
    private WebDriver createFirefoxDriver(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();

        if (headless) {
            options.addArguments("--headless");
            options.addArguments("--width=1920");
            options.addArguments("--height=1080");
        }

        // SSL handling
        options.setAcceptInsecureCerts(true);

        LOGGER.info("Creating FirefoxDriver with options: " + options.asMap());
        return new FirefoxDriver(options);
    }

    /**
     * Create EdgeDriver with configured options
     * @param headless - whether to run in headless mode
     * @return EdgeDriver instance
     */
    private WebDriver createEdgeDriver(boolean headless) {
        EdgeOptions options = new EdgeOptions();

        if (headless) {
            options.addArguments("--headless");
            options.addArguments("--window-size=1920,1080");
        } else {
            options.addArguments("--start-maximized");
        }

        options.setAcceptInsecureCerts(true);

        LOGGER.info("Creating EdgeDriver with options: " + options.asMap());
        return new EdgeDriver(options);
    }

    /**
     * Configure WebDriver with timeouts and other settings
     * @param driver - WebDriver instance to configure
     */
    private void configureDriver(WebDriver driver) {
        // Maximize browser window
        driver.manage().window().maximize();
        
        // Set implicit wait
        int implicitWait = SerenityConfigReader.getImplicitWait();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(implicitWait));

        // Set page load timeout
        String pageLoadTimeout = SerenityConfigReader.getProperty("webdriver.timeouts.pageLoadTimeout", "60000");
        driver.manage().timeouts().pageLoadTimeout(Duration.ofMillis(Long.parseLong(pageLoadTimeout)));

        // Set script timeout
        driver.manage().timeouts().scriptTimeout(Duration.ofMillis(30000));

        LOGGER.info("Driver configured with timeouts - Implicit: " + implicitWait + "ms, PageLoad: " + pageLoadTimeout + "ms");
    }

    /**
     * Quit the current WebDriver instance
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            try {
                LOGGER.info("Quitting WebDriver");
                driver.quit();
            } catch (Exception e) {
                LOGGER.warning("Error while quitting driver: " + e.getMessage());
            } finally {
                driverThreadLocal.remove();
            }
        }
    }

    /**
     * Check if driver is initialized
     * @return true if driver exists
     */
    public static boolean isDriverInitialized() {
        return driverThreadLocal.get() != null || Serenity.getDriver() != null;
    }

    /**
     * Get browser name from driver
     * @return browser name
     */
    public static String getBrowserName() {
        try {
            WebDriver driver = getDriver();
            if (driver instanceof RemoteWebDriver) {
                return ((RemoteWebDriver) driver).getCapabilities().getBrowserName();
            }
        } catch (Exception e) {
            LOGGER.warning("Unable to get browser name: " + e.getMessage());
        }
        return SerenityConfigReader.getBrowser();
    }

    /**
     * Get browser version from driver
     * @return browser version
     */
    public static String getBrowserVersion() {
        try {
            WebDriver driver = getDriver();
            if (driver instanceof RemoteWebDriver) {
                return ((RemoteWebDriver) driver).getCapabilities().getBrowserVersion();
            }
        } catch (Exception e) {
            LOGGER.warning("Unable to get browser version: " + e.getMessage());
        }
        return "Unknown";
    }

    @Override
    public boolean takesScreenshots() {
        return true;
    }
}

