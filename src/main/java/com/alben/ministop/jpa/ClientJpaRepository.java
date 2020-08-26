package com.alben.ministop.jpa;

import com.alben.ministop.jpa.entities.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface ClientJpaRepository extends JpaRepository<JpaClient, Long> {
    Optional<JpaClient> findJpaClientByName(String name);
}
