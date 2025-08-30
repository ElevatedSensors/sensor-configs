# How to Configure Custom Delays for Either/Both Sensors

## Understanding How It Works

The "Either" and "Both" sensors **automatically inherit** the delays from the individual Left and Right sensors. You don't set delays directly on Either/Both - they use the delays from their component sensors.

## Step-by-Step Configuration

### 1. In Home Assistant
Go to **Settings → Devices & Services → ESPHome → Your Bed Presence Device**

### 2. Set Response Speed to Custom
Find **"Response Speed"** entity and set it to **"Custom"**

### 3. Configure Individual Sensor Delays
You'll see these entities:
- **Left Occupied Delay** (0-300 seconds)
- **Left Clear Delay** (0-300 seconds)  
- **Right Occupied Delay** (0-300 seconds)
- **Right Clear Delay** (0-300 seconds)

### 4. How Either/Both Work with Your Delays

#### "Bed Occupied Either" Behavior:
- Triggers when **EITHER** left OR right detects occupancy
- Uses the **shortest** path to trigger:
  - If Left has 5s occupied delay and Right has 10s occupied delay
  - "Either" will trigger after 5s (when Left triggers)
- For clearing: Waits for **BOTH** to be clear (using their individual clear delays)

#### "Bed Occupied Both" Behavior:  
- Triggers when **BOTH** left AND right detect occupancy
- Uses the **longest** path to trigger:
  - If Left has 5s occupied delay and Right has 10s occupied delay
  - "Both" will trigger after 10s (when both are occupied)
- For clearing: Clears when **EITHER** side clears (using their individual clear delays)

## Example Configurations

### Quick Response (for testing):
```
Left Occupied Delay: 1 second
Left Clear Delay: 2 seconds
Right Occupied Delay: 1 second  
Right Clear Delay: 2 seconds
```
Result: Either/Both respond within 1-2 seconds

### Prevent False Triggers (recommended):
```
Left Occupied Delay: 10 seconds
Left Clear Delay: 30 seconds
Right Occupied Delay: 10 seconds
Right Clear Delay: 30 seconds
```
Result: Must be in bed 10s to trigger, stays occupied for 30s after movement

### Different Sides (partner has different sleep patterns):
```
Left Occupied Delay: 5 seconds (quick sleeper)
Left Clear Delay: 60 seconds (restless)
Right Occupied Delay: 15 seconds (takes time to settle)
Right Clear Delay: 30 seconds (still sleeper)
```
Result: 
- "Either" triggers after 5s (left side)
- "Both" triggers after 15s (both sides)
- Clearing depends on movement patterns

### Long Confirmation (deep sleep detection):
```
Left Occupied Delay: 120 seconds (2 minutes)
Left Clear Delay: 180 seconds (3 minutes)
Right Occupied Delay: 120 seconds
Right Clear Delay: 180 seconds
```
Result: Only triggers after 2 minutes of sustained presence, maintains state for 3 minutes

## Testing Your Configuration

1. **Set your delays** (start with small values like 5-10 seconds for testing)
2. **Watch the sensors** in Developer Tools → States
3. **Test scenarios**:
   - Sit on left side only → "Either" should trigger after left delay
   - Sit on both sides → "Both" should trigger after longest delay
   - Stand up from one side → "Either" stays on, "Both" clears
   - Stand up completely → Both "Either" and "Both" clear after delays

## Important Notes

- **You only set delays on Left and Right sensors**
- **Either/Both automatically use these delays**
- **No separate delay configuration for Either/Both needed**
- When Response Speed = "Custom", all sensors use custom delays
- When Response Speed = "Fast/Normal/Slow", all sensors use predefined delays

## Automation Examples

### Using "Either" (someone in bed):
```yaml
automation:
  - alias: "Turn off lights when someone gets in bed"
    trigger:
      - platform: state
        entity_id: binary_sensor.bed_occupied_either
        to: 'on'
    action:
      - service: light.turn_off
        entity_id: light.bedroom
```

### Using "Both" (both in bed):
```yaml
automation:
  - alias: "Activate sleep mode when both in bed"
    trigger:
      - platform: state
        entity_id: binary_sensor.bed_occupied_both
        to: 'on'
        for: '00:01:00'  # Additional 1 minute confirmation
    action:
      - service: script.activate_sleep_mode
```

### Using individual sensors:
```yaml
automation:
  - alias: "Partner's reading light"
    trigger:
      - platform: state
        entity_id: binary_sensor.bed_occupied_left
        to: 'on'
    condition:
      - condition: state
        entity_id: binary_sensor.bed_occupied_right
        state: 'off'
    action:
      - service: light.turn_on
        entity_id: light.left_reading_lamp