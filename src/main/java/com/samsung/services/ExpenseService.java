package com.samsung.services;

import com.samsung.entities.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;

public interface ExpenseService {
    Page<Expense> getAllExpenses(Pageable page);
    Expense getExpenseById(Long id);
    void deleteExpenseById(Long id);
    Expense saveExpenseDetails(Expense expense);
    Expense updateExpenseDetails(Long id, Expense expense);
    List<Expense> readExpensesByCategory(String category, Pageable page);
    List<Expense> readExpensesByName(String name, Pageable page);
    List<Expense> readExpenseBetweenDate(Date startDate, Date endDate, Pageable page);
}
