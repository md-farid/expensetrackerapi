package com.samsung.repositories;

import com.samsung.entities.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long> {

    // SELECT * FROM tbl_expenses WHERE category=?
    Page<Expense> findByCategory(String category, Pageable page);

    // SELECT * FROM tbl_expenses name LIKE '%keyword%'
    Page<Expense> findByNameContaining(String keyword, Pageable page);

    // SELECT * FROM tbl_expenses date BETWEEN 'startDate' AND 'endDate'
    Page<Expense> findByDateBetween(Date startDate, Date endDate, Pageable page);

    // SELECT * FROM tbl_expenses WHERE userId=?
    Page<Expense> findByUserId(Long userId, Pageable page);
}
