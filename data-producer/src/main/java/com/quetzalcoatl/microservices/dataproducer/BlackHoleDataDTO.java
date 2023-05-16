package com.quetzalcoatl.microservices.dataproducer;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BlackHoleDataDTO {
    @NotNull
    private Float accretionRate;
    @NotNull
    private Integer emission;
    @NotNull
    private Float gravitationalWavesDensity;
    @NotNull
    private Integer eventHorizonStability;
}
