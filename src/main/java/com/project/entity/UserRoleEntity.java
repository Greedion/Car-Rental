package com.project.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USER_ROLE")
public class UserRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "ROLE", nullable = false)
    private String role;

    @OneToMany(mappedBy = "role")
    private List<UserEntity> accountEntities;

    public UserRoleEntity(String role) {
        this.role = role;
    }


    public UserRoleEntity(Long id, String role) {
        this.id = id;
        this.role = role;
    }
}
