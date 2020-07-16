package com.alben.ministop.services;

import com.alben.ministop.exceptions.ValidationException;
import com.alben.ministop.models.Client;

public interface ClientService {
    Client register(String serviceName) throws ValidationException;
}
