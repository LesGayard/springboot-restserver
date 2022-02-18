package com.leslie.springbootrestserver.service;

import com.leslie.springbootrestserver.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserService {

    Collection<User> getAllUsers();

    Optional<User> getUserById(Long id) throws BusinessResourceException;

    Optional<User> findByLogin(String login) throws BusinessResourceException;

    User saveOrUpdateUser (User user) throws BusinessResourceException;

    void deleteUser(Long id) throws BusinessResourceException;

    Optional<User> findLoginAndPassword(String login, String password) throws BusinessResourceException;

}
