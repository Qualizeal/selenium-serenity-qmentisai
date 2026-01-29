# Framework Architecture Documentation

## Overview
This Serenity BDD framework has been refactored to follow a clean architecture pattern with separate classes for driver management, report handling, and test lifecycle management.

## Framework Structure

### 1. Driver Management (`com.serenity.driver`)

#### DriverManager.java
- **Purpose**: Manages WebDriver creation, configuration, and lifecycle
- **Key Features**:
  - Implements `DriverSource` interface for Serenity integration
  - Supports multiple browsers (Chrome, Firefox, Edge)
  - Configurable headless mode
  - Thread-safe driver management using ThreadLocal
  - Automatic timeout configuration
  - SSL certificate handling
  - Download preferences management

**Key Methods**:
```java
- newDriver() - Creates a new WebDriver instance
- getDriver() - Returns current thread's WebDriver
- quitDriver() - Quits the WebDriver instance
- isDriverInitialized() - Checks if driver is active
- getBrowserName() - Returns browser name
- getBrowserVersion() - Returns browser version
```

**Usage**:
```java
WebDriver driver = DriverManager.getDriver();
DriverManager.quitDriver();
```

#### DriverFactory.java
- **Purpose**: Factory pattern for simplified driver access
- **Key Features**:
  - Singleton pattern for DriverManager
  - Simplified driver creation methods
  - Browser information utilities

**Usage**:
```java
WebDriver driver = DriverFactory.createDriver();
String browserInfo = DriverFactory.getBrowserInfo();
DriverFactory.quitDriver();
```

### 2. Report Management (`com.serenity.reporting`)

#### ReportManager.java
- **Purpose**: Centralized report handling and customization
- **Key Features**:
  - Thread-safe test context management
  - Custom data recording in Serenity reports
  - Test execution metadata tracking
  - Environment information logging
  - Screenshot management
  - Test failure recording
  - Report directory management

**Key Methods**:
```java
- recordTestExecutionMetadata() - Records test metadata
- recordCustomData(title, content) - Records custom data
- addTag(tag) - Adds tag to current test
- setTestTitle(title) - Sets test title
- recordStep(description) - Records a test step
- takeScreenshot(name) - Takes a screenshot
- recordTestFailure() - Records failure details
- recordEnvironmentInfo() - Records environment details
```

**Usage**:
```java
ReportManager.recordStep("User logged in successfully");
ReportManager.recordCustomData("Login Details", "Username: admin");
ReportManager.takeScreenshot("LoginSuccess");
```

### 3. Test Lifecycle Management (`com.serenity.hooks`)

#### TestHooks.java
- **Purpose**: Manages test lifecycle with Cucumber hooks
- **Key Features**:
  - @Before hooks for scenario setup
  - @After hooks for scenario cleanup
  - @BeforeStep and @AfterStep hooks
  - Automatic metadata recording
  - Failure handling and screenshot capture
  - Execution time tracking
  - Tag management

**Hook Order**:
1. `@Before(order=1)` - Initialize test context, record metadata
2. `@Before(order=2)` - Initialize WebDriver
3. `@BeforeStep` - Record step start time
4. `@AfterStep` - Calculate step duration, take screenshots
5. `@After(order=1)` - Handle results, record failures
6. `@After(order=2)` - Cleanup driver

**Usage**: Hooks are automatically executed by Cucumber/Serenity

### 4. Base Classes (`com.serenity.base`)

#### BaseTest.java
- **Purpose**: Base class for all test classes
- **Key Features**:
  - Common test utilities
  - Page object initialization
  - Navigation helpers
  - Logging utilities
  - Report integration

**Usage**:
```java
public class LoginSteps extends BaseTest {
    public void someTest() {
        navigateToBaseUrl();
        recordStep("Navigated to base URL");
        takeScreenshot("HomePage");
    }
}
```

#### BasePage.java
- **Purpose**: Base class for all page objects
- **Key Features**:
  - Extended Serenity PageObject functionality
  - Common element interaction methods
  - Wait utilities
  - JavaScript execution
  - Frame and alert handling
  - Logging and reporting integration

**Usage**:
```java
public class LoginPage extends BasePage {
    public void login(String user, String pass) {
        typeText(username, user);
        typeText(password, pass);
        clickElement(loginButton);
        logStep("User logged in");
    }
}
```

### 5. Utilities (`com.serenity.utilities`)

#### SerenityConfigReader.java
- Reads configuration from `serenity.properties`
- Provides typed getters for all configurations
- Supports system property overrides

## Configuration

### serenity.properties
```properties
# Driver Configuration
webdriver.driver=chrome
webdriver.base.url=https://app.fi360test.com/app/security/login
webdriver.base.username=qzteam@test.com
webdriver.base.password=QZteam2025!
headless.mode=false

# Custom Driver Manager (optional)
#webdriver.provided.type=com.serenity.driver.DriverManager

# Timeouts
webdriver.timeouts.implicitlywait=20000
webdriver.wait.for.timeout=60000
webdriver.timeouts.pageLoadTimeout=60000

# Screenshots
serenity.take.screenshots=AFTER_EACH_STEP
serenity.full.page.screenshot.strategy=true

# Reporting
serenity.project.name=BR_POC
report.customfields.ApplicationVersion=17.3.2.1
report.customfields.environment=QA
report.customfields.user=Praneetha Ramireddy
```

