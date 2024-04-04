package com.bej.authentication.repository;

import com.bej.authentication.domain.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() throws Exception {
        user = new User("U1234", "JOHN123");
    }
    @AfterEach
    public void tearDown() throws Exception {
        user = null;
    }

    @Test
    public void testSaveUserSuccess() {
        userRepository.save(user);
        User object = userRepository.findById(user.getEmailId()).get();
        assertEquals(user.getEmailId(), object.getEmailId());
    }

    @Test
    public void testLoginUserSuccess() {
        userRepository.save(user);
        User object = userRepository.findById(user.getEmailId()).get();
        assertEquals(user.getEmailId(), object.getEmailId());
    }
}