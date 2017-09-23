package clients;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public final class KafkaInsuranceProducer {

    final Producer<String, String> producer;
    private final String topicName;

    /// {todo} class immutability comprised due to unit test
    /// {todo} need to set producer as mock producer for unit test
    /// {todo} added new constructor that takes producer as input
    /*public KafkaInsuranceProducer(final Properties kafkaProperties, final String topicName) {
        producer = new KafkaProducer<>(kafkaProperties);
        this.topicName =topicName;
    }*/

    public KafkaInsuranceProducer(final Producer<String, String> producer,
                                  final String topicName) {
        this.producer = producer;
        this.topicName =topicName;
    }

    public void close() {
        this.producer.close();
    }

    public void send(final String message) {
        producer.send(new ProducerRecord<>(this.topicName, message));
        this.producer.flush();
    }
}
