package com.charter.rewardapplication.purchase.validator;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.charter.rewardapplication.purchase.PurchaseDto;

@Component
public class PurchasePositiveValidator implements PurchaseValidator {

    @Override
    public void validate(PurchaseDto purchaseDto) {
        if (purchaseDto.getPrice().compareTo(BigDecimal.ZERO) < 0)
            throwException("Price must be positive!");
    }

}
