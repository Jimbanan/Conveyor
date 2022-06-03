package com.neoflex.conveyor.services;

import com.neoflex.conveyor.DTO.LoanApplicationRequestDTO;
import com.neoflex.conveyor.DTO.LoanOfferDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConveyorServiceImpl implements ConveyorService{


    @Override
    public List<LoanOfferDTO> getOffers(LoanApplicationRequestDTO loanApplicationRequestDTO) {

        System.out.println("getOffers");
//        if(prescoring(loanApplicationRequestDTO)){
//            List<LoanOfferDTO> loanOfferList = new ArrayList<>();
//
//
//
//            return null;
//        }


        return null;
    }
}
