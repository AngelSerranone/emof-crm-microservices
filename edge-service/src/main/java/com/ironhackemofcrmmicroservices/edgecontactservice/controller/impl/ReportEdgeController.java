package com.ironhackemofcrmmicroservices.edgecontactservice.controller.impl;

import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.*;
import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.LeadsBySalesRepDto;
import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.OppsByCountryDto;
import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.OppsByProductDto;
import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.OppsBySalesRepDto;
import com.ironhackemofcrmmicroservices.edgecontactservice.controller.interfaces.IReportEdgeController;
import com.ironhackemofcrmmicroservices.edgecontactservice.service.interfaces.IReportEdgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReportEdgeController implements IReportEdgeController {

    @Autowired
    private IReportEdgeService reportEdgeService;

    @GetMapping("/report/leads-by-salesRep")
    public List<LeadsBySalesRepDto> getLeadsBySalesRep() {
        return reportEdgeService.getLeadsBySalesRep();
    }

    @GetMapping("/report/opps-by-salesRep")
    public List<OppsBySalesRepDto> getOppsBySalesRep() {
        return reportEdgeService.getOppsBySalesRep();
    }

    @GetMapping("/report/opps-by-salesRep/{status}")
    public List<OppsBySalesRepDto> getOppsBySalesRepAndStatus(@PathVariable String status) {
        return reportEdgeService.getOppsBySalesRepAndStatus(status);
    }

    @GetMapping("/report/opps-by-product")
    public List<OppsByProductDto> getOppsByProduct() {
        return reportEdgeService.getOppsByProduct();
    }

    @GetMapping("/report/opps-by-product/{status}")
    public List<OppsByProductDto> getOppsByProductAndStatus(@PathVariable String status) {
        return reportEdgeService.getOppsByProductAndStatus(status);
    }

    @GetMapping("/report/opps-by-city")
    public List<OppsByCityDto> getOppCountByCity() {
        return reportEdgeService.getOppCountByCity();
    }

    @GetMapping("report/opps-by-city/{status}")
    public List<OppsByCityDto> getOppsByCityAndStatus(@PathVariable String status) {
        return reportEdgeService.getOppsByCityAndStatus(status);
    }

    @GetMapping("/report/opps-by-country")
    public List<OppsByCountryDto> getOppsByCountry() {
        return reportEdgeService.getOppsByCountry();
    }

    @GetMapping("/report/opps-by-country/{status}")
    public List<OppsByCountryDto> getOppsByCountryAndStatus(String status) {
        return reportEdgeService.getOppsByCountryAndStatus(status);
    }
}
