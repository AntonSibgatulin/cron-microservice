package ru.antonsibgatulin;

import org.json.JSONObject;
import redis.clients.jedis.Jedis;

import java.io.*;

/**
 * Hello world!
 *
 */
public class App 
{

    public static Config config;
    public static void main( String[] args )
    {
        config = new Config();
        loadConfig("configure/config.json");
        var jedis = new Jedis(config.getRedis_host(),Integer.valueOf(config.getRedis_port()));
        var cronController = new CronController(jedis);

    }

    private static JSONObject loadFile(String path) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(path)));
            String all = "";
            String pie = null;
            while ((pie = bufferedReader.readLine()) != null) {
                all += pie;
            }
            System.out.println(all);
            return new JSONObject(all);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void loadConfig(String path) {
        JSONObject jsonObject = loadFile(path);
        config.setRedis_host(jsonObject.getString("redis_host"));
        config.setRedis_port(jsonObject.getString("redis_port"));
        config.setKafka_host(jsonObject.getString("kafka_host"));
        config.setKafka_port(jsonObject.getString("kafka_port"));
        config.setKafkaProducerTopic(jsonObject.getString("kafkaProducerTopic"));
        config.setTime_poll(jsonObject.getLong("time_poll"));
    }
}
