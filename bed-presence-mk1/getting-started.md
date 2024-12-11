---
layout: default
title: Getting Started
subtitle: Bed Presence for ESPHome
header_type: post
show_breadcrumb: true
breadcrumb_list:
    - label: Bed Presence for ESPHome
      url: /bed-presence-mk1
---

Welcome to Bed Presence for ESPHome! This page contains everything you need to get started.

## Bluetooth Provisioning

1. If you have a Bluetooth proxy, simply connect USB-C power supply and skip to `Adding to Home Assistant`.

## WiFi Provisioning

1. Connect USB-C power supply.
2. Use phone to connect to `bed-presence-xxxxxx` WiFi.
3. If asked to stay connected, press `Yes`.
4. Open a web browser and navigate to `http://192.168.4.1`
5. Select WiFi network you would like to add the device to, and provide the password.

## Adding to Home Assistant

1. If you'd like full control over the firmware on the device (advanced), adopt the device into the ESPHome Dashboard.
2. In Home Assistant navigate to `Settings` -> `Devices & services`.
3. You should see a discovered item called `Bed Presence xxxxxx`.

    <img src="/assets/images/bp-discovered.png" class="rounded m-2" width="400"/>

4. Click `Configure` then `Submit`. If desired, specify an area and click `Finish`.

## Installing Sensor on Bed

1. Remove mattress from bed frame.

    <img src="/assets/images/bp-install-1.jpg" class="rounded m-2" width="400"/>

2. Select a bed slat in the torso region, where most of your weight will be applied to the bed (think about where you sit or lay in bed). If possible, select a bed slat that does not rock side to side.

3. Orient the sensor with the circuit board/text facing up, and the thicker part of the device hanging over the edge of the bed slat. If the selected slat can move, you may need to screw it in place.

    <img src="/assets/images/bp-install-2.jpg" class="rounded m-2" width="400"/>

4. Firmly attach the device using the provided screw. Pre-drill if necessary. For metal slats, use the included double-sided mounting square (it may be easier to insert the sensor strips before applying the adhesive square).

    <img src="/assets/images/bp-install-3.jpg" class="rounded m-2" width="400"/>
    <img src="/assets/images/bp-install-4.jpg" class="rounded m-2" width="400"/>

5. Insert the sensor strips, then peal the adhesive backing and affix the sensors to the bed slat. For a temporary setup, sensors may be gently taped in place around bed slat. Do not cover end of sensors, as there is a required vent opening. For King beds, consider using the included extension wires to space the sensors futher apart. Zip ties can be used to hold everything neatly in place.

    <img src="/assets/images/bp-install-5.jpg" class="rounded m-2" width="400"/>
    <img src="/assets/images/bp-install-6.jpg" class="rounded m-2" width="400"/>
    <img src="/assets/images/bp-install-7.jpg" class="rounded m-2" width="400"/>

6. Connect USB-C power.

    <img src="/assets/images/bp-install-8.jpg" class="rounded m-2" width="400"/>

7. Carefully place the mattress back in place. This may require 2 people, as dragging the mattress across the sensors may dislodge them.

## Automatic Sensor Calibration

The calibration process requires you to get into and our of your bed. It records the sensor readings while the bed is both occupied and unoccupied and automatically calculates the best trigger pressure.

1. Navigate to the device under Home Assistant Devices. Find the `Configuration` section.
2. Have the person you are calibrating for gently lay on their side of the bed.
3. Let the sensor settle for a few seconds and press `Calibrate Occupied` for the correct side of the bed.
4. Get out of bed.
5. Leave the entire bed unoccupied for a couple minutes, then press `Calibrate Unoccupied` for the correct side of the bed.
6. Repeat for the other side of the bed.
7. The goal of the automatic calibration is to get the trigger value to a good starting point. If you find that you are getting false triggers, want to increase/decrease sensitivity, etc, continue to `Manual Sensor Calibration`.

## Manual Sensor Calibration
1. Alternatively, you can manually set the `Trigger Pressure`. Viewing the graph of the sensor pressure can help guide your desired pressure.
2. If using only once sensor, you can set the value slightly higher than the "non occupied" pressure. If using both sensors, make sure to set it high enough that someone on the opposite side of the bed doesn't trigger it.
