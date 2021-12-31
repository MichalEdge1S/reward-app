package com.charter.rewardapplication.common.data;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.charter.rewardapplication.customer.Customer;
import com.charter.rewardapplication.customer.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerDataInitializer {

    public static final String CUSTOMER_1_FIRST_NAME = "James";
    public static final String CUSTOMER_1_LAST_NAME = "Bond";
    public static final String CUSTOMER_2_FIRST_NAME = "Auric";
    public static final String CUSTOMER_2_LAST_NAME = "Goldfinger";

    private final CustomerRepository customerRepository;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        customerRepository.save(Customer.builder()
                .firstName(CUSTOMER_1_FIRST_NAME)
                .lastName(CUSTOMER_1_LAST_NAME)
                .build());
        customerRepository.save(Customer.builder()
                .firstName(CUSTOMER_2_FIRST_NAME)
                .lastName(CUSTOMER_2_LAST_NAME)
                .build());

    }

}
