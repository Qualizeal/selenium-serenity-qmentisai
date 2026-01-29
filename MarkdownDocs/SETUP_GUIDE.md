# Environment Setup Guide

## Setting Up Your Test Environment

### Step 1: Create Your .env File

1. Copy the example file:
   ```bash
   cp .env.example .env
   ```

2. Open `.env` in your text editor

3. Update the following values:

### Step 2: Application Configuration

```properties
# Your test environment URL
TEST_BASE_URL=https://app.fi360test.com/app/security/login

# Environment name
TEST_ENVIRONMENT=QA

# Application version being tested
APP_VERSION=17.3.2.1
```

### Step 3: Test Credentials

**⚠️ SECURITY IMPORTANT:**
- DO NOT use production credentials
- DO NOT commit this file to Git
- Use test-specific accounts only

```properties
# Your test account credentials
TEST_USERNAME=your_test_email@example.com
TEST_PASSWORD=your_secure_test_password
```

### Step 4: Browser Settings

```properties
# Browser: chrome, firefox, or edge
BROWSER_TYPE=chrome

# Headless mode: true for CI/CD, false for local debugging
HEADLESS_MODE=false
```

### Step 5: Performance Tuning

```properties
# Adjust these based on your application performance
# Values are in milliseconds

# How long to wait for elements to appear
IMPLICIT_WAIT_TIMEOUT=20000

# Maximum wait for explicit waits
EXPLICIT_WAIT_TIMEOUT=30000

# Page load timeout
PAGE_LOAD_TIMEOUT=60000
```

### Step 6: Parallel Execution

```properties
# Number of tests to run in parallel
# Recommended: Number of CPU cores / 2
# Example: 4-core machine = 2 parallel threads
# Example: 8-core machine = 4 parallel threads
FORK_COUNT=4

# Enable parallel execution
PARALLEL_EXECUTION=true
```

### Step 7: Reporting

```properties
# Your name for test reports
REPORT_USER=John Doe
```

---

## Verification

Test your configuration:

```bash
# This should run without errors
mvn clean verify -Dcucumber.filter.tags="@Smoke"
```

---

## Alternative: System Environment Variables

Instead of `.env` file, you can set system environment variables:

### Windows
```cmd
set TEST_USERNAME=your_username@test.com
set TEST_PASSWORD=your_password
mvn clean verify
```

### macOS/Linux
```bash
export TEST_USERNAME="your_username@test.com"
export TEST_PASSWORD="your_password"
mvn clean verify
```

---

## CI/CD Configuration

For GitHub Actions, Jenkins, or other CI/CD:

1. Set environment variables in your CI/CD tool's secrets/variables section
2. DO NOT put credentials in workflow files
3. Use the tool's built-in secret management

### GitHub Actions Example
```yaml
env:
  TEST_USERNAME: ${{ secrets.TEST_USERNAME }}
  TEST_PASSWORD: ${{ secrets.TEST_PASSWORD }}
```

---

## Troubleshooting

### "Environment variable not found" error

**Solution:** Verify your `.env` file exists and contains all required variables

### Tests using wrong credentials

**Solution:** Check that `.env` is in the project root directory (same level as `pom.xml`)

### Credentials not loading

**Solution:** Restart your IDE or terminal after creating `.env` file

---

## Security Best Practices

✅ **DO:**
- Use test-specific accounts
- Store credentials in `.env` locally
- Use CI/CD secret management for pipelines
- Rotate test passwords regularly
- Use environment variables

❌ **DON'T:**
- Commit `.env` to Git
- Use production accounts
- Share credentials in chat/email
- Hardcode credentials in code
- Store passwords in feature files

---

## Need Help?

Contact the test automation team or see the main README.md
