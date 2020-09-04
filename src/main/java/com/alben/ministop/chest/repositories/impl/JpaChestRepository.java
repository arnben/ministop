package com.alben.ministop.chest.repositories.impl;

import com.alben.ministop.chest.repositories.*;
import com.alben.ministop.models.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public class JpaChestRepository implements ChestRepository {
    @Override
    public Collection<ChestProperty> getAllProperties(String clientName, Collection<String> realms) {
        return null;
    }

    @Override
    public Optional<ChestProperty> getProperty(String clientName, String realm) {
        return Optional.empty();
    }
}
