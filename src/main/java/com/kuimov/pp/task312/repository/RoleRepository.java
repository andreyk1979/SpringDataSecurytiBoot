package com.kuimov.pp.task312.repository;

import com.kuimov.pp.task312.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Set<Role> getRolesByNameIn(Set<String> roles);
}