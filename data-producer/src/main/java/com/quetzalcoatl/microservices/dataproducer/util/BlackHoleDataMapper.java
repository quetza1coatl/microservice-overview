package com.quetzalcoatl.microservices.dataproducer.util;

import com.quetzalcoatl.microservices.avro.BlackHoleData;
import com.quetzalcoatl.microservices.dataproducer.model.BlackHoleDataDTO;

import java.util.UUID;

public final class BlackHoleDataMapper {
    private BlackHoleDataMapper(){

    }
    public static BlackHoleData convertFromDto(BlackHoleDataDTO dto){
        BlackHoleData data = new BlackHoleData();
        data.setDataId(UUID.randomUUID().toString());
        data.setSensorId(dto.getSensorId());
        data.setAccretionRate(dto.getAccretionRate());
        data.setXRayEmission(dto.getEmission());
        data.setGravitationalWavesDensity(dto.getGravitationalWavesDensity());
        data.setEventHorizonStability(dto.getEventHorizonStability());
        return data;
    }
}
