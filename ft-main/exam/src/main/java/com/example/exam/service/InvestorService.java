package com.example.exam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.exam.entity.*;
import com.example.exam.repository.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class InvestorService {
    private final InvestorRepository investorRepository;
    private final Random random = new Random();
    
    // Sample data for random generation
    private final List<String> firstNames = Arrays.asList(
            "John", "Emma", "Michael", "Olivia", "William", "Sophia", "James", "Isabella", 
            "Benjamin", "Charlotte", "Lucas", "Amelia", "Henry", "Ava", "Alexander", "Mia",
            "Daniel", "Harper", "Matthew", "Evelyn");
            
    private final List<String> lastNames = Arrays.asList(
            "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis",
            "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson",
            "Thomas", "Taylor", "Moore", "Jackson", "Martin");
    
    private final List<String> stockNames = Arrays.asList(
            "AAPL", "GOOG", "MSFT", "AMZN", "TSLA", "FB", "NFLX", "NVDA", 
            "JPM", "V", "WMT", "PG", "JNJ", "DIS", "BAC", "INTC", "VZ", "CSCO", "PFE", "KO");
            
    // System identifiers for audit purposes
    private final List<String> systemIds = Arrays.asList(
            "WEB_APP", "MOBILE_APP", "API", "BATCH_SYSTEM", "ADMIN_CONSOLE");
            
    @Autowired
    public InvestorService(InvestorRepository investorRepository) {
        this.investorRepository = investorRepository;
    }
    
    public List<Investor> getAllInvestors() {
        return investorRepository.findAll();
    }
    
    public List<Investor> findByName(String name) {
        if (name == null || name.isEmpty()) {
            return getAllInvestors();
        }
        return investorRepository.findByNameContainingIgnoreCase(name);
    }
    
    public void generateDummyData(int count) {
        List<Investor> investors = IntStream.range(0, count)
                .mapToObj(i -> createRandomInvestor())
                .collect(Collectors.toList());
        
        investorRepository.saveAll(investors);
    }
    
    private Investor createRandomInvestor() {
        String firstName = firstNames.get(random.nextInt(firstNames.size()));
        String lastName = lastNames.get(random.nextInt(lastNames.size()));
        String name = firstName + " " + lastName;
        
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@example.com";
        String stockName = stockNames.get(random.nextInt(stockNames.size()));
        
        // Generate random investment amount between 1,000 and 100,000
        BigDecimal investmentAmount = BigDecimal.valueOf(1000 + random.nextInt(99000));
        
        // Generate random date from last 5 years
        LocalDate now = LocalDate.now();
        long minDay = now.minusYears(5).toEpochDay();
        long maxDay = now.toEpochDay();
        long randomDay = minDay + random.nextInt((int) (maxDay - minDay));
        LocalDate investmentDate = LocalDate.ofEpochDay(randomDay);
        
        // Create new investor with the basic fields
        Investor investor = new Investor(name, email, stockName, investmentAmount, investmentDate);
        
        // Override the default system ID in @PrePersist with a random one from our list
        // We need to modify the Investor entity to let us set this
        setSystemIdForInvestor(investor);
        
        return investor;
    }
    
    /**
     * Sets a random system ID for the investor
     * This will override the default value set in the @PrePersist method
     */
    private void setSystemIdForInvestor(Investor investor) {
        String randomSystemId = systemIds.get(random.nextInt(systemIds.size()));
        try {
            // Using reflection to set the systemId since it doesn't have a setter
            // This is a workaround for demo purposes - in production, consider adding a setter
            java.lang.reflect.Field systemIdField = Investor.class.getDeclaredField("systemId");
            systemIdField.setAccessible(true);
            systemIdField.set(investor, randomSystemId);
        } catch (Exception e) {
            // Fallback to default value set in @PrePersist if reflection fails
            System.out.println("Could not set custom systemId, using default: " + e.getMessage());
        }
    }
    
    // Additional method for finding investors by UUID
    public Investor findByUuid(String uuid) {
        return investorRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Investor not found with UUID: " + uuid));
    }
    
    // Method to get investors created after a certain date
    public List<Investor> findByGenerationDateAfter(LocalDateTime date) {
        return investorRepository.findByGenerationDateAfter(date);
    }
    
    // Method to get investors by system ID
    public List<Investor> findBySystemId(String systemId) {
        return investorRepository.findBySystemId(systemId);
    }
    
    // New method to get all system IDs for UI dropdowns
    public List<String> getAllSystemIds() {
        return systemIds;
    }
}
