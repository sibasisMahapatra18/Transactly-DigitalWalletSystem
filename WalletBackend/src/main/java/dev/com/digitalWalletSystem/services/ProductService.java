package dev.com.digitalWalletSystem.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import dev.com.digitalWalletSystem.models.Product;
import dev.com.digitalWalletSystem.models.User;
import dev.com.digitalWalletSystem.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired private ProductRepository productRepo;
    @Autowired private UserService userService;
    @Autowired private TransactionService txnService;

    public Product add(Product p) {
        return productRepo.save(p);
    }

    public List<Product> listAll() {
        return productRepo.findAll();
    }

    public boolean buyProduct(Long pid, String username) {
        Product p = productRepo.findById(pid).orElse(null);
        User user = userService.getUser(username);
        if (p == null || user == null || user.getBalance() < p.getPrice()) return false;
        user.setBalance(user.getBalance() - p.getPrice());
        txnService.log(username, "debit", (double) p.getPrice(), user.getBalance());
        return true;
    }
}

