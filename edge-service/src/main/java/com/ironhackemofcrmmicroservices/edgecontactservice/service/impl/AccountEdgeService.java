package com.ironhackemofcrmmicroservices.edgecontactservice.service.impl;

import com.ironhackemofcrmmicroservices.edgecontactservice.client.AccountClient;
import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.AccountDto;
import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.UpdateAccountDto;
import com.ironhackemofcrmmicroservices.edgecontactservice.service.interfaces.IAccountEdgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AccountEdgeService implements IAccountEdgeService {

    @Autowired
    private AccountClient accountClient;

    private CircuitBreakerFactory circuitBreakerFactory = new Resilience4JCircuitBreakerFactory();

    public AccountDto showAccount(Integer id) {
        CircuitBreaker showAccountCircuitBreaker = circuitBreakerFactory.create("account-service");
        return showAccountCircuitBreaker.run(() -> accountClient.showAccount(id), throwable -> showAccountFallback());
    }

    private AccountDto showAccountFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
    }

    public List<AccountDto> showAccounts() {
        CircuitBreaker showAccountsCircuitBreaker = circuitBreakerFactory.create("account-service");
        return showAccountsCircuitBreaker.run(() -> accountClient.showAccounts(), throwable -> showAccountsFallback());
    }

    private List<AccountDto> showAccountsFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
    }

    public AccountDto storeAccount(AccountDto accountDTO) {
        CircuitBreaker storeAccountCircuitBreaker = circuitBreakerFactory.create("account-service");
        return storeAccountCircuitBreaker.run(() -> accountClient.storeAccount(accountDTO), throwable -> storeAccountFallback());
    }

    private AccountDto storeAccountFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
    }

    public void updateAccount(UpdateAccountDto updateAccountDto) {
        accountClient.updateAccount(updateAccountDto);
    }

    private Double accountDoubleFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
    }

    private Double accountIntegerFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
    }

    public Double getMeanOppsPerAccount() {
        CircuitBreaker storeAccountCircuitBreaker = circuitBreakerFactory.create("account-service");
        return storeAccountCircuitBreaker.run(() -> accountClient.getMeanOppsPerAccount(), throwable -> accountDoubleFallback());
    }

    public Integer getMaxOppsPerAccount() {
        CircuitBreaker storeAccountCircuitBreaker = circuitBreakerFactory.create("account-service");
        return (Integer) storeAccountCircuitBreaker.run(() -> accountClient.getMaxOppsPerAccount(), throwable -> accountIntegerFallback());
    }


    public Integer getMinOppsPerAccount() {
        CircuitBreaker storeAccountCircuitBreaker = circuitBreakerFactory.create("account-service");
        return (Integer) storeAccountCircuitBreaker.run(() -> accountClient.getMinOppsPerAccount(), throwable -> accountIntegerFallback());
    }


    public Double getMedianOppsPerAccount() {
        CircuitBreaker storeAccountCircuitBreaker = circuitBreakerFactory.create("account-service");
        return storeAccountCircuitBreaker.run(() -> accountClient.getMedianOppsPerAccount(), throwable -> accountDoubleFallback());
    }


    public Double getMeanEmployeeCount() {
        CircuitBreaker storeAccountCircuitBreaker = circuitBreakerFactory.create("account-service");
        return storeAccountCircuitBreaker.run(() -> accountClient.getMeanEmployeeCount(), throwable -> accountDoubleFallback());
    }

    public Integer getMaxEmployeeCount() {
        CircuitBreaker storeAccountCircuitBreaker = circuitBreakerFactory.create("account-service");
        return (Integer) storeAccountCircuitBreaker.run(() -> accountClient.getMaxEmployeeCount(), throwable -> accountIntegerFallback());
    }


    public Integer getMinEmployeeCount() {
        CircuitBreaker storeAccountCircuitBreaker = circuitBreakerFactory.create("account-service");
        return (Integer) storeAccountCircuitBreaker.run(() -> accountClient.getMinEmployeeCount(), throwable -> accountIntegerFallback());
    }


    public Double getMedianEmployeeCount() {
        CircuitBreaker storeAccountCircuitBreaker = circuitBreakerFactory.create("account-service");
        return storeAccountCircuitBreaker.run(() -> accountClient.getMedianEmployeeCount(), throwable -> accountDoubleFallback());
    }
}
