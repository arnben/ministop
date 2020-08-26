package com.alben.ministop.jpa.entities;

import com.alben.ministop.jpa.*;
import com.alben.ministop.models.*;
import lombok.*;

import javax.persistence.*;
import java.util.*;
import java.util.stream.*;

@Entity
@Table(name = "clients")
@Data
public class JpaClient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Collection<JpaEmail> emails;

    public static JpaClient of(Client client) {
        JpaClient jpaClient = new JpaClient();
        jpaClient.setName(client.getName());
        jpaClient.setEmails(client.getEmails().stream().map(JpaEmail::of).collect(Collectors.toList()));
        return jpaClient;
    }
}
