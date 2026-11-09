package com.technu.erp.service;

import com.technu.erp.dto.ExpenseDateDto;
import com.technu.erp.dto.ExpenseDto;
import com.technu.erp.dto.ExpensePageDto;
import com.technu.erp.enumeration.BucketName;
import com.technu.erp.exception.domain.ExpenseNotFoundException;
import com.technu.erp.exception.domain.InvalidInputException;
import com.technu.erp.external.UploadFeignClient;
import com.technu.erp.model.Expense;
import com.technu.erp.model.request.FileUploadRequest;
import com.technu.erp.repository.ExpenseRepository;
import com.technu.erp.util.BasicUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.technu.erp.constant.ExpenseConstant.EXPENSE_NOT_FOUND;
import static com.technu.erp.constant.FileConstant.ORG_NAME;

@Service
@Log4j2
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UploadFeignClient uploadFeignClient;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository, UploadFeignClient uploadFeignClient) {
        this.expenseRepository = expenseRepository;
        this.uploadFeignClient = uploadFeignClient;
    }

    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<ExpenseDto> createExpense(ExpenseDto expenseDto) throws InvalidInputException {
        log.info("Method  createExpense() started");
        String base64 = expenseDto.getInvoiceLink();
        expenseDto.setInvoiceLink("");
        Expense expense= BasicUtil.convertExpenseDtoToExpense(expenseDto);
        Expense newExpense = expenseRepository.save(expense);


        if (!BasicUtil.notEmptyNotNull(base64))
            throw new InvalidInputException("Please select file");

        String path = ORG_NAME.concat("/").concat(newExpense.getOrgId()).concat("/").concat(BucketName.PURCHASEBILL.getBucketName());

        FileUploadRequest uploadRequest = FileUploadRequest.builder()
                .id(newExpense.getId())
                .path(path)
                .data(base64)
                .build();
        
        path = uploadFeignClient.uploadBase64(uploadRequest).getBody();
        newExpense.setInvoiceLink(path);
        newExpense = expenseRepository.save(newExpense);

        ExpenseDto expenseDtoResponse = BasicUtil.convertExpenseToExpenseDto(newExpense);
        log.info("Method  createExpense() ended");
        return new ResponseEntity<>(expenseDtoResponse, HttpStatus.CREATED);
    }

    public ResponseEntity<ExpenseDto> getExpenseById(String id) throws ExpenseNotFoundException {
        log.info("Method  getExpenseById() started");
        Expense eExpense = findExpenseById(id);
        ExpenseDto expenseDto = BasicUtil.convertExpenseToExpenseDto(eExpense);
        log.info("Method  getExpenseById() ended");
        return new ResponseEntity<>(expenseDto,HttpStatus.OK);
    }

    public ResponseEntity<ExpensePageDto> getAllExpense(final String orgId, final int page, final int pageSize) {
        log.info("Method  getAllExpense() started");
        Pageable pageable = PageRequest.of(page,pageSize);
        Page<Expense> pages = expenseRepository.findAllByOrgId(orgId,pageable);
        ExpensePageDto data = getExpensePageData(pages);
        log.info("Method  getAllExpense() started");
        return new ResponseEntity<>(data,HttpStatus.OK);
    }

    private ExpensePageDto getExpensePageData(Page<Expense> pages) {
        ExpensePageDto expensePageDto = new ExpensePageDto();
        expensePageDto.setData(BasicUtil.convertExpenseListToExpenseDtoList(pages.getContent()));
        expensePageDto.setTotalPages(pages.getTotalPages());
        return expensePageDto;
    }

    public ResponseEntity<List<ExpenseDto>> getAllExpenseByMonth(String orgId, ExpenseDateDto expenseDateDto) {
        log.info("Method getAllExpenseByMonth() is started");
        List<Expense> expenseList = expenseRepository.findAllByOrgId(orgId).stream()
                .filter(expense -> expense.getCreatedDateTime().getMonthValue() == expenseDateDto.getMonth()
                        && expense.getCreatedDateTime().getYear() == expenseDateDto.getYear())
                .collect(Collectors.toList());
        log.info("Method getAllExpenseByMonth() is ended");
        return new ResponseEntity<>(BasicUtil.convertExpenseListToExpenseDtoList(expenseList),HttpStatus.OK);
    }

    public ResponseEntity<ExpenseDto> updateExpense(String id, ExpenseDto expenseDto) throws  ExpenseNotFoundException {
        log.info("Method  updateExpense() started");
        findExpenseById(id);
        Expense eExpense = BasicUtil.convertExpenseDtoToExpense(expenseDto);
        eExpense = expenseRepository.save(eExpense);
        log.info("Method  updateExpense() ended");
        return new ResponseEntity<>(BasicUtil.convertExpenseToExpenseDto(eExpense), HttpStatus.ACCEPTED);
    }

    private Expense findExpenseById(String id) throws ExpenseNotFoundException {
        return expenseRepository.findById(id)
                .orElseThrow(()->new ExpenseNotFoundException(EXPENSE_NOT_FOUND));
    }

    public void expenseDeleteById(String id) throws ExpenseNotFoundException {
        log.info("Method  expenseDeleteById() started");
        findExpenseById(id);
        expenseRepository.deleteById(id);
        log.info("Method  expenseDeleteById() ended");
    }
}
