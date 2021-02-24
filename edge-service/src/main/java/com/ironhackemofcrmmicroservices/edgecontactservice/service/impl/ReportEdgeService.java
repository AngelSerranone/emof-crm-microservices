package com.ironhackemofcrmmicroservices.edgecontactservice.service.impl;

import com.ironhackemofcrmmicroservices.edgecontactservice.client.OpportunityClient;
import com.ironhackemofcrmmicroservices.edgecontactservice.client.SalesRepClient;
import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.LeadsBySalesRepDto;
import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.OppsByProductDto;
import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.OppsBySalesRepDto;
import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.SalesRepDto;
import com.ironhackemofcrmmicroservices.edgecontactservice.service.interfaces.IReportEdgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportEdgeService implements IReportEdgeService {

    @Autowired
    private SalesRepClient salesRepClient;

    @Autowired
    private OpportunityClient opportunityClient;

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
}
