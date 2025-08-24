package com.ia.conectors.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ia.conectors.constants.ApiConstants;
import com.ia.conectors.model.VehicleDTO;

@Service
public class GetLocationService {
	
	 private final RestTemplate restTemplate = new RestTemplate();
	    private final ObjectMapper objectMapper = new ObjectMapper();
	
	    
	public List<VehicleDTO> getLocation(String accessToken, List<String> imeis) throws Exception {
        String imeisParam = String.join(",", imeis);
        String url = String.format("%s/api/track?access_token=%s&imeis=%s",
                ApiConstants.API_BASE_PROTRACK, accessToken, imeisParam);


        String response = restTemplate.getForObject(url, String.class);
        JsonNode json = objectMapper.readTree(response);

        // Verificar código de respuesta
        if (json.get("code").asInt() != 0) {
            throw new RuntimeException("Error obteniendo ubicaciones: " + json.toString());
        }

        // Convertir JsonNode que contiene array a List<Vehiculo>
        JsonNode recordNode = json.get("record");
        List<VehicleDTO> vehiculos = objectMapper.convertValue(
                recordNode,
                new TypeReference<List<VehicleDTO>>() {}
        );

        return vehiculos;
    }

}
