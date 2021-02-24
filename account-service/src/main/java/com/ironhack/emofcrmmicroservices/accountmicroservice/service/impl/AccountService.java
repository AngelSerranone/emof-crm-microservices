package com.ironhack.emofcrmmicroservices.accountmicroservice.service.impl;


import com.ironhack.emofcrmmicroservices.accountmicroservice.controller.dto.*;
import com.ironhack.emofcrmmicroservices.accountmicroservice.enums.*;
import com.ironhack.emofcrmmicroservices.accountmicroservice.model.*;
import com.ironhack.emofcrmmicroservices.accountmicroservice.repository.*;
import com.ironhack.emofcrmmicroservices.accountmicroservice.service.interfaces.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.server.*;

import java.util.*;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ContactIdRepository contactIdRepository;

    @Autowired
    private OpportunityIdRepository opportunityIdRepository;

    public AccountDTO showAccount(Integer id) {

        Optional<Account> account = accountRepository.findById(id);

        if (account.isPresent()) {

            List<Integer> contacts = new ArrayList<>();

            for (int i=0; i < account.get().getContactList().size(); i++){
                contacts.add(account.get().getContactList().get(i).getContactId());
            }

            List<Integer> opportunities = new ArrayList<>();

            for (int i=0; i < account.get().getOpportunityList().size(); i++){
                opportunities.add(account.get().getOpportunityList().get(i).getOpportunityId());
            }

            return new AccountDTO(id, account.get().getIndustry().toString(), account.get().getEmployeeCount(),
                    account.get().getCity(), account.get().getCountry(), contacts, opportunities);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    public List<AccountDTO> showAccounts() {

        List<Account> accountList = accountRepository.findAll();
        List<AccountDTO> accountDTOList = new ArrayList<>();

        for (Account account : accountList) {

            List<Integer> contacts = new ArrayList<>();

            for (int i=0; i < account.getContactList().size(); i++){
                contacts.add(account.getContactList().get(i).getContactId());
            }

            List<Integer> opportunities = new ArrayList<>();

            for (int i=0; i < account.getOpportunityList().size(); i++){
                opportunities.add(account.getOpportunityList().get(i).getOpportunityId());
            }

            accountDTOList.add(new AccountDTO(account.getId(), account.getIndustry().toString(), account.getEmployeeCount(), account.getCity(),
                    account.getCountry(), contacts, opportunities));
        }
        return accountDTOList;

    }

    public AccountDTO storeAccount(AccountDTO accountDTO) {

        List<ContactId> contacts = new ArrayList<>();
        List<OpportunityId> opportunities = new ArrayList<>();

        Account account = new Account();
        account.setIndustry(Industry.valueOf(accountDTO.getIndustry()));
        account.setEmployeeCount(accountDTO.getEmployeeCount());
        account.setCity(accountDTO.getCity());
        account.setCountry(accountDTO.getCountry());
        accountRepository.save(account);

        for (int i=0; i < accountDTO.getContactList().size(); i++){
            ContactId contactId = new ContactId(accountDTO.getContactList().get(i), account);
            contacts.add(contactId);
            contactIdRepository.save(contactId);
        }

        for (int i=0; i < accountDTO.getOpportunityList().size(); i++){
            OpportunityId opportunityId = new OpportunityId(accountDTO.getOpportunityList().get(i), account);
            opportunities.add(opportunityId);
            opportunityIdRepository.save(opportunityId);
        }

        account.setContactList(contacts);
        account.setOpportunityList(opportunities);

        accountRepository.save(account);
        accountDTO.setId(account.getId());
        return accountDTO;
    }

    public void updateAccount(UpdateAccountDTO updateAccountDTO) {
        Optional<Account> account = accountRepository.findById(updateAccountDTO.getAccountId());

        if (account.isPresent()) {

            ContactId contactId = new ContactId(updateAccountDTO.getContactId(), account.get());
            OpportunityId opportunityId = new OpportunityId(updateAccountDTO.getOpportunityId(), account.get());

            contactIdRepository.save(contactId);
            opportunityIdRepository.save(opportunityId);

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public List<OppsByCountryDto> getOppsByCountry() {
        List<Object[]> reportList = accountRepository.getOppsByCountry();
        List<OppsByCountryDto> dtoList = new ArrayList<>();
        for (Object[] element: reportList) {
            dtoList.add(new OppsByCountryDto((String) element[0], (Integer) element[1]));
        }
        return dtoList;
    }

}
