package ru.antonsibgatulin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jp.konosuba.include.cron.Cron;

public class ClassUtils {

    public static Cron fromStringToCron(String cron){
        var objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(cron,Cron.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
