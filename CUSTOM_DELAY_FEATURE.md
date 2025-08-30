# Custom Delay Feature for Bed Presence Sensor

## Overview
This new feature adds the ability to configure custom delays for occupation detection in the ESPHome bed sensor. You can now set independent delays for:
- **Occupied Delay**: Time before the sensor reports the bed as occupied
- **Clear Delay**: Time before the sensor reports the bed as clear (unoccupied)

## How It Works

### Configuration Options
Each sensor side (Left and Right) now has two new configuration entities:
- **Occupied Delay** (0-300 seconds / 0-5 minutes): Controls how long the sensor must detect pressure before reporting "occupied"
- **Clear Delay** (0-300 seconds / 0-5 minutes): Controls how long the sensor must detect no pressure before reporting "clear"

### Combined Sensors Support
The "Both" and "Either" combined sensors automatically inherit the custom delays:
- **Bed Occupied Both**: Uses custom delays when both sides need to be occupied
- **Bed Occupied Either**: Uses custom delays when either side needs to be occupied
- These combined sensors respect the Response Speed setting including "Custom" mode

### Response Speed Modes
The sensor supports four response speed modes:
1. **Fast**: Minimal delays (default: 0ms on/off)
2. **Normal**: Standard delays (default: 0s on, 1s off)
3. **Slow**: Longer delays (default: 2s on, 5s off)
4. **Custom**: Uses your configured Occupied/Clear delays

## Usage Instructions

### In Home Assistant

1. **Select Custom Mode**:
   - Find the "Response Speed" selector in your device configuration
   - Change it from "Normal" to "Custom"

2. **Configure Delays**:
   - Navigate to the device configuration
   - Find the delay settings for each side:
     - "Left Occupied Delay"
     - "Left Clear Delay"
     - "Right Occupied Delay"
     - "Right Clear Delay"
   - Set your desired values (in seconds, 0-300 range / 0-5 minutes)

3. **Test Your Configuration**:
   - Get in/out of bed to test the response times
   - Adjust delays as needed for your preferences

## Use Cases

### Preventing False Positives
- Set a longer **Occupied Delay** (e.g., 10-30 seconds) to avoid triggering when briefly sitting on the bed
- For extreme cases, use up to 60-120 seconds to ensure only sustained presence triggers

### Preventing False Negatives
- Set a longer **Clear Delay** (e.g., 30-60 seconds) to maintain occupied status when shifting positions during sleep
- For restless sleepers, consider 120-180 seconds (2-3 minutes) to prevent false clearing

### Quick Response for Automations
- Set short delays (e.g., 0.5s occupied, 1s clear) for responsive lighting automations

### Long-term Presence Detection
- Use extended delays (e.g., 240-300 seconds) for scenarios requiring confirmed long-term presence
- Useful for deep sleep detection or extended absence confirmation

### Partner Movement Isolation
- Use different delay settings for left/right sides based on movement patterns
- One partner can have 60s clear delay while the other has 180s based on sleep habits

## Technical Implementation

The feature adds:
1. A new "Custom" binary sensor for each side that uses the configurable delays
2. Number entities for setting the delay values (stored in flash memory)
3. Integration with the existing Response Speed selector
4. Dynamic delay filters using lambda expressions

## Default Values
- **Occupied Delay**: 0 seconds (immediate detection)
- **Clear Delay**: 1 second (brief delay before clearing)

These defaults provide a good starting point and can be adjusted based on your specific needs.

## Compatibility
This feature is compatible with:
- ESPHome 2024.6.0 or later
- All existing bed sensor calibration features
- Home Assistant automations and scripts
- The existing Fast/Normal/Slow response modes

## Troubleshooting

If the custom delays aren't working:
1. Ensure "Response Speed" is set to "Custom"
2. Check that delay values are within the 0-300 second range
3. Restart the ESP device after making changes
4. Verify the sensor is properly calibrated
5. For delays over 60 seconds, ensure your automations account for the longer response time

## Notes
- Delays are applied after the pressure threshold detection
- Each side (Left/Right) can have independent delay settings
- The "Either" and "Both" combined sensors automatically use the custom delays when Response Speed is set to "Custom"
- Combined sensors now support all four modes: Fast, Normal, Slow, and Custom
- Custom delays are preserved across device restarts