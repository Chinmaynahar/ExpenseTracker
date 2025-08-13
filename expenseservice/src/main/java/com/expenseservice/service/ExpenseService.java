package com.expenseservice.service;

import com.expenseservice.entities.Expense;
import com.expenseservice.entities.ExpenseDto;
import com.expenseservice.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

   public Expense create(ExpenseDto expenseDto)throws Exception{
        try{
            String uid=UUID.randomUUID().toString();
           Expense resp= expenseRepository.save(new Expense(uid,expenseDto.getUserId(),expenseDto.getAmount(),expenseDto.getMerchant()));
           return resp;
        }catch (Exception e){
            throw new Exception(e.toString());
        }
   }
   public Expense update(ExpenseDto expenseDto)throws Exception{
        try {
            Expense expense=expenseRepository.findById(expenseDto.getId())
                    .orElseThrow(()->new Exception("Expense not found"));
            expense.setAmount(expenseDto.getAmount());
            expense.setMerchant(expenseDto.getMerchant());
           return expenseRepository.save(expense);
        } catch (Exception e) {
            throw new Exception(e.toString());
        }
   }

    public boolean delete(String id) throws Exception {
        Optional<Expense> expense=expenseRepository.findById(id);
       if (expense.isEmpty()){
           throw new Exception("Expense not found");
       }
      try {
          expenseRepository.deleteById(id);
      }catch (Exception e){
          throw new Exception(e.toString());
      }
      return true;
    }

    public List<Expense> getExpenses(String id)throws Exception{
        try{
            List<Expense>ls=expenseRepository.findAllByUserId(id);
            if (ls!=null){
                return ls;
            }else {
                throw new Exception("Userid not found");
            }
        }catch (Exception e){
            throw new Exception(e.toString());
        }
    }
}
