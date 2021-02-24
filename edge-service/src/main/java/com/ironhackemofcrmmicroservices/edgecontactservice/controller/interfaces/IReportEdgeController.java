package com.ironhackemofcrmmicroservices.edgecontactservice.controller.interfaces;

import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.LeadsBySalesRepDto;
import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.OppsByProductDto;
import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.OppsBySalesRepDto;
import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.SalesRepDto;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface IReportEdgeController {
    List<LeadsBySalesRepDto> getLeadsBySalesRep();

    List<OppsBySalesRepDto> getOppsBySalesRep();

    List<OppsBySalesRepDto> getOppsBySalesRepAndStatus(String status);

    List<OppsByProductDto> getOppsByProduct();

    List<OppsByProductDto> getOppsByProductAndStatus(String status);
}
