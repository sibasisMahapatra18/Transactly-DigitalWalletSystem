package dev.com.digitalWalletSystem.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.com.digitalWalletSystem.models.Product;
import dev.com.digitalWalletSystem.services.ProductService;
import dev.com.digitalWalletSystem.services.UserService;

@RestController
public class ProductController {

    @Autowired private ProductService productService;
    @Autowired private UserService userService;

    @PostMapping("/product")
    public ResponseEntity<?> add(@RequestBody Product p) {
        Product saved = productService.add(p);
        return new ResponseEntity<>(Map.of("id", saved.getId(), "message", "Product added"), HttpStatus.CREATED);
    }

    @GetMapping("/product")
    public List<Product> list() {
        return productService.listAll();
    }

@PostMapping("/buy")
public ResponseEntity<?> buy(@RequestBody Map<String, Object> req) {
    Long pid = Long.valueOf(req.get("product_id").toString());

    org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName(); // this is the authenticated user

    boolean success = productService.buyProduct(pid, username);
    if (!success) {
        return ResponseEntity.badRequest().body(Map.of("error", "Insufficient balance or invalid product"));
    }

    Double newBal = userService.getBalance(username);
    return ResponseEntity.ok(Map.of("message", "Product purchased", "balance", newBal));
}


}

