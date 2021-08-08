package com.qardio.assignment.temperature.util;

import com.qardio.assignment.temperature.dto.message.TemperatureSensorDataMessage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TemperatureSensorDataMessageDeserializerTest {

    @Test
    public void shouldDeserializeTemperatureSensorDataMessageByteArrayToObject() {

        // Given

        TemperatureSensorDataMessageDeserializer deserializer = new TemperatureSensorDataMessageDeserializer();

        byte[] data = "{\"deviceId\":\"123\",\"temperatureInFahrenheit\":10.0,\"unixTimestamp\":1563142799}".getBytes();

        // When
        TemperatureSensorDataMessage message = deserializer.deserialize("any topic", data);

        // Then
        assertNotNull(message);
        assertEquals("123", message.getDeviceId());
        assertEquals(10.0, ((TemperatureSensorDataMessage) message).getTemperatureInFahrenheit(), 1e-15);
        assertEquals(1563142799, message.getUnixTimestamp());
    }
}
