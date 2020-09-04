package com.alben.ministop.chest.repositories;

import com.alben.ministop.models.*;

import java.util.*;

public interface ChestRepository {
    Collection<ChestProperty> getAllProperties(String clientName, Collection<String> realms);
    Optional<ChestProperty> getProperty(String clientName, String realm);
}
