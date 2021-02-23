package com.ironhack.emofcrmmicroservices.accountmicroservice.repository;

import com.ironhack.emofcrmmicroservices.accountmicroservice.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {


}