## Package Structure

```
com.serenity
├── base
│   ├── BaseTest.java          # Base test class
│   └── BasePage.java          # Base page object class
├── driver
│   ├── DriverManager.java     # WebDriver manager
│   └── DriverFactory.java     # Driver factory
├── hooks
│   └── TestHooks.java         # Cucumber hooks
├── pages
│   └── LoginPage.java         # Page objects
├── reporting
│   └── ReportManager.java     # Report manager
├── stepdefinition
│   └── LoginStepDefinitions.java # Step definitions
└── utilities
    ├── SerenityConfigReader.java  # Config reader
    └── JsonReader.java            # JSON utilities
```

## Usage Examples

### 1. Creating a New Page Object
```java
package com.serenity.pages;

import com.serenity.base.BasePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

public class DashboardPage extends BasePage {
    
    @FindBy(id = "welcomeMessage")
    private WebElementFacade welcomeMessage;
    
    public String getWelcomeMessage() {
        waitForElementVisible(welcomeMessage, 30);
        String message = getElementText(welcomeMessage);
        logStep("Retrieved welcome message: " + message);
        return message;
    }
}
```

### 2. Creating Step Definitions
```java
package com.serenity.stepdefinition;

import com.serenity.base.BaseTest;
import com.serenity.pages.DashboardPage;
import com.serenity.reporting.ReportManager;
import io.cucumber.java.en.Then;

public class DashboardSteps extends BaseTest {
    
    private DashboardPage dashboardPage;
    
    @Then("I should see the welcome message")
    public void iShouldSeeWelcomeMessage() {
        dashboardPage = initPage(DashboardPage.class);
        String message = dashboardPage.getWelcomeMessage();
        
        ReportManager.recordCustomData("Welcome Message", message);
        assert message.contains("Welcome");
    }
}
```

### 3. Custom Driver Configuration
To use custom driver configuration, uncomment in `serenity.properties`:
```properties
webdriver.provided.type=com.serenity.driver.DriverManager
```

### 4. Recording Custom Data
```java
// In step definitions or page objects
Map<String, String> testData = new HashMap<>();
testData.put("Username", "testuser");
testData.put("Environment", "QA");
testData.put("Test Type", "Smoke");

ReportManager.recordCustomData("Test Information", testData);
```

### 5. Handling Test Failures
```java
try {
    // Test logic
} catch (Exception e) {
    ReportManager.recordTestFailure(
        "Login Test",
        e.getMessage(),
        Arrays.toString(e.getStackTrace())
    );
    throw e;
}
```

## Best Practices

1. **Always extend BasePage** for page objects to get common utilities
2. **Use BaseTest** for step definitions to access framework utilities
3. **Use ReportManager** for all custom logging and data recording
4. **Let TestHooks** handle driver lifecycle - don't manually quit drivers
5. **Use SerenityConfigReader** for all configuration access
6. **Record meaningful steps** using ReportManager.recordStep()
7. **Take screenshots** at important points using ReportManager.takeScreenshot()
8. **Use logStep()** in page objects for better traceability

## Troubleshooting

### Driver Not Starting
- Check `webdriver.driver` property in serenity.properties
- Verify browser is installed
- Check if custom driver manager is configured correctly

### Reports Not Generated
- Ensure hooks are being executed (check logs)
- Verify `@Before` and `@After` annotations are recognized
- Check report directory: `target/site/serenity`

### Thread Safety Issues
- All managers use ThreadLocal for parallel execution
- Don't share page objects between scenarios
- Use Serenity session variables for cross-step data

## Running Tests

```bash
# Run all tests
mvn clean verify

# Run specific tag
mvn clean verify -Dcucumber.filter.tags="@ICSMFRS360-60345"

# Run with custom properties
mvn clean verify -Dwebdriver.driver=firefox -Dheadless.mode=true
```

## Report Viewing

After test execution:
1. Open `target/site/serenity/index.html` in browser
2. View detailed test results with screenshots
3. Check custom data recorded during execution
4. View environment and execution metadata

## Extending the Framework

### Adding New Browser Support
1. Edit `DriverManager.java`
2. Add new case in `newDriver()` switch statement
3. Create browser-specific options method

### Adding Custom Reporting
1. Create methods in `ReportManager.java`
2. Use Serenity APIs for report integration
3. Add custom hooks in `TestHooks.java` if needed

### Adding New Utilities
1. Create new utility class in `com.serenity.utilities`
2. Make methods static for easy access
3. Document usage in this README

## Support
For issues or questions, please refer to:
- Serenity BDD Documentation: https://serenity-bdd.info
- Project-specific documentation in `/MarkdownDocs/`

