package com.alben.ministop.clients.repositories;

import com.alben.ministop.models.Client;

import java.util.Optional;

public interface ClientRepository {
    Client register(Client client);
    Optional<Client> getClientById(long id);
    Optional<Client> getClientByName(String name);
}
