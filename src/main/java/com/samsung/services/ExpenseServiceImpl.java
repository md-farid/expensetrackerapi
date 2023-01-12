package com.samsung.services;

import com.samsung.entities.Expense;
import com.samsung.exceptions.ResourceNotFoundException;
import com.samsung.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService{

    @Autowired
    private UserService userService;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public Page<Expense> getAllExpenses(Pageable page) {
        return expenseRepository.findByUserId(userService.getLoggedInUser().getId(),page);
    }

    @Override
    public Expense getExpenseById(Long id) {
        Optional<Expense> expense = expenseRepository.findById(id);
        if(expense.isPresent()){
            return expense.get();
        }
        throw new ResourceNotFoundException("Expense not found with id: "+id);
    }

    @Override
    public void deleteExpenseById(Long id) {
        Expense expense = getExpenseById(id);
        expenseRepository.delete(expense);
    }

    @Override
    public Expense saveExpenseDetails(Expense expense) {
        expense.setUser(userService.getLoggedInUser());
        return expenseRepository.save(expense);
    }

    @Override
    public Expense updateExpenseDetails(Long id, Expense expense) {
        Expense existingExpense = getExpenseById(id);
        existingExpense.setName( expense.getName()!=null ? expense.getName() : existingExpense.getName());
        existingExpense.setDescription(expense.getDescription()!=null ? expense.getDescription() : existingExpense.getDescription());
        existingExpense.setAmount(expense.getAmount()!=null ? expense.getAmount() : existingExpense.getAmount());
        existingExpense.setCategory(expense.getCategory()!=null ? expense.getCategory() : existingExpense.getCategory());
        existingExpense.setDate(expense.getDate()!=null ? expense.getDate() : expense.getDate());
        return expenseRepository.save(existingExpense);
    }

    @Override
    public List<Expense> readExpensesByCategory(String category, Pageable page) {
        return expenseRepository.findByCategory(category,page).toList();
    }

    @Override
    public List<Expense> readExpensesByName(String name, Pageable page) {
        return expenseRepository.findByNameContaining(name,page).toList();
    }

    @Override
    public List<Expense> readExpenseBetweenDate(Date startDate, Date endDate, Pageable page) {
        if(startDate==null){
            startDate = new Date(0);
        }
        if(endDate==null){
            endDate = new Date(System.currentTimeMillis());
        }
        return expenseRepository.findByDateBetween(startDate, endDate, page).toList();
    }
}
