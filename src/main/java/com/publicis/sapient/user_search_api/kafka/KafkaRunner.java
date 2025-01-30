package com.publicis.sapient.user_search_api.kafka;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class KafkaRunner implements CommandLineRunner {

    private final KafkaProducerService kafkaProducerService;

    public KafkaRunner(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    public void run(String... args) throws Exception {
            try {
                kafkaProducerService.fetchDataAndSendToKafka();
            } catch (Exception e) {
                System.out.println("Exception occurred: " + e.getMessage());
            }
        }

}

