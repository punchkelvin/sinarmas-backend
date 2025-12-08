@echo off
echo.
echo ==========================================
echo    INSURANCE MICROSERVICES LAUNCHER
echo ==========================================
echo.

echo  [Step 1/2] Building all Microservices...
echo ---------------------------------------------

call gradlew.bat clean build -x test

if %ERRORLEVEL% NEQ 0 (
  echo.
  echo BUILD FAILED! Fix the errors above before running Docker.
  exit /b %ERRORLEVEL%
)

echo.
echo Build Successful! JAR files created.
echo.
echo [Step 2/2] Starting Docker Containers...
echo ---------------------------------------------

docker-compose up --build