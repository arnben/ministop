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

    @Column(name = "name", unique = true)
    private String name;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<JpaEmail> emails;

    @Column(name="susi")
    private byte[] key;

    public static JpaClient of(Client client) {
        JpaClient jpaClient = new JpaClient();
        jpaClient.setName(client.getName());
        jpaClient.setEmails(client.getEmails().stream().map(e -> JpaEmail.of(jpaClient, e)).collect(Collectors.toList()));
        jpaClient.setKey(Base64.getEncoder().encode(client.getKey().getBytes()));
        return jpaClient;
    }

    public Client toClient() {
        return Client.builder()
                .name(name)
                .emails(emails.stream().map(e -> e.getEmail()).collect(Collectors.toSet()))
                .key(new String(Base64.getDecoder().decode(key)))
                .build();
    }
}
