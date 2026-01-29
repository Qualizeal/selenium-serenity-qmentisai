#!/bin/bash
# Framework Verification Script for macOS/Linux
# Run this to verify your setup is correct

echo "================================================"
echo "   Serenity BDD Framework Setup Verification"
echo "================================================"
echo ""

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Track errors
ERRORS=0

# Check Java
echo "[1/5] Checking Java installation..."
if command -v java &> /dev/null; then
    java -version
    echo -e "${GREEN}[OK] Java found${NC}"
else
    echo -e "${RED}[ERROR] Java is not installed or not in PATH${NC}"
    echo "Please install Java 11 or higher"
    ERRORS=$((ERRORS+1))
fi
echo ""

# Check Maven
echo "[2/5] Checking Maven installation..."
if command -v mvn &> /dev/null; then
    mvn -version
    echo -e "${GREEN}[OK] Maven found${NC}"
else
    echo -e "${RED}[ERROR] Maven is not installed or not in PATH${NC}"
    echo "Please install Maven 3.6 or higher"
    ERRORS=$((ERRORS+1))
fi
echo ""

# Check .env file
echo "[3/5] Checking .env file..."
if [ -f .env ]; then
    echo -e "${GREEN}[OK] .env file exists${NC}"
else
    echo -e "${YELLOW}[WARNING] .env file not found${NC}"
    echo "Creating from template..."
    cp .env.example .env
    echo -e "${YELLOW}[ACTION REQUIRED] Please edit .env file with your credentials${NC}"
    echo "Run: nano .env (or vim .env)"
fi
echo ""

# Check dependencies
echo "[4/5] Installing dependencies..."
mvn clean install -DskipTests
if [ $? -eq 0 ]; then
    echo -e "${GREEN}[OK] Dependencies installed${NC}"
else
    echo -e "${RED}[ERROR] Maven build failed${NC}"
    ERRORS=$((ERRORS+1))
fi
echo ""

# Run smoke test
echo "[5/5] Running smoke test..."
echo "This will take a moment..."
mvn clean verify -Dcucumber.filter.tags="@Smoke" -Dheadless.mode=true
if [ $? -eq 0 ]; then
    echo -e "${GREEN}[OK] Smoke tests passed!${NC}"
else
    echo -e "${YELLOW}[WARNING] Some tests may have failed${NC}"
    echo "Check the report: target/site/serenity/index.html"
fi
echo ""

# Summary
if [ $ERRORS -eq 0 ]; then
    echo "================================================"
    echo "   Setup Verification Complete!"
    echo "================================================"
    echo ""
    echo "Next steps:"
    echo "1. Edit .env file with your credentials (if not done)"
    echo "2. Run: mvn clean verify"
    echo "3. View report: open target/site/serenity/index.html"
    echo ""
    echo "Documentation:"
    echo "- Quick Start: QUICKSTART.md"
    echo "- Setup Guide: SETUP_GUIDE.md"
    echo "- Full Docs: README.md"
    echo ""
    exit 0
else
    echo "================================================"
    echo "   Setup Verification Failed"
    echo "================================================"
    echo ""
    echo "Please fix the errors above and run this script again."
    echo ""
    exit 1
fi
