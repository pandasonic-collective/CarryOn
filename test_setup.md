# CarryOn Mod Test Setup Guide

## Minimal Test Environment

### Prerequisites
- Minecraft 1.21.5
- NeoForge 21.5.81
- Java 21

### Step-by-Step Test Process

1. **Create Fresh Minecraft Instance**
   - Create a new launcher profile/instance
   - Use Minecraft 1.21.5
   - Install NeoForge 21.5.81
   - DO NOT copy any existing configs/saves

2. **Install Only CarryOn**
   - Use the freshly built JAR: `NeoForge/build/libs/carryon-neoforge-1.21.5-2.3.0.jar`
   - Place it in the mods folder
   - No other mods initially

3. **Test Launch**
   - Launch the game
   - Create a new world
   - Test basic CarryOn functionality

4. **If Issues Persist**
   - Remove CarryOn mod
   - Launch with just NeoForge 21.5.81
   - If still crashing, the issue is with your NeoForge/Minecraft setup

### Expected Behavior
- Game should launch successfully
- You should be able to create worlds
- CarryOn functionality should work (pick up blocks/entities)

### If Registry Errors Persist
This indicates a problem with:
- Your Minecraft installation
- NeoForge installation
- Java version compatibility
- System-level issues

### Troubleshooting Registry Errors
1. Delete .minecraft folder completely and reinstall
2. Verify Java 21 is being used
3. Check for antivirus interference
4. Try different launcher (MultiMC, Prism, etc.)
5. Check system logs for hardware/memory issues

## File Locations

### Built Mod Files
- NeoForge: `H:\_Github\CarryOn\NeoForge\build\libs\carryon-neoforge-1.21.5-2.3.0.jar`
- Forge: `H:\_Github\CarryOn\Forge\build\libs\carryon-forge-1.21.5-2.3.0-all.jar`
- Fabric: `H:\_Github\CarryOn\Fabric\build\libs\carryon-fabric-1.21.5-2.3.0.jar`
