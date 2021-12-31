package com.charter.rewardapplication.common.validator;

import org.springframework.stereotype.Component;

import com.charter.rewardapplication.exception.ValidationException;
import com.charter.rewardapplication.purchase.PurchaseDto;

@Component
public interface Validator<T> {

    void validate(T t);

    default void throwException(String message) {
        throw new ValidationException(message);
    };

}
