package com.v1zefull.pft.service;

import com.v1zefull.pft.entity.Category;
import com.v1zefull.pft.entity.Transaction;
import com.v1zefull.pft.entity.User;
import com.v1zefull.pft.exception.ResourceNotFoundException;
import com.v1zefull.pft.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    public TransactionService(
            TransactionRepository transactionRepository,
            UserService userService,
            CategoryService categoryService
    ) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    public Transaction createTransaction(Transaction transaction, Long userId, Long categoryId){
        User user = userService.getUserById(userId);
        Category category = categoryService.getCategoryById(categoryId);

        transaction.setUser(user);
        transaction.setCategory(category);

        return transactionRepository.save(transaction);
    }

    public Transaction getTransactionById(Long id){
        return transactionRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Transaction not found with id " + id)
        );
    }

    public List<Transaction> getAllTransactionsByUserId(Long userId){
        return transactionRepository.findByUserId(userId);
    }

    public void deleteTransaction(Long id){
        Transaction transaction = getTransactionById(id);
        transactionRepository.deleteById(transaction.getId());
    }

}
