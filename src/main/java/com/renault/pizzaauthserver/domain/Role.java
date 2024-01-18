package com.renault.pizzaauthserver.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ElementCollection(fetch = FetchType.EAGER) //Note: Collections are Lazy Fetch by default, must customize Fetch in this.
    private Set<String> permissions;

}

