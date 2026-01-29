# Serenity BDD Test Automation Framework

[![Java](https://img.shields.io/badge/Java-11+-orange.svg)](https://www.oracle.com/java/)
[![Serenity BDD](https://img.shields.io/badge/Serenity%20BDD-4.2.8-blue.svg)](http://www.thucydides.info/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-red.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

## 📋 Table of Contents
- [Overview](#overview)
- [Framework Architecture](#framework-architecture)
- [Key Features](#key-features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running Tests](#running-tests)
- [Project Structure](#project-structure)
- [Writing Tests](#writing-tests)
- [Reporting](#reporting)
- [CI/CD Integration](#cicd-integration)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)

## 🎯 Overview

This is a comprehensive **Serenity BDD** test automation framework for the **Fi360 Cloud Platform**. It combines the power of **Selenium WebDriver**, **Cucumber BDD**, and **Serenity BDD reporting** to create maintainable, scalable, and readable test automation.

### What is Serenity BDD?

[Serenity BDD](http://www.thucydides.info/) is an open-source reporting library that helps you write better-structured acceptance criteria and produces rich, meaningful test reports. It seamlessly integrates with BDD tools like Cucumber and provides:

- **Living Documentation**: Automatically generated reports from your tests
- **Clear Test Results**: Detailed step-by-step execution with screenshots
- **Requirements Traceability**: Link tests to requirements and features
- **Parallel Execution**: Built-in support for concurrent test execution

## 🏗️ Framework Architecture

```
┌─────────────────────────────────────────────────────────┐
│                    Feature Files                         │
│              (Cucumber BDD Scenarios)                    │
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│              Step Definitions Layer                      │
│    (Business Logic - LoginStepDefinitions)              │
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│                  Page Objects Layer                      │
│         (UI Interactions - LoginPage)                    │
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│               Base Components Layer                      │
│  (BasePage, DriverManager, Utilities, Exceptions)       │
└─────────────────────────────────────────────────────────┘
```

### Design Patterns Used

- **Page Object Model (POM)**: Separates page structure from test logic
- **Singleton Pattern**: For driver and configuration management
- **Factory Pattern**: For driver creation
- **Builder Pattern**: For test data creation (future enhancement)

## ✨ Key Features

### 1. **BDD with Cucumber**
- Gherkin syntax for readable test scenarios
- Business stakeholder-friendly feature files
- Reusable step definitions

### 2. **Robust Waiting Strategies**
- No hard-coded `Thread.sleep()`
- Explicit waits with proper timeout handling
- Dynamic element waiting with Serenity's `WebElementFacade`

### 3. **Comprehensive Assertions**
- **AssertJ** for fluent, readable assertions
- Custom assertion messages for better failure diagnosis
- Positive validations (checking what SHOULD be there)

### 4. **Security Best Practices**
- No hardcoded credentials in code
- Environment variable support via `.env` files
- Sensitive data excluded from version control

### 5. **Cross-Platform Support**
- Platform-independent path handling
- Support for Windows, macOS, and Linux
- Docker-ready configuration

### 6. **Parallel Execution**
- Configurable parallel test execution
- Thread-safe design with `ThreadLocal`
- Optimized for CI/CD pipelines

### 7. **Rich Reporting**
- Serenity's beautiful HTML reports
- Step-by-step screenshots
- Custom metadata and test context
- Requirements traceability

### 8. **Exception Handling**
- Custom exception hierarchy
- Framework-specific exceptions
- Detailed error messages with context

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

| Tool | Version | Download Link |
|------|---------|---------------|
| **Java JDK** | 11 or higher | [Download](https://www.oracle.com/java/technologies/downloads/) |
| **Apache Maven** | 3.6+ | [Download](https://maven.apache.org/download.cgi) |
| **Git** | Latest | [Download](https://git-scm.com/downloads) |
| **Chrome Browser** | Latest | [Download](https://www.google.com/chrome/) |

### Verify Installation

```bash
# Check Java
java -version

# Check Maven
mvn -version

# Check Git
git --version
```

## 🚀 Installation

### 1. Clone the Repository

```bash
git clone <repository-url>
cd BR_POC
```

### 2. Install Dependencies

```bash
mvn clean install -DskipTests
```

### 3. Configure Environment Variables

Create a `.env` file in the project root directory:

```bash
cp .env.example .env
```

Edit the `.env` file with your actual credentials:

```properties
# Application Configuration
TEST_BASE_URL=https://app.fi360test.com/app/security/login
TEST_ENVIRONMENT=QA
APP_VERSION=17.3.2.1

# Test Credentials (DO NOT COMMIT)
TEST_USERNAME=your_username@test.com
TEST_PASSWORD=your_secure_password

# Browser Configuration
BROWSER_TYPE=chrome
HEADLESS_MODE=false

# Timeout Configuration (milliseconds)
IMPLICIT_WAIT_TIMEOUT=20000
EXPLICIT_WAIT_TIMEOUT=30000
PAGE_LOAD_TIMEOUT=60000

# Parallel Execution
FORK_COUNT=4

# Reporting
REPORT_USER=Your Name
```

**⚠️ IMPORTANT**: Never commit the `.env` file to version control!

## ⚙️ Configuration

### Browser Configuration

The framework supports multiple browsers:

- **Chrome** (default)
- **Firefox**
- **Edge**

Set the browser in `.env`:
```properties
BROWSER_TYPE=chrome
```

### Headless Mode

For CI/CD or background execution:
```properties
HEADLESS_MODE=true
```

### Timeout Configuration

Adjust timeouts based on your application:
```properties
IMPLICIT_WAIT_TIMEOUT=20000      # 20 seconds
EXPLICIT_WAIT_TIMEOUT=30000      # 30 seconds
PAGE_LOAD_TIMEOUT=60000          # 60 seconds
```

### Parallel Execution

Configure the number of parallel threads:
```properties
FORK_COUNT=4  # Run 4 tests in parallel
```

## 🏃 Running Tests

### Run All Tests

```bash
mvn clean verify
```

### Run with Specific Tags

```bash
# Run smoke tests only
mvn clean verify -Dcucumber.filter.tags="@Smoke"

# Run login tests
mvn clean verify -Dcucumber.filter.tags="@Login"

# Run critical tests
mvn clean verify -Dcucumber.filter.tags="@Critical"

# Run positive tests only
mvn clean verify -Dcucumber.filter.tags="@Positive"

# Exclude negative tests
mvn clean verify -Dcucumber.filter.tags="not @Negative"

# Combine tags (AND)
mvn clean verify -Dcucumber.filter.tags="@Login and @Smoke"

# Combine tags (OR)
mvn clean verify -Dcucumber.filter.tags="@Login or @Critical"
```

### Run in Headless Mode

```bash
mvn clean verify -Dheadless.mode=true
```

### Run with Parallel Execution

```bash
mvn clean verify -Dfork.count=4
```

### Generate Reports Only

```bash
mvn serenity:aggregate
```

## 📁 Project Structure

```
BR_POC/
├── .github/
│   └── workflows/
│       └── main.yml                 # CI/CD pipeline configuration
├── src/
│   ├── main/java/com/serenity/
│   │   ├── base/
│   │   │   ├── BasePage.java        # Base page with common methods
│   │   │   └── BaseTest.java        # Base test setup
│   │   ├── driver/
│   │   │   ├── DriverFactory.java   # Driver creation factory
│   │   │   └── DriverManager.java   # WebDriver lifecycle management
│   │   ├── exceptions/
│   │   │   ├── FrameworkException.java
│   │   │   ├── DriverException.java
│   │   │   ├── PageException.java
│   │   │   ├── ElementException.java
│   │   │   ├── ConfigurationException.java
│   │   │   └── AssertionException.java
│   │   ├── hooks/
│   │   │   └── TestHooks.java       # Before/After hooks
│   │   ├── pages/
│   │   │   └── LoginPage.java       # Page object for login
│   │   ├── reporting/
│   │   │   └── ReportManager.java   # Custom report utilities
│   │   ├── utilities/
│   │   │   ├── SerenityConfigReader.java  # Config reader
│   │   │   ├── EnvironmentLoader.java     # .env file loader
│   │   │   └── JsonReader.java            # JSON data reader
│   │   └── model/
│   │       └── CreateAccount.java   # Test data models
│   └── test/
│       ├── java/com/serenity/test/
│       │   └── TestRunner.java      # Cucumber test runner
│       ├── stepdefinition/
│       │   └── LoginStepDefinitions.java  # Cucumber step defs
│       └── resources/
│           ├── features/             # Cucumber feature files
│           │   ├── Login.feature
│           │   ├── ICSMFRS360-60345_*.feature
│           │   └── SmokeTests.feature
│           ├── data/                 # Test data JSON files
│           │   ├── testData.json
│           │   ├── templateData.json
│           │   └── createAccount.json
│           └── logback-test.xml      # Logging configuration
├── .env.example                      # Environment template
├── .gitignore                        # Git ignore rules
├── pom.xml                           # Maven configuration
├── serenity.properties               # Serenity configuration
└── README.md                         # This file
```

## 📝 Writing Tests

### 1. Create a Feature File

Create a new `.feature` file in `src/test/resources/features/`:

```gherkin
@MyFeature @Regression
Feature: My New Feature
  As a user
  I want to perform some action
  So that I can achieve a goal

  @Smoke @Critical
  Scenario: Successful action
    Given I am on the home page
    When I perform an action
    Then I should see the result
```

### 2. Create a Page Object

Create a new page class in `src/main/java/com/serenity/pages/`:

```java
package com.serenity.pages;

import com.serenity.base.BasePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

public class MyPage extends BasePage {

    @FindBy(id = "myElement")
    private WebElementFacade myElement;

    public void clickMyElement() {
        clickElement(myElement);
        logStep("Clicked my element");
    }

    public boolean isMyElementVisible() {
        return isElementDisplayed(myElement);
    }
}
```

### 3. Create Step Definitions

Create step definitions in `src/test/stepdefinition/`:

```java
package com.serenity.stepdefinition;

import com.serenity.pages.MyPage;
import io.cucumber.java.en.*;
import net.serenitybdd.core.Serenity;

import static org.assertj.core.api.Assertions.*;

public class MyStepDefinitions {

    private MyPage getMyPage() {
        MyPage page = Serenity.sessionVariableCalled("myPage");
        if (page == null) {
            page = new MyPage();
            page.setDriver(Serenity.getDriver());
            Serenity.setSessionVariable("myPage").to(page);
        }
        return page;
    }

    @Given("I am on the home page")
    public void iAmOnTheHomePage() {
        getMyPage().open();
        assertThat(getMyPage().isMyElementVisible())
            .as("My element should be visible")
            .isTrue();
    }
}
```

## 📊 Reporting

### View Serenity Reports

After test execution, reports are generated in:
```
target/site/serenity/index.html
```

Open in browser:
```bash
# Windows
start target/site/serenity/index.html

# macOS
open target/site/serenity/index.html

# Linux
xdg-open target/site/serenity/index.html
```

### Report Features

- **Test Results Dashboard**: Overview of pass/fail/pending tests
- **Feature Coverage**: Requirements mapped to tests
- **Step-by-Step Execution**: Detailed steps with screenshots
- **Execution Timeline**: Test duration and performance metrics
- **Failure Analysis**: Stack traces and error details
- **Custom Metadata**: Environment, browser, version info

## 🔄 CI/CD Integration

### GitHub Actions

The framework includes a pre-configured GitHub Actions workflow (`.github/workflows/main.yml`).

#### Trigger Options:
- **Push to main/master**: Automatic execution
- **Pull Request**: Test validation
- **Manual Trigger**: Via GitHub UI
- **Scheduled**: Nightly runs

### Jenkins Integration

```groovy
pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git 'repository-url'
            }
        }
        stage('Run Tests') {
            steps {
                sh 'mvn clean verify'
            }
        }
        stage('Publish Reports') {
            steps {
                publishHTML([
                    reportDir: 'target/site/serenity',
                    reportFiles: 'index.html',
                    reportName: 'Serenity Report'
                ])
            }
        }
    }
}
```

## 🐛 Troubleshooting

### Common Issues

#### 1. WebDriver Not Found

**Error**: `WebDriver executable not found`

**Solution**: The framework uses WebDriverManager to auto-download drivers. Ensure you have internet access.

#### 2. Tests Failing Due to Timeouts

**Error**: `TimeoutException: Element not visible`

**Solution**: 
- Increase timeouts in `.env` file
- Check if application is slow
- Verify element locators

#### 3. Environment Variables Not Loading

**Error**: `ConfigurationException: Environment variable not found`

**Solution**:
- Verify `.env` file exists
- Check variable names match exactly
- Ensure no trailing spaces

#### 4. Parallel Execution Issues

**Error**: Tests interfering with each other

**Solution**:
- Reduce `FORK_COUNT` in `.env`
- Ensure test data isolation
- Use unique test accounts

### Debug Mode

Enable verbose logging in `serenity.properties`:
```properties
serenity.logging = VERBOSE
```

## 🤝 Contributing

### Coding Standards

1. **Follow Java naming conventions**
2. **Add JavaDoc comments** to all public methods
3. **Write meaningful commit messages**
4. **Include unit tests** for utilities
5. **Update documentation** for new features

### Pull Request Process

1. Create a feature branch: `git checkout -b feature/my-feature`
2. Make your changes
3. Run tests: `mvn clean verify`
4. Commit changes: `git commit -m "Add my feature"`
5. Push to branch: `git push origin feature/my-feature`
6. Create Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📧 Contact

For questions or support, please contact:
- **Project Lead**: [Your Name]
- **Email**: [your.email@company.com]
- **Slack**: #test-automation

---

## 🏆 Best Practices

### ✅ DO
- Use AssertJ for assertions
- Use explicit waits, not Thread.sleep()
- Keep credentials in .env file
- Write descriptive test names
- Add screenshots for failures
- Use tags to organize tests

### ❌ DON'T
- Hardcode test data in code
- Commit credentials to Git
- Use Thread.sleep()
- Create dependencies between tests
- Skip proper exception handling

---

**Happy Testing! 🚀**
