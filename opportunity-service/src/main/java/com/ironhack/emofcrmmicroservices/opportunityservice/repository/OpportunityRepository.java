package com.ironhack.emofcrmmicroservices.opportunityservice.repository;

import com.ironhack.emofcrmmicroservices.opportunityservice.model.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Integer> {
}
