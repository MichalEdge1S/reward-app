package com.charter.rewardapplication.purchase.validator;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.charter.rewardapplication.purchase.PurchaseDto;

@Component
public class PurchaseNotFromFutureValidator implements PurchaseValidator {

    @Override
    public void validate(PurchaseDto purchaseDto) {
        if (purchaseDto.getDate().isAfter(LocalDate.now()))
            throwException("Purchase cannot be from the future!");
    }

}
