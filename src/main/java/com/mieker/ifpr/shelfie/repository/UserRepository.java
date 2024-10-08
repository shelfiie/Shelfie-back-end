package com.mieker.ifpr.shelfie.repository;

import com.mieker.ifpr.shelfie.entity.Book;
import com.mieker.ifpr.shelfie.entity.User;
import com.mieker.ifpr.shelfie.entity.enumeration.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndEnabled(String email, boolean b);
    Optional<User> findById(UUID id);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    int countByRole(UserRoles userRoles);
//    List<User> findAllUsers();

}
