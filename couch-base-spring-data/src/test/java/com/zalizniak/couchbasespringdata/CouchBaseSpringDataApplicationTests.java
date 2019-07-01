package com.zalizniak.couchbasespringdata;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CouchBaseSpringDataApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldCreateUser() {

        User user = new User();
        user.setFirstname("Alex");
        user.setLastname("Zalizniak");

        userRepository.save(user);
    }

    @Test
    public void shouldFindUserById() {

        User user = new User();
        user.setFirstname("Alex");
        user.setLastname("Zalizniak");

        User savedUser = userRepository.save(user);

        User foundUser = userRepository.findById(savedUser.getId()).get();

        Assert.assertNotNull(foundUser);
    }

}
