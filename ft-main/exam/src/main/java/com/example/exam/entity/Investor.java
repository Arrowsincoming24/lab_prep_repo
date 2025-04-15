package com.example.exam.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "investors")
public class Investor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String email;
    private String stockName;
    private BigDecimal investmentAmount;
    private LocalDate investmentDate;
    
    // Audit columns
    @Column(name = "generation_date", nullable = false, updatable = false)
    private LocalDateTime generationDate;
    
    @Column(name = "system_id", nullable = false)
    private String systemId;
    
    @Column(name = "uuid", nullable = false, updatable = false, unique = true)
    private String uuid;
    
    // Default constructor required by JPA
    public Investor() {
    }
    
    // Constructor with all fields except id and audit fields
    public Investor(String name, String email, String stockName, BigDecimal investmentAmount, LocalDate investmentDate) {
        this.name = name;
        this.email = email;
        this.stockName = stockName;
        this.investmentAmount = investmentAmount;
        this.investmentDate = investmentDate;
    }
    
    // Auto-generate audit values before persisting
    @PrePersist
    public void prePersist() {
        this.generationDate = LocalDateTime.now();
        
        // Only set default system ID if it hasn't been manually set
        if (this.systemId == null) {
            this.systemId = "SYSTEM"; // Default system identifier
        }
        
        this.uuid = UUID.randomUUID().toString();
    }
    
    // Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getStockName() {
        return stockName;
    }
    public void setStockName(String stockName) {
        this.stockName = stockName;
    }
    public BigDecimal getInvestmentAmount() {
        return investmentAmount;
    }
    public void setInvestmentAmount(BigDecimal investmentAmount) {
        this.investmentAmount = investmentAmount;
    }
    public LocalDate getInvestmentDate() {
        return investmentDate;
    }
    public void setInvestmentDate(LocalDate investmentDate) {
        this.investmentDate = investmentDate;
    }
    
    // Getters and setters for audit fields
    public LocalDateTime getGenerationDate() {
        return generationDate;
    }
    
    public String getSystemId() {
        return systemId;
    }
    
    // Added setter for systemId to allow manual setting
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
    
    public String getUuid() {
        return uuid;
    }
    
    @Override
    public String toString() {
        return "Investor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", stockName='" + stockName + '\'' +
                ", investmentAmount=" + investmentAmount +
                ", investmentDate=" + investmentDate +
                ", generationDate=" + generationDate +
                ", systemId='" + systemId + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Investor investor = (Investor) o;
        return Objects.equals(id, investor.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}