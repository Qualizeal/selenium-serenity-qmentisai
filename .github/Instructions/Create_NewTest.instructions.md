---
description: Generate test scripts from test scenario specifications using MCP Recording
temperature: 0.3
---

# Create Test Script Command (MCP Recording Workflow)

Generate a complete test script based on the provided test scenario specification. This command uses Playwright MCP to record UI interactions and transforms them into framework-compliant Serenity BDD code.

## Required Rules

Before generating code, analyze the codebase and apply these rules:

1. **@framework-overview** - Core framework conventions (always active)
2. **@mcp-workflow** - MCP Recording and Framework Integration Workflow
3. **@test-class-patterns** - Test class structure and patterns
4. **@page-object-patterns** - Page action class patterns
5. **@locator-conventions** - Locator naming and structure
6. **@test-data-management** - Test data handling
7. **@reporting-error-handling** - Reporting and exception patterns

## Input Format

Provide a brief scenario description (sentence or short paragraph; no table required). Example:
“Validate user can create a company without mandatory fields and capture error messages.”

Assistant responsibilities after receiving the scenario:
- Ensure Playwright MCP server is running (if not reachable, stop and request start).
- Load configuration from `src/test/resources/application.properties` (e.g., ApplicationURL, credentials) before navigation; if missing/invalid, stop and request correct values.
- Use only MCP browser tools (not the IDE browser) to explore the application from the scenario.
- Navigate using the application URL from configuration or user input; take an initial `browser_snapshot`.
- Discover the flow, record interactions via MCP tools (`browser_snapshot`, `browser_click`, `browser_type`, `browser_select_option`, etc.).
- Derive required data, locators, pages, and tests strictly from recorded interactions—do not invent selectors.
- If recording fails or no refs are captured, stop and report instead of generating code.

## MCP Recording Workflow

### Pre-flight (must pass before recording)

1. Confirm Playwright MCP server (`@playwright/mcp`) is installed and running per the repository README (do not proceed until user confirms).
2. Load configuration from `src/test/resources/application.properties` (e.g., ApplicationURL, credentials) before navigation; if missing/invalid, stop and request correct values.
3. Use only MCP browser tools (`mcp_cursor-ide-browser_browser_*`). If a non-MCP/browser-in-IDE window launches, stop and notify the user to start the Playwright MCP server.
3. Ensure the scenario includes a valid Application URL; if missing, request it before recording.
4. Take an initial `browser_snapshot` after navigation. If snapshot fails or returns no refs, stop and report instead of continuing.

### Phase 1: MCP Recording Session

#### Step 1.1: Navigate to Application

Use the Playwright MCP browser tools to navigate to the application:

```
browser_navigate to the Application URL provided in the scenario
```

#### Step 1.2: Capture Initial Snapshot

```
browser_snapshot to capture the accessibility tree and discover elements
```

#### Step 1.3: Execute Test Steps

For each test step in the scenario:

1. **Identify target element** from the snapshot (note the `ref` attribute)
2. **Execute the action** using appropriate MCP tool:
    - `browser_type` for text input
    - `browser_click` for button clicks
    - `browser_select_option` for dropdowns
    - `browser_hover` for hover actions
3. **Capture snapshot** after the action to verify state change
4. **Document** the selector/locator from the Playwright code output

#### Step 1.4: Record Verification Points

After key actions, capture snapshots and note:
- Success/error messages displayed
- Element states (visible, enabled, selected)
- Page transitions completed

#### Step 1.5: Close MCP Browser

- After completing the recording session, close the MCP browser instance to release resources.

### Phase 2: Analyze Existing Codebase

After recording, analyze the codebase:

1. **Discover existing page classes** in `src/main/java/.../pages/web/actions/`
2. **Discover existing locator classes** in `src/main/java/.../pages/web/locators/`
3. **Check existing utility methods** in base page and utility classes
4. **Identify reusable methods** that can be leveraged
5. **Superset guardrail:** If a method already handles a superset of the required fields/flow, reuse or update it (with optional/null-safe branches) instead of creating any new/narrower method. Stop and adjust the plan rather than adding a duplicate.
6. **Understand the package structure** from existing files

### Phase 3: Identify Required Components

Based on the MCP recording and test scenario:

1. **List pages involved** in the test flow
2. **List new page classes** needed (if any)
3. **List new locator classes** needed (if any)
4. **List new methods** needed in existing page classes
5. **List Excel columns** required for test data

### Phase 4: Generate Code

Generate in this order:

1. **New Locator Classes** (if needed)
    - Transform MCP selectors to Selenium `By` objects
    - Follow @locator-conventions rules
    - Add MCP source comments for traceability

2. **New Page Action Classes** (if needed)
    - Follow @page-object-patterns rules
    - Extend base page class
    - Map MCP actions to actionKeyword methods
    - Add MCP source comments for traceability

3. **New Methods in Existing Pages** (if needed)
    - Follow existing class patterns
    - Reuse existing utility methods
    - Maintain consistency with existing methods
    - When the same scenario is re-run with fewer steps/fields, update or trim the existing method instead of creating a new one; keep the original method name unless the workflow meaningfully changes.
    - Before adding a method, check if an implementation with the same responsibility already exists; if present, reuse/update it instead of adding a duplicate.
    - Prefer reusing an existing comprehensive method that handles all fields (with null/optional checks) rather than creating narrower variants when some data columns are omitted in the scenario or Excel; do not introduce new method names just to skip fields.
    - If an existing method already covers a superset of the fields in the scenario, reuse it even when only a subset is provided; do not generate a new method for the subset.

4. **Test Class**
    - Follow @test-class-patterns rules
    - Reference MCP-recorded step sequence
    - Include proper exception handling
    - Report to test management tool

5. **Test Data Requirements**
    - List required Excel columns
    - Specify sheet name
    - Provide sample data format

### Phase 5: Debugging with MCP (if needed)

If any issues arise during test execution:

1. Use `browser_navigate` to go to the failing step's URL
2. Use `browser_snapshot` to capture current page state
3. Compare recorded selectors with actual page elements
4. Update locators if page structure has changed
5. Re-record specific interactions if necessary

## Output Format

Post-run deliverable: only the Playwright MCP execution trace. Do not generate additional summary/markdown documents or ancillary reports.

### Section 1: MCP Recording Summary

```
MCP Recording Session:
- Application URL: <URL>
- Steps Recorded: <count>
- Elements Discovered: <count>

Recorded Interactions:
1. <Step description> - <MCP tool used> - <Element ref>
2. ...
```

### Section 2: Analysis Summary

```
Existing Pages Used: [list]
New Pages Required: [list or "None"]
New Locators Required: [list or "None"]
New Methods in Existing Pages: [list or "None"]
```

### Section 3: Generated Code

For each file, provide:
- Full file path
- Complete code with proper package and imports
- MCP source comments referencing the recorded selectors

### Section 4: Test Data Requirements

```
Excel Sheet: <SheetName>
Required Columns:
| Column Name | Description | Sample Value |
|-------------|-------------|--------------|
| TESTCASENAME | Test case ID | TC-05 |
| ... | ... | ... |
```

### Section 5: TestNG XML Entry

```xml
<class name="com.esta.testcases.<feature>.<ClassName>"/>
```

## Constraints

