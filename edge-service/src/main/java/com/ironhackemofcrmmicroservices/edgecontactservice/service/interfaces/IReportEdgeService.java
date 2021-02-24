package com.ironhackemofcrmmicroservices.edgecontactservice.service.interfaces;

import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.LeadsBySalesRepDto;
import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.OppsByProductDto;
import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.OppsBySalesRepDto;

import java.util.List;

public interface IReportEdgeService {
    List<LeadsBySalesRepDto> getLeadsBySalesRep();

    List<OppsBySalesRepDto> getOppsBySalesRep();

    List<OppsBySalesRepDto> getOppsBySalesRepAndStatus(String status);

    List<OppsByProductDto> getOppsByProduct();

    List<OppsByProductDto> getOppsByProductAndStatus(String status);
}
