package com.is.smartlight.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    private String username;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String phone;

    private String role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<LightGroup> lightGroups;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<UserPreset> userPresets;

}
