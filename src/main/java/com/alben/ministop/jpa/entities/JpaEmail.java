package com.alben.ministop.jpa.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "client_emails")
@Data
@ToString(exclude = "client")
public class JpaEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private JpaClient client;

    @Column(name = "email")
    private String email;

    public static JpaEmail of(JpaClient jpaClient, String email) {
        JpaEmail jpaEmail = new JpaEmail();
        jpaEmail.setEmail(email);
        jpaEmail.setClient(jpaClient);
        return jpaEmail;
    }
}
