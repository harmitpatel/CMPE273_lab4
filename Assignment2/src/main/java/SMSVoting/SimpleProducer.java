package SMSVoting;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import java.util.Properties;


public class SimpleProducer {
	
private static Producer<Integer, String> producer;
private final Properties properties = new Properties();

public SimpleProducer() {
	
        properties.put("metadata.broker.list", "54.149.84.25:9092");
        properties.put("serializer.class", "kafka.serializer.StringEncoder");
        properties.put("request.required.acks", "1");
        producer = new Producer<>(new ProducerConfig(properties));
        
    }
public void kafka(String topic, String msg) {
	
new SimpleProducer();
//String topic = args[0];
////String msg = args[1];
KeyedMessage<Integer, String> data = new KeyedMessage<>(topic, msg);
        producer.send(data);
        producer.close();
        
    }

/*
public static void main(String[] args) {
new SimpleProducer();
String topic = "cmpe273-topic";
String msg = "mbarve@live.com:010125505:Poll Result [Android=100,iPhone=200]";
KeyedMessage<Integer, String> data = new KeyedMessage<>(topic, msg);
        producer.send(data);
        producer.close();
    }
*/
}
