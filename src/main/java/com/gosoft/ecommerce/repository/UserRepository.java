package com.gosoft.ecommerce.repository;

import com.gosoft.ecommerce.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * " +
            "FROM User Where email like '%?1%' OR firstName like '%?1%' OR lastName like '%?1%'",nativeQuery = true)
    List<User> findByEmailOrFirstNameOrLastNameLike(String search);
}