package com.abuseapp.speaknativeappapijava.endpoints.users.repositories;

import com.abuseapp.speaknativeappapijava.endpoints.users.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u where u.email=?1")
    User findByEmail(String email);
}