1. **STOP if MCP tools are unavailable** - If Playwright MCP server is not running or the browser tools are not reachable, pause and request the user to start the server; do not generate code.
2. **DO NOT generate code without MCP recording** - Always record first.
3. **DO NOT fabricate selectors** - Use only MCP-recorded selectors.
4. **Use only MCP browser tools** - Do not continue if a non-MCP/browser-in-IDE session opens; inform the user to restart with Playwright MCP.
5. **DO NOT hardcode test data** - Use Excel.
6. **DO NOT skip error handling** - Always wrap in try-catch.
7. **DO NOT use System.out** - Use SLF4J logger.
8. **DO NOT use emojis** anywhere in code or comments.
9. **DO NOT guess package names** - Discover from existing files.
10. **DO NOT create unnecessary abstractions** - Keep it simple.
11. **ALWAYS check existing classes** before creating new ones.
12. **ALWAYS follow naming conventions** from the rules.
13. **ALWAYS include screenshots** for pass/fail reporting.
14. **ALWAYS add MCP source comments** for traceability.
15. **After recording, close the MCP browser.**
16. **Do not generate summary/markdown documents after completion; keep only the Playwright MCP execution trace.**
17. **On repeat scenarios (even with fewer steps/fields), update existing methods/pages/tests instead of creating new ones; prefer reusing the same method names and removing unneeded steps.**
18. **Before adding any method to a class, verify if an equivalent implementation already exists; if it does, reuse/update rather than adding a duplicate.**
19. **Do not create narrower variant methods when test data omits fields—reuse the existing comprehensive method and rely on null/optional handling instead of adding new method names.**
20. **When a superset method exists (e.g., handles all fields), bind the scenario to that method even if only a subset of fields is supplied; do not create a new subset method.**

## MCP Tool Quick Reference

| Task | MCP Tool | Framework Method |
|------|----------|------------------|
| Navigate to URL | `browser_navigate` | Test setup / page navigation |
| Capture page state | `browser_snapshot` | Element discovery |
| Click button/link | `browser_click` | `actionKeyword.clickElement()` |
| Enter text | `browser_type` | `actionKeyword.clearAndInputText()` |
| Select dropdown | `browser_select_option` | `actionKeyword.selectItemByText()` |
| Hover element | `browser_hover` | `actionKeyword.hoverOverElement()` |
| Press key | `browser_press_key` | `actionKeyword.pressKey()` |
| Wait for element | `browser_wait_for` | `actionKeyword.waitUntilElementIsVisible()` |
| Take screenshot | `browser_take_screenshot` | `actionKeyword.takeScreenshotAndSave()` |

## Selector Transformation Reference

| MCP Playwright Selector | Selenium By Equivalent |
|-------------------------|------------------------|
| `getByRole('button', { name: 'X' })` | `By.xpath("//button[text()='X']")` |
| `getByRole('textbox', { name: 'X' })` | `By.xpath("//input[@name='X' or @placeholder='X']")` |
| `getByLabel('X')` | `By.xpath("//input[@id=(//label[text()='X']/@for)]")` |
| `locator('#id')` | `By.id("id")` |
| `locator('[name="x"]')` | `By.name("x")` |
| `locator('.class')` | `By.cssSelector(".class")` |
| `getByText('X')` | `By.xpath("//*[contains(text(),'X')]")` |

## Example Usage

User provides:

```
Test ID: TC-05
Scenario: User Login
Test Name: Verify user can login with valid credentials
Objective: Validate successful login flow
Precondition: User exists in the system
Script Name: TC5_ValidUserLogin
Application URL: https://app.example.com/login
Test Step:
1. Navigate to login page
2. Enter username
3. Enter password
4. Click login button
5. Verify dashboard is displayed
Test Data: USERNAME=testuser, PASSWORD=Test@123
Expected Result: User is logged in and dashboard is displayed
```

The command will:

1. **MCP Recording Phase**:
    - Navigate to https://app.example.com/login
    - Snapshot the login page
    - Record entering username (capture selector)
    - Record entering password (capture selector)
    - Record clicking login button (capture selector)
    - Snapshot dashboard to capture success elements

2. **Analysis Phase**:
    - Check if LoginPage exists, note if update needed
    - Check if LoginLoc exists, note if update needed

3. **Generation Phase**:
    - Generate/update LoginLoc with MCP selectors
    - Generate/update LoginPage with action methods
    - Generate TC5_ValidUserLogin test class
    - List required Excel columns

4. **Output Phase**:
    - Provide MCP recording summary
    - Provide all generated code with MCP comments
    - Provide test data requirements
    - Provide TestNG XML entry

---

**Now analyze the provided test scenario, execute MCP recording, and generate the test script following all rules and conventions.**
