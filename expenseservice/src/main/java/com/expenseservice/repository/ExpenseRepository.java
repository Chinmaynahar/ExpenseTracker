package com.expenseservice.repository;

import com.expenseservice.entities.Expense;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense,String> {
    List<Expense> findAllByUserId(String userId);
}
