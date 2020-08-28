package com.alben.ministop.chest.services.impl;

import com.alben.ministop.chest.repository.*;
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
        return null;
    }

    @Override
    public Object getProperty(String clientName, String realm, String propertyKey) {
        return null;
    }
}
