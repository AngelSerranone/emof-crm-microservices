package com.ironhackemofcrmmicroservices.edgecontactservice.controller.interfaces;


import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.AccountDto;
import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.UpdateAccountDto;

import java.util.List;

public interface IAccountEdgeController {
    AccountDto showAccount(Integer id);

    List<AccountDto> showAccounts();

    AccountDto storeAccount(AccountDto accountDTO);

    void updateAccount(UpdateAccountDto updateAccountDTO);

    public Double getMeanOppsPerAccount();

    public Integer getMaxOppsPerAccount();

    public Integer getMinOppsPerAccount();

    public Double getMedianOppsPerAccount();

    public Double getMeanEmployeeCount();

    public Integer getMaxEmployeeCount();

    public Integer getMinEmployeeCount();

    public Double getMedianEmployeeCount();
}
