---
layout: page
title: "Bed Presence for ESPHome"
---

Welcome to Bed Presence for ESPHome! This page contains everything you need to get started.

## WiFi Provisioning

1. Connect USB-C power supply.
2. Use phone to connect to "bed-presence-xxxxxx" WiFi.
3. If asked to stay connected, press "Yes".
4. Open a web browser and navigate to http://192.168.4.1
5. Select WiFi network you would like to add the device to, and provide the password.

## Adding to ESPHome

1. Navigate to your ESPHome Dashboard.
2. The device should appear as "Discovered".
3. Press "Adopt".
4. Provide a name for the device and click "Adopt" (By default, it will be named "Bed Presence" followed by the last 6 digits of its MAC address).
5. Click "Install".
6. Wait for firmware to compile and upload.
7. You are done! You can now hit "Stop".

## Adding to Home Assistant

1. Now that the device has been adopted in ESPHome, you should get a notification that a new device was discovered.
2. Open the notification and press "Check it out".
3. If you don't get a notification, go to Settings -> Devices & services.
4. The discovered device should appear at the top.
5. Press "Configure" then "Submit".
6. Select an area for the device if desired and press "Finish".
7. Congrats! Your sensor is ready to use.

## Installing Sensor on Bed

1. Remove mattress from bed frame.

  <img src="/assets/images/bed-presence.png" width="300">

2. Select a bed slat in the torso region, where most of your weight will be applied to the bed (think about where you sit or lay in bed). If possible, select a bed slat that does not rock side to side.

  <img src="/assets/images/bed-presence.png" width="300">

3. Orient the sensor with the circuit board/text facing up.

  <img src="/assets/images/bed-presence.png" width="300">

4. Firmly attach the device using the provided screw. Pre-drill if necessary.

  <img src="/assets/images/bed-presence.png" width="300">

5. Insert the sensor strips, then peal the adhesive backing and affix the sensors to the bed slat.

  <img src="/assets/images/bed-presence.png" width="300">

6. Connect USB-C power.

  <img src="/assets/images/bed-presence.png" width="300">

## Automatic Sensor Calibration

1. The calibration process requires you to get into and out of your bed. It uses the maximum and minimum observed pressures to automatically figure out the best trigger pressure.
2. Have the person you are calibrating for lay on their side of the bed.
3. Navigate to the device and find the "Configuration" section.
4. Turn on "Calibration \[Right or Left\] (Auto)".
5. Have the person get out of bed.
6. Turn off "Calibration \[Right or Left\] (Auto)".
7. Alternatively, you can manually set the "Trigger Pressure". The Max/Min Pressure values in the "Diagnostic" section can help guide your desired pressure.
