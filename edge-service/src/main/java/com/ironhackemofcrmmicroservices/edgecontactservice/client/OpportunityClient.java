package com.ironhackemofcrmmicroservices.edgecontactservice.client;

import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("opportunity-service")
public interface OpportunityClient {
    @GetMapping("/get-opportunity/{id}")
    OpportunityDto getOpportunity(@PathVariable Integer id);

    @PostMapping("/convert-opportunity")
    void convertOpportunity(@RequestBody ConvertOpportunityDto convertOpportunityDto);

    @PutMapping("/close-opportunity")
    void closeOpportunity(@RequestBody CloseOpportunityDto closeOpportunityDto);

    @PutMapping("/report/opps-by-salesRep/{status}")
    List<OppsBySalesRepDto> getOppsBySalesRepAndStatus(@RequestBody List<SalesRepDto> salesRepDtoList, @PathVariable String status);
}
