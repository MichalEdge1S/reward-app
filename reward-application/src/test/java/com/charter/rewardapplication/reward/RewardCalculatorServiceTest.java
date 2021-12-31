package com.charter.rewardapplication.reward;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class RewardCalculatorServiceTest {

    @Autowired
    RewardCalculatorService rewardCalculatorService;

    @ParameterizedTest
    @MethodSource("rewardsForPrices")
    void shouldCalculateRewardPoints(BigDecimal price, Integer points) {
        //when
        int result = rewardCalculatorService.calculatePoints(price);

        //then
        assertEquals(points, result);
    }

    private static Stream<Arguments> rewardsForPrices() {
        return Stream.of(
                Arguments.of(BigDecimal.valueOf(0), 0),
                Arguments.of(BigDecimal.valueOf(20), 0),
                Arguments.of(BigDecimal.valueOf(50), 0),
                Arguments.of(BigDecimal.valueOf(70), 20),
                Arguments.of(BigDecimal.valueOf(100), 50),
                Arguments.of(BigDecimal.valueOf(120), 90),
                Arguments.of(BigDecimal.valueOf(200), 250)
        );
    }

}
