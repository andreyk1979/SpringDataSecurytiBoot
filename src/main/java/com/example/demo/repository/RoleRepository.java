package com.example.demo.repository;

import com.example.demo.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Set<Role> getRolesByNameIn(Set<String> roles);
}