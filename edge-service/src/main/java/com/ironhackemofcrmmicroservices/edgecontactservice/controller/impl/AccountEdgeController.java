package com.ironhackemofcrmmicroservices.edgecontactservice.controller.impl;

import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.AccountDto;
import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.UpdateAccountDto;
import com.ironhackemofcrmmicroservices.edgecontactservice.controller.interfaces.IAccountEdgeController;
import com.ironhackemofcrmmicroservices.edgecontactservice.service.impl.AccountEdgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountEdgeController implements IAccountEdgeController {

    @Autowired
    private AccountEdgeService accountEdgeService;

    @GetMapping("/get-account/{id}")
    public AccountDto showAccount(@PathVariable Integer id) {
        return accountEdgeService.showAccount(id);
    }

    @GetMapping("/get-account")
    public List<AccountDto> showAccounts() {
        return accountEdgeService.showAccounts();
    }

    @PostMapping("/store-account")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto storeAccount(@RequestBody AccountDto accountDTO) {
        return accountEdgeService.storeAccount(accountDTO);
    }

    @PutMapping("/update-account")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAccount(@RequestBody UpdateAccountDto updateAccountDTO) {
        accountEdgeService.updateAccount(updateAccountDTO);
    }

    @GetMapping("/report/opps-per-account/mean")
    public Double getMeanOppsPerAccount() {
        return accountEdgeService.getMeanOppsPerAccount();
    }

    @GetMapping("/report/opps-per-account/max")
    public Integer getMaxOppsPerAccount() {
        return accountEdgeService.getMaxOppsPerAccount();
    }

    @GetMapping("/report/opps-per-account/min")
    public Integer getMinOppsPerAccount() {
        return accountEdgeService.getMinOppsPerAccount();
    }

    @GetMapping("/report/opps-per-account/median")
    public Double getMedianOppsPerAccount() {
        return accountEdgeService.getMedianOppsPerAccount();
    }

    @GetMapping("/report/employee-count/mean")
    public Double getMeanEmployeeCount() {
        return accountEdgeService.getMeanEmployeeCount();
    }

    @GetMapping("/report/employee-count/max")
    public Integer getMaxEmployeeCount() {
        return accountEdgeService.getMaxEmployeeCount();
    }

    @GetMapping("/report/employee-count/min")
    public Integer getMinEmployeeCount() {
        return accountEdgeService.getMinEmployeeCount();
    }

    @GetMapping("/report/employee-count/median")
    public Double getMedianEmployeeCount() {
        return accountEdgeService.getMedianEmployeeCount();
    }

}
