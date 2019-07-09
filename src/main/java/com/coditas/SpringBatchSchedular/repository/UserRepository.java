package com.coditas.SpringBatchSchedular.repository;

import com.coditas.SpringBatchSchedular.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,User> {
}
