package com.serenity.model;

import java.time.LocalDate;
import java.util.List;

/**
 * POJO representing the test data fields required for TAP Deal Intake Opportunity creation.
 * This model is used to drive data-driven tests for TAP Deal Intake scenarios.
 */
public class TapDealIntakeData {
    // Salesforce login credentials
    private String username;
    private String password;

    // Account and Opportunity fields
    private String accountName;
    private String accountRecordType;
    private String dealType;
    private String primaryProducer;
    private String opportunityName;
    private String dealStructure;
    private List<String> pAndCStrategy; // Chosen P&C Strategies
    private List<String> peopleSolutionsStrategy; // Chosen People Solutions Strategies
    private LocalDate signDate;
    private String buyerAccount;
    private String targetAccount;
    private LocalDate targetCloseDate;
    private String repsAndWarranty;
    private Integer numberOfEmployees;
    private Double revenue;
    private String diligenceFeeAgreement;
    private String loiSigned;
    private String stage;
    private LocalDate deliverableDueDate;

    // Additional fields for extended scenarios
    private String reportType;
    private String locationOfTarget;
    private Double enterpriseValue;
    private Boolean internationalOperations;
    private Integer numberOfInternationalEmployees;
    private List<String> internationalCountryDetail;
    private String dealStatus;
    private String dataRoomLink;
    private LocalDate drlNextDueDate;
    private LocalDate pAndCDateLastCheckedDataRoom;
    private LocalDate drlLastSent;
    private LocalDate peopleSolutionsDateLastCheckedData;
    private LocalDate dateReportIssued;
    private String secondaryStage;
    private Double repWarrantyRevenue;
    private Double pAndCRecurringRevenue;
    private Double peopleSolutionsRecurringRevenue;
    private Double pAndCTailRevenue;
    private String newBusinessOpportunity;
    private Boolean internationalCharges;
    private LocalDate usInvoiceCreatedDate;
    private LocalDate internationalInvoiceCreatedDate;
    private Double usPCInvoiceAmount;
    private String internationalBenefitsInvoiceStatus;
    private Double usPeopleSolutionsInvoiceAmount;
    private Double internationalBenefitsInvoiceAmount;
    private Double internationalPCInvoiceAmount;

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getAccountName() { return accountName; }
    public void setAccountName(String accountName) { this.accountName = accountName; }

    public String getAccountRecordType() { return accountRecordType; }
    public void setAccountRecordType(String accountRecordType) { this.accountRecordType = accountRecordType; }

    public String getDealType() { return dealType; }
    public void setDealType(String dealType) { this.dealType = dealType; }

    public String getPrimaryProducer() { return primaryProducer; }
    public void setPrimaryProducer(String primaryProducer) { this.primaryProducer = primaryProducer; }

    public String getOpportunityName() { return opportunityName; }
    public void setOpportunityName(String opportunityName) { this.opportunityName = opportunityName; }

    public String getDealStructure() { return dealStructure; }
    public void setDealStructure(String dealStructure) { this.dealStructure = dealStructure; }

    public List<String> getPAndCStrategy() { return pAndCStrategy; }
    public void setPAndCStrategy(List<String> pAndCStrategy) { this.pAndCStrategy = pAndCStrategy; }

    public List<String> getPeopleSolutionsStrategy() { return peopleSolutionsStrategy; }
    public void setPeopleSolutionsStrategy(List<String> peopleSolutionsStrategy) { this.peopleSolutionsStrategy = peopleSolutionsStrategy; }

    public LocalDate getSignDate() { return signDate; }
    public void setSignDate(LocalDate signDate) { this.signDate = signDate; }

    public String getBuyerAccount() { return buyerAccount; }
    public void setBuyerAccount(String buyerAccount) { this.buyerAccount = buyerAccount; }

    public String getTargetAccount() { return targetAccount; }
    public void setTargetAccount(String targetAccount) { this.targetAccount = targetAccount; }

    public LocalDate getTargetCloseDate() { return targetCloseDate; }
    public void setTargetCloseDate(LocalDate targetCloseDate) { this.targetCloseDate = targetCloseDate; }

    public String getRepsAndWarranty() { return repsAndWarranty; }
    public void setRepsAndWarranty(String repsAndWarranty) { this.repsAndWarranty = repsAndWarranty; }

    public Integer getNumberOfEmployees() { return numberOfEmployees; }
    public void setNumberOfEmployees(Integer numberOfEmployees) { this.numberOfEmployees = numberOfEmployees; }

    public Double getRevenue() { return revenue; }
    public void setRevenue(Double revenue) { this.revenue = revenue; }

    public String getDiligenceFeeAgreement() { return diligenceFeeAgreement; }
    public void setDiligenceFeeAgreement(String diligenceFeeAgreement) { this.diligenceFeeAgreement = diligenceFeeAgreement; }

    public String getLoiSigned() { return loiSigned; }
    public void setLoiSigned(String loiSigned) { this.loiSigned = loiSigned; }

    public String getStage() { return stage; }
    public void setStage(String stage) { this.stage = stage; }

    public LocalDate getDeliverableDueDate() { return deliverableDueDate; }
    public void setDeliverableDueDate(LocalDate deliverableDueDate) { this.deliverableDueDate = deliverableDueDate; }

    public String getReportType() { return reportType; }
    public void setReportType(String reportType) { this.reportType = reportType; }

    public String getLocationOfTarget() { return locationOfTarget; }
    public void setLocationOfTarget(String locationOfTarget) { this.locationOfTarget = locationOfTarget; }

    public Double getEnterpriseValue() { return enterpriseValue; }
    public void setEnterpriseValue(Double enterpriseValue) { this.enterpriseValue = enterpriseValue; }

