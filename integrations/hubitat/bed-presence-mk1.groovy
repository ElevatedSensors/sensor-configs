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
        //capability 'PresenceSensor'

        attribute 'networkStatus', 'enum', [ 'connecting', 'online', 'offline' ] // auto populated by ESPHome API Library
        attribute 'bedOccupiedBoth', 'enum', [ 'present', 'not present' ]        // consider child devices for'PresenceSensor's with 'presence' attribute 
        attribute 'bedOccupiedEither', 'enum', [ 'present', 'not present' ]      // *
        attribute 'bedOccupiedLeft', 'enum', [ 'present', 'not present' ]        // *
        attribute 'bedOccupiedRight', 'enum', [ 'present', 'not present' ]       // *
        attribute 'leftPressure', 'decimal'
        attribute 'rightPressure', 'decimal'
        attribute 'wifiSignalPercent', 'decimal'
        attribute 'uptimeSeconds', 'number'
        
        //attribute 'full_range', 'enum', [ 'on', 'off' ]
        //response_speed
        
        //command 'fullRangeOn'
        //command 'fullRangeOff'
        command 'calibrateLeftOccupied'
        command 'calibrateLeftUnoccupied'
        command 'calibrateRightOccupied'
        command 'calibrateRightUnoccupied'
        command 'restart'
    }
    
    //preferences
    //full_range
    //left_trigger_pressure
    //right_trigger_pressure

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

void initialize() {
    // API library command to open socket to device, it will automatically reconnect if needed
    openSocket()
    
    if (logEnable || logDriverEnable) {
        runIn(1800, 'logsOff')
    }
}

void installed() {
    log.info "${device} driver installed"
}

void logsOff() {
    espHomeSubscribeLogs(LOG_LEVEL_INFO, false) // disable device logging
    device.updateSetting('logEnable', false)
    device.updateSetting('logDriverEnable', false)
    log.info "${device} debug logging disabled"
}

void refresh() {
    log.info "${device} refresh"
    state.clear()
    state.requireRefresh = true
    espHomeDeviceInfoRequest()
}

void updated() {
    log.info "${device} driver configuration updated"
    initialize()
}

void uninstalled() {
    closeSocket('driver uninstalled') // make sure the socket is closed when uninstalling
    log.info "${device} driver uninstalled"
}

// commands
//void fullRangeOn() {
//    espHomeSwitchCommand(key: state['full_range'], state: true)
//}
//
//void fullRangeOff() {
//    espHomeSwitchCommand(key: state['full_range'], state: false)
//}

void calibrateLeftOccupied() {
    espHomeButtonCommand(key: state['calibrate_left_occupied'])
}

void calibrateLeftUnoccupied() {
    espHomeButtonCommand(key: state['calibrate_left_unoccupied'])
}

void calibrateRightOccupied() {
    espHomeButtonCommand(key: state['calibrate_right_occupied'])
}

void calibrateRightUnoccupied() {
    espHomeButtonCommand(key: state['calibrate_right_unoccupied'])
}

void restart() {
    espHomeButtonCommand(key: state['restart'])
}

// the parse method is invoked by the API library when messages are received
void parse(final Map message) {
    if (logDriverEnable) { log.debug "ESPHome received: ${message}" }

    switch (message.type) {
        case 'device':
            // Device information
            break

        case 'entity':
            state[message.objectId] = message.key as Long
            break

        case 'state':
            //parseStateMessage(message)
            parseState(message)
            break
    }
}

// internal helper functions
private void parseState(final Map message) {
    if (message.state != null) {
        final Long key = message.key as Long
        switch (key) {
            case state['bed_occupied_either']:
                updateCurrentState('bedOccupiedEither', message.state ? 'on' : 'off')
                break
            case state['bed_occupied_both']:
                updateCurrentState('bedOccupiedBoth', message.state ? 'on' : 'off')
                break
            case state['bed_occupied_left']:
                updateCurrentState('bedOccupiedLeft', message.state ? 'on' : 'off')
                break
            case state['bed_occupied_right']:
                updateCurrentState('bedOccupiedRight', message.state ? 'on' : 'off')
                break
            //case state['full_range']:
            //    updateCurrentState('full_range', message.state ? 'on' : 'off')
            //    break
            case state['left_pressure']:
                updateCurrentState('leftPressure', message.state as Double, '%')
                break
            case state['right_pressure']:
                updateCurrentState('rightPressure', message.state as Double, '%')
                break
            case state['uptime']:
                updateCurrentState('uptimeSeconds', message.state as Integer, 's')
                break
            case state['wifi_signal_db']:
                updateCurrentState('rssi', message.state as Integer, 'dBm')
                break
            case state['wifi_signal_percent']:
                updateCurrentState('wifiSignalPercent', message.state as Integer, '%')
                break
            default:
                if (logDriverEnable) { log.debug "Key does not have associate case. Message: ${message}" }
                break
        }
    } else {
        if (logDriverEnable) { log.debug "State is null, skipping. Message: ${message}" }
    }
}

private void updateCurrentState(final String attribute, final Object value, final String unit = null) {
    final String descriptionText = "${attribute} was set to ${value}${unit ?: ''}"
    if (device.currentValue(attribute) != value) {
        //sendEvent(name: attribute, value: value, unit: unit, type: type, descriptionText: descriptionText)
        sendEvent(name: attribute, value: value, unit: unit, descriptionText: descriptionText, isStateChange: true)
        if (settings.logTextEnable) { log.info descriptionText }
    }
}

// Include the ESPHome API library helper
#include esphome.espHomeApiHelper
