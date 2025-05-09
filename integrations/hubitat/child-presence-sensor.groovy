metadata {
    definition(
        name: 'Child Presence Sensor',
        namespace: 'esphome',
        author: 'Elevated Sensors',
        singleThreaded: true,
        importUrl: 'https://raw.githubusercontent.com/ElevatedSensors/sensor-configs/main/integrations/hubitat/child-presence-sensor.groovy') {
        
        capability 'PresenceSensor'
    }
}

void setStatus(state) {
    final String descriptionText = "${device.label} was set to ${state}"
    sendEvent(name: 'presence', value: state, descriptionText: descriptionText, isStateChange: true)
    if (settings.logTextEnable) {log.info descriptionText }
}
