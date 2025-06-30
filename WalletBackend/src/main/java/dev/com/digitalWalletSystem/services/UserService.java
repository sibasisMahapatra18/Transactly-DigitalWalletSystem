package dev.com.digitalWalletSystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.com.digitalWalletSystem.models.User;
import dev.com.digitalWalletSystem.repository.UserRepository;


@Service
public class UserService {

    @Autowired private UserRepository userRepo;

    public User register(User user) {
        user.setBalance(0.0);
        return userRepo.save(user);
    }

    public User getUser(String username) {
        return userRepo.findByUsername(username).orElse(null);
    }

public Double fund(String username, Double amt) {
    User user = getUser(username);
    if (user == null) {
        throw new RuntimeException("User not found: " + username);
    }
    user.setBalance(user.getBalance() + amt);
    userRepo.save(user);
    return user.getBalance();
}



public boolean pay(String fromUsername, String toUsername, Double amt) {
    User sender = getUser(fromUsername);
    User receiver = getUser(toUsername);

    System.out.println("Sender: " + sender);
    System.out.println("Receiver: " + receiver);
    System.out.println("Sender balance: " + (sender != null ? sender.getBalance() : null));
    System.out.println("Amount: " + amt);

    if (sender == null || receiver == null || sender.getBalance() < amt) return false;

    sender.setBalance(sender.getBalance() - amt);
    receiver.setBalance(receiver.getBalance() + amt);
    userRepo.save(sender);
    userRepo.save(receiver);
    return true;
}


    public Double getBalance(String username) {
        return getUser(username).getBalance();
    }
}

