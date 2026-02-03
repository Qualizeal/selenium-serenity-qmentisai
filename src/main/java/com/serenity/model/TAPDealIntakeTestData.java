package com.serenity.model;

import java.util.Date;
import java.util.List;

/**
 * POJO representing TAP Deal Intake test data fields for mapping JSON test data.
 * This model is designed for use with JsonReader to facilitate data-driven testing.
 *
 * Updated to reflect all fields used by testData_TAPDealIntake.json and page objects.
 */
public class TAPDealIntakeTestData {
    // Nested class for valid user credentials
    public static class ValidUser {
        private String username;
        private String password;
        private String role;
        private String description;

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

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class Account {
        private String accountName;
        private String accountId;
        private String recordType;
        private String description;

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public String getRecordType() {
            return recordType;
        }

        public void setRecordType(String recordType) {
            this.recordType = recordType;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class Producer {
        private String name;
        private String producerId;
        private String description;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProducerId() {
            return producerId;
        }

        public void setProducerId(String producerId) {
            this.producerId = producerId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class OpportunityDefaults {
        private String dealType;
        private String dealStructure;
        private String opportunityName;
        private String stage;
        private String reportType;
        private String signDate;
        private String targetCloseDate;
        private String deliverableDueDate;
        private String diligenceFeeAgreement;
        private String loiSigned;
        private String repsAndWarranty;
        private String locationOfTarget;
        private String enterpriseValue;
        private String buyer;
        private String target;
        private String producerName;
        private String primaryProducer;
        private Integer numberOfEmployees;
        private Double revenue;
        private List<String> pAndCStrategy;
        private List<String> peopleSolutionsStrategy;
        private Boolean internationalOperations;
        private Integer numberOfInternationalEmployees;
        private List<String> internationalCountryDetail;
        // Add more fields as needed for mapping

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

        public String getOpportunityName() {
            return opportunityName;
        }

        public void setOpportunityName(String opportunityName) {
            this.opportunityName = opportunityName;
        }

        public String getStage() {
            return stage;
        }

        public void setStage(String stage) {
            this.stage = stage;
        }

        public String getReportType() {
            return reportType;
        }

        public void setReportType(String reportType) {
            this.reportType = reportType;
        }

        public String getSignDate() {
            return signDate;
        }

        public void setSignDate(String signDate) {
            this.signDate = signDate;
        }

        public String getTargetCloseDate() {
            return targetCloseDate;
        }

        public void setTargetCloseDate(String targetCloseDate) {
            this.targetCloseDate = targetCloseDate;
        }

        public String getDeliverableDueDate() {
            return deliverableDueDate;
        }

        public void setDeliverableDueDate(String deliverableDueDate) {
            this.deliverableDueDate = deliverableDueDate;
        }

        public String getDiligenceFeeAgreement() {
            return diligenceFeeAgreement;
        }

        public void setDiligenceFeeAgreement(String diligenceFeeAgreement) {
            this.diligenceFeeAgreement = diligenceFeeAgreement;
        }

        public String getLoiSigned() {
            return loiSigned;
        }

        public void setLoiSigned(String loiSigned) {
            this.loiSigned = loiSigned;
        }

        public String getRepsAndWarranty() {
            return repsAndWarranty;
        }

        public void setRepsAndWarranty(String repsAndWarranty) {
            this.repsAndWarranty = repsAndWarranty;
        }

        public String getLocationOfTarget() {
            return locationOfTarget;
        }

        public void setLocationOfTarget(String locationOfTarget) {
            this.locationOfTarget = locationOfTarget;
        }

        public String getEnterpriseValue() {
            return enterpriseValue;
        }

        public void setEnterpriseValue(String enterpriseValue) {
            this.enterpriseValue = enterpriseValue;
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

        public String getProducerName() {
            return producerName;
        }

        public void setProducerName(String producerName) {
            this.producerName = producerName;
        }

        public String getPrimaryProducer() {
            return primaryProducer;
        }

        public void setPrimaryProducer(String primaryProducer) {
            this.primaryProducer = primaryProducer;
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

        public Boolean getInternationalOperations() {
            return internationalOperations;
        }

        public void setInternationalOperations(Boolean internationalOperations) {
            this.internationalOperations = internationalOperations;
        }

        public Integer getNumberOfInternationalEmployees() {
            return numberOfInternationalEmployees;
        }

        public void setNumberOfInternationalEmployees(Integer numberOfInternationalEmployees) {
            this.numberOfInternationalEmployees = numberOfInternationalEmployees;
        }

        public List<String> getInternationalCountryDetail() {
            return internationalCountryDetail;
        }

        public void setInternationalCountryDetail(List<String> internationalCountryDetail) {
            this.internationalCountryDetail = internationalCountryDetail;
        }
    }

    // Top-level fields for mapping JSON
    private List<ValidUser> validTAPUsers;
    private List<Account> accounts;
    private Producer producer;
    private Producer buyer;
    private Producer target;
    private OpportunityDefaults opportunityDefaults;

    // Legacy/flat fields for backward compatibility
    private ValidUser validUser;
    private String accountName;
    private String producerName;
    private String opportunityName;
    private String dealType;
    private String dealStructure;
    private Date signDate;
    private Date targetCloseDate;
    private Date deliverableDueDate;
    private String buyerName;
    private String targetName;
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
    public List<ValidUser> getValidTAPUsers() {
        return validTAPUsers;
    }

    public void setValidTAPUsers(List<ValidUser> validTAPUsers) {
        this.validTAPUsers = validTAPUsers;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public Producer getBuyer() {
        return buyer;
    }

    public void setBuyer(Producer buyer) {
        this.buyer = buyer;
    }

    public Producer getTarget() {
        return target;
    }

    public void setTarget(Producer target) {
        this.target = target;
    }

    public OpportunityDefaults getOpportunityDefaults() {
        return opportunityDefaults;
    }

    public void setOpportunityDefaults(OpportunityDefaults opportunityDefaults) {
        this.opportunityDefaults = opportunityDefaults;
    }

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

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
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
