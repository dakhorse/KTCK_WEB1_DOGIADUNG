package com.example.KTCK.repository;

import com.example.KTCK.model.CartItem;
import com.example.KTCK.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByProductAndColorAndSize(Products product, String color, String size);
}


