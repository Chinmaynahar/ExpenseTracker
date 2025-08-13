package com.expenseservice.controller;

import com.expenseservice.entities.Expense;
import com.expenseservice.entities.ExpenseDto;
import com.expenseservice.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExpenseController {
 private final ExpenseService expenseService;


    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping("expense/v1/create")
    public ResponseEntity<Expense> create(@RequestBody ExpenseDto expenseDto) throws Exception {
        try {
         Expense exp= expenseService.create(expenseDto);
         return new ResponseEntity<>(exp,HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception(e.toString());
        }
    }
    @PostMapping("expense/v1/update")
    public ResponseEntity<Expense> update(@RequestBody ExpenseDto expenseDto)throws Exception{
        try{
            Expense expense=expenseService.update(expenseDto);
            return new ResponseEntity<>(expense,HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception(e.toString());
        }
    }

    @DeleteMapping("expense/v1/delete/{id}")
    public void delete(@PathVariable String id) throws Exception {
        try{
            expenseService.delete(id);
        } catch (Exception e) {
            throw new Exception(e.toString());
        }
    }

    @GetMapping("expense/v1/get/{id}")
    public ResponseEntity<List<Expense>> getExpense(@PathVariable String id)throws Exception{
        try {
           List<Expense>ls=expenseService.getExpenses(id);
           return new ResponseEntity<>(ls,HttpStatus.OK);
        }catch (Exception e){
            throw new Exception(e.toString());
        }
    }
}
