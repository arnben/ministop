package com.alben.ministop.services.impl;

import com.alben.ministop.models.Client;
import com.alben.ministop.repositories.ClientRepository;
import com.alben.ministop.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultClientService implements ClientService {

    @Autowired
    private ClientRepository clientsRepository;

    @Override
    public void register(String serviceName) {

    }
}
