package com.algaworks.algasensors.temperature_monitoring.domain.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EqualsAndHashCode
public class SensorAlert {
    @Id
    @AttributeOverride(name = "value", column = @Column(name = "id", columnDefinition = "bigint"))
    private SensorId id;

    private Double maxTemperature;
    private Double minTemperature;
}
