package dev.com.digitalWalletSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.com.digitalWalletSystem.models.Transaction;
import dev.com.digitalWalletSystem.models.User;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserOrderByTimestampDesc(User user);
}

