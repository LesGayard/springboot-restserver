package com.leslie.springbootrestserver.repository;


import com.leslie.springbootrestserver.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static sun.nio.cs.Surrogate.is;


@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;
    User user = new User("Leslie", "password", 1);

    @Before
    public void setUp(){
        testEntityManager.persist(user);
        testEntityManager.flush();
    }

    @Test
    public void testFindAllUsers(){
        List<User>users = this.userRepository.findAll();
        Assertions.assertEquals(4, users.size());
    }

    @Test
    public void testSaveUser(){
        User user = new User("athena", "password", 1);
        User userToSave = this.userRepository.save(user);
        assertNotNull(userToSave.getLogin());
        Assertions.assertEquals("athena", userToSave.getLogin());
    }

    @Test
    public void testFindById(){
        Optional<User> userFromDb = this.userRepository.findById(user.getUserID());
        Assertions.assertEquals(user.getLogin(),userFromDb.get().getLogin());
    }

    @Test
    public void testFindByLogin(){
        Optional<User> userFromDb = this.userRepository.findByLogin("login2");
        Assertions.assertEquals("login2",userFromDb.get().getLogin());
    }

    @Test
    public void testDeleteUser(){
        this.userRepository.delete(user);
        Optional<User> userFromDb = this.userRepository.findByLogin(user.getLogin());
        Assertions.assertEquals(Optional.empty(),Optional.ofNullable(userFromDb.get()));
    }

    @Test
    public void testUpdateUser() {
        Optional<User> userToUpdate = this.userRepository.findByLogin(user.getLogin());
        userToUpdate.get().setActive(0);
        this.userRepository.save(userToUpdate.get());
        Optional<User> userUpdatedFromDB = this.userRepository.findByLogin(userToUpdate.get().getLogin());
        assertNotNull(userUpdatedFromDB);
        Assertions.assertEquals(0, is(userUpdatedFromDB.get().getActive()));
    }


}
