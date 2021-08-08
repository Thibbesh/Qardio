package com.qardio.assignment.temperature.exception;

/**
 * Exception class in case InfluxDB data could not be accessed.
 */
public class KafkaRecordProducerException extends RuntimeException{
    public KafkaRecordProducerException(String message) {
        super(message);
    }
}
