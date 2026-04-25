package com.algaworks.algasensors.temperature_monitoring.api.controller;

import com.algaworks.algasensors.temperature_monitoring.api.model.SensorAlertInput;
import com.algaworks.algasensors.temperature_monitoring.api.model.SensorAlertOutput;
import com.algaworks.algasensors.temperature_monitoring.domain.model.SensorAlert;
import com.algaworks.algasensors.temperature_monitoring.domain.model.SensorId;
import com.algaworks.algasensors.temperature_monitoring.domain.model.SensorMonitoring;
import com.algaworks.algasensors.temperature_monitoring.domain.repository.SensorAlertRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/sensors/{sensorId}/alert")
@RequiredArgsConstructor
public class SensorAlertController {
    private final SensorAlertRepository sensorAlertRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public SensorAlertOutput getDetail(@PathVariable TSID sensorId) {
        SensorAlert sensorAlert = returnSensor(sensorId);

        return SensorAlertOutput.builder()
                    .id(sensorAlert.getId().getValue())
                    .maxTemperature(sensorAlert.getMaxTemperature())
                    .minTemperature(sensorAlert.getMinTemperature())
                    .build();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public SensorAlertOutput createOrUpdate(@PathVariable TSID sensorId, @RequestBody SensorAlertInput input) {
        SensorAlert sensorAlert = findByIdOrDefault(sensorId);

        sensorAlert.setMinTemperature(input.getMinTemperature());
        sensorAlert.setMaxTemperature(input.getMaxTemperature());

        sensorAlertRepository.saveAndFlush(sensorAlert);

        return SensorAlertOutput.builder()
                .id(sensorAlert.getId().getValue())
                .maxTemperature(sensorAlert.getMaxTemperature())
                .minTemperature(sensorAlert.getMinTemperature())
                .build();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable TSID sensorId) {
        SensorAlert sensorAlert = returnSensor(sensorId);

        sensorAlertRepository.delete(sensorAlert);
    }

    private SensorAlert returnSensor(TSID sensorId) {
        return sensorAlertRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private SensorAlert findByIdOrDefault(TSID sensorId) {
        return sensorAlertRepository.findById(new SensorId(sensorId))
                .orElse(SensorAlert.builder()
                        .id(new SensorId(sensorId))
                        .maxTemperature(null)
                        .minTemperature(null)
                        .build());
    }
}
