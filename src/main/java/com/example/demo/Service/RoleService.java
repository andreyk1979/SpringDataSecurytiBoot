package com.example.demo.Service;

import com.example.demo.models.Role;

import java.util.Set;

public interface RoleService {

    Set<Role> getAllRoles();

    void add(Role role);

    Set<Role> getSetRoles(Set<String> roles);

}
