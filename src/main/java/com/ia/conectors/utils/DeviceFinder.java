package com.ia.conectors.utils;

import java.util.List;
import java.util.stream.Collectors;

import com.ia.conectors.model.DeviceDTO;

public class DeviceFinder {
	
    public DeviceDTO buscarPorNombre(List<DeviceDTO> devices, String nombreBuscado) {
        return devices.stream()
                .filter(d -> d.getDevicename().equalsIgnoreCase(nombreBuscado))
                .findFirst()
                .orElse(null);
    }


    public List<DeviceDTO> buscarPorNombres(List<DeviceDTO> devices, List<String> nombresBuscados) {
        return devices.stream()
                .filter(d -> nombresBuscados.stream()
                        .anyMatch(name -> d.getDevicename().equalsIgnoreCase(name)))
                .collect(Collectors.toList());
    }

}
