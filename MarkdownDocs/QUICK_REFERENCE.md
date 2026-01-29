# Quick Reference Guide - Refactored Framework

## Package Structure
```
com.serenity
├── base/                    # Base classes
│   ├── BaseTest.java       # Base for step definitions
│   └── BasePage.java       # Base for page objects
├── driver/                  # Driver management
│   ├── DriverManager.java  # WebDriver manager
│   └── DriverFactory.java  # Driver factory
├── hooks/                   # Test lifecycle
│   └── TestHooks.java      # Cucumber hooks
├── pages/                   # Page objects
│   └── LoginPage.java
├── reporting/               # Report management
│   └── ReportManager.java  # Report utilities
├── stepdefinition/          # Step definitions
│   └── LoginStepDefinitions.java
└── utilities/               # Utilities
    ├── SerenityConfigReader.java
    └── JsonReader.java
```

## Quick Access APIs

### Driver Management
```java
// Get driver
WebDriver driver = DriverFactory.getDriver();

// Check if driver is ready
boolean isReady = DriverFactory.isDriverInitialized();

// Get browser info
String info = DriverFactory.getBrowserInfo();
```

### Reporting
```java
// Record a step
ReportManager.recordStep("User clicked login button");

// Record custom data
ReportManager.recordCustomData("Title", "Content");

// Record with map
Map<String, String> data = new HashMap<>();
data.put("Key", "Value");
ReportManager.recordCustomData("Title", data);

// Take screenshot
ReportManager.takeScreenshot("ScreenshotName");

// Record failure
ReportManager.recordTestFailure("TestName", "Error", "StackTrace");
```

### Page Objects (extending BasePage)
```java
// Click element
clickElement(myElement);

// Type text
typeText(myElement, "text");

// Get text
String text = getElementText(myElement);

// Check visibility
boolean isVisible = isElementDisplayed(myElement);

// Scroll
scrollToElement(myElement);
scrollToTop();
scrollToBottom();

// JavaScript
Object result = executeJavaScript("return document.title;");

// Wait
waitForPageLoad();
waitFor(2000); // milliseconds

// Logging
logStep("Step description");
logData("Title", "Content");
captureScreenshot("Name");
```

### Step Definitions (extending BaseTest)
```java
// Initialize page
MyPage page = initPage(MyPage.class);

// Get page from session
MyPage page = getPage(MyPage.class, "pageKey");

// Navigate
navigateToBaseUrl();

// Record step
recordStep("Step description");

// Take screenshot
takeScreenshot("Name");

// Record data
recordData("Title", "Content");

// Get current URL
String url = getCurrentUrl();

// Get page title
String title = getPageTitle();

// Navigate
refreshPage();
navigateBack();
navigateForward();

// Logging
logInfo("Info message");
logWarning("Warning message");
logError("Error message");
```

## Configuration

### serenity.properties
```properties
# Browser
webdriver.driver=chrome
headless.mode=false

# URLs
webdriver.base.url=https://example.com

# Credentials
webdriver.base.username=user@test.com
webdriver.base.password=password

# Timeouts
webdriver.timeouts.implicitlywait=20000
webdriver.wait.for.timeout=60000
webdriver.timeouts.pageLoadTimeout=60000

# Screenshots
serenity.take.screenshots=AFTER_EACH_STEP

# Reporting
serenity.project.name=MyProject
report.customfields.environment=QA
report.customfields.ApplicationVersion=1.0.0
```

## Common Patterns

### Create a New Page Object
```java
package com.serenity.pages;

import com.serenity.base.BasePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

public class MyPage extends BasePage {
    
    @FindBy(id = "elementId")
    private WebElementFacade element;
    
    public void clickElement() {
        clickElement(element);
        logStep("Element clicked");
    }
    
    public String getElementText() {
        return getElementText(element);
    }
}
```

### Create Step Definitions
```java
package com.serenity.stepdefinition;

import com.serenity.base.BaseTest;
import com.serenity.pages.MyPage;
import com.serenity.reporting.ReportManager;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class MySteps extends BaseTest {
    
    private MyPage myPage = initPage(MyPage.class);
    
    @When("I click the element")
    public void iClickElement() {
        myPage.clickElement();
        ReportManager.recordStep("Element clicked successfully");
    }
    
    @Then("I should see the text")
    public void iShouldSeeText() {
        String text = myPage.getElementText();
        ReportManager.recordCustomData("Element Text", text);
    }
}
```

### Use Test Hooks
Hooks are automatic! Just ensure TestHooks.java is in your project.

To customize:
```java
@Before(order = 1)
public void beforeScenario(Scenario scenario) {
    // Your setup code
}

@After(order = 1)
public void afterScenario(Scenario scenario) {
    // Your cleanup code
}
```

## Maven Commands

```bash
# Compile all classes
mvn clean compile

# Compile test classes
mvn clean test-compile

# Run tests
mvn clean verify

# Run specific tag
mvn clean verify -Dcucumber.filter.tags="@MyTag"

# Run with properties
mvn clean verify -Dwebdriver.driver=firefox -Dheadless.mode=true

# Generate report only
mvn serenity:aggregate

# Clean everything
mvn clean
```

## Hook Execution Order

1. `@Before(order=1)` - beforeScenario() - Initialize context
2. `@Before(order=2)` - initializeDriver() - Setup driver
3. **Scenario Steps Execute**
   - `@BeforeStep` - before each step
   - Step execution
   - `@AfterStep` - after each step (screenshots)
4. `@After(order=1)` - afterScenario() - Handle results
5. `@After(order=2)` - cleanupDriver() - Cleanup

## Troubleshooting

| Issue | Solution |
|-------|----------|
| Compilation error | `mvn clean compile` |
| Tests not running | Check glue in TestRunner |
| No screenshots | Check serenity.properties |
| Driver not starting | Check webdriver.driver property |
| Reports not generating | Ensure hooks are executing |

## Best Practices

1. ✅ Always extend BasePage for page objects
2. ✅ Use ReportManager for all logging
3. ✅ Use descriptive screenshot names
4. ✅ Log important steps
5. ✅ Use Serenity session for page objects
6. ✅ Let hooks handle driver lifecycle
7. ✅ Record custom data for debugging
8. ✅ Use meaningful test tags

## File Locations

- **Page Objects**: `src/main/java/com/serenity/pages/`
- **Step Definitions**: `src/main/java/com/serenity/stepdefinition/`
- **Features**: `src/test/resources/features/`
- **Test Data**: `src/test/resources/data/`
- **Config**: Root `serenity.properties`
- **Reports**: `target/site/serenity/`

## Need Help?

- 📖 Full docs: `FRAMEWORK_ARCHITECTURE.md`
- 📝 Summary: `REFACTORING_SUMMARY.md`
- 🌐 Serenity: https://serenity-bdd.info
- 🥒 Cucumber: https://cucumber.io/docs

