package com.gosoft.ecommerce.repository;


import com.gosoft.ecommerce.entity.OurUsers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepo extends CrudRepository<OurUsers, Integer> {

    Optional<OurUsers> findByEmail(String email);
}
