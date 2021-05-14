package com.project.diploma.service.services.implementation;

import com.project.diploma.data.models.Role;
import com.project.diploma.data.repository.RoleRepository;
import com.project.diploma.service.services.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void seedRolesInDb() {
        this.roleRepository.saveAndFlush(new Role("ADMIN"));
        this.roleRepository.saveAndFlush(new Role("USER"));
    }
}
