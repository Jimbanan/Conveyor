package com.neoflex.conveyor.controllers;

import com.neoflex.conveyor.dto.*;
import com.neoflex.conveyor.services.DealServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(("/deal"))
@RestController
public class DealController {

    @Autowired
    DealServiceImpl dealService;

    @PostMapping("/application")
    public List<LoanOfferDTO> offersDeal(@Valid @RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO) {

        loanApplicationRequestDTO.setApplicationId(dealService.addClient(loanApplicationRequestDTO));

        RestTemplate restTemplate = new RestTemplate();
        String uri_Offers = "http://localhost:8080/conveyor/offers";
        List<LoanOfferDTO> loanOfferList = restTemplate.postForObject(uri_Offers, loanApplicationRequestDTO, List.class);

        return loanOfferList;
    }

    @PutMapping("/offer")
    public void offers(@RequestBody LoanOfferDTO loanOfferDTO) {

        dealService.addOffer(loanOfferDTO);

    }

    @PutMapping("/calculate/{applicationId}")
    public void calculate(@RequestBody FinishRegistrationRequestDTO finishRegistrationRequestDTO,
            @PathVariable Long applicationId) {

        ScoringDataDTO scoringDataDTO = dealService.createScoringDataDTO(finishRegistrationRequestDTO, applicationId);

        RestTemplate restTemplate = new RestTemplate();
        String uri_Calculate = "http://localhost:8080/conveyor/calculation";
        CreditDTO creditDTO = restTemplate.postForObject(uri_Calculate, scoringDataDTO, CreditDTO.class);

        System.out.println(creditDTO);
    }


}
