package com.alben.ministop.repositories;

import com.alben.ministop.models.Client;

import java.util.Optional;

public interface ClientRepository {
    void register(Client client);
    Optional<Client> getCLientById(String eq);
}
