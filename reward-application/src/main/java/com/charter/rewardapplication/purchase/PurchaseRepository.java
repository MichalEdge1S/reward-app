package com.charter.rewardapplication.purchase;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends CrudRepository<Purchase, Long> {

    List<Purchase> getAllByCustomer_Id(Long id);

    List<Purchase> getAllByCustomer_IdAndDateBetween(Long id, LocalDate dateFrom, LocalDate dateTo);

}


