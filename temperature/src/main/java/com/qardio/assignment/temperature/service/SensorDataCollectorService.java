package com.qardio.assignment.temperature.service;

import com.qardio.assignment.temperature.dto.response.DataCollectionStatusResponse;

import java.util.List;

/**
 * Generic collector service APIs.
 */
public interface SensorDataCollectorService<Data> {

    DataCollectionStatusResponse collect(Data data);

    List<DataCollectionStatusResponse> collect(List<Data> dataList);
}
