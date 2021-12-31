package com.charter.rewardapplication.customer;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.charter.rewardapplication.common.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
@Entity
public class Customer extends BaseEntity {

    private String firstName;
    private String lastName;

}
