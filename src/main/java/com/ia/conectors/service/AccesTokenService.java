package com.ia.conectors.service;

import java.security.MessageDigest;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ia.conectors.constants.ApiConstants;
import com.ia.conectors.model.TokenInfo;


@Service
public class AccesTokenService {

	 private final RestTemplate restTemplate = new RestTemplate();
	    private final ObjectMapper objectMapper = new ObjectMapper();

	    // Cache de tokens por account
	    private final Map<String, TokenInfo> tokenCache = new ConcurrentHashMap<>();


	    /**
	     * Obtiene token dinámico por account y password
	     */
	    public String obtenerToken(String account, String password) throws Exception {
	        long now = Instant.now().getEpochSecond();
	        String timeStr = String.valueOf(now);

	        return tokenCache.compute(account, (key, info) -> {
	            try {
	                if (info == null || info.expiry <= now) {

	                    String passwordMd5 = md5(password);
	                    String rawSignature = passwordMd5 + timeStr;
	                    String signature = md5(rawSignature);


	                    String url = String.format(ApiConstants.URL_AUTHORIZATION_TOKEN,
	                            ApiConstants.API_BASE_PROTRACK, timeStr, account, signature);

	                    String response = restTemplate.getForObject(url, String.class);
	                    JsonNode json = objectMapper.readTree(response);

	                    if (json.get("code").asInt() != 0) {
	                        throw new RuntimeException("Error obteniendo token: " + json.toString());
	                    }

	                    String accessToken = json.get("record").get("access_token").asText();
	                    long expiresIn = json.get("record").get("expires_in").asLong();
	                    return new TokenInfo(accessToken, now + expiresIn);
	                }
	                return info;
	            } catch (Exception e) {
	                throw new RuntimeException(e);
	            }
	        }).token;
	    }



	    /**
	     * Función MD5
	     */
	    private String md5(String input) throws Exception {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        byte[] digest = md.digest(input.getBytes());
	        StringBuilder sb = new StringBuilder();
	        for (byte b : digest) {
	            sb.append(String.format("%02x", b));
	        }
	        return sb.toString();
	    }
}
