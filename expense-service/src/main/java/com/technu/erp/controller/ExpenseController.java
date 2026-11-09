package com.technu.erp.controller;

import com.technu.erp.dto.ExpenseDateDto;
import com.technu.erp.dto.ExpenseDto;
import com.technu.erp.dto.ExpensePageDto;
import com.technu.erp.dto.ExpenseUpdateDto;
import com.technu.erp.exception.ExceptionHandling;
import com.technu.erp.exception.domain.ExpenseNotFoundException;
import com.technu.erp.exception.domain.InvalidInputException;
import com.technu.erp.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expense")
public class ExpenseController extends ExceptionHandling {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping("/add")
    public ResponseEntity<ExpenseDto> createExpense(@RequestBody ExpenseDto expenseDTO) throws InvalidInputException {
        return expenseService.createExpense(expenseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDto> getExpenseById(@PathVariable String id) throws ExpenseNotFoundException {
        return expenseService.getExpenseById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ExpenseDto> updateExpense(@PathVariable String id, @RequestBody ExpenseDto expenseDto) throws ExpenseNotFoundException {
        return expenseService.updateExpense(id,expenseDto);
    }

    @GetMapping("list/{orgId}/{page}/{pageSize}")
    public ResponseEntity<ExpensePageDto> getAllExpense(@PathVariable String orgId, @PathVariable int page, @PathVariable int pageSize){
        return expenseService.getAllExpense(orgId,page,pageSize);
    }

    @GetMapping("date/{orgId}")
    public ResponseEntity<List<ExpenseDto>> getAllExpenseByMonth(@PathVariable String orgId,@RequestBody ExpenseDateDto expenseDateDto){
        return expenseService.getAllExpenseByMonth(orgId,expenseDateDto);
    }

    @DeleteMapping("/delete/{id}")
    public void expenseDeleteById(@PathVariable String id) throws ExpenseNotFoundException {
        expenseService.expenseDeleteById(id);
    }
}
