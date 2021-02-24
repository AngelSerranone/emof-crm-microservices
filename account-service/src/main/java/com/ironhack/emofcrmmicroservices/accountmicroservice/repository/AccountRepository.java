package com.ironhack.emofcrmmicroservices.accountmicroservice.repository;

import com.ironhack.emofcrmmicroservices.accountmicroservice.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
	@Query("SELECT CAST(a.country AS int), CAST(COUNT(*) AS int) FROM OpportunityId o JOIN Account a ON o.account_id = a.id GROUP BY o.account_id")
	List<Object[]> getOppsByCountry();
}
