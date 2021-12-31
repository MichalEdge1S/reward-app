package com.charter.rewardapplication.reward;

import java.time.Month;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Value;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@ToString
public class RewardDto {

    int rewardPoints;
    Month month;

}
