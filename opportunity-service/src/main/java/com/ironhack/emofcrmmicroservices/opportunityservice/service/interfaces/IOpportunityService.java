package com.ironhack.emofcrmmicroservices.opportunityservice.service.interfaces;

import com.ironhack.emofcrmmicroservices.opportunityservice.controller.dto.*;

import java.util.List;

public interface IOpportunityService {
    OpportunityDto getOpportunity(Integer id);

    void convertOpportunity(ConvertOpportunityDto convertOpportunityDto);

    void closeOpportunity(CloseOpportunityDto closeOpportunityDto);

    List<OppsBySalesRepDto> getOppsBySalesRepAndStatus(List<SalesRepDto> salesRepDtoList, String status);

    List<OppsByProductDto> getOppsByProduct();

    List<OppsByProductDto> getOppsByProductAndStatus(String status);

	List<OppsByCountryDto> getOppsByCountryAndStatus(List<AccountDto> accountDtoList, String status);
}
