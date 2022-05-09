package com.example.demo.repository;

import com.example.demo.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Set;


public interface RoleRepository extends JpaRepository<Role,Long> {

    @Query(value = "select role from Role role where role.name in :roles")
    Set<Role> getSetRoles(@Param("roles") Set<String>roles);
}
