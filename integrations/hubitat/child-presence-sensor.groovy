metadata {
    definition(
        name: 'Elevated Sensors - Presence Sensor Child',
        namespace: 'elevated_sensors',
        author: 'Elevated Sensors',
        singleThreaded: true,
        importUrl: 'https://raw.githubusercontent.com/ElevatedSensors/sensor-configs/main/integrations/hubitat/child-presence-sensor.groovy') {
        
        capability 'PresenceSensor'
    }
}

void setState(final Object value) {
    final String descriptionText = "${device.label} was set to ${value}"
    sendEvent(name: 'presence', value: value, descriptionText: descriptionText, isStateChange: true)
    if (settings.logTextEnable) {log.info descriptionText }
}
