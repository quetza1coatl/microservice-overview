package com.quetzalcoatl.microservices.dataproducer.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
public class BlackHoleDataDTO {
    @NotNull
    private SensorId sensorId;
    @NotNull
    @PositiveOrZero
    private Float accretionRate;
    @NotNull
    @PositiveOrZero
    private Integer emission;
    @NotNull
    @PositiveOrZero
    private Float gravitationalWavesDensity;
    @NotNull
    private Integer eventHorizonStability;
}
