package main;

import clients.CsvReader;
import clients.KafkaInsuranceProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.ImmutableList;
import models.FlInsuranceData;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Main Class responsible for getting and validating input parameters
 * and executing kafka producer to send messages
 * @author tahir ali
 */
public class Main {
    private static final String SPLITTER = ",";
    private static final String FILE_PATH = "/opt/FL_insurance_sample2.csv";
    private static final String SERVER = "192.168.1.8:9092";
    private static final String TOPIC_NAME = "insurance-data";
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static void main(String[] args) throws IOException {

        final File csvFile = new File(FILE_PATH);
        if (!csvFile.exists()) {
            throw new FileNotFoundException("File not found");
        }
        final ImmutableList<FlInsuranceData> insuranceDataList = ImmutableList
                .copyOf(
                        new CsvReader(SPLITTER)
                                .loadCsvContentToList(new BufferedReader(new FileReader(csvFile)))
                );
        if(insuranceDataList.size() == 0) {
            System.out.println("No Data Found in File");
            return;
        }
        final Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        final KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        final KafkaInsuranceProducer kafkaInsuranceProducer =
                new KafkaInsuranceProducer(
                        producer,
                        TOPIC_NAME);
        insuranceDataList.forEach(policy -> {
            try {
                kafkaInsuranceProducer.send(mapper.writeValueAsString(policy));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } finally {
                kafkaInsuranceProducer.close();
            }
        });
    }
}
