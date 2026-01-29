# Quick Setup Guide

## ✅ Framework Successfully Refactored!

Your framework now supports **multiple environments** with **clean architecture** for both local and pipeline execution.

---

## 🚀 Quick Start (Local Development)

###  1. Create Your Environment File

```bash
# For QA environment (default)
copy .env.example .env.qa

# For DEV environment
copy .env.example .env.dev
```

### 2. Configure Your Credentials

Edit `.env.qa` with your actual credentials:

```properties
TEST_BASE_URL=https://app.fi360test.com/app/security/login
TEST_USERNAME=your_username@test.com
TEST_PASSWORD=YourActualPassword!
BROWSER_TYPE=chrome
FORK_COUNT=1
```

### 3. Run Tests

```bash
mvn clean verify
```

That's it! The framework will automatically:
- Read `test.environment = qa` from `serenity.properties`
- Load credentials from `.env.qa`
- Execute your tests

---

## 🔄 Switching Environments

### Switch to DEV

1. Edit `serenity.properties`:
   ```properties
   test.environment = dev
   ```

2. Ensure `.env.dev` exists with DEV credentials

3. Run tests:
   ```bash
   mvn clean verify
   ```

### Switch to QA

1. Edit `serenity.properties`:
   ```properties
   test.environment = qa
   ```

2. Ensure `.env.qa` exists with QA credentials

3. Run tests:
   ```bash
   mvn clean verify
   ```

---

## 🔐 CI/CD Pipeline Setup

### GitHub Actions Example

```yaml
name: Serenity Tests

on: [push]

jobs:
  test:
    runs-on: ubuntu-latest
    
    steps:
      - uses: actions/checkout@v3
      
      - name: Setup Java
        uses: actions/setup-java@v3
        with:
          java-version: '11'
          
      - name: Run Tests
        env:
          TEST_BASE_URL: ${{ secrets.QA_BASE_URL }}
          TEST_USERNAME: ${{ secrets.QA_USERNAME }}
          TEST_PASSWORD: ${{ secrets.QA_PASSWORD }}
        run: mvn clean verify
```

### Jenkins Pipeline Example

```groovy
pipeline {
    agent any
    
    environment {
        TEST_BASE_URL = credentials('qa-base-url')
        TEST_USERNAME = credentials('qa-username')
        TEST_PASSWORD = credentials('qa-password')
    }
    
    stages {
        stage('Test') {
            steps {
                sh 'mvn clean verify'
            }
        }
    }
}
```

The framework **automatically detects** pipeline execution and uses system environment variables instead of `.env` files!

---

## 📊 What Changed?

| Feature | Before | After |
|---------|--------|-------|
| Environments | Manual .env editing | Separate .env.dev, .env.qa files |
| Switching | Change .env contents | Change one line in serenity.properties |
| Pipeline | Manual configuration | Automatic detection + secrets |
| Code Duplication | ~80 lines duplicate | ✅ Zero duplication |
| Maintainability | Hard to manage | Easy and clean |

---

## ✨ New Features

1. **Environment Selection** - One-line switch in `serenity.properties`
2. **Automatic Detection** - Framework knows if running locally or in pipeline
3. **Priority System** - System env vars (pipeline) override .env files (local)
4. **Clean Architecture** - No duplicate code, single responsibility
5. **Security** - All credential files gitignored

---

## 📁 Files Created/Modified

### New Files:
- `.env.dev` - DEV environment credentials (gitignored)
- `.env.qa` - QA environment credentials (gitignored)
- `.env.example` - Template for new environments
- `README.md` - Complete documentation
- `MarkdownDocs/REFACTORING_2025_SUMMARY.md` - Technical details

### Modified Files:
- `EnvironmentLoader.java` - Unified, powerful loader
- `TestRunner.java` - Simplified initialization
- `TestHooks.java` - Removed duplication
- `serenity.properties` - Added environment selection
- `.gitignore` - Updated to exclude env files

---

## 🎯 Current Configuration

Your framework is configured to:
- **Environment**: `qa` (from serenity.properties)
- **Config Source**: `.env.qa` file (local) or system env vars (pipeline)
- **Browser**: `chrome` (default in serenity.properties)

---

## 🔍 Verification

To verify your setup is correct:

```bash
# 1. Check your environment files exist
dir .env.*

# 2. Verify serenity.properties has correct environment
findstr "test.environment" serenity.properties

# 3. Run a quick compile
mvn clean compile
```

---

## ❓ Troubleshooting

### "Environment variables not found"
**Fix**: Ensure `.env.qa` exists and has correct variable names (`TEST_BASE_URL`, `TEST_USERNAME`, `TEST_PASSWORD`)

### "Unsupported browser type"
**Fix**: Check that your `.env.qa` has `BROWSER_TYPE=chrome`

### "Invalid URL"
**Fix**: Verify `TEST_BASE_URL` in your `.env.qa` is a valid URL starting with `https://`

---

## 📚 Documentation

For detailed information, see:
- `README.md` - Full setup and usage guide
- `MarkdownDocs/REFACTORING_2025_SUMMARY.md` - Technical refactoring details
- `MarkdownDocs/FRAMEWORK_ARCHITECTURE.md` - Architecture overview

---

## 🎉 You're Ready!

Your framework is now production-ready with:
- ✅ Multi-environment support
- ✅ Clean, maintainable code
- ✅ Pipeline integration
- ✅ Secure credential management

Just create your `.env.qa` file with actual credentials and run `mvn clean verify`!
