# Custom Delay Feature for Bed Presence Sensor

## Overview
This new feature adds the ability to configure custom delays for occupation detection in the ESPHome bed sensor. You can now set independent delays for:
- **Occupied Delay**: Time before the sensor reports the bed as occupied
- **Clear Delay**: Time before the sensor reports the bed as clear (unoccupied)

## How It Works

### Configuration Options
Each sensor side (Left and Right) now has two new configuration entities:
- **Occupied Delay** (0-30 seconds): Controls how long the sensor must detect pressure before reporting "occupied"
- **Clear Delay** (0-30 seconds): Controls how long the sensor must detect no pressure before reporting "clear"

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
   - Set your desired values (in seconds, 0-30 range)

3. **Test Your Configuration**:
   - Get in/out of bed to test the response times
   - Adjust delays as needed for your preferences

## Use Cases

### Preventing False Positives
- Set a longer **Occupied Delay** (e.g., 2-3 seconds) to avoid triggering when briefly sitting on the bed

### Preventing False Negatives
- Set a longer **Clear Delay** (e.g., 5-10 seconds) to maintain occupied status when shifting positions during sleep

### Quick Response for Automations
- Set short delays (e.g., 0.5s occupied, 1s clear) for responsive lighting automations

### Partner Movement Isolation
- Use different delay settings for left/right sides based on movement patterns

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
2. Check that delay values are within the 0-30 second range
3. Restart the ESP device after making changes
4. Verify the sensor is properly calibrated

## Notes
- Delays are applied after the pressure threshold detection
- Each side (Left/Right) can have independent delay settings
- The "Either" and "Both" combined sensors inherit the delays from their component sensors
- Custom delays are preserved across device restarts