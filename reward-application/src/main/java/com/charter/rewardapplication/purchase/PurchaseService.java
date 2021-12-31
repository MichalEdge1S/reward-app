package com.charter.rewardapplication.purchase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.charter.rewardapplication.customer.CustomerRepository;
import com.charter.rewardapplication.exception.CustomerNotFoundException;
import com.charter.rewardapplication.purchase.validator.PurchaseValidator;
import com.charter.rewardapplication.reward.RewardCalculatorService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final CustomerRepository customerRepository;
    private final RewardCalculatorService rewardService;
    private final List<PurchaseValidator> validators;

    public PurchaseDto addPurchase(PurchaseDto purchaseDto) {
        validators.forEach(validator -> validator.validate(purchaseDto));

        Purchase purchase = fromDto(purchaseDto);
        purchase.setRewardPoints(rewardService.calculatePoints(purchaseDto.getPrice()));

        return purchaseRepository
                .save(purchase)
                .toDto();
    }

    public List<PurchaseDto> getAllPurchasesByCustomer(Long customerId) {
        return purchaseRepository.getAllByCustomer_Id(customerId).stream()
                .map(Purchase::toDto)
                .collect(Collectors.toList());
    }

    public List<PurchaseDto> getAllPurchasesByCustomerAndDateAfter(Long customerId, LocalDate dateFrom, LocalDate dateTo) {
        return purchaseRepository.getAllByCustomer_IdAndDateBetween(customerId, dateFrom, dateTo).stream()
                .map(Purchase::toDto)
                .collect(Collectors.toList());
    }

    private Purchase fromDto(PurchaseDto purchaseDto) {
        return Purchase.builder()
                .id(purchaseDto.getId())
                .price(purchaseDto.getPrice())
                .date(purchaseDto.getDate())
                .rewardPoints(purchaseDto.getRewardPoints())
                .customer(customerRepository
                        .findById(purchaseDto.getCustomerId())
                        .orElseThrow(() -> new CustomerNotFoundException(purchaseDto.getCustomerId())))
                .build();
    }

}
