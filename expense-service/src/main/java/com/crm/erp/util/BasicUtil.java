package com.crm.erp.util;

import com.crm.erp.dto.ExpenseDto;
import com.crm.erp.model.Expense;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class BasicUtil {

    private BasicUtil(){}

    public static boolean notEmptyNotNull(String input){
        return !(input == null || input.isEmpty() );
    }

    public static boolean notEmptyNotNull(Integer input){
        return input != null && input > 0;
    }

    public static boolean notEmptyNotNull(Double input){
        return input != null && input > 0.0;
    }

    public static boolean isValidatePhoneNumber(String input){
        return input.length() == 10;
    }


    public static ExpenseDto convertExpenseToExpenseDto(Expense expense){
        return new ModelMapper().map(expense,ExpenseDto.class);
    }

    public static Expense convertExpenseDtoToExpense(ExpenseDto expenseDto){
        return new ModelMapper().map(expenseDto,Expense.class);
    }

    public static List<Expense> convertExpenseDtoListToExpenseList(List<ExpenseDto> expenseDtoList){
        return expenseDtoList.stream()
                .map(expenseDto -> new ModelMapper().map(expenseDto,Expense.class))
                .collect(Collectors.toList());
    }

    public static List<ExpenseDto> convertExpenseListToExpenseDtoList(List<Expense> expenseList){
        return expenseList.stream()
                .map(expense -> new ModelMapper().map(expense,ExpenseDto.class))
                .collect(Collectors.toList());
    }
}
