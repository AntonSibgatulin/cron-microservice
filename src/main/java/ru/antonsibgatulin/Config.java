package ru.antonsibgatulin;

import lombok.Data;

@Data
public class Config {
    private String kafkaProducerTopic;
    private String redis_host;
    private String redis_port;

    private Long time_poll;
    private String kafka_host;
    private String kafka_port;
}
