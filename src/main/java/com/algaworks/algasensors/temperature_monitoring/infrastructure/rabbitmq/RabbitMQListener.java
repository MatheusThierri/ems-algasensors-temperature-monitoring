package com.algaworks.algasensors.temperature_monitoring.infrastructure.rabbitmq;

import com.algaworks.algasensors.temperature_monitoring.api.model.TemperatureLogData;
import com.algaworks.algasensors.temperature_monitoring.domain.service.TemperatureMonitoringService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Duration;

import static com.algaworks.algasensors.temperature_monitoring.infrastructure.rabbitmq.RabbitMQConfig.QUEUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQListener {
    private final TemperatureMonitoringService temperatureMonitoringService;

    @RabbitListener(queues = QUEUE)
    @SneakyThrows
    public void handle(@Payload TemperatureLogData temperatureLogData) {
        temperatureMonitoringService.processTemperatureReading(temperatureLogData);
        Thread.sleep(Duration.ofSeconds(5));
    }
}
