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

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId).orElse(new Cart());
    }

    @Transactional
    public void addToCart(Long cartId, Products product, String color, String size, int quantity,String sessionId) {
       
        Cart cart = cartRepository.findById(cartId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setCreatedDate(LocalDateTime.now());
            newCart.setSessionId(sessionId != null ? sessionId : "default-session-id");
            return newCart;
        });

        
        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()) && item.getColor().equals(color) && item.getSize().equals(size))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
          
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartItemRepository.save(existingItem);  // Lưu lại CartItem đã cập nhật
        } else {
           
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setColor(color);
            newItem.setSize(size);
            newItem.setQuantity(quantity);
            newItem.setCart(cart);
            cart.getItems().add(newItem);
            cartItemRepository.save(newItem);  // Lưu CartItem mới
        }

        cartRepository.save(cart);  // Lưu giỏ hàng với các thay đổi
    }

    @Transactional
    public Long createCartForUser(String sessionId) {
        if (sessionId == null) {
            sessionId = "default-session-id";  // Giá trị mặc định nếu không có sessionId
        }

        Cart cart = new Cart(sessionId);

        if (cart.getCreatedDate() == null) {
            cart.setCreatedDate(LocalDateTime.now());  // Gán thời gian hiện tại
        }

        cartRepository.save(cart);

        return cart.getId();
    }
}
