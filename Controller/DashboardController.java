package com.example.financedashboardpro.controller;

import com.example.financedashboardpro.dto.DashboardData;
import com.example.financedashboardpro.model.User;
import com.example.financedashboardpro.repository.UserRepository;
import com.example.financedashboardpro.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<DashboardData> getDashboardData(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        DashboardData data = dashboardService.getDashboardData(user.getId());
        return ResponseEntity.ok(data);
    }
}
