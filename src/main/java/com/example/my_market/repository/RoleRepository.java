package com.example.my_market.repository;

import com.example.my_market.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role getByName(String name);
}
