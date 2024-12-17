package com.example.KTCK.service;

import com.example.KTCK.model.Cart;
import com.example.KTCK.model.CartItem;
import com.example.KTCK.model.Products;
import com.example.KTCK.repository.CartItemRepository;
import com.example.KTCK.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    public Cart findById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    public void delete(Long id) {
        cartRepository.deleteById(id);
    }
}
