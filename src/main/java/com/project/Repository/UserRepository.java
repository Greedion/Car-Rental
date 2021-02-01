package com.project.Repository;

import com.project.Entity.UserEntity;
import com.project.Entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Boolean existsByUsername(String username);

    UserEntity findByUsername(String username);

    Boolean existsByUsernameAndRole(String username, UserRoleEntity roleEntity);
}
