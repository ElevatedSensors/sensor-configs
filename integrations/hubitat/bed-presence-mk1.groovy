/**
 * Adapted from https://github.com/bradsjm/hubitat-public/blob/main/ESPHome/ESPHome-ContactSensor.groovy (MIT License, 2022)
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
        attribute 'bed_occupied_left', 'enum', [ 'present', 'not present' ]
        attribute 'bed_occupied_right', 'enum', [ 'present', 'not present' ]
        attribute 'full_range', 'enum', [ 'on', 'off' ]
        
        command 'fullRangeOn'
        command 'fullRangeOff'
    }

    preferences {
        input name: 'ipAddress',          // required setting for API library
                type: 'text',
                title: 'Device IP Address',
                required: true

        input name: 'password',           // optional setting for API library
                type: 'text',
                title: 'Device Password <i>(if required)</i>',
                required: false

        input name: 'logTextEnable',      // if enabled, the driver will log sendEvent details
              type: 'bool',
              title: 'Enable descriptionText logging',
              required: false,
              defaultValue: true
        
        input name: 'logDriverEnable',    // if enabled the driver will log debug details
              type: 'bool',
              title: 'Enable driver DEBUG logging',
              required: false,
              defaultValue: false
        
        input name: 'logEnable',          // if enabled the library will log ESPHome debug details
                type: 'bool',
                title: 'Enable ESPHome DEBUG logging',
                required: false,
                defaultValue: false
    }
}

public void initialize() {
    // API library command to open socket to device, it will automatically reconnect if needed
    openSocket()
    
    if (logEnable || logDriverEnable) {
        runIn(1800, 'logsOff')
    }
}

public void installed() {
    log.info "${device} driver installed"
}

public void logsOff() {
    espHomeSubscribeLogs(LOG_LEVEL_INFO, false) // disable device logging
    device.updateSetting('logEnable', false)
    device.updateSetting('logDriverEnable', false)
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
    
    //runIn(5, configure)
}

public void uninstalled() {
    closeSocket('driver uninstalled') // make sure the socket is closed when uninstalling
    log.info "${device} driver uninstalled"
}

public void fullRangeOn() {
    espHomeSwitchCommand(key: state['full_range'] as Long, state: true)
}

public void fullRangeOff() {
    espHomeSwitchCommand(key: state['full_range'] as Long, state: false)
}

// the parse method is invoked by the API library when messages are received
public void parse(Map message) {
    if (logDriverEnable) { log.debug "ESPHome received: ${message}" }

    switch (message.type) {
        case 'device':
            // Device information
            break

        case 'entity':
            state[message.objectId] = message.key as Long
            
            //parseEntityMessage(message)
            break

        case 'state':
            //parseStateMessage(message)
            parseState(message)
            break
    }
}

private void parseState(final Map Message) {
    log.debug "called parseState"
    log.debug "${state['bed_occupied_left']}"
}

private void updateCurrentState(final String attribute, final Object value, final String unit = null, final String type = null) {
    final String descriptionText = "${attribute} was set to ${value}${unit ?: ''}"
    if (device.currentValue(attribute) != value) {
        sendEvent(name: attribute, value: value, unit: unit, type: type, descriptionText: descriptionText)
        if (settings.logTextEnable) { log.info descriptionText }
    }
}

// Include the ESPHome API library helper
#include esphome.espHomeApiHelper
