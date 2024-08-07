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

## WiFi Provisioning

1. Connect USB-C power supply.
2. Use phone to connect to `bed-presence-xxxxxx` WiFi.
3. If asked to stay connected, press `Yes`.
4. Open a web browser and navigate to http://192.168.4.1
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

3. Orient the sensor with the circuit board/text facing up, and the thicker part of the device hanging over the edge of the bed slat.

    <img src="/assets/images/bp-install-2.jpg" class="rounded m-2" width="400"/>

4. Firmly attach the device using the provided screw. Pre-drill if necessary.

    <img src="/assets/images/bp-install-3.jpg" class="rounded m-2" width="400"/>
    <img src="/assets/images/bp-install-4.jpg" class="rounded m-2" width="400"/>

5. Insert the sensor strips, then peal the adhesive backing and affix the sensors to the bed slat. For a temporary setup, sensors may be gently taped in place around bed slat. Do not cover end of sensors, as there is a required vent opening.

    <img src="/assets/images/bp-install-5.jpg" class="rounded m-2" width="400"/>
    <img src="/assets/images/bp-install-6.jpg" class="rounded m-2" width="400"/>
    <img src="/assets/images/bp-install-7.jpg" class="rounded m-2" width="400"/>

6. Connect USB-C power.

    <img src="/assets/images/bp-install-8.jpg" class="rounded m-2" width="400"/>

7. Carefully place the mattress back in place. This may require 2 people, as dragging the mattress across the sensors may dislodge them.

## Automatic Sensor Calibration

1. The calibration process requires you to get into and our of your bed. It records the sensor readings while the bed is both occupied and unoccupied and automatically calculates the best trigger pressure. This process is best performed when the bed has settled in the unoccupied state. For some beds, this is instant. For others, the unoccupied pressure may increase slowly over several hours. NOTE: you can always go back and update the unoccupied pressure at a later time when it's more convenient (e.g. you're at work and the bed has been unoccupied for several hours).
2. Navigate to the device under Home Assistant Devices. Find the `Configuration` section.
3. Press `Calibrate Unoccupied` for the desired side of the bed.
4. Have the person you are calibrating for gently lay on their side of the bed.
5. Let the sensor settle for a few seconds and press `Calibrate Occupied` for the desired side of the bed.
6. The goal of the automatic calibration is to get the value close to optimal. If you find that you are getting false triggers, want to increase/decrease sensitivity, etc, continue to `Manual Sensor Calibration`.

## Manual Sensor Calibration
1. Alternatively, you can manually set the `Trigger Pressure`. Viewing the graph of the sensor pressure can help guide your desired pressure.
2. If using only once sensor, you can set the value slightly higher than the "non occupied" pressure. If using both sensors, make sure to set it high enough that someone on the opposite side of the bed doesn't trigger it.
