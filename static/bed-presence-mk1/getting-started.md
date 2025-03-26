---
layout: default
title: Getting Started
subtitle: Bed Presence for ESPHome
header_type: post
show_breadcrumb: true
breadcrumb_list:
    - label: Bed Presence for ESPHome
      url: /bed-presence-mk1
show_toc: true
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
If you are installing using the Universal Mounting Kit, select between [Queen/Full & Twin](#queenfull--twin-mounting-kit-install) and [King](#king-mounting-kit-install).

### Slat Install

1. Remove mattress from bed frame.

    <img src="/assets/images/bp-install-1.jpg" class="rounded m-2" width="400"/>

2. Select a bed slat in the torso region, where most of your weight will be applied to the bed (think about where you sit or lay in bed). If possible, select a bed slat that does not rock side to side.

3. Orient the sensor with the circuit board/text facing up, and the thicker part of the device hanging over the edge of the bed slat. If the selected slat can move, you may need to screw it in place.

    <img src="/assets/images/bp-install-2.jpg" class="rounded m-2" width="400"/>

4. Firmly attach the device using the provided screw. Pre-drill if necessary. For metal slats, use the included double-sided mounting square (it may be easier to insert the sensor strips before applying the adhesive square).

    <img src="/assets/images/bp-install-3.jpg" class="rounded m-2" width="400"/>
    <img src="/assets/images/bp-install-4.jpg" class="rounded m-2" width="400"/>

5. Insert the sensor strips, then peel the adhesive backing and affix the sensors to the bed slat. For a temporary setup, sensors may be gently taped in place around bed slat. Do not cover end of sensors with tape, as there is a required vent opening. For King beds, consider using the included extension wires to space the sensors futher apart. Zip ties can be used to hold everything neatly in place.

    <img src="/assets/images/bp-install-5.jpg" class="rounded m-2" width="400"/>
    <img src="/assets/images/bp-install-6.jpg" class="rounded m-2" width="400"/>
    <img src="/assets/images/bp-install-7.jpg" class="rounded m-2" width="400"/>

6. Connect USB-C power.

    <img src="/assets/images/bp-install-8.jpg" class="rounded m-2" width="400"/>

7. Carefully place the mattress back in place. This may require 2 people, as dragging the mattress across the sensors may dislodge them.

### Queen/Full & Twin Mounting Kit Install

1. Remove the flexible slat from the packaging and lay it on a flat surface such as a carpet.
2. Align the main unit cradle under the center hole of the slat.

    <img src="/assets/images/bp-mount-install/1-align_cradle.jpg" class="rounded m-2" width="400"/>

3. Place the main unit into the cradle and loosely attach it using the blunt-tip screw.

    <img src="/assets/images/bp-mount-install/2-install_main_unit.jpg" class="rounded m-2" width="400"/>

4. Insert the sensor strips into the slots labeled "Left Sensor" and "Right Sensor".
    - For the Twin Kit, attach the sensor to whichever side is most convenient based on your installation location.

    <img src="/assets/images/bp-mount-install/3-sensor_strips.jpg" class="rounded m-2" width="400"/>

5. Fully tighten the screw until the main unit is secure and no longer moves easily.
    - Do not overtighten, as this may strip the plastic.
6. Peel off the adhesive backing on each sensor strip and firmly stick them down.
7. Connect USB-C power. The unit is now ready to install on top of a box spring, upholstered base, solid platform, or foundation.
    - For beds with wooden slats or platforms, optionally use the provided wood screws to secure the ends of the flexible slat in place.

### King Mounting Kit Install

1. Remove the flexible slat from the packaging and lay it on a flat surface such as a carpet.
2. Align the main unit cradle under the center hole of the slat.

    <img src="/assets/images/bp-mount-install/1-align_cradle.jpg" class="rounded m-2" width="400"/>

3. Place the main unit into the cradle and loosely attach it using the blunt-tip screw.

    <img src="/assets/images/bp-mount-install/2-install_main_unit.jpg" class="rounded m-2" width="400"/>

4. Insert the sensor extension wires into the slots labeled "Left Sensor" and "Right Sensor".

    <img src="/assets/images/bp-mount-install/4-extension_wires.jpg" class="rounded m-2" width="400"/>

5. Fully tighten the screw until the main unit is secure and no longer moves easily.
    - Do not overtighten, as this may strip the plastic.
6. Connect the sensor strips to the ends of the extension wires.

    <img src="/assets/images/bp-mount-install/5-sensor_extensions.jpg" class="rounded m-2" width="400"/>

7. Fully extend the extension wires, then peel off the adhesive backing on each sensor strip and firmly stick them down.
    - Ensure the sensor strips are approximately centered top to bottom.

    <img src="/assets/images/bp-mount-install/6-sensor_adhesive.jpg" class="rounded m-2" width="400"/>

8. Gently install the sensor extension clips, inserting the thin side under the slat.
    - Extension wire/sensor strip connector should be approximately centered in the clip.
    - The connectors should click into a cavity, leaving the clips flush with the slat.
    - It’s okay if the slot in the slat does not perfectly align with the slot in the clip.

    <img src="/assets/images/bp-mount-install/7-extension_clip.jpg" class="rounded m-2" width="400"/>

9. Insert the included zip tie through the slot, tighten it, and trim excess.

    <img src="/assets/images/bp-mount-install/8-zip_tie.jpg" class="rounded m-2" width="400"/>

10. Connect USB-C power. The unit is now ready to install on top of a box spring, upholstered base, solid platform, or foundation.
    - For beds with wooden slats or platforms, optionally use the provided wood screws to secure the ends of the flexible slat in place.

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
