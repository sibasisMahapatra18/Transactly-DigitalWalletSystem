package dev.com.digitalWalletSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.com.digitalWalletSystem.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {}

