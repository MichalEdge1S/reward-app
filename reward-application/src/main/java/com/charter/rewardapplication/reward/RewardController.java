package com.charter.rewardapplication.reward;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/rewards")
@RequiredArgsConstructor
public class RewardController {

    private final RewardService rewardService;

    @GetMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    EntityModel<RewardDto> getTotalPointsForCustomer(@PathVariable Long customerId) {

        return EntityModel.of(rewardService.getTotalPointsForCustomer(customerId),
                linkTo(methodOn(RewardController.class).getTotalPointsForCustomer(customerId)).withSelfRel());

    }

    @GetMapping("/{customerId}/lastThreeMonths")
    @ResponseStatus(HttpStatus.OK)
    CollectionModel<EntityModel<RewardDto>> getPointsForLastThreeMonths(@PathVariable Long customerId) {
        List<EntityModel<RewardDto>> rewards = rewardService.getRewardsForLastThreeMonths(customerId)
                .stream()
                .map(reward -> EntityModel.of(reward,
                        linkTo(methodOn(RewardController.class).getPointsForLastThreeMonths(customerId)).withRel("lastThreeMonths")))
                .collect(Collectors.toList());

        return CollectionModel.of(rewards,
                linkTo(methodOn(RewardController.class).getPointsForLastThreeMonths(customerId)).withSelfRel());
    }

}
