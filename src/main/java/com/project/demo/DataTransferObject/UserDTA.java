package com.project.demo.DataTransferObject;
import lombok.*;
import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
public class UserDTA implements Serializable {

    String id;

    String username;

    String password;

     String role;

    String moneyOnTheAccount;

}
