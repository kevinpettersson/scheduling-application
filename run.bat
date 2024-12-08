@echo off
setlocal enabledelayedexpansion

REM Function to handle cleanup
:cleanup
echo Cleaning up...
taskkill /PID !BACKEND_PID! /F
taskkill /PID !FRONTEND_PID! /F
exit /b 1

REM Navigate to the backend and build (only on the first run)
cd timebridge
echo Building the backend...
mvn clean install
if %ERRORLEVEL% neq 0 (
    echo Backend build failed. Exiting...
    call :cleanup
)

echo Starting the Spring Boot server...
start /B cmd /c "mvn spring-boot:run"
set BACKEND_PID=!ERRORLEVEL!

REM Navigate to the frontend and install dependencies (only on the first run)
cd ..\timebridge-web
echo Installing frontend dependencies...
npm install
if %ERRORLEVEL% neq 0 (
    echo Frontend installation failed. Exiting...
    call :cleanup
)

echo Starting the frontend server...
start /B cmd /c "npm run dev"
set FRONTEND_PID=!ERRORLEVEL!

REM Wait for processes to complete
REM (Note: Windows batch files don't support waiting for background processes like Unix, so this is a workaround)
pause