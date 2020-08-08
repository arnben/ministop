package com.alben.ministop.services.impl;

import com.alben.ministop.exceptions.ErrorDetails;
import com.alben.ministop.exceptions.ValidationException;
import com.alben.ministop.models.Client;
import com.alben.ministop.repositories.ClientRepository;
import com.alben.ministop.services.ClientService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class DefaultClientService implements ClientService {

    @Autowired
    private ClientRepository clientsRepository;

    @Override
    public Client register(String serviceName) throws ValidationException {
        if(StringUtils.containsWhitespace(serviceName))
            throw new ValidationException(ErrorDetails.CLIENT_NAME_HAS_SPACES, serviceName);

        Optional<Client> existingClient = clientsRepository.getCLientById(serviceName);
        if(existingClient.isPresent())
            throw new ValidationException(ErrorDetails.CLIENT_EXISTS, serviceName);

        String secret = RandomStringUtils.random(7, true, true);
        Client client = Client.builder().id(serviceName).secret(secret).build();
        clientsRepository.register(client);
        return client;
    }
}
