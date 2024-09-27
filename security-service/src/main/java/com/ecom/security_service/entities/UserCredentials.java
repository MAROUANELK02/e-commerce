package com.ecom.security_service.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserCredentials {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userCredentialsId;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private long userId;

    @ManyToOne
    private Role role;
}
