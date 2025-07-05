@echo off
echo CarryOn Mod Testing Helper
echo ========================

set SOURCE_DIR=%~dp0NeoForge\build\libs
set MOD_FILE=carryon-neoforge-1.21.5-2.3.0.jar

echo.
echo Checking if mod file exists...
if not exist "%SOURCE_DIR%\%MOD_FILE%" (
    echo ERROR: Mod file not found at %SOURCE_DIR%\%MOD_FILE%
    echo Please run gradlew build first
    pause
    exit /b 1
)

echo Found mod file: %SOURCE_DIR%\%MOD_FILE%
echo.

echo Please provide the path to your Minecraft mods folder:
echo Example: C:\Users\YourName\AppData\Roaming\.minecraft\mods
echo.
set /p MODS_DIR="Mods folder path: "

if not exist "%MODS_DIR%" (
    echo ERROR: Mods directory does not exist: %MODS_DIR%
    pause
    exit /b 1
)

echo.
echo Copying mod file...
copy "%SOURCE_DIR%\%MOD_FILE%" "%MODS_DIR%\" >nul

if %ERRORLEVEL% EQU 0 (
    echo SUCCESS: Mod copied to %MODS_DIR%\%MOD_FILE%
    echo.
    echo Test Steps:
    echo 1. Remove ALL other mods from the mods folder
    echo 2. Launch Minecraft with NeoForge 21.5.81
    echo 3. Create a new world
    echo 4. Test CarryOn functionality
) else (
    echo ERROR: Failed to copy mod file
)

echo.
pause
