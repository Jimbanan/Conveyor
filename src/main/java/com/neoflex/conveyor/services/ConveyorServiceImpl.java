package com.neoflex.conveyor.services;

import com.neoflex.conveyor.DTO.LoanApplicationRequestDTO;
import com.neoflex.conveyor.DTO.LoanOfferDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConveyorServiceImpl implements ConveyorService {


    @Override
    public List<LoanOfferDTO> getOffers(LoanApplicationRequestDTO loanApplicationRequestDTO) {

        List<LoanOfferDTO> loanOfferList = new ArrayList<>();

        loanOfferList.add(formationOfOffers(loanApplicationRequestDTO, true, true));
        loanOfferList.add(formationOfOffers(loanApplicationRequestDTO, true, false));
        loanOfferList.add(formationOfOffers(loanApplicationRequestDTO, false, false));
        loanOfferList.add(formationOfOffers(loanApplicationRequestDTO, false, true));

        return loanOfferList;
    }

    @Override
    public LoanOfferDTO formationOfOffers(LoanApplicationRequestDTO loanApplicationRequestDTO, Boolean isInsuranceEnabled, Boolean isSalaryClient) {

        LoanOfferDTO loanOfferDTO = new LoanOfferDTO();
        loanOfferDTO.setIsInsuranceEnabled(isInsuranceEnabled);
        loanOfferDTO.setIsSalaryClient(isSalaryClient);

        return loanOfferDTO;
    }


}
