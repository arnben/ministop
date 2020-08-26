package com.alben.ministop.clients.services;

import com.alben.ministop.exceptions.ValidationException;
import com.alben.ministop.models.Client;

public interface ClientService {
    Client register(Client client) throws ValidationException;
}
