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

Learn about the details of each sensor and what they mean. Each side of the bed (Right/Left) has the following sensors, values, and calibration methods.

## Sensors

| Sensor Name               | Type                | Values         |  Details                                    |
|---------------------------|---------------------|----------------|---------------------------------------------|
| `Bed Occupied`              | Binary (Occupancy)  | Detected/Clear | The primary sensor that indicates whether this side of the bed is occupied (**Detected**) or not (**Clear**). This sensor is slightly delayed to ensure stability and should be sufficient for most automations.|
| `Bed Occupied (Fast)`       | Binary (Occupancy)  | Detected/Clear | Like `Bed Occupied`, this sensor indicates whether this side of the bed is occupied or not. However, this sensor has less of a delay. If you need quick response when getting into or out of bed, and can tolerate an increased chance of false negatives, use this sensor. This sensor must be manually enabled in the Home Assistant UI.|
| `Pressure (Current)`        | Number (Pressure&nbsp;%) | 0% -> 100%     | Indicates the current pressure reading. This value ranges from 0% to 100%, indicating the full range of the sensor. Do not worry if your unoccupied pressure reading is not 0%, or even if both sides of the bed are not similar. Some beds may swing from 0% (Unoccupied) to 95% (Occupied), while others may start at 70% and only increase to 75%. The important thing is that there is a measureable difference between the bed being occupied and unoccupied.|

## Values

| Value Name                | Type                | Values         |  Details                                    |
|---------------------------|---------------------|----------------|---------------------------------------------|
| `Trigger Pressure`          | Number (Pressure&nbsp;%) | 0% -> 100%     | When `Pressure (Current)` is above this value, `Bed Occupied` will register **Detected**. The calibration process will set this value, but it can also be adjusted manually.|
| `Unoccupied Pressure`       | Number (Pressure&nbsp;%) | 0% -> 100%     | Indicates the sensor value when the bed is unoccupied. Used for calculating the ideal `Trigger Pressure`. The calibration process will set this value, but it can also be adjust manually. Note: this should indicate the highest possible value observed when the bed is unoccupied.|
| `Occupied Pressure`         | Number (Pressure&nbsp;%) | 0% -> 100%     | Indicates the sensor value when the bed is occupied. Used for calculating the ideal `Trigger Pressure`. The calibration process will set this value, but it can also be adjusted manually.|

## Calibration

| Method Name               | Details                                    |
|---------------------------|---------------------------------------------|
| `Calibrate Unoccupied`      | Sets the `Unoccupied Pressure` using the value from `Pressure (Current)`. This should be done (or redone) once the bed has been unoccupied for an extended period.|
| `Calibrate Occupied`        | Sets the `Occupied Pressure` using the value from `Pressure (Current)`.|
