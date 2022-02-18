package com.leslie.springbootrestserver.repository;

import com.leslie.springbootrestserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/* Implements all the CRUD methods extends JpaRepository or CrudRepository */
public interface UserRepository extends JpaRepository<User,Long> {

    /* not in the CRUD methods */
    Optional<User> findByLogin(String loginParam);
}
