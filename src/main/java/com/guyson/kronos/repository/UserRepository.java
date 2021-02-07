package com.guyson.kronos.repository;

import com.guyson.kronos.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    List<User> findByRoleEquals(String role);

}
