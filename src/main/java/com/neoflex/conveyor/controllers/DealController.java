package com.neoflex.conveyor.controllers;

import com.neoflex.conveyor.dto.FinishRegistrationRequestDTO;
import com.neoflex.conveyor.dto.LoanApplicationRequestDTO;
import com.neoflex.conveyor.dto.LoanOfferDTO;
import com.neoflex.conveyor.dto.ScoringDataDTO;
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

    private final String uri = "http://localhost:8080/conveyor/offers";

    @PostMapping("/application")
    public List<LoanOfferDTO> offersDeal(@Valid @RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO) {

        loanApplicationRequestDTO.setApplicationId(dealService.addClient(loanApplicationRequestDTO));

        RestTemplate restTemplate = new RestTemplate();
        List<LoanOfferDTO> loanOfferList = restTemplate.postForObject(uri, loanApplicationRequestDTO, List.class);

        System.out.println(loanOfferList);

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



    }


}
