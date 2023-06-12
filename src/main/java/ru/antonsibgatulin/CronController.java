package ru.antonsibgatulin;

import jp.konosuba.include.cron.Cron;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONObject;
import redis.clients.jedis.Jedis;

import java.util.Properties;

public class CronController extends Thread{

    private Jedis jedis;
    private HttpClient httpClient = new HttpClient();


    private KafkaProducer<String,String> producer;


    public CronController(Jedis jedis){
        this.jedis = jedis;

        Properties props = new Properties();
        props.put("bootstrap.servers", App.config.getKafka_host() + ":" + App.config.getKafka_port());
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<>(props);


        start();
    }


    @Override
    public void run(){
        while(true){
            try {
                Thread.sleep(App.config.getTime_poll());
                var task = jedis.rpop("task#main");
                if(task == null)continue;
                System.out.println(task);
                var cron = ClassUtils.fromStringToCron(task);
                if(cron==null){
                    continue;
                }
                int code = httpClient.getResponseCode(cron.getHttp());
                System.out.println(code + task);
                if(code != cron.getCodeFine()){

                    cronError(cron);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    public void sendMessageInKafka(String message) {
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(App.config.getKafkaProducerTopic(), null, message);
        producer.send(record);
    }
    private void cronError(Cron cron) {
        var jsonObject = new JSONObject();
        jsonObject.put("typeOperation","error_cron");
        jsonObject.put("cronId",cron.getId());

        sendMessageInKafka(jsonObject.toString());
    }
}