    public Boolean getInternationalOperations() { return internationalOperations; }
    public void setInternationalOperations(Boolean internationalOperations) { this.internationalOperations = internationalOperations; }

    public Integer getNumberOfInternationalEmployees() { return numberOfInternationalEmployees; }
    public void setNumberOfInternationalEmployees(Integer numberOfInternationalEmployees) { this.numberOfInternationalEmployees = numberOfInternationalEmployees; }

    public List<String> getInternationalCountryDetail() { return internationalCountryDetail; }
    public void setInternationalCountryDetail(List<String> internationalCountryDetail) { this.internationalCountryDetail = internationalCountryDetail; }

    public String getDealStatus() { return dealStatus; }
    public void setDealStatus(String dealStatus) { this.dealStatus = dealStatus; }

    public String getDataRoomLink() { return dataRoomLink; }
    public void setDataRoomLink(String dataRoomLink) { this.dataRoomLink = dataRoomLink; }

    public LocalDate getDrlNextDueDate() { return drlNextDueDate; }
    public void setDrlNextDueDate(LocalDate drlNextDueDate) { this.drlNextDueDate = drlNextDueDate; }

    public LocalDate getPAndCDateLastCheckedDataRoom() { return pAndCDateLastCheckedDataRoom; }
    public void setPAndCDateLastCheckedDataRoom(LocalDate pAndCDateLastCheckedDataRoom) { this.pAndCDateLastCheckedDataRoom = pAndCDateLastCheckedDataRoom; }

    public LocalDate getDrlLastSent() { return drlLastSent; }
    public void setDrlLastSent(LocalDate drlLastSent) { this.drlLastSent = drlLastSent; }

    public LocalDate getPeopleSolutionsDateLastCheckedData() { return peopleSolutionsDateLastCheckedData; }
    public void setPeopleSolutionsDateLastCheckedData(LocalDate peopleSolutionsDateLastCheckedData) { this.peopleSolutionsDateLastCheckedData = peopleSolutionsDateLastCheckedData; }

    public LocalDate getDateReportIssued() { return dateReportIssued; }
    public void setDateReportIssued(LocalDate dateReportIssued) { this.dateReportIssued = dateReportIssued; }

    public String getSecondaryStage() { return secondaryStage; }
    public void setSecondaryStage(String secondaryStage) { this.secondaryStage = secondaryStage; }

    public Double getRepWarrantyRevenue() { return repWarrantyRevenue; }
    public void setRepWarrantyRevenue(Double repWarrantyRevenue) { this.repWarrantyRevenue = repWarrantyRevenue; }

    public Double getPAndCRecurringRevenue() { return pAndCRecurringRevenue; }
    public void setPAndCRecurringRevenue(Double pAndCRecurringRevenue) { this.pAndCRecurringRevenue = pAndCRecurringRevenue; }

    public Double getPeopleSolutionsRecurringRevenue() { return peopleSolutionsRecurringRevenue; }
    public void setPeopleSolutionsRecurringRevenue(Double peopleSolutionsRecurringRevenue) { this.peopleSolutionsRecurringRevenue = peopleSolutionsRecurringRevenue; }

    public Double getPAndCTailRevenue() { return pAndCTailRevenue; }
    public void setPAndCTailRevenue(Double pAndCTailRevenue) { this.pAndCTailRevenue = pAndCTailRevenue; }

    public String getNewBusinessOpportunity() { return newBusinessOpportunity; }
    public void setNewBusinessOpportunity(String newBusinessOpportunity) { this.newBusinessOpportunity = newBusinessOpportunity; }

    public Boolean getInternationalCharges() { return internationalCharges; }
    public void setInternationalCharges(Boolean internationalCharges) { this.internationalCharges = internationalCharges; }

    public LocalDate getUsInvoiceCreatedDate() { return usInvoiceCreatedDate; }
    public void setUsInvoiceCreatedDate(LocalDate usInvoiceCreatedDate) { this.usInvoiceCreatedDate = usInvoiceCreatedDate; }

    public LocalDate getInternationalInvoiceCreatedDate() { return internationalInvoiceCreatedDate; }
    public void setInternationalInvoiceCreatedDate(LocalDate internationalInvoiceCreatedDate) { this.internationalInvoiceCreatedDate = internationalInvoiceCreatedDate; }

    public Double getUsPCInvoiceAmount() { return usPCInvoiceAmount; }
    public void setUsPCInvoiceAmount(Double usPCInvoiceAmount) { this.usPCInvoiceAmount = usPCInvoiceAmount; }

    public String getInternationalBenefitsInvoiceStatus() { return internationalBenefitsInvoiceStatus; }
    public void setInternationalBenefitsInvoiceStatus(String internationalBenefitsInvoiceStatus) { this.internationalBenefitsInvoiceStatus = internationalBenefitsInvoiceStatus; }

    public Double getUsPeopleSolutionsInvoiceAmount() { return usPeopleSolutionsInvoiceAmount; }
    public void setUsPeopleSolutionsInvoiceAmount(Double usPeopleSolutionsInvoiceAmount) { this.usPeopleSolutionsInvoiceAmount = usPeopleSolutionsInvoiceAmount; }

    public Double getInternationalBenefitsInvoiceAmount() { return internationalBenefitsInvoiceAmount; }
    public void setInternationalBenefitsInvoiceAmount(Double internationalBenefitsInvoiceAmount) { this.internationalBenefitsInvoiceAmount = internationalBenefitsInvoiceAmount; }

    public Double getInternationalPCInvoiceAmount() { return internationalPCInvoiceAmount; }
    public void setInternationalPCInvoiceAmount(Double internationalPCInvoiceAmount) { this.internationalPCInvoiceAmount = internationalPCInvoiceAmount; }
}
