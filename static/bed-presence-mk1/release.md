---
layout: default
title: Firmware Release Notes
subtitle: Bed Presence for ESPHome
header_type: post
show_breadcrumb: true
breadcrumb_list:
    - label: Bed Presence for ESPHome
      url: /bed-presence-mk1
---

## 2025.2.0
**Fixed Calibration Cap**: Accomodate the increased sensitivity of revC boards, ensuring accurate pressure calibration up to
120% instead of being limited to 110%.

## 2024.10.0
This is the first firmware update for Bed Presence Mk1. It will be available OTA to managed devices. Devices already
imported into the ESPHome Dashboard will need to be recompiled to get access to the new features.

**Eliminate DB Bloat** - The default delta threshold and reporting interval have been updated so that the sensor only
reports new values when something actually changes. This will decrease the update frequency dramatically and eliminate
bloating the database.

**Improved Sensor Response** - The sensor now uses a window average by default. This helps smooth out any quick movements,
while also slightly improving the response time for getting out of bed.

**Calibrated Sensor** - If you're annoyed by a different response from each side of your bed, the `Calibrated Sensor` is for
you. This is an additional sensor that scales your raw pressure values between the `Unoccupied Pressure` and `Occupied
Pressure`.

**Status Sensor** - Added a binary status sensor to expose the device's connectivity state.

**Option to Use Full Range** - By default, Pressure [Right/Left] is focused on the most sensitive zone of the pressure
sensor (Full Range = Off). This should perform well for most setups. By turning on Full Range, you can expand it to use
the full range of the sensor. Consider turning ON Full Range if slight movements in bed quickly drop the sensor value to
zero, causing frequent false negatives.

**Lots of Customization** - If you import the device into the ESPHome Dashboard, there are now lots more substitutions
within the package to customize behavior.

See <a href="https://github.com/ElevatedSensors/sensor-configs/blob/main/bed-presence-mk1/sensor.yaml" target="_blank">https://github.com/ElevatedSensors/sensor-configs/blob/main/bed-presence-mk1/sensor.yaml</a> for substitution descriptions.

```
substitutions:
- trigger_percentile: '0.75'
- averaging_window_samples: '5'
- fast_delayed_on: '0ms'
- fast_delayed_off: '0ms'
- standard_delayed_on: '0s'
- standard_delayed_off: '1s'
- reporting_delta: '1.0'
- reporting_interval_max: '180s'
- calibrate_100: '408000'
- calibrate_0: '276000'
```

## 2024.9.0
- Initial Release

