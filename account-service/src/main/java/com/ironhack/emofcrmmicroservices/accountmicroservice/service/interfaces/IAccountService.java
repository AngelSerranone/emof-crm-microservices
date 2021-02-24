package com.ironhack.emofcrmmicroservices.accountmicroservice.service.interfaces;

import com.ironhack.emofcrmmicroservices.accountmicroservice.controller.dto.*;

import java.util.*;

public interface IAccountService {
    AccountDTO showAccount(Integer id);
    List<AccountDTO> showAccounts();
    AccountDTO storeAccount(AccountDTO accountDTO);
    void updateAccount(UpdateAccountDTO updateAccountDTO);
    List<OppsByCountryDto> getOppsByCountry();
}
