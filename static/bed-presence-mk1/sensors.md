---
layout: default
title: Sensor Details
subtitle: Bed Presence for ESPHome
header_type: post
show_breadcrumb: true
breadcrumb_list:
    - label: Bed Presence for ESPHome
      url: /bed-presence-mk1
---

Learn about the details of each sensor and what they mean.

## Sensors

| Sensor Name                 | Type                                  | Details                                     |
|-----------------------------|---------------------------------------|---------------------------------------------|
| `Bed Occupied [Right/Left]` | Binary (Occupancy) [Detected/Clear]   | The primary sensor that indicates whether this side of the bed is occupied (**Detected**) or not (**Clear**). This sensor is slightly delayed to ensure stability and should be sufficient for most automations. |
| `Bed Occupied Both`         | Binary (Occupancy) [Detected/Clear]   | Indicates if **Both** sides of the bed are occupied or not. |
| `Bed Occupied Either`       | Binary (Occupancy) [Detected/Clear]   | Indicates if **Either** side of the bed is occupied or not. |
| `(Fast) Sensors`            | Binary (Occupancy) [Detected/Clear]   | A copy of the primary occupancy sensors (Right/Left/Both/Either), with less delay. If you need quick response when getting into or out of bed, and can tolerate an increased chance of false negatives, use this sensor. `These sensors are disabled by default and must be manually enabled in the Home Assistant UI.` |
| `Pressure [Right/Left]`     | Number (Pressure&nbsp;%) [0%&nbsp;&rArr;&nbsp;100%] | Indicates the current pressure reading. This value ranges from 0% to 100%, indicating the full range of the sensor. Do not worry if your unoccupied pressure reading is not 0%, or even if both sides of the bed are not similar. Some beds may swing from 0% (Unoccupied) to 95% (Occupied), while others may start at 70% and only increase to 75%. The important thing is that there is a measureable difference between the bed being occupied and unoccupied. |

## Values

| Value Name                         | Type                                  |  Details                                    |
|------------------------------------|---------------------------------------|---------------------------------------------|
| `[Right/Left] Trigger Pressure`    | Number (Pressure&nbsp;%) [0%&nbsp;&rArr;&nbsp;100%] | When `[Right/Left] Pressure` is above this value, `[Right/Left] Bed Occupied` will register **Detected**. The calibration process will set this value, but it can also be adjusted manually. |
| `[Right/Left] Unoccupied Pressure` | Number (Pressure&nbsp;%) [0%&nbsp;&rArr;&nbsp;100%] | Indicates the sensor value when the bed is unoccupied. Used for calculating the ideal `[Right/Left] Trigger Pressure`. The calibration process will set this value, but it can also be adjust manually. |
| `[Right/Left] Occupied Pressure`   | Number (Pressure&nbsp;%) [0%&nbsp;&rArr;&nbsp;100%] | Indicates the sensor value when the bed is occupied. Used for calculating the ideal `[Right/Left] Trigger Pressure`. The calibration process will set this value, but it can also be adjusted manually. |

## Calibration

| Method Name                         | Details                                                                                   |
|-------------------------------------|-------------------------------------------------------------------------------------------|
| `Calibrate [Right/Left] Unoccupied` | Sets the `[Right/Left] Unoccupied Pressure` using the value from `[Right/Left] Pressure`. |
| `Calibrate [Right/Left] Occupied`   | Sets the `[Right/Left] Occupied Pressure` using the value from `[Right/Left] Pressure`.   |
