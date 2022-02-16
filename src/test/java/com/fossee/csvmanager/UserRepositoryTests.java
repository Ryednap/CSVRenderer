package com.fossee.csvmanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;


/*
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback
public class UserRepositoryTests {
    @Autowired private UserRepository userRepository;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUserName("Ujjwal");
        user.setPassword("Humanity");

        User savedUser = userRepository.save(user);
        Optional<User> existUser = userRepository.findById(savedUser.getId());

        // user exits in the database
        assert(existUser.isPresent());
        assert(savedUser.equals(existUser.get()));
    }
}
*/
