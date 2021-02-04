package com.guyson.kronos.repository;

import com.guyson.kronos.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {}
