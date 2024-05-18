package com.kafka.mysensor;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.Date;

@WebService(endpointInterface = "com.kafka.mysensor.Sensor")
@Service
public class HeartRateSensor implements Sensor {
    public final String topicName;
    public double heartRateValue;
    public final String sensorType;
    public final String unit;
    public final String alertTopic;
    public Date date;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public HeartRateSensor(KafkaTemplate<String, String> kafkaTemplate) {
        this.topicName = "heartrate";
        this.sensorType = "HEARTRATE";
        this.alertTopic = "heartrateAlert";
        this.unit = "bpm";
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String getData() {
        return this.toString();
    }

    @Override
    public String getSensorType() {
        return sensorType;
    }

    @Override
    public String toString() {
        return "{Type de capteur: " + this.sensorType + ", Valeur: " + String.format("%.2f", this.heartRateValue) + ", Unité: " + this.unit  + " Date: " + this.date +"}";
    }

    public void generateData() {
        this.heartRateValue = 50 + Math.random() * 70; // 60 -> 100 bpm
        this.date = new Date();
    }

    public void captureData() {
        // Generating the data
        generateData();
        kafkaTemplate.send(topicName, toString());
        kafkaTemplate.send(alertTopic, String.format("%.2f", this.heartRateValue));
        synchronized (Sensor.SHARED_TOPIC) {
            kafkaTemplate.send(Sensor.SHARED_TOPIC, toString());
        }
    }
}
