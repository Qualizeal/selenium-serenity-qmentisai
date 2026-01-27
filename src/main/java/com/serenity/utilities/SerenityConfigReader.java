package com.serenity.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class to read configuration from serenity.properties file.
 */
public class SerenityConfigReader {

	private static Properties properties;
	private static final String PROPERTIES_FILE = "serenity.properties";

	static {
		loadProperties();
	}

	/**
	 * Load properties from serenity.properties file
	 */
	private static void loadProperties() {
		properties = new Properties();

		// Try to load from file system first
		try (InputStream inputStream = new FileInputStream(PROPERTIES_FILE)) {
			properties.load(inputStream);
		} catch (IOException e) {
			// If not found in file system, try classpath
			try (InputStream inputStream = SerenityConfigReader.class.getClassLoader()
					.getResourceAsStream(PROPERTIES_FILE)) {
				if (inputStream != null) {
					properties.load(inputStream);
				}
			} catch (IOException ex) {
				// Ignore - will use defaults
			}
		}
	}

	/**
	 * Get property value by key
	 * @param key - property key
	 * @return property value
	 */
	public static String getProperty(String key) {
		// First try system property (if non-empty)
		String value = System.getProperty(key);
		if (value != null && !value.isEmpty()) {
			return value;
		}
		// Then try from loaded properties file
		return properties.getProperty(key);
	}

	/**
	 * Get property value with default fallback
	 * @param key - property key
	 * @param defaultValue - default value if property not found
	 * @return property value or default
	 */
	public static String getProperty(String key, String defaultValue) {
		String value = getProperty(key);
		return (value != null) ? value : defaultValue;
	}

	/**
	 * Get base URL from serenity.properties
	 * @return base URL
	 */
	public static String getBaseUrl() {
		return getProperty("webdriver.base.url", "https://app.fi360test.com/app/security/login");
	}

	/**
	 * Get test username from serenity.properties
	 * @return test username
	 */
	public static String getUsername() {
		return getProperty("webdriver.base.username", "qzteam@test.com");
	}

	/**
	 * Get test password from serenity.properties
	 * @return test password
	 */
	public static String getPassword() {
		return getProperty("webdriver.base.password", "QZteam2025!");
	}

	/**
	 * Get browser type from serenity.properties
	 * @return browser type
	 */
	public static String getBrowser() {
		return getProperty("webdriver.driver", "chrome");
	}

	/**
	 * Get implicit wait timeout from serenity.properties
	 * @return implicit wait timeout in milliseconds
	 */
	public static int getImplicitWait() {
		String timeout = getProperty("webdriver.timeouts.implicitlywait", "10000");
		return Integer.parseInt(timeout);
	}

	/**
	 * Get explicit wait timeout from serenity.properties
	 * @return explicit wait timeout in milliseconds
	 */
	public static int getExplicitWait() {
		String timeout = getProperty("webdriver.wait.for.timeout", "15000");
		return Integer.parseInt(timeout);
	}

	/**
	 * Get project name from serenity.properties
	 * @return project name
	 */
	public static String getProjectName() {
		return getProperty("serenity.project.name", "BR_POC");
	}

	/**
	 * Get environment from serenity.properties
	 * @return environment
	 */
	public static String getEnvironment() {
		return getProperty("test.environment", "qa");
	}

	/**
	 * Get application version from serenity.properties
	 * @return application version
	 */
	public static String getApplicationVersion() {
		return getProperty("report.customfields.ApplicationVersion", "17.3.2.1");
	}

	/**
	 * Get all properties as a formatted string
	 * @return all properties
	 */
	public static String getAllProperties() {
		StringBuilder sb = new StringBuilder();
		sb.append("=== Serenity Configuration ===\n");
		sb.append("Project Name: ").append(getProjectName()).append("\n");
		sb.append("Environment: ").append(getEnvironment()).append("\n");
		sb.append("Base URL: ").append(getBaseUrl()).append("\n");
		sb.append("Username: ").append(getUsername()).append("\n");
		sb.append("Browser: ").append(getBrowser()).append("\n");
		sb.append("Implicit Wait: ").append(getImplicitWait()).append("ms\n");
		sb.append("Explicit Wait: ").append(getExplicitWait()).append("ms\n");
		sb.append("App Version: ").append(getApplicationVersion()).append("\n");
		return sb.toString();
	}
}
