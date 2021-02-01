package com.project.Repository;

import com.project.Entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    Boolean existsByRole(String role);

    UserRoleEntity findByRole(String role);
}