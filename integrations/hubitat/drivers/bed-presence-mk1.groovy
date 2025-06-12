/**
 * Adapted from examples at https://github.com/bradsjm/hubitat-public/blob/main/ESPHome (MIT License)
 */

metadata {
    definition(
        name: 'Elevated Sensors Bed Presence Mk1',
        namespace: 'elevated_sensors',
        author: 'Elevated Sensors',
        singleThreaded: true,
        importUrl: 'https://raw.githubusercontent.com/ElevatedSensors/sensor-configs/main/integrations/hubitat/drivers/bed-presence-mk1.groovy') {

        capability 'Sensor'
        capability 'Refresh'
        capability 'SignalStrength'
        capability 'Initialize'

        attribute 'networkStatus', 'enum', [ 'connecting', 'online', 'offline' ] // auto populated by ESPHome API Library
        attribute 'bedOccupiedBoth', 'enum', [ 'present', 'not present' ]
        attribute 'bedOccupiedEither', 'enum', [ 'present', 'not present' ]
        attribute 'bedOccupiedLeft', 'enum', [ 'present', 'not present' ]
        attribute 'bedOccupiedRight', 'enum', [ 'present', 'not present' ]
        attribute 'leftPressure', 'decimal'
        attribute 'rightPressure', 'decimal'
        attribute 'wifiSignalPercent', 'decimal'
        attribute 'uptimeSeconds', 'number'

        command 'calibrateLeftOccupied'
        command 'calibrateLeftUnoccupied'
        command 'calibrateRightOccupied'
        command 'calibrateRightUnoccupied'
        command 'restart'
    }

    preferences {
        input name: 'ipAddress',          	// required setting for API library
                type: 'text',
                title: 'Device IP Address',
                required: true

        input name: 'password',           	// optional setting for API library
                type: 'text',
                title: 'Device Password <i>(if required)</i>',
                required: false

        input name: 'logTextEnable',      	// if enabled, the driver will log sendEvent details
                type: 'bool',
                title: 'Enable descriptionText logging',
                required: false,
                defaultValue: true

        input name: 'logDriverEnable',    	// if enabled the driver will log debug details
                type: 'bool',
                title: 'Enable driver DEBUG logging',
                required: false,
                defaultValue: false

        input name: 'logEnable',          	// if enabled the library will log ESPHome debug details
                type: 'bool',
                title: 'Enable ESPHome DEBUG logging',
                required: false,
                defaultValue: false

        input name: 'leftTriggerPressure',	// the pressure at which bed_occupied_left will register 'present'
                type: 'decimal',
                title: 'Left Trigger Pressure',
                required: true,
                defaultValue: 50.0,
                range: '0..120'

        input name: 'rightTriggerPressure',	// the pressure at which bed_occupied_right will register 'present'
                type: 'decimal',
                title: 'Right Trigger Pressure',
                required: true,
                defaultValue: 50.0,
                range: '0..120'

        input name: 'fullRange',         	// use the full range of the sensor
                type: 'bool',
                title: 'Enable Full Range',
                required: true,
                defaultValue: false

        input name: 'responseSpeed',        // control how fast values update (trade off between speed and false negatives)
                type: 'enum',
                title: 'Sensor Response Speed',
                options: ['Fast', 'Normal', 'Slow'],
                required: true,
                defaultValue: 'Normal'
    }
}

// device commands
void initialize() {
    // API library command to open socket to device, it will automatically reconnect if needed
    openSocket()
    state.currentIp = settings.ipAddress

    if (logEnable || logDriverEnable) {
        runIn(1800, 'logsOff')
    }
}

void installed() {
    syncDriverVersion()
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

    if (settings.ipAddress != state.currentIp) {
        log.info "New ipAddress detected for ${device}, initializing connection"
        state.currentIp = settings.ipAddress
        initialize()
    } else {
        log.info "Configuring preferences on ${device}"
        espHomeNumberCommand(key: state['left_trigger_pressure'], state: settings['leftTriggerPressure'] as Double)
        espHomeNumberCommand(key: state['right_trigger_pressure'], state: settings['rightTriggerPressure'] as Double)
        espHomeSwitchCommand(key: state['full_range'], state: settings['fullRange'])
        espHomeSelectCommand(key: state['response_speed'], state: settings['responseSpeed'] as String)
    }
}

void uninstalled() {
    closeSocket('driver uninstalled') // make sure the socket is closed when uninstalling

    // delete child devices
    for (device in getChildDevices()) {
        deleteChildDevice(device.deviceNetworkId)
    }

    log.info "${device} driver uninstalled"
}

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

// component commands
void componentRefresh(child) {
    log.info "refresh request from ${child.displayName}"
}

