package com.neuroplay.NeuroPlay.Entities.User;

import jakarta.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique=true)
    private String username;

    private String password;


}