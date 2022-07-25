package com.minibank.mini.repository;

import com.minibank.mini.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT * FROM transactions WHERE to_account = :actNo OR from_account = :actNo ORDER BY created_at DESC", nativeQuery = true)
    List<Transaction> getAllAccountTransactionsInDescendingOrder(@Param("actNo") String accountNumber);

    //List<Transaction> findByToAccountOrFromAccountOrderByCreatedAtDesc(String accountNumber);

}
