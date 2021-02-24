package com.ironhackemofcrmmicroservices.edgecontactservice.controller.impl;

import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.CloseOpportunityDto;
import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.ConvertOpportunityDto;
import com.ironhackemofcrmmicroservices.edgecontactservice.controller.dtos.OpportunityDto;
import com.ironhackemofcrmmicroservices.edgecontactservice.controller.interfaces.IOpportunityEdgeController;
import com.ironhackemofcrmmicroservices.edgecontactservice.service.interfaces.IOpportunityEdgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class OpportunityEdgeController implements IOpportunityEdgeController {

    @Autowired
    private IOpportunityEdgeService opportunityEdgeService;

    @GetMapping("/get-opportunity/{id}")
    public OpportunityDto getOpportunity(@PathVariable Integer id) {
        return opportunityEdgeService.getOpportunity(id);
    }

    @PostMapping("/convert-opportunity")
    @ResponseStatus(HttpStatus.CREATED)
    public void convertOpportunity(@RequestBody ConvertOpportunityDto convertOpportunityDto) {
        opportunityEdgeService.convertOpportunity(convertOpportunityDto);
    }

    @PutMapping("/close-opportunity")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void closeOpportunity(@RequestBody CloseOpportunityDto closeOpportunityDto) {
        opportunityEdgeService.closeOpportunity(closeOpportunityDto);
    }

    @GetMapping("/report/quantity-ordered-products/mean")
    public Double getMeanQuantityOrderedProducts() {
        return opportunityEdgeService.getMeanQuantityOrderedProducts();
    }

    @GetMapping("/report/quantity-ordered-products/max")
    public Integer getMaxQuantityOrderedProducts() {
        return opportunityEdgeService.getMaxQuantityOrderedProducts();
    }

    @GetMapping("/report/quantity-ordered-products/min")
    public Integer getMinQuantityOrderedProducts() {
        return opportunityEdgeService.getMinQuantityOrderedProducts();
    }

    @GetMapping("/report/quantity-ordered-products/median")
    public Double getMedianQuantityOrderedProducts() {
        return opportunityEdgeService.getMedianQuantityOrderedProducts();
    }
}
