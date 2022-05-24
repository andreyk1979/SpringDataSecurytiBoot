package com.kuimov.pp.task312.service;

import com.kuimov.pp.task312.models.Role;

import java.util.Set;

public interface RoleService {

    Set<Role> getAllRoles();

    void add(Role role);

    Set<Role> getSetRoles(Set<String> roles);

}
