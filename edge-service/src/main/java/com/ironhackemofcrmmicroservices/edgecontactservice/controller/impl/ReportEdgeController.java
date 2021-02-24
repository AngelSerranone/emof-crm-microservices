package com.ironhackemofcrmmicroservices.edgecontactservice.controller.impl;

import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.LeadsBySalesRepDto;
import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.OppsByProductDto;
import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.OppsBySalesRepDto;
import com.ironhackemofcrmmicroservices.edgecontactservice.controller.interfaces.IReportEdgeController;
import com.ironhackemofcrmmicroservices.edgecontactservice.service.interfaces.IReportEdgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
