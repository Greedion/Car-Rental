package com.project.model;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @NotBlank(message = "{User.username.notBlank}")
    @NotNull(message = "{User.username.notNull}")
    @NotEmpty(message = "{User.username.notEmpty}")
    @Length(min = 3, max = 13,message = "{User.username.size}")
    private String username;

    @NotBlank(message = "{User.password.notBlank}")
    @NotNull(message = "{User.password.notBlank}")
    @NotEmpty(message = "{User.password.notEmpty}")
    @Length(min = 3, max = 13,message = "{User.password.size}")
    private String password;
}
