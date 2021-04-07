package com.guyson.kronos.repository;

import com.guyson.kronos.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    List<User> findByRoleEquals(String role);

    List<User> findByRoleOrderByCreatedAtDesc(String role);

    Optional<User> findFirstBy_class_ClassID(int classID);

}
