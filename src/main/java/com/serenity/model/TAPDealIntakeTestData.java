package com.serenity.model;

import java.util.Date;
import java.util.List;

/**
 * POJO representing TAP Deal Intake test data fields for mapping JSON test data.
 * This model is designed for use with JsonReader to facilitate data-driven testing.
 */
public class TAPDealIntakeTestData {
    // Nested class for valid user credentials
    public static class ValidUser {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    private ValidUser validUser;
    private String accountName;
    private String producerName;
    private String opportunityName;
    private String dealType;
    private String dealStructure;
    private Date signDate;
    private Date targetCloseDate;
    private Date deliverableDueDate;
    private String buyer;
    private String target;
    private Integer numberOfEmployees;
    private Double revenue;
    private String diligenceFee;
    private String loiSigned;
    private String stage;
    private List<String> pAndCStrategy; // For dual listbox (P&C Strategy)
    private List<String> peopleSolutionsStrategy; // For dual listbox (People Solutions Strategy)
    private String repsAndWarranty;
    private String relatedAccountId; // For lookup fields if needed
    private String relatedProducerId;

    // Getters and Setters
    public ValidUser getValidUser() {
        return validUser;
    }

    public void setValidUser(ValidUser validUser) {
        this.validUser = validUser;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public String getOpportunityName() {
        return opportunityName;
    }

    public void setOpportunityName(String opportunityName) {
        this.opportunityName = opportunityName;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public String getDealStructure() {
        return dealStructure;
    }

    public void setDealStructure(String dealStructure) {
        this.dealStructure = dealStructure;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public Date getTargetCloseDate() {
        return targetCloseDate;
    }

    public void setTargetCloseDate(Date targetCloseDate) {
        this.targetCloseDate = targetCloseDate;
    }

    public Date getDeliverableDueDate() {
        return deliverableDueDate;
    }

    public void setDeliverableDueDate(Date deliverableDueDate) {
        this.deliverableDueDate = deliverableDueDate;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Integer getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(Integer numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    public String getDiligenceFee() {
        return diligenceFee;
    }

    public void setDiligenceFee(String diligenceFee) {
        this.diligenceFee = diligenceFee;
    }

    public String getLoiSigned() {
        return loiSigned;
    }

    public void setLoiSigned(String loiSigned) {
        this.loiSigned = loiSigned;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public List<String> getPAndCStrategy() {
        return pAndCStrategy;
    }

    public void setPAndCStrategy(List<String> pAndCStrategy) {
        this.pAndCStrategy = pAndCStrategy;
    }

    public List<String> getPeopleSolutionsStrategy() {
        return peopleSolutionsStrategy;
    }

    public void setPeopleSolutionsStrategy(List<String> peopleSolutionsStrategy) {
        this.peopleSolutionsStrategy = peopleSolutionsStrategy;
    }

    public String getRepsAndWarranty() {
        return repsAndWarranty;
    }

    public void setRepsAndWarranty(String repsAndWarranty) {
        this.repsAndWarranty = repsAndWarranty;
    }

    public String getRelatedAccountId() {
        return relatedAccountId;
    }

    public void setRelatedAccountId(String relatedAccountId) {
        this.relatedAccountId = relatedAccountId;
    }

    public String getRelatedProducerId() {
        return relatedProducerId;
    }

    public void setRelatedProducerId(String relatedProducerId) {
        this.relatedProducerId = relatedProducerId;
    }
}
