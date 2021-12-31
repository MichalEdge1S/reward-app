package com.charter.rewardapplication.purchase;

import java.math.BigDecimal;
import java.time.LocalDate;

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
public class PurchaseDto {

    Long id;
    BigDecimal price;
    LocalDate date;
    int rewardPoints;
    Long customerId;

}
