package com.anik.E_Commerce.repositories;

import com.anik.E_Commerce.allenum.OrderType;
import com.anik.E_Commerce.table.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findByCustomerIdAndStatus( Integer customerId, OrderType status);
}
