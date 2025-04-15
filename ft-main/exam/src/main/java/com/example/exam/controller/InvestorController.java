package com.example.exam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.exam.entity.Investor;
import com.example.exam.service.InvestorService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class InvestorController {
    private final InvestorService investorService;
    
    @Autowired
    public InvestorController(InvestorService investorService) {
        this.investorService = investorService;
    }
    
    @GetMapping("/generate")
    @ResponseBody
    public String generateDummyData() {
        investorService.generateDummyData(50);
        return "50 dummy investors have been generated successfully!";
    }
    
    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "Test endpoint working!";
    }
    
    @GetMapping("/investors")
    public String getAllInvestors(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String systemId,
            Model model) {
        
        List<Investor> investors;
        
        if (name != null && !name.isEmpty()) {
            investors = investorService.findByName(name);
            model.addAttribute("searchName", name);
        } else if (systemId != null && !systemId.isEmpty()) {
            investors = investorService.findBySystemId(systemId);
            model.addAttribute("searchSystemId", systemId);
        } else {
            investors = investorService.getAllInvestors();
        }
        
        // Add system IDs for dropdown selection
        model.addAttribute("availableSystemIds", investorService.getAllSystemIds());
        model.addAttribute("investors", investors);
        return "investors";
    }
    
    @GetMapping("/investors/uuid/{uuid}")
    public String getInvestorByUuid(@PathVariable String uuid, Model model) {
        try {
            Investor investor = investorService.findByUuid(uuid);
            model.addAttribute("investor", investor);
            return "investor-detail";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
    
    @GetMapping("/investors/recent")
    public String getRecentInvestors(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime since,
            Model model) {
        
        List<Investor> investors = investorService.findByGenerationDateAfter(since);
        model.addAttribute("investors", investors);
        model.addAttribute("since", since);
        
        // Add system IDs for dropdown selection
        model.addAttribute("availableSystemIds", investorService.getAllSystemIds());
        return "investors";
    }
}