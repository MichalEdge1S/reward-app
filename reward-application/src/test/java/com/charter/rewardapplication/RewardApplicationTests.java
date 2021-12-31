package com.charter.rewardapplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.charter.rewardapplication.purchase.PurchaseDto;

import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.MethodName.class)
public class RewardApplicationTests {

    @Autowired
    protected WebTestClient client;

    @ParameterizedTest
    @MethodSource("prepareCorrectPurchases")
    @Order(1)
    void shouldAddPurchase(LocalDate date, BigDecimal price, Long customerId) {
        client.post().uri("api/purchases")
                .body(Mono.just(PurchaseDto.builder()
                        .date(date)
                        .price(price)
                        .customerId(customerId)
                        .build()), PurchaseDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.date").isEqualTo(date.toString())
                .jsonPath("$.price").isEqualTo(price)
                .jsonPath("$.customerId").isEqualTo(customerId);
    }

    private static Stream<Arguments> prepareCorrectPurchases() {
        return Stream.of(
                Arguments.of(LocalDate.now(), BigDecimal.valueOf(120), 1L),
                Arguments.of(LocalDate.now().minusMonths(1), BigDecimal.valueOf(80), 1L),
                Arguments.of(LocalDate.now().minusMonths(2), BigDecimal.valueOf(25), 1L),
                Arguments.of(LocalDate.now().minusMonths(2), BigDecimal.valueOf(150), 1L),
                Arguments.of(LocalDate.now().minusMonths(3), BigDecimal.valueOf(500), 1L),
                Arguments.of(LocalDate.now().minusMonths(3), BigDecimal.valueOf(100), 1L),
                Arguments.of(LocalDate.now().minusMonths(4), BigDecimal.valueOf(1000), 1L),
                Arguments.of(LocalDate.now().minusMonths(4), BigDecimal.valueOf(423.52), 2L)
        );
    }

    @Test
    @Order(2)
    void shouldNotAddPurchaseWhenCustomerNotExists() {
        client.post().uri("api/purchases")
                .body(Mono.just(PurchaseDto.builder()
                        .date(LocalDate.now())
                        .price(BigDecimal.ONE)
                        .customerId(99L)
                        .build()), PurchaseDto.class)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().jsonPath("$").isEqualTo("Customer with id: 99 doesn't exist");
    }

    @ParameterizedTest
    @MethodSource("prepareIncorrectPurchases")
    @Order(3)
    void shouldNotAddPurchaseWhenValidationFailed(LocalDate date, BigDecimal price, String errorMessage) {
        client.post().uri("api/purchases")
                .body(Mono.just(PurchaseDto.builder()
                        .date(date)
                        .price(price)
                        .customerId(1L)
                        .build()), PurchaseDto.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody().jsonPath("$").isEqualTo("Validation failed: " + errorMessage);
    }

    private static Stream<Arguments> prepareIncorrectPurchases() {
        return Stream.of(
                Arguments.of(LocalDate.now().plusDays(10), BigDecimal.valueOf(120), "Purchase cannot be from the future!"),
                Arguments.of(LocalDate.now().minusDays(40), BigDecimal.valueOf(-1.23), "Price must be positive!")
        );
    }

    @ParameterizedTest
    @MethodSource("prepareCorrectCustomerIdsWithTotalPoints")
    @Order(4)
    void shouldGetTotalRewardPointsForCustomer(Long id, int points) {
        client.get().uri("api/rewards/" + id)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.rewardPoints").isEqualTo(points);
    }

    private static Stream<Arguments> prepareCorrectCustomerIdsWithTotalPoints() {
        return Stream.of(
                Arguments.of(1L, 3020),
                Arguments.of(2L, 696)
        );
    }

    @Test
    @Order(5)
    void shouldGetRewardsForLastThreeMonths() {
        client.get().uri("api/rewards/1/lastThreeMonths")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$._embedded.rewardDtoList[0].rewardPoints").isEqualTo(900)
                .jsonPath("$._embedded.rewardDtoList[1].rewardPoints").isEqualTo(150)
                .jsonPath("$._embedded.rewardDtoList[2].rewardPoints").isEqualTo(30);
    }

}
