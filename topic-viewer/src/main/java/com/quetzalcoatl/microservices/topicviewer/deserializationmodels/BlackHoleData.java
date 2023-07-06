package com.quetzalcoatl.microservices.topicviewer.deserializationmodels;

import lombok.Data;

import java.util.UUID;

@Data
public class BlackHoleData {
    private UUID dataId;
    private SensorId sensorId;
    private Float accretionRate;
    private Integer xRayEmission;
    private Float gravitationalWavesDensity;
    private Integer eventHorizonStability;
}
