package com.qardio.assignment.temperature.util;

import com.qardio.assignment.temperature.dto.message.TemperatureSensorDataMessage;

/**
 * Temperature sensor data Apache Kafka message deserializer class.
 */
public class TemperatureSensorDataMessageDeserializer  extends SensorDataMessageDeserializer<TemperatureSensorDataMessage>{

    public TemperatureSensorDataMessageDeserializer() {
        super(TemperatureSensorDataMessage.class);
    }
}
