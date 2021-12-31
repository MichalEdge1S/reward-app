package com.charter.rewardapplication.reward;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RewardCalculatorService {

    //could be configurable eg. @ConfigurationProperties
    Map<Integer, Integer> pointThresholds = new LinkedHashMap<>();

    @PostConstruct
    void setPointThresholds() {
        pointThresholds.put(50, 1);
        pointThresholds.put(100, 2);
    }

    public int calculatePoints(BigDecimal price) {
        int points = 0;
        int usedMultiplier = 0;

        for (Map.Entry<Integer, Integer> entry: pointThresholds.entrySet()) {
            if (entry.getKey() > price.intValue()) {
                break;
            }

            int multiplier = entry.getValue() - usedMultiplier;
            points += (price.intValue() - entry.getKey()) * multiplier;
            usedMultiplier += multiplier;
        }

        return points;
    }

}
