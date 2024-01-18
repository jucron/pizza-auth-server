package com.renault.pizzaauthserver.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;

//    @ManyToMany(fetch = FetchType.EAGER)
//    private Collection<Role> roles = new ArrayList<>();
    @OneToOne(fetch = FetchType.EAGER)
    private Role role;
}