// the parse method is invoked by the API library when messages are received
void parse(final Map message) {
    if (logDriverEnable) { log.debug "ESPHome received: ${message}" }

    switch (message.type) {
        case 'device':
            // Device information
            break
        case 'entity':
            // store object key
            state[message.objectId] = message.key as Long

            // create child devices
            switch (message.objectId) {
                case 'bed_occupied_either':
                    createChildPresenceSensor('either')
                    break
                case 'bed_occupied_both':
                    createChildPresenceSensor('both')
                    break
                case 'bed_occupied_left':
                    createChildPresenceSensor('left')
                    break
                case 'bed_occupied_right':
                    createChildPresenceSensor('right')
                    break
            }
            break
        case 'state':
            parseState(message)
            break
    }
}

// internal helper functions
private void parseState(final Map message) {
    if (message.state != null) {
        final Long key = message.key as Long
        switch (key) {
            // current states
            case state['bed_occupied_either']:
                final String present = message.state ? 'present' : 'not present'
                updateCurrentState('bedOccupiedEither', present)
                updateChildPresenceSensor('either', present)
                break
            case state['bed_occupied_both']:
                final String present = message.state ? 'present' : 'not present'
                updateCurrentState('bedOccupiedBoth', present)
                updateChildPresenceSensor('both', present)
                break
            case state['bed_occupied_left']:
                final String present = message.state ? 'present' : 'not present'
                updateCurrentState('bedOccupiedLeft', present)
                updateChildPresenceSensor('left', present)
                break
            case state['bed_occupied_right']:
                final String present = message.state ? 'present' : 'not present'
                updateCurrentState('bedOccupiedRight', present)
                updateChildPresenceSensor('right', present)
                break
            case state['left_pressure']:
                updateCurrentState('leftPressure', round2(message.state as Double), '%')
                break
            case state['right_pressure']:
                updateCurrentState('rightPressure', round2(message.state as Double), '%')
                break
            case state['uptime']:
                updateCurrentState('uptimeSeconds', message.state as Integer, 's')
                syncDriverVersion()
                break
            case state['wifi_signal_db']:
                updateCurrentState('rssi', message.state as Integer, 'dBm')
                break
            case state['wifi_signal_percent']:
                updateCurrentState('wifiSignalPercent', message.state as Integer, '%')
                break
            // preferences
            case state['left_trigger_pressure']:
                updatePreference('leftTriggerPressure', round2(message.state))
                break
            case state['right_trigger_pressure']:
                updatePreference('rightTriggerPressure', round2(message.state))
                break
            case state['full_range']:
                updatePreference('fullRange', message.state)
                break
            case state['response_speed']:
                updatePreference('responseSpeed', [value: message.state, type: 'enum'])
                break
            default:
                if (logDriverEnable) { log.debug "Key does not have associate case. Message: ${message}" }
                break
        }
    } else {
        if (logDriverEnable) { log.debug "State is null, skipping. Message: ${message}" }
    }
}

private void createChildPresenceSensor(final String side) {
    final String dni = "${device.deviceNetworkId}-bed_occupied_${side}"

    if (!getChildDevice(dni)) {
        def child = addChildDevice('hubitat', 'Generic Component Presence Sensor', dni,
                       [name: "${device.displayName} Bed Occupied ${side.capitalize()}", isComponent: true])

        child.sendEvent(name: 'presence', value: 'not present') // set default value
        log.info "Created child device: ${dni}"
    }
}

private void deleteChildPresenceSensor(final String side) {
    final String dni = "${device.deviceNetworkId}-bed_occupied_${side}"

    if (getChildDevice(dni)) {
        deleteChildDevice(dni)
        log.info "Deleted child device: ${dni}"
    }
}

private void updateChildPresenceSensor(final String side, final Object value) {
    final String dni = "${device.deviceNetworkId}-bed_occupied_${side}"
    def child = getChildDevice(dni)
    if (child) {
        final String descriptionText = "${child.displayName} was set to ${value}"
        child.sendEvent(name: 'presence', value: value, descriptionText: descriptionText, isStateChange: true)
    } else {
        if (settings.logDriverEnable) { log.debug "Missing child:  ${dni}" }
    }
}

private void updateCurrentState(final String attribute, final Object value, final String unit = null) {
    final String descriptionText = "Current state ${attribute} was set to ${value}${unit ?: ''}"
    if (device.currentValue(attribute) != value) {
        sendEvent(name: attribute, value: value, unit: unit, descriptionText: descriptionText, isStateChange: true)
        if (settings.logTextEnable) { log.info descriptionText }
    }
}

private void updatePreference(final String attribute, final Object value) {
    final String descriptionText = "Preference ${attribute} was set to ${value}"
    device.updateSetting(attribute, value)
    if (settings.logTextEnable) { log.info descriptionText }
}

private double round2(final double value) {
    return Math.round(value * 100) / 100.0
}

private void syncDriverVersion() {
    final String DRIVER_VERSION = "2025.6.0"

    final String stored = device.getDataValue("Driver Version")
    if (stored != DRIVER_VERSION) {
        device.updateDataValue('Driver Version', DRIVER_VERSION)
    }
}

// Include the ESPHome API library helper
#include esphome.espHomeApiHelper
