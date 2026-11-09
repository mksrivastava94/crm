package com.crm.erp.service;

import com.crm.erp.dto.SaleBillDateRangeDto;
import com.crm.erp.dto.SaleBillDto;
import com.crm.erp.model.SaleBill;
import com.crm.erp.repository.SaleBillRepository;
import com.crm.erp.util.BasicUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleBillService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaleBillService.class);
    public final SaleBillRepository saleBillRepository;

    @Autowired
    public SaleBillService(SaleBillRepository saleBillRepository) {
        this.saleBillRepository = saleBillRepository;
    }

    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<SaleBillDto> createSaleBill(SaleBillDto saleBillDto)  {
        SaleBill saleBill = BasicUtil.convertSaleBillDtoToSaleBill(saleBillDto);
        SaleBill newSaleBill = saleBillRepository.save(saleBill);
        SaleBillDto newSaleBillDto = BasicUtil.convertSaleBillToSaleBillDto(newSaleBill);
        return new ResponseEntity<>(newSaleBillDto, HttpStatus.CREATED);
    }

    public ResponseEntity<List<SaleBillDto>> getAllSaleBill(String orgId) {
        LOGGER.info("Method getAllSaleBill() is started");
        List<SaleBill> saleList = saleBillRepository.findAllByOrgId(orgId);
        LOGGER.info("Method getAllSaleBill() is ended");
        return new ResponseEntity<>(BasicUtil.convertSaleListToSaleDtoList(saleList), HttpStatus.OK);
    }

    public ResponseEntity<List<SaleBillDto>> getAllSaleBillByMonth(String orgId, SaleBillDateRangeDto dateRangeDto) {
        LOGGER.info("Method getAllSaleBillByMonth() is started");
        List<SaleBill> saleList = saleBillRepository.findAllByOrgId(orgId).stream()
                .filter(sale -> sale.getCreatedDateTime().getMonthValue() == dateRangeDto.getMonth()
                        && sale.getCreatedDateTime().getYear() == dateRangeDto.getYear())
                .collect(Collectors.toList());
        LOGGER.info("Method getAllSaleBillByMonth() is ended");
        return new ResponseEntity<>(BasicUtil.convertSaleListToSaleDtoList(saleList), HttpStatus.OK);
    }

}
