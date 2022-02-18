package com.leslie.springbootrestserver.service;

import com.leslie.springbootrestserver.model.Role;
import com.leslie.springbootrestserver.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.stream.Stream;

public class RoleServiceImpl implements RoleService{

    private RoleRepository roleRepository;

    public RoleServiceImpl(){
        super();
    }

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository){
        super();
        this.roleRepository = roleRepository;
    }


    @Override
    public Role findByRoleName(String roleName){
        Role roleFound = this.roleRepository.findByRoleName(roleName);
        return roleFound;
    }

    @Override
    public Collection<Role> getAllRoles(){
        return this.roleRepository.findAll();
    }

    @Override
    public Stream<Role> getAllRolesStream(){
        return this.roleRepository.getAllRolesStream();
    }
}
