package com.v1zefull.pft.service;

import com.v1zefull.pft.dto.transaction.TransactionRequest;
import com.v1zefull.pft.dto.transaction.TransactionResponse;
import com.v1zefull.pft.entity.Transaction;
import com.v1zefull.pft.exception.ResourceNotFoundException;
import com.v1zefull.pft.repository.CategoryRepository;
import com.v1zefull.pft.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    //Entity -> Response
    private TransactionResponse toResponse(Transaction transaction){
        return new TransactionResponse(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getDate(),
                transaction.getDescription(),
                categoryService.toResponse(transaction.getCategory()),
                userService.toResponse(transaction.getUser())
        );
    }

    //Request -> Entity
    private Transaction toEntity(TransactionRequest request){
        return new Transaction(
                request.getDate(),
                request.getAmount(),
                request.getDescription(),
                categoryService.getCategoryEntityById(request.getCategoryId()),
                userService.getUserEntityById(request.getUserId())
        );
    }


    public TransactionResponse createTransaction(TransactionRequest request) {
        Transaction saved = transactionRepository.save(toEntity(request));
        return toResponse(saved);
    }

    public TransactionResponse getTransactionById(Long id){
        Transaction transaction = getTransactionEntityById(id);
        return toResponse(transaction);
    }

    public Transaction getTransactionEntityById(Long id) {
        return transactionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Transaction not found with id " + id)
        );
    }
    public List<TransactionResponse> getAllTransactionsByUserId(Long userId){
        return transactionRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void deleteTransaction(Long id){
        Transaction transaction = getTransactionEntityById(id);
        transactionRepository.deleteById(transaction.getId());
    }

}
