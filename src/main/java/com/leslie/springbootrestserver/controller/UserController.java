package com.leslie.springbootrestserver.controller;

import com.leslie.springbootrestserver.model.User;
import com.leslie.springbootrestserver.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping("/user/*")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/users")
    public ResponseEntity<Collection<User>>getAllUsers(){
        Collection<User> users = this.userService.getAllUsers();
        return new ResponseEntity<Collection<User>>(users, HttpStatus.FOUND);
    }

    @PostMapping("/users")
    public ResponseEntity<User>saveUser(@RequestBody User user){
        User userSaved = this.userService.saveOrUpdateUser(user);
        return new ResponseEntity<User>(userSaved,HttpStatus.CREATED);
    }

    @PutMapping("/users")
    public ResponseEntity<User>updateUser(@RequestBody User user){
        User userUpdated = this.userService.saveOrUpdateUser(user);
        return new ResponseEntity<User>(userUpdated,HttpStatus.OK);
    }

    @DeleteMapping("/users")
    public ResponseEntity<Void>deleteUser(@RequestParam ( value = "id") Long id){
        this.userService.deleteUser(id);
        return new ResponseEntity<Void>(HttpStatus.GONE);
    }

    @PostMapping("/users/login")
    public ResponseEntity<User>findUserByLoginAndPassword(@RequestBody User user){
        Optional<User> userFound = this.userService.findLoginAndPassword(user.getLogin(), user.getPassword());
        return new ResponseEntity<User>(userFound.get(),HttpStatus.FOUND);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User>findUserById(@PathVariable(value = "id") Long id){
        Optional<User> userFound = this.userService.getUserById(id);
        return new ResponseEntity<User>(userFound.get(),HttpStatus.FOUND);
    }
}
