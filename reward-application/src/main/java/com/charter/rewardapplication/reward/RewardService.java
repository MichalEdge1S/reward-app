package com.charter.rewardapplication.reward;

import org.springframework.stereotype.Service;

import com.charter.rewardapplication.purchase.PurchaseDto;
import com.charter.rewardapplication.purchase.PurchaseService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RewardService {

    private final PurchaseService purchaseService;

    public RewardDto getTotalPointsForCustomer(Long customerId) {
        return RewardDto.builder()
                .rewardPoints(purchaseService.getAllPurchasesByCustomer(customerId)
                        .stream()
                        .map(PurchaseDto::getRewardPoints)
                        .reduce(0, Integer::sum))
                .build();
    }

    public List<RewardDto> getRewardsForLastThreeMonths(Long customerId) {
        LocalDate dateFrom = LocalDate.now().minusMonths(3).withDayOfMonth(1);
        LocalDate dateTo = LocalDate.now().withDayOfMonth(1).minusDays(1);

        return purchaseService.getAllPurchasesByCustomerAndDateAfter(customerId, dateFrom, dateTo)
                .stream()
                .collect(Collectors.groupingBy(purchaseDto -> purchaseDto.getDate().getMonth()))
                .entrySet()
                .stream()
                .map(entry -> RewardDto.builder()
                        .month(entry.getKey())
                        .rewardPoints(this.calculatePointsForMonth(entry.getValue()))
                        .build())
                .sorted(Comparator.comparing(RewardDto::getMonth))
                .collect(Collectors.toList());

    }

    private int calculatePointsForMonth(List<PurchaseDto> purchaseDtos) {
        return purchaseDtos.stream()
                .map(PurchaseDto::getRewardPoints)
                .reduce(0, Integer::sum);
    }

}
