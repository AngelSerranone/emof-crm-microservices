package com.ironhack.emofcrmmicroservices.accountmicroservice.controller.dto;

import javax.validation.constraints.*;

public class UpdateAccountDTO {
    @NotNull
    private Integer accountId;
    @NotNull
    private Integer contactId;
    @NotNull
    private Integer opportunityId;

    public UpdateAccountDTO() {
    }

    public UpdateAccountDTO(@NotNull Integer accountId, @NotNull Integer contactId, @NotNull Integer opportunityId) {
        this.accountId = accountId;
        this.contactId = contactId;
        this.opportunityId = opportunityId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public Integer getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(Integer opportunityId) {
        this.opportunityId = opportunityId;
    }
}
