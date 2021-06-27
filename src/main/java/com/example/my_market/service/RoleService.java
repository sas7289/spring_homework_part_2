package com.example.my_market.service;

import com.example.my_market.entity.Role;
import com.example.my_market.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRole(String name) {
        return roleRepository.getByName(name);
    }

}
