package com.ironhackemofcrmmicroservices.edgecontactservice.client;

import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.AccountDto;
import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.OppsByCountryDto;
import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.UpdateAccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient("account-service")
public interface AccountClient {

    @GetMapping("/get-account/{id}")
    AccountDto showAccount(@PathVariable Integer id);

    @GetMapping("/get-account")
    List<AccountDto> showAccounts();

    @PostMapping("/store-account")
    @ResponseStatus(HttpStatus.CREATED)
    AccountDto storeAccount(@RequestBody @Valid AccountDto accountDTO);

    @PutMapping("/update-account")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateAccount(@RequestBody @Valid UpdateAccountDto updateAccountDTO);

    @GetMapping("/report/opps-by-country")
    List<OppsByCountryDto> getOppsByCountry();
}

