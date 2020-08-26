package com.alben.ministop.jpa;

import com.alben.ministop.jpa.entities.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface ClientJpaRepository extends JpaRepository<JpaClient, Long> {

}
