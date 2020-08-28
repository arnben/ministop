package com.alben.ministop.clients.services.impl;

import com.alben.ministop.clients.repositories.*;
import com.alben.ministop.clients.services.*;
import com.alben.ministop.exceptions.*;
import com.alben.ministop.models.*;
import org.apache.commons.lang3.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class DefaultClientService implements ClientService {

    @Autowired
    private ClientRepository clientsRepository;

    @Override
    public Client register(Client client) throws ValidationException {
        if(StringUtils.containsWhitespace(client.getName()))
            throw new ValidationException(ErrorDetails.CLIENT_NAME_HAS_SPACES, client.getName());

        Optional<Client> existingClient = clientsRepository.getClientByName(client.getName());
        if(existingClient.isPresent())
            throw new ValidationException(ErrorDetails.CLIENT_EXISTS, client.getName());

        client.setKey(RandomStringUtils.randomAlphanumeric(12));
        Client savedClient = clientsRepository.register(client);
        return savedClient;
    }

    @Override
    public Collection<String> getAllClientNames() {
        return clientsRepository.getAllClientNames();
    }

    @Override
    public Client getClient(String clientName) {
        Optional<Client> client = clientsRepository.getClientByName(clientName);
        if(client.isPresent())
            return client.get();
        else
            throw new ResourceNotFoundException(ErrorDetails.CLIENT_NOT_FOUND, clientName);
    }
}
