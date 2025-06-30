package dev.com.digitalWalletSystem.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.com.digitalWalletSystem.models.Transaction;
import dev.com.digitalWalletSystem.models.User;
import dev.com.digitalWalletSystem.repository.TransactionRepository;
import dev.com.digitalWalletSystem.repository.UserRepository;

@Service
public class TransactionService {

    @Autowired private TransactionRepository transactionRepo;
    @Autowired private UserRepository userRepo;

    public void log(String username, String kind, Double amt, Double updatedBal) {
        User user = userRepo.findByUsername(username).orElse(null);
        if (user == null) return;
        Transaction txn = new Transaction();
        txn.setUser(user);
        txn.setKind(kind);
        txn.setAmt(amt);
        txn.setUpdatedBal(updatedBal);
        txn.setTimestamp(LocalDateTime.now());
        transactionRepo.save(txn);
    }

    public List<Transaction> getStatement(String username) {
        User user = userRepo.findByUsername(username).orElse(null);
        return user == null ? List.of() : transactionRepo.findByUserOrderByTimestampDesc(user);
    }
}

