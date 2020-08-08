package com.alben.ministop.repositories.jpa;

import com.alben.ministop.models.Client;
import com.alben.ministop.repositories.ClientRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.*;

import java.util.Optional;

@Repository
public class JpaClientRepository implements ClientRepository {


    @Override
    public void register(Client client) {

    }

    @Override
    public Optional<Client> getCLientById(String eq) {
        return Optional.empty();
    }

    public static interface ClientJpaRepository extends JpaRepository<JpaClient, String> {}


    public static class JpaClient {

    }
}
