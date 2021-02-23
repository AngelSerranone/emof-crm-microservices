package com.ironhack.emofcrmmicroservices.opportunityservice.service.impl;

import com.ironhack.emofcrmmicroservices.opportunityservice.client.AccountClient;
import com.ironhack.emofcrmmicroservices.opportunityservice.client.ContactClient;
import com.ironhack.emofcrmmicroservices.opportunityservice.client.LeadClient;
import com.ironhack.emofcrmmicroservices.opportunityservice.client.SalesRepClient;
import com.ironhack.emofcrmmicroservices.opportunityservice.controller.dto.*;
import com.ironhack.emofcrmmicroservices.opportunityservice.enums.Product;
import com.ironhack.emofcrmmicroservices.opportunityservice.enums.Status;
import com.ironhack.emofcrmmicroservices.opportunityservice.model.Opportunity;
import com.ironhack.emofcrmmicroservices.opportunityservice.repository.OpportunityRepository;
import com.ironhack.emofcrmmicroservices.opportunityservice.service.interfaces.IOpportunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OpportunityService implements IOpportunityService {

    @Autowired
    private OpportunityRepository opportunityRepository;

    @Autowired
    private LeadClient leadClient;

    @Autowired
    private ContactClient contactClient;

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private SalesRepClient salesRepClient;

    private CircuitBreakerFactory circuitBreakerFactory = new Resilience4JCircuitBreakerFactory();

    private CircuitBreaker leadCircuitBreaker = circuitBreakerFactory.create("lead-service");

    private CircuitBreaker contactCircuitBreaker = circuitBreakerFactory.create("contact-service");

    private CircuitBreaker accountCircuitBreaker = circuitBreakerFactory.create("account-service");

    private CircuitBreaker salesRepCircuitBreaker = circuitBreakerFactory.create("salesRep-service");


    public OpportunityDto getOpportunity(Integer id) {
        Opportunity opportunity = retrieveOpportunity(id);
        return oppModelToDto(opportunity);

    }

    private OpportunityDto oppModelToDto(Opportunity opportunity) {
        OpportunityDto opportunityDto = new OpportunityDto();
        opportunityDto.setId(opportunity.getId());
        opportunityDto.setProduct(String.valueOf(opportunity.getProduct()));
        opportunityDto.setQuantity(opportunity.getQuantity());
        opportunityDto.setStatus(String.valueOf(opportunity.getStatus()));
        opportunityDto.setContactId(opportunity.getContactId());
        opportunityDto.setSalesRepId(opportunity.getSalesRepId());
        return opportunityDto;
    }

    public void convertOpportunity(ConvertOpportunityDto convertOpportunityDto) {
        LeadDto leadDto = leadCircuitBreaker.run(() -> leadClient.getLead(convertOpportunityDto.getLeadId()), throwable -> getLeadFallback());
        ContactDto contactDto = contactCircuitBreaker.run(() -> contactClient.storeContact(leadDto), throwable -> storeContactFallback());
        Opportunity opportunity = createOpportunity(convertOpportunityDto, leadDto.getSalesRepId(), contactDto.getId());
        assignToSalesRep(leadDto.getSalesRepId(), opportunity.getId());
        assignToAccount(convertOpportunityDto, contactDto.getId(), opportunity.getId());
        leadClient.deleteLead(leadDto.getId());
    }

    private LeadDto getLeadFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
    }

    private ContactDto storeContactFallback() {
        throw new ResponseStatusException(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED);
    }

    private Opportunity createOpportunity(ConvertOpportunityDto convertOpportunityDto, Integer salesRepId, Integer contactId) {
        Product product = checkProductFormat(convertOpportunityDto.getProduct());
        checkSalesRep(salesRepId);
        Opportunity opportunity = new Opportunity(product, convertOpportunityDto.getQuantity(),
                contactId, Status.OPEN, salesRepId);
        opportunity = opportunityRepository.save(opportunity);
        return opportunity;
    }

    private Product checkProductFormat(String product) {
        try {
            return Product.valueOf(product.toUpperCase());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect product format: " +
                    "HYBRID/FLATBED/BOX");
        }
    }

    private void checkSalesRep(Integer salesRepId) {
        salesRepCircuitBreaker.run(() -> salesRepClient.getSalesRep(salesRepId), throwable -> getSalesRepFallback());
    }

    private SalesRepDto getSalesRepFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
    }

    private void assignToSalesRep(Integer salesRepId, Integer opportunityId) {
        try {
            salesRepClient.associateOppToSalesRep(salesRepId, opportunityId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    private void assignToAccount(ConvertOpportunityDto convertOpportunityDto, Integer contactId, Integer opportunityId) {
        if (convertOpportunityDto.getAccountId() != null) {
            updateAccount(convertOpportunityDto, contactId, opportunityId);
        } else {
            createAccount(convertOpportunityDto, contactId, opportunityId);
        }
    }

    private void updateAccount(ConvertOpportunityDto convertOpportunityDto, Integer contactId, Integer opportunityId) {
        UpdateAccountDto updateAccountDto = new UpdateAccountDto();
        updateAccountDto.setOpportunityId(opportunityId);
        updateAccountDto.setAccountId(convertOpportunityDto.getAccountId());
        updateAccountDto.setContactId(contactId);
        try {
            accountClient.updateAccount(updateAccountDto);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    private void createAccount(ConvertOpportunityDto convertOpportunityDto, Integer contactId, Integer opportunityId) {
        AccountDto accountDto = new AccountDto();
        accountDto.setIndustry(convertOpportunityDto.getIndustry());
        accountDto.setEmployeeCount(convertOpportunityDto.getEmployeeCount());
        accountDto.setCity(convertOpportunityDto.getCity());
        accountDto.setCountry(convertOpportunityDto.getCountry());
        accountDto.setContactList(List.of(contactId));
        accountDto.setOpportunityList(List.of(opportunityId));
        accountCircuitBreaker.run(() -> accountClient.storeAccount(accountDto), throwable -> storeAccountFallback());
    }

    private AccountDto storeAccountFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
    }

    public void closeOpportunity(CloseOpportunityDto closeOpportunityDto) {
        Opportunity opportunity = retrieveOpportunity(closeOpportunityDto.getOpportunityId());
        checkIfClosed(opportunity);
        Status status = checkStatusFormat(closeOpportunityDto.getStatus());
        opportunity.setStatus(status);
        opportunityRepository.save(opportunity);
    }

    public List<OppsBySalesRepDto> getOppsBySalesRepAndStatus(List<SalesRepDto> salesRepDtoList, String status) {
        List<OppsBySalesRepDto> dtoList = new ArrayList<>();
        Status checkedStatus = checkStatusFormat(status);
        for (SalesRepDto element : salesRepDtoList) {
            int oppCount = 0;
            for (Integer oppId : element.getOpportunities()) {
                Opportunity opportunity = retrieveOpportunity(oppId);
                if (opportunity.getStatus().equals(checkedStatus)) {
                    oppCount++;
                }
            }
            dtoList.add(new OppsBySalesRepDto(element.getId(), oppCount));
        }
        return dtoList;
    }

    private Opportunity retrieveOpportunity(Integer opportunityId) {
        Optional<Opportunity> opportunity = opportunityRepository.findById(opportunityId);
        if (opportunity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Opportunity with id " + opportunityId + " not found");
        }
        return opportunity.get();
    }

    private void checkIfClosed(Opportunity opportunity) {
        if (opportunity.getStatus().equals(Status.CLOSED_LOST) || opportunity.getStatus().equals(Status.CLOSED_WON)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Opportunity already closed.");
        }
    }

    private Status checkStatusFormat(String status) {
        try {
            return Status.valueOf(status.toUpperCase());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect status format: CLOSED-WON / CLOSED-LOST");
        }
    }
}
