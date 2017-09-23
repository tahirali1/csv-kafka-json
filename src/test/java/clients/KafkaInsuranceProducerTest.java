package clients;

import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class KafkaInsuranceProducerTest {

    private static final String TOPIC_NAME = "test";
    private static final String SERVER = "localhost:9092";
    private static final String INPUT_MESSAGE = "{"
            + "\"policyId\":119736,"
            + "\"stateCode\":\"FL\","
            + "\"country\":\"CLAY COUNTY\","
            + "\"construction\" : \"Residential\"}";
    private static final String KEY = "insurance-data";
    private MockProducer<String, String> producer;
    private KafkaInsuranceProducer kafkaInsuranceProducer;

    @Before
    public void setUp() throws Exception {
        final Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        producer = new MockProducer<>(
                true, new StringSerializer(), new StringSerializer());
        kafkaInsuranceProducer = new KafkaInsuranceProducer(producer,TOPIC_NAME);
    }

    @Test
    public void testSend() throws InterruptedException, IOException {
        kafkaInsuranceProducer.send(INPUT_MESSAGE);
        List<ProducerRecord<String, String>> history = producer.history();
        List<ProducerRecord<String, String>> expected = Arrays.asList(
                new ProducerRecord<>(TOPIC_NAME, INPUT_MESSAGE));
        Assert.assertEquals("Sent didn't match expected", expected, history);
    }
}
