package com.neoflex.conveyor.controllers;

import com.neoflex.conveyor.models.client.Client;
import com.neoflex.conveyor.models.client.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DealController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/test")
    public Iterable<Client> test(){
        Iterable<Client> clients = clientRepository.findAll();
        return clients;
    }

}
