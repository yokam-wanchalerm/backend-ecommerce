package com.gosoft.ecommerce.repository;

import com.gosoft.ecommerce.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT u " +
            "FROM User u Where u.email like CONCAT('%',:search, '%') OR u.firstName like CONCAT('%',:search, '%') OR u.lastName like CONCAT('%',:search, '%')")
    List<User> findByEmailOrFirstNameOrLastNameLike(@Param("search") String search);
}