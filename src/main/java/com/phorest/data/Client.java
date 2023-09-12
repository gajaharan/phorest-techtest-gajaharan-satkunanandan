package com.phorest.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
@Table(name = "clients")
public class Client extends JpaEntity {

    public Client(@NotNull String id, String firstName, String lastName, String email, String phone, String gender, Boolean banned) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.banned = banned;
    }

    @Id
    @NotNull
    private String id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Email
    @NotNull
    private String email;
    @NotNull
    private String phone;

    @NotNull
    private String gender;

    @NotNull
    private Boolean banned;
}
