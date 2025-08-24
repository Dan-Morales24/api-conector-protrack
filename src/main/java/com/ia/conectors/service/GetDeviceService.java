package com.ia.conectors.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ia.conectors.model.DeviceDTO;

@Service
public class GetDeviceService {

	private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String apiUrl = "http://api.protrack365.com";

    public List<DeviceDTO> obtenerDispositivos(String accessToken) throws Exception {
        String url = String.format("%s/api/device/list?access_token=%s", apiUrl, accessToken);

        String response = restTemplate.getForObject(url, String.class);
        JsonNode json = objectMapper.readTree(response);

        if (json.get("code").asInt() != 0) {
            throw new RuntimeException("Error en device/list: " + json.toString());
        }

        return objectMapper.convertValue(
                json.get("record"),
                new TypeReference<List<DeviceDTO>>() {}
        );
    }
	
}
