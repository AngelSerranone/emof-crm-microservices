package com.ironhack.emofcrmmicroservices.accountmicroservice.controller.impl;



import com.ironhack.emofcrmmicroservices.accountmicroservice.controller.dto.*;
import com.ironhack.emofcrmmicroservices.accountmicroservice.controller.interfaces.*;
import com.ironhack.emofcrmmicroservices.accountmicroservice.service.impl.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.*;

@RestController
public class AccountController implements IAccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/get-account/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDTO showAccount(@PathVariable Integer id) {
        return accountService.showAccount(id);
    }

    @GetMapping("/get-account")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountDTO> showAccounts() {
        return accountService.showAccounts();
    }

    @PostMapping("/store-account")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDTO storeAccount(@RequestBody @Valid AccountDTO accountDTO) {
        return accountService.storeAccount(accountDTO);
    }

    @PutMapping("/update-account")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAccount(@RequestBody @Valid UpdateAccountDTO updateAccountDTO) {
        accountService.updateAccount(updateAccountDTO);
    }

}
