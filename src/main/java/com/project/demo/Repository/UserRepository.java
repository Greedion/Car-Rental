package com.project.demo.Repository;
import com.project.demo.Entity.UserEntity;
import com.project.demo.Entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Boolean existsByUsername(String username);
    UserEntity findByUsername(String username);
    Boolean existsByUsernameAndRole(String username, UserRoleEntity roleEntity);
}
