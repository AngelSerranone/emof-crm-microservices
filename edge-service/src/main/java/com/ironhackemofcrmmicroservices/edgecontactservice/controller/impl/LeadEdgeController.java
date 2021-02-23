package com.ironhackemofcrmmicroservices.edgecontactservice.controller.impl;

import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.LeadDto;
import com.ironhackemofcrmmicroservices.edgecontactservice.service.impl.LeadEdgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LeadEdgeController {

    @Autowired
    LeadEdgeService edgeService;

    @GetMapping("/get-lead/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LeadDto getLead(@PathVariable Integer id) {
        return edgeService.getLead(id);
    }

    @GetMapping("/get-leads")
    @ResponseStatus(HttpStatus.OK)
    public List<LeadDto> getAllLeads() {
        return edgeService.getAllLeads();
    }

    @PostMapping("/store-lead")
    @ResponseStatus(HttpStatus.CREATED)
    public LeadDto storeLead(@RequestBody LeadDto leadDto) {
        return edgeService.storeLead(leadDto);
    }

    @DeleteMapping("delete-lead/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteLead(@PathVariable Integer id) {
        edgeService.deleteLead(id);
    }
}
