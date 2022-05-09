package com.example.demo.Service;

import com.example.demo.models.Role;
import com.example.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class RoleService {

    @Autowired
    public RoleRepository roleRepository;

    public Set<Role> getAllRoles() {
        return new HashSet<>(roleRepository.findAll());
    }

    public void add(Role role) {
        roleRepository.save(role);
    }

    public Set<Role> getSetRoles(Set<String> roles) {
        return roleRepository.getSetRoles(roles);
    }
}
