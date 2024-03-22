package com.example.thesis_new.repository;

import com.example.thesis_new.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u from User u where u.email = ?1  ")
    User findByEmail(String email);

    @Query("select u from User u where u.email = ?1 and u.password = ?2")
    User findByEmailAndPassword(String email, String password);

    Optional<User> findByUsername(String username);

    @Query("SELECT COUNT(u) FROM User u WHERE u.role.id=?1")
    int countByRoleid(int id);

    @Query("SELECT u FROM User u WHERE u.role.id=?1")
    List<User> getUserByRoleid(int id);

    @Query("SELECT u FROM DeliveryInfo di, User u where u.id = di.deliveryPerson.id and di.available = true")
    List<User> getAvailableDelivery();

    Optional<User> findByName(String name);

}
