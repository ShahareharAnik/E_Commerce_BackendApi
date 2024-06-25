package com.anik.E_Commerce.repositories;

import com.anik.E_Commerce.table.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    List<User> findByUserID(Integer userID);
    List<User> findByName(String name);
    List<User> findByType(String type);
    List<User> findAll(Sort sort);
}
