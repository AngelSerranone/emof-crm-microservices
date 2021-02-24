package com.ironhackemofcrmmicroservices.edgecontactservice.service.impl;

import com.ironhackemofcrmmicroservices.edgecontactservice.client.AccountClient;
import com.ironhackemofcrmmicroservices.edgecontactservice.client.OpportunityClient;
import com.ironhackemofcrmmicroservices.edgecontactservice.client.SalesRepClient;
import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.*;
import com.ironhackemofcrmmicroservices.edgecontactservice.service.interfaces.IReportEdgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class ReportEdgeService implements IReportEdgeService {

    @Autowired
    private SalesRepClient salesRepClient;

    @Autowired
    private OpportunityClient opportunityClient;

    @Autowired
    private AccountClient accountClient;

    public List<LeadsBySalesRepDto> getLeadsBySalesRep() {
        return salesRepClient.getLeadsBySalesRep();
    }

    public List<OppsBySalesRepDto> getOppsBySalesRep() {
        return salesRepClient.getOppsBySalesRep();
    }

    public List<OppsBySalesRepDto> getOppsBySalesRepAndStatus(String status) {
        List<SalesRepDto> salesRepDtoList = salesRepClient.getAllSalesRep();
        return opportunityClient.getOppsBySalesRepAndStatus(salesRepDtoList, status);
    }

    public List<OppsByProductDto> getOppsByProduct() {
        return opportunityClient.getOppsByProduct();
    }

    public List<OppsByProductDto> getOppsByProductAndStatus(String status) {
        return opportunityClient.getOppsByProductAndStatus(status);
    }

    public List<OppsByCityDto> getOppCountByCity() {
        return accountClient.getOppCountByCity();
    }

    public List<OppsByCityDto> getOppsByCityAndStatus(String status) {
        List<AccountDto> accountDtoList = accountClient.showAccounts();
        return opportunityClient.getOppsByCityAndStatus(accountDtoList, status);
    }

    public List<OppsByCountryDto> getOppsByCountry() {
        return accountClient.getOppsByCountry();
    }

    public List<OppsByCountryDto> getOppsByCountryAndStatus(String status) {
        List<AccountDto> accountDtoList = accountClient.showAccounts();
        return opportunityClient.getOppsByCountryAndStatus(accountDtoList, status);
    }
}
