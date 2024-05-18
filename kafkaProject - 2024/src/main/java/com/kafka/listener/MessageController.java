package com.kafka.listener;

import com.kafka.alertAndRecommendation.AlertAndRecommendationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/kafka")
@RestController
public class MessageController {

    @Autowired
    KafkaListeners listener;

    @Autowired
    AlertAndRecommendationServiceImpl alertAndRecommendationManager;

    @GetMapping("/generatedData")
    @ResponseBody
    public List<String> getMessages() {
        return listener.getMessages();
    }

    @GetMapping("/alerts")
    @ResponseBody
    public List<String> getAlerts(){
        List<String> alerts=alertAndRecommendationManager.getAlerts();
        return alerts;
    }

    @GetMapping("/recommandations")
    @ResponseBody
    public List<String> getRecommendations(){
        List<String> recommendations=alertAndRecommendationManager.getRecommendations();
        return recommendations;
    }
}
