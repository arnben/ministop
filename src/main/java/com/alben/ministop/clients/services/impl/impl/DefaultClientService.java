package com.alben.ministop.clients.services.impl.impl;

import com.alben.ministop.exceptions.ErrorDetails;
import com.alben.ministop.exceptions.ValidationException;
import com.alben.ministop.models.Client;
import com.alben.ministop.clients.repositories.ClientRepository;
import com.alben.ministop.clients.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

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

        Client savedClient = clientsRepository.register(client);
        return savedClient;
    }
}
