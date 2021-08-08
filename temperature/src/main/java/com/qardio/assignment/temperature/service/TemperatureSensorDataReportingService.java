package com.qardio.assignment.temperature.service;

import com.qardio.assignment.temperature.dto.response.TemperatureSensorDataResponse;
import com.qardio.assignment.temperature.model.TemperatureSensorQueryResult;
import com.qardio.assignment.temperature.repository.TemperatureSensorDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Temperature sensor data reporting service.
 */
@Slf4j
@Service
public class TemperatureSensorDataReportingService {

    private final LoggedInUserService loggedInUserService;
    private final TemperatureSensorDataRepository repository;

    public TemperatureSensorDataReportingService(LoggedInUserService loggedInUserService,
                                                 TemperatureSensorDataRepository repository) {
        this.loggedInUserService = loggedInUserService;
        this.repository = repository;
    }

    /**
     * Generate report filtered with start and end time of logged in device.
     *
     * @param startTime the start unix timestamp.
     * @param endTime   the end unix timestamp.
     * @return the DTO of aggregated data.
     */
    public TemperatureSensorDataResponse getReport(long startTime, long endTime) {

        List<TemperatureSensorQueryResult> queryResults
                = repository.getTemperatures(loggedInUserService.getLoggedInDeviceId(), startTime, endTime);

        List<TemperatureSensorDataResponse.TemperatureSensorData> dataPoints = queryResults.stream()
                .map(data -> new TemperatureSensorDataResponse.TemperatureSensorData(data.getTime().getEpochSecond(), data.getTemperatureInFahrenheit()))
                .collect(Collectors.toList());

        return TemperatureSensorDataResponse.builder()
                .deviceId(loggedInUserService.getLoggedInDeviceId())
                .data(dataPoints)
                .build();

    }
}
