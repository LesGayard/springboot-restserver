package com.leslie.springbootrestserver.service;

import com.leslie.springbootrestserver.model.Role;

import java.util.Collection;
import java.util.stream.Stream;

public interface RoleService {

    Role findByRoleName(String roleName);

    Collection<Role> getAllRoles();

    Stream<Role>getAllRolesStream();
}
