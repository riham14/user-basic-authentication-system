package com.user.basic.authentication.repositories;

import com.user.basic.authentication.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    default User addUser(User user){
        return save(user);
    }

    User findByEmail(String email);

    User findByPhoneNumber(String phoneNumber);

    User findByPhoneNumberAndToken(String phoneNumber, String token);
}
