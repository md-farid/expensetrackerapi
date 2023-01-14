package com.samsung.repositories;

import com.samsung.entities.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long> {

    // SELECT * FROM tbl_expenses WHERE category=?
    Page<Expense> findByUserIdAndCategory(Long userId, String category, Pageable page);

    // SELECT * FROM tbl_expenses name LIKE '%keyword%'
    Page<Expense> findByUserIdAndNameContaining(Long userId, String keyword, Pageable page);

    // SELECT * FROM tbl_expenses date BETWEEN 'startDate' AND 'endDate'
    Page<Expense> findByUserIdAndDateBetween(Long userId, Date startDate, Date endDate, Pageable page);

    // SELECT * FROM tbl_expenses WHERE userId=?
    Page<Expense> findByUserId(Long userId, Pageable page);

    // SELECT * FROM tbl_expenses WHERE userId=? AND id=?
    Optional<Expense> findByUserIdAndId(Long userId, Long id);
}
