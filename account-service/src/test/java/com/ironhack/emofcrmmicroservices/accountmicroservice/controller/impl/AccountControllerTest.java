package com.ironhack.emofcrmmicroservices.accountmicroservice.controller.impl;

import com.fasterxml.jackson.databind.*;
import com.ironhack.emofcrmmicroservices.accountmicroservice.controller.dto.*;
import com.ironhack.emofcrmmicroservices.accountmicroservice.enums.*;
import com.ironhack.emofcrmmicroservices.accountmicroservice.model.*;
import com.ironhack.emofcrmmicroservices.accountmicroservice.repository.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.web.context.*;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class AccountControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ContactIdRepository contactIdRepository;

    @Autowired
    private OpportunityIdRepository opportunityIdRepository;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    private Account account1;
    private Account account2;
    private ContactId contactId1;
    private ContactId contactId2;
    private OpportunityId opportunityId1;
    private OpportunityId opportunityId2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        account1 = new Account();
        account1.setIndustry(Industry.ECOMMERCE);
        account1.setEmployeeCount(40);
        account1.setCity("Madrid");
        account1.setCountry("Spain");

        account2 = new Account();
        account2.setIndustry(Industry.MEDICAL);
        account2.setEmployeeCount(100);
        account2.setCity("Málaga");
        account2.setCountry("Spain");
        accountRepository.saveAll(List.of(account1, account2));

        contactId1 = new ContactId(1, account1);
        contactId2 = new ContactId(2, account2);
        contactIdRepository.saveAll(List.of(contactId1, contactId2));

        opportunityId1 = new OpportunityId(1, account1);
        opportunityId2 = new OpportunityId(1, account2);
        opportunityIdRepository.saveAll(List.of(opportunityId1, opportunityId2));

        account1.setContactList(List.of(contactId1));
        account1.setOpportunityList(List.of(opportunityId1));

        account2.setContactList(List.of(contactId2));
        account2.setOpportunityList(List.of(opportunityId2));

        accountRepository.saveAll(List.of(account1, account2));
    }

    @AfterEach
    void tearDown() {
        contactIdRepository.deleteAll();
        opportunityIdRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    void showAccount_ValidId_CorrectAccount() throws Exception {

        MvcResult result = this.mockMvc.perform(
                get("/get-account/" + account1.getId())
        ).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Madrid"));
    }

    @Test
    void showAccount_InvalidId_NotFound() throws Exception {

        MvcResult result = this.mockMvc.perform(
                get("/get-account/10")
        ).andExpect(status().isNotFound()).andReturn();
    }

    @Test
    void showAccounts_NoInput_AllAccounts() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/get-account")
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2))).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Madrid"));
        assertTrue(result.getResponse().getContentAsString().contains("100"));
    }

    @Test
    void storeAccount_ValidAccountDto_Created() throws Exception {
        AccountDto accountDTO = new AccountDto();
        accountDTO.setIndustry("MEDICAL");
        accountDTO.setEmployeeCount(40);
        accountDTO.setCity("Oviedo");
        accountDTO.setCountry("Spain");
        accountDTO.setContactList(List.of(2, 4, 6));
        accountDTO.setOpportunityList(List.of(1, 3, 5));

        String body = objectMapper.writeValueAsString(accountDTO);

        MvcResult result = this.mockMvc.perform(
                post("/store-account")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();

        MvcResult result2 = this.mockMvc.perform(
                get("/get-account")
        ).andExpect(status().isOk()).andReturn();

        assertTrue(result2.getResponse().getContentAsString().contains("Oviedo"));

    }

    @Test
    void storeAccount_InvalidAccountDto_Error() throws Exception {
        AccountDto accountDTO = new AccountDto();
        accountDTO.setIndustry("MEDICAL");
        accountDTO.setEmployeeCount(40);
        accountDTO.setCity("");
        accountDTO.setCountry("");
        accountDTO.setContactList(List.of(2, 4, 6));
        accountDTO.setOpportunityList(List.of(1, 3, 5));

        String body = objectMapper.writeValueAsString(accountDTO);

        MvcResult result = this.mockMvc.perform(
                post("/store-account")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest()).andReturn();

        MvcResult result2 = this.mockMvc.perform(
                get("/get-account")
        ).andExpect(status().isOk()).andReturn();

    }

    @Test
    void updateAccount_ValidId_AccountUpdated() throws Exception {
        UpdateAccountDto updateAccountDto = new UpdateAccountDto(account1.getId(),123,456);

        String body = objectMapper.writeValueAsString(updateAccountDto);

        MvcResult result = this.mockMvc.perform(
                put("/update-account")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent()).andReturn();

        MvcResult result2 = this.mockMvc.perform(
                get("/get-account/" + account1.getId())
        ).andExpect(status().isOk()).andReturn();

        assertTrue(result2.getResponse().getContentAsString().contains("456"));

    }

    @Test
    void updateAccount_InvalidId_NotFound() throws Exception {
        UpdateAccountDto updateAccountDto = new UpdateAccountDto(1000,123,456);

        String body = objectMapper.writeValueAsString(updateAccountDto);

        MvcResult result = this.mockMvc.perform(
                put("/update-account")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();

    }
}