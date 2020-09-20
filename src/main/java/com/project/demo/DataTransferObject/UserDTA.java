package com.project.demo.DataTransferObject;


import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
public class UserDTA {

    String id;

    String username;

    String password;

     String role;

    String moneyOnTheAccount;

}
