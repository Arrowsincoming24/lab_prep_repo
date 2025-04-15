package com.example.exam.repository;

import com.example.exam.entity.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvestorRepository extends JpaRepository<Investor, Long> {
    
    List<Investor> findByNameContainingIgnoreCase(String name);
    
    // New repository methods for audit columns
    Optional<Investor> findByUuid(String uuid);
    
    List<Investor> findByGenerationDateAfter(LocalDateTime date);
    
    List<Investor> findBySystemId(String systemId);
}