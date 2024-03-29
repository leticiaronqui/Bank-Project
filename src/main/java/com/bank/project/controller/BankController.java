package com.bank.project.controller;


import com.bank.project.entity.TransactionRequest;
import com.bank.project.service.BankService;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;


@Slf4j
@AllArgsConstructor
@RestController
public class BankController {

    private BankService bankService;

    @RequestMapping(value = "/balance", method = RequestMethod.GET)
    public ResponseEntity<BigDecimal> getBalance(@PathParam("account_id") Integer account_id) throws IOException {
        log.info(">>> Init find balance <<<");
        var response = bankService.findBalance(account_id);

        return response.equals(BigDecimal.ZERO) ? new ResponseEntity<>(BigDecimal.ZERO, HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/event", method = RequestMethod.POST)
    public ResponseEntity<Object> transactionAccount(@RequestBody TransactionRequest request) throws IOException {
        log.info(">>> Init transaction account <<<");
        try {
            return new ResponseEntity<>(bankService.transaction(request), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(BigDecimal.ZERO, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public ResponseEntity<String> resetState() throws IOException {
        log.info(">>> Init reset <<<");
        bankService.reset();

        return new ResponseEntity<>("OK", HttpStatus.OK);

    }

}
