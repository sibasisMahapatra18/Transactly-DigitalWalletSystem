package dev.com.digitalWalletSystem.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.com.digitalWalletSystem.models.Transaction;
import dev.com.digitalWalletSystem.models.User;
import dev.com.digitalWalletSystem.services.TransactionService;
import dev.com.digitalWalletSystem.services.UserService;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired private UserService userService;
    @Autowired private TransactionService txnService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return new ResponseEntity<>(userService.register(user), HttpStatus.CREATED);
    }
@PostMapping("/fund")
public Map<String, Object> fund(@RequestBody Map<String, Object> req) {
    org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
   System.out.println("AUTH OBJECT: " + auth);
System.out.println("AUTH CLASS: " + auth.getClass());
System.out.println("AUTH NAME: " + username);
System.out.println("AUTH PRINCIPAL: " + auth.getPrincipal());
// <- ADD THIS

    Double amt = Double.valueOf(req.get("amt").toString());
    Double newBal = userService.fund(username, amt);
    txnService.log(username, "credit", amt, newBal);
    return Map.of("balance", newBal);
}



@PostMapping("/pay")
public Map<String, Object> pay(@RequestBody Map<String, Object> req) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String fromUsername = auth.getName(); // logged-in user
    String toUsername = req.get("to").toString();
    Double amt = Double.valueOf(req.get("amt").toString());

    System.out.println("FromUsername: " + fromUsername);
    System.out.println("ToUsername: " + toUsername);

    boolean success = userService.pay(fromUsername, toUsername, amt);
    if (!success) {
        return Map.of("status", "failed", "reason", "Insufficient funds or invalid recipient");
    }

    Double bal = userService.getBalance(fromUsername);
    txnService.log(fromUsername, "debit", amt, bal);
    txnService.log(toUsername, "credit", amt, userService.getBalance(toUsername));

    return Map.of("status", "success", "balance", bal);
}


@GetMapping("/bal")
public Map<String, Object> getBalance(@RequestParam(required = false) String currency) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName(); // from logged-in user

    Double inr = userService.getBalance(username);
    return Map.of("balance", inr, "currency", "INR");
}


@GetMapping("/stmt")
public List<Transaction> getStatement() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return txnService.getStatement(username);
}

}

