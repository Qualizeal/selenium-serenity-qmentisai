@echo off
REM Framework Verification Script for Windows
REM Run this to verify your setup is correct

echo ================================================
echo    Serenity BDD Framework Setup Verification
echo ================================================
echo.

REM Check Java
echo [1/5] Checking Java installation...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Java is not installed or not in PATH
    echo Please install Java 11 or higher
    goto :error
) else (
    java -version
    echo [OK] Java found
)
echo.

REM Check Maven
echo [2/5] Checking Maven installation...
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Maven is not installed or not in PATH
    echo Please install Maven 3.6 or higher
    goto :error
) else (
    mvn -version
    echo [OK] Maven found
)
echo.

REM Check .env file
echo [3/5] Checking .env file...
if exist .env (
    echo [OK] .env file exists
) else (
    echo [WARNING] .env file not found
    echo Creating from template...
    copy .env.example .env
    echo [ACTION REQUIRED] Please edit .env file with your credentials
    notepad .env
)
echo.

REM Check dependencies
echo [4/5] Installing dependencies...
mvn clean install -DskipTests
if %errorlevel% neq 0 (
    echo [ERROR] Maven build failed
    goto :error
) else (
    echo [OK] Dependencies installed
)
echo.

REM Run smoke test
echo [5/5] Running smoke test...
echo This will take a moment...
mvn clean verify -Dcucumber.filter.tags="@Smoke" -Dheadless.mode=true
if %errorlevel% neq 0 (
    echo [WARNING] Some tests may have failed
    echo Check the report: target\site\serenity\index.html
) else (
    echo [OK] Smoke tests passed!
)
echo.

REM Success
echo ================================================
echo    Setup Verification Complete!
echo ================================================
echo.
echo Next steps:
echo 1. Edit .env file with your credentials (if not done)
echo 2. Run: mvn clean verify
echo 3. View report: target\site\serenity\index.html
echo.
echo Documentation:
echo - Quick Start: QUICKSTART.md
echo - Setup Guide: SETUP_GUIDE.md
echo - Full Docs: README.md
echo.
pause
exit /b 0

:error
echo.
echo ================================================
echo    Setup Verification Failed
echo ================================================
echo.
echo Please fix the errors above and run this script again.
echo.
pause
exit /b 1
