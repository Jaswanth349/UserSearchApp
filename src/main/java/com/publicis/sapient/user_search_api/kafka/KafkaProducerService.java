package com.publicis.sapient.user_search_api.kafka;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    private static final String TOPIC = "users_topic";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RestTemplate restTemplate;

    @Value("${api.url.users}")
    private String apiUrl; // "https://dummyjson.com/users"

    @Autowired
    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate, RestTemplate restTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "fallbackFetchDataAndSendToKafka")
    public void fetchDataAndSendToKafka() throws JsonProcessingException {
        try{
        logger.debug("Fetching data from external API: {}", apiUrl);
        ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, Map.class);
        logger.info("Fetched data: {}", response.getBody());
        List<Map<String, Object>> users = (List<Map<String, Object>>) response.getBody().get("users");
        for (Map<String, Object> user : users) {
            String userJson = new ObjectMapper().writeValueAsString(user);
            kafkaTemplate.send(TOPIC, userJson);
            logger.debug("Published user to Kafka: {}", userJson);
        }
        }catch (Exception e) {
                logger.error("Error fetching or processing data: {}", e.getMessage(), e);
                throw e;
            }
    }

    public void fallbackFetchDataAndSendToKafka(Exception ex) {
        logger.error("Circuit Breaker triggered! Fallback method executed: {}", ex.getMessage());
    }
}


