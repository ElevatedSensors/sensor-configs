/**
 * Adapted from https://github.com/ElevatedSensors/hubitat-public/blob/main/ESPHome/ESPHome-ContactSensor.groovy (MIT License, 2022)
 */
metadata {
    definition(
        name: 'Bed Presence Mk1',
        namespace: 'esphome',
        author: 'Elevated Sensors',
        singleThreaded: true,
        importUrl: 'https://raw.githubusercontent.com/ElevatedSensors/sensor-configs/main/integrations/hubitat/bed-presence-mk1.groovy') {

        capability 'Sensor'
        capability 'Refresh'
        capability 'SignalStrength'
        capability 'Initialize'
        capability 'PresenceSensor'

        // attribute populated by ESPHome API Library automatically
        attribute 'networkStatus', 'enum', [ 'connecting', 'online', 'offline' ]
        attribute 'bedOccupiedLeft', 'enum', [ 'present', 'not present' ]
        attribute 'bedOccupiedRight', 'enum', [ 'present', 'not present' ]
    }

    preferences {
        input name: 'ipAddress',    // required setting for API library
                type: 'text',
                title: 'Device IP Address',
                required: true

        input name: 'password',     // optional setting for API library
                type: 'text',
                title: 'Device Password <i>(if required)</i>',
                required: false

        input name: 'logEnable',    // if enabled the library will log debug details
                type: 'bool',
                title: 'Enable Debug Logging',
                required: false,
                defaultValue: false

        input name: 'logTextEnable',
              type: 'bool',
              title: 'Enable descriptionText logging',
              required: false,
              defaultValue: true
    }
}

public void initialize() {
    // API library command to open socket to device, it will automatically reconnect if needed
    openSocket()

    if (logEnable) {
        runIn(1800, 'logsOff')
    }
}

public void installed() {
    log.info "${device} driver installed"
}

public void logsOff() {
    espHomeSubscribeLogs(LOG_LEVEL_INFO, false) // disable device logging
    device.updateSetting('logEnable', false)
    log.info "${device} debug logging disabled"
}

public void refresh() {
    log.info "${device} refresh"
    state.clear()
    state.requireRefresh = true
    espHomeDeviceInfoRequest()
}

public void updated() {
    log.info "${device} driver configuration updated"
    initialize()
}

public void uninstalled() {
    closeSocket('driver uninstalled') // make sure the socket is closed when uninstalling
    log.info "${device} driver uninstalled"
}

// the parse method is invoked by the API library when messages are received
public void parse(Map message) {
    if (logEnable) { log.debug "ESPHome received: ${message}" }

    switch (message.type) {
        case 'device':
            // Device information
            break

        case 'entity':
            //state.entity_info = (state.entity_info ?: [:]) + [ (message.key): message ]
            // This will populate the cover dropdown with all the entities
            // discovered and the entity key which is required when sending commands
            //if (message.platform == 'binary') {
            //    state.entity_binary_sensors = (state.entity_binary_sensors ?: [:]) + [ (message.key): message ]
            //    if (!settings.binarysensor) {
            //        device.updateSetting('binarysensor', message.key)
            //    }
            //    return
            //}

            //if (message.platform == 'sensor') {
            //    state.entity_sensors = (state.entity_sensors?: [:]) + [ (message.key): message ]
            //
            //    switch (message.deviceClass) {
            //        case 'signal_strength':
            //            state['signalStrength'] = message.key
            //            break
            //   }
            //    return
            //}


            //log.debug "Entity: ${message.objectId}"
            if (message.objectId == 'bed_occupied_left') {
                state['bed_occupied_left_key'] = message.key
            } else if (message.objectId == 'bed_occupied_right') {
                state['bed_occupied_right_key'] = message.key
            }

            break

        case 'state':
            // Check if the entity key matches the message entity key received to update device state
            //state.state_info = (state.state_info ?: [:]) + [ (message.key): message ]
            //log.debug("State: ${message.state} Message: ${message}")
            //log.debug("Device: ${state.entity_info[message.key]}")
            //log.debug("Match: ${state.bed_occupied_left_key as Long == message.key} Desired Key: ${state.bed_occupied_left_key}, Actual Key: ${message.key}")

            if (state.bed_occupied_left_key as Long == message.key && message.hasState) {
                //log.debug("State: ${message.state}")
                String value = message.state ? 'present' : 'not present'

                if (device.currentValue('bedOccupiedLeft') != value) {
                    sendEvent(name: 'bedOccupiedLeft', value: value, descriptionText: "Bed Left is ${value}")
                }
                return
            } else if (state.bed_occupied_right_key as Long == message.key && message.hasState) {
                String value = message.state ? 'present' : 'not present'

                if (device.currentValue('bedOccupiedRight') != value) {
                    sendEvent(name: 'bedOccupiedRight', value: value, descriptionText: "Bed Right is ${value}")
                }
                return
            }

            // Signal Strength
            if (state.signalStrength as Long == message.key && message.hasState) {
                Integer rssi = Math.round(message.state as Float)
                String unit = 'dBm'
                if (device.currentValue('rssi') != rssi) {
                    descriptionText = "${device} rssi is ${rssi}"
                    sendEvent(name: 'rssi', value: rssi, unit: unit, descriptionText: descriptionText)
                    if (logTextEnable) { log.info descriptionText }
                }
                return
            }
            break
    }
}

// Include the ESPHome API library helper
#include esphome.espHomeApiHelper
