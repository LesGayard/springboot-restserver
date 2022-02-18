package com.leslie.springbootrestserver.service;

import com.leslie.springbootrestserver.model.Role;
import com.leslie.springbootrestserver.model.User;
import com.leslie.springbootrestserver.repository.RoleRepository;
import com.leslie.springbootrestserver.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class UserServiceImpl implements UserService{

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(){
        super();
    }

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        super();
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public Collection<User>getAllUsers(){
        return this.userRepository.findAll();
    }


    @Override
    public Optional<User>getUserById(Long id) throws BusinessResourceException{
        Optional<User> userFound = this.userRepository.findById(id);

        if(Boolean.FALSE.equals(userFound.isPresent())){
            throw new BusinessResourceException("User not found with this id : " + id);
        }
        return userFound;
    }


    @Override
    Optional<User>findByLogin(String login) throws BusinessResourceException{

        Optional<User> userFound = this.userRepository.findByLogin(login);

        if(Boolean.FALSE.equals(userFound.isPresent())){
            throw new BusinessResourceException("User not found with this login : " + login);
        }
        return userFound;
    }

    @Override
    public User saveOrUpdateUser(User user) throws BusinessResourceException{
        try{
            /* Search if the user exists
             if not create one an save,
            * if it exists update the user and save it
            * */
           if(user.getUserID() == null){
                //User creation
               addUserRole(user);
               user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
           }else{
               //User to update
               Optional<User> userToUpdate = getUserById(user.getUserID());
               if(!this.bCryptPasswordEncoder.matches(user.getPassword(),userToUpdate.get().getPassword())){
                   /* Update the user password */
                   user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
               }else{
                   /* retrieve the encoded password */
                   user.setPassword(userToUpdate.get().getPassword());
               }
               updateUserRole(user);
           }
           User userToSave = this.userRepository.save(user);
           return userToSave;
        }catch(DataIntegrityViolationException ex){
            logger.error("The user doesn't exist : ", ex);
            throw new BusinessResourceException("Duplicate value error , a user already exists with the Login : " + user.getLogin(), HttpStatus.CONFLICT);

        }catch(BusinessResourceException businessResourceException){
            logger.error("the user doesn't exist : ", businessResourceException);
            throw new BusinessResourceException("No user exist with id : " + user.getUserID(), HttpStatus.NOT_FOUND);

        }catch(Exception exception){
            logger.error("Technical error cannot create or update the user", exception);
            throw new BusinessResourceException("Technical error cannot create or update the user : " + user.getLogin(),HttpStatus.INTERNAL_SERVER_ERROR );
        }

    }

    @Override
    public void deleteUser(Long id) throws BusinessResourceException{
        try{
            this.userRepository.deleteById(id);
        }catch(EmptyResultDataAccessException e){
            logger.error(String.format("No user with this id ") + id, e);
            throw new BusinessResourceException("DeleteUserError", "Cannot delete the user with the id : " + id, HttpStatus.NOT_FOUND);
        }catch(Exception ex){
            throw new BusinessResourceException("DeleteUserError", "Cannot delete the user with the id : " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Optional<User> findLoginAndPassword(String login,String password)throws BusinessResourceException{
        try{
            Optional<User> userFound = this.userRepository.findByLogin(login);
            if(this.bCryptPasswordEncoder.matches(password,userFound.get().getPassword())){
                return userFound;
            }else{
                throw new BusinessResourceException("User ot found", "Incorrect password",HttpStatus.NOT_FOUND);
            }
        }catch(BusinessResourceException e){
            logger.error("Incorrect login or password",e);
            throw new BusinessResourceException("login or password error",HttpStatus.NOT_FOUND);
        }catch(Exception ex){
            logger.error("Technical Error",ex);
            throw new BusinessResourceException("Technical error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* Helper methods */
    private void addUserRole(User user){
        Set<Role>roles = new HashSet<Role>();

        Role roleUser = new Role("ROLE_USER");

        roles.add(roleUser);
        user.setActive(0);
        Set<Role> roleFromDB = extractRole_Java8(roles, this.roleRepository.getAllRolesStream());
        user.setRoles(roleFromDB);
    }

    private void updateUserRole(User user) {

        Set<Role> roleFromDB = extractRole_Java8(user.getRoles(), this.roleRepository.getAllRolesStream());
        user.setRoles(roleFromDB);
    }

    private Set<Role> extractRole_Java8(Set<Role> rolesSetFromUser, Stream<Role> roleStreamFromDB) {
        // Collect UI role names
        Set<String> uiRoleNames = rolesSetFromUser.stream()
                .map(Role::getRoleName)
                .collect(Collectors.toCollection(HashSet::new));
        // Filter DB roles
        return roleStreamFromDB
                .filter(role -> uiRoleNames.contains(role.getRoleName()))
                .collect(Collectors.toSet());
    }

}
