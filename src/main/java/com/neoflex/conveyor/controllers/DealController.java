package com.neoflex.conveyor.controllers;

import com.neoflex.conveyor.dto.LoanApplicationRequestDTO;
import com.neoflex.conveyor.dto.LoanOfferDTO;
import com.neoflex.conveyor.models.client.Client;
import com.neoflex.conveyor.models.client.ClientRepository;
import com.neoflex.conveyor.services.ConveyorServiceImpl;
import com.neoflex.conveyor.services.DealServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

@RequestMapping(("/deal"))
@RestController
public class DealController {

    @Autowired
    DealServiceImpl dealService;

//    @GetMapping("/test")
//    public Iterable<Client> test() {
//        Iterable<Client> clients = clientRepository.findAll();
//        return clients;
//    }


    @PostMapping("/application")
    public List<LoanOfferDTO> offersDeal(@Valid @RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO) {

        dealService.addClient(loanApplicationRequestDTO);

        return null;
    }

//    @PutMapping("/offer")
//    public void offers(@Valid @RequestBody LoanOfferDTO loanOfferDTO) {
//
//
//    }

//    @PutMapping("/calculate/{applicationId}")
//    public List<LoanOfferDTO> offers(@Valid @RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO) {
//        List<LoanOfferDTO> loanOfferList = conveyorService.getOffers(loanApplicationRequestDTO);
//        loanOfferList.sort(Comparator.comparing(LoanOfferDTO::getRate).reversed());
//        return loanOfferList;
//    }


}
