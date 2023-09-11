package com.phorest.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String gender;
    private Boolean banned;

}
