package com.alben.ministop.chest.services;

import org.springframework.stereotype.*;

import java.util.*;

public interface ChestService {
    Map<String, Object> getAllProperties(String clientName, String realm);
    Object getProperty(String clientName, String realm, String propertyKey);
}
