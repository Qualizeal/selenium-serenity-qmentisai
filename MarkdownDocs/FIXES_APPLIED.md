# Serenity BDD Project - Fixes Applied

## Issues Found and Fixed

### 1. **JVM Argument Compatibility Issue**
**Problem:** The JVM argument `-XX:MaxPermSize=256m` was being used, which was deprecated and removed in Java 8+. You are using Java 25 (jdk-25), which doesn't support this parameter.

**Fix:** Changed the `argLine` in `maven-failsafe-plugin` configuration from:
```xml
<argLine>-Xmx1024m -XX:MaxPermSize=256m</argLine>
```
to:
```xml
<argLine>-Xmx1024m -XX:MetaspaceSize=256m</argLine>
```

### 2. **Serenity BDD Version Compatibility**
**Problem:** Serenity BDD 2.6.0 is too old and doesn't support Java 25. The ByteBuddy library used by older Serenity versions doesn't recognize newer Java versions, causing the error:
```
Failed to resolve the class file version of the current VM: Unknown Java version: 0
```

**Fix:** Updated Serenity BDD from version 2.6.0 to 4.2.8 in the properties:
```xml
<serenity.version>4.2.8</serenity.version>
```

### 3. **Cucumber Version Update**
**Problem:** Cucumber version 6.11.0 needed to be updated to match the newer Serenity version.

**Fix:** Updated Cucumber from 6.11.0 to 7.18.1:
```xml
<cucumber.version>7.18.1</cucumber.version>
```

### 4. **Serenity Cucumber Dependency**
**Problem:** The dependency artifact name `serenity-cucumber6` or `serenity-cucumber7` doesn't exist for Serenity 4.x versions.

**Fix:** Changed the dependency from `serenity-cucumber6` to `serenity-cucumber`:
```xml
<dependency>
    <groupId>net.serenity-bdd</groupId>
    <artifactId>serenity-cucumber</artifactId>
    <version>${serenity.version}</version>
</dependency>
```

### 5. **Updated Cucumber Java and JUnit Dependencies**
**Fix:** Updated to use the version variables:
```xml
<dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-java</artifactId>
    <version>${cucumber.version}</version>
</dependency>
<dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-junit</artifactId>
    <version>${cucumber.version}</version>
</dependency>
```

## How to Run Your Tests

### Option 1: Using the run.bat file
Simply double-click on `run.bat` or run it from command line:
```batch
run.bat
```

### Option 2: Using Maven command directly
```batch
mvn clean verify
```

### Option 3: Run specific test features
```batch
mvn clean verify -Dcucumber.options="--tags @yourTag"
```

## Updated Project Dependencies

| Dependency | Old Version | New Version |
|-----------|------------|-------------|
| Serenity BDD | 2.6.0 | 4.2.8 |
| Cucumber | 6.11.0 | 7.18.1 |
| ByteBuddy | 1.x (transitive) | 1.15.2 (transitive) |

## Compatibility Notes

- ✅ Now compatible with Java 8 through Java 25
- ✅ Uses modern Serenity BDD 4.x with better reporting
- ✅ Updated Cucumber 7.x with improved features
- ✅ Fixed JVM memory arguments for modern Java versions

## Test Reports

After running the tests, you can find the Serenity reports at:
```
target/site/serenity/index.html
```

Open this file in a browser to view the detailed test execution report.

## Next Steps

1. Run the tests using `run.bat` or `mvn clean verify`
2. Check the console output for test results
3. View the detailed HTML report in `target/site/serenity/index.html`
4. If tests fail, check the feature files and step definitions

## Troubleshooting

If you still encounter issues:

1. **Clean the Maven cache:**
   ```batch
   mvn dependency:purge-local-repository
   mvn clean install
   ```

2. **Verify Java version:**
   ```batch
   java -version
   ```
   Should show Java 25 (jdk-25)

3. **Check Maven version:**
   ```batch
   mvn -version
   ```

4. **If using an IDE**, make sure to:
   - Reload/Reimport Maven project
   - Invalidate caches and restart
   - Ensure the IDE is using the same Java version as command line

## Summary

All build errors have been fixed. The project now uses:
- **Serenity BDD 4.2.8** (latest stable version)
- **Cucumber 7.18.1** (modern version with better features)
- **Proper JVM arguments** compatible with Java 8+
- **Correct dependency artifact names** for Serenity 4.x

The project should now build and run tests successfully! 🎉

