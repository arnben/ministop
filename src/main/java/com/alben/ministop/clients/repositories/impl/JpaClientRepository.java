package com.alben.ministop.clients.repositories.impl;

import com.alben.ministop.clients.repositories.*;
import com.alben.ministop.jpa.*;
import com.alben.ministop.jpa.entities.*;
import com.alben.ministop.models.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public class JpaClientRepository implements ClientRepository {

    @Autowired
    private ClientJpaRepository repository;

    @Override
    public Client register(Client client) {
        repository.save(JpaClient.of(client));
        return client;
    }

    @Override
    public Optional<Client> getClientById(String eq) {
        return Optional.empty();
    }

}
