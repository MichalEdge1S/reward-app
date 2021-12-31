package com.charter.rewardapplication.purchase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.charter.rewardapplication.common.entity.BaseEntity;
import com.charter.rewardapplication.customer.Customer;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
@Entity
public class Purchase extends BaseEntity {

    private BigDecimal price;
    private LocalDate date;
    private int rewardPoints;

    @ManyToOne
    @JoinColumn
    private Customer customer;

    public PurchaseDto toDto() {
        return PurchaseDto.builder()
                .id(id)
                .price(price)
                .date(date)
                .rewardPoints(rewardPoints)
                .customerId(customer.getId())
                .build();
    }

}
