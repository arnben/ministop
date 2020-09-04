package com.alben.ministop.chest.services.impl;

import com.alben.ministop.chest.repositories.*;
import com.alben.ministop.chest.services.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class DefaultChestService implements ChestService {

    @Autowired
    private ChestRepository chestRepository;

    @Override
    public Map<String, Object> getAllProperties(String clientName, String realm) {
        Map<String, Object> response = new TreeMap<>();
        response.put("downStreamUrl", "http://localhost:8080/");
        response.put("allowedUser", "10");
        response.put("keywords",
            Arrays.asList("tito", "vic", "joey"));
        return response;
    }

    @Override
    public Object getProperty(String clientName, String realm, String propertyKey) {
        return null;
    }
}
