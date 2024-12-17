package com.example.KTCK.controller;

import com.example.KTCK.model.Cart;
import com.example.KTCK.model.CartItem;
import com.example.KTCK.model.Products;
import com.example.KTCK.repository.CartItemRepository;
import com.example.KTCK.service.CartService;
import com.example.KTCK.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
public class CartController{
    @Autowired
    private CartService cartService;

    @GetMapping
    @ResponseBody
    public List<Cart> getAllCarts(){
        return cartService.findAll();
    }
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Cart> getCartById(@PathVariable Long id){
        Cart cart = cartService.findById(id);
        return ResponseEntity.ok(cart);
    }
    @PostMapping
    @ResponseBody
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart){
        Cart newCart = cartService.save(cart);
        return ResponseEntity.status(201).body(newCart);
    }
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Cart> updateCart(@PathVariable Long id, @RequestBody Cart cartDetails){
        Cart cart = cartService.findById(id);
        cart.setProductList(cartDetails.getProductList());
        cart.setTotalAmount(cartDetails.getTotalAmount());
        cartService.save(cart);
        return ResponseEntity.ok(cart);
    }
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteCart(@PathVariable Long id){
        cartService.delete(id);
        return ResponseEntity.noContent().build();
    }
}