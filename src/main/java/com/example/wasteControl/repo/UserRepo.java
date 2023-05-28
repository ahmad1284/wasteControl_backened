package com.example.wasteControl.repo;

import com.example.wasteControl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    Optional<User> findByUserName(String userName);
    Boolean existsByUserName(String userName);
    Boolean existsByEmail(String email);
    @Query("SELECT u.password FROM User u WHERE u.userName = :userName")
    String findPasswordByUserName(@Param("userName") String userName);


}
