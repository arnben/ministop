package com.alben.ministop.jpa.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "client_emails")
@Data
public class JpaEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private JpaClient client;

    @Column(name = "email")
    private String email;

    public static JpaEmail of(String email) {
        JpaEmail jpaEmail = new JpaEmail();
        jpaEmail.setEmail(email);
        return jpaEmail;
    }
}
