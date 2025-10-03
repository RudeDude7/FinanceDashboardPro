package com.example.financedashboardpro.controller;

import com.example.financedashboardpro.dto.LinkTokenResponse;
import com.example.financedashboardpro.dto.PublicTokenRequest;
import com.example.financedashboardpro.model.PlaidItem;
import com.example.financedashboardpro.model.User;
import com.example.financedashboardpro.repository.PlaidItemRepository;
import com.example.financedashboardpro.repository.UserRepository;
import com.example.financedashboardpro.service.PlaidClientService;
import com.example.financedashboardpro.service.TransactionProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/plaid")
public class PlaidController {

    @Autowired
    private PlaidClientService plaidClientService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlaidItemRepository plaidItemRepository;
    @Autowired
    private TransactionProcessingService transactionProcessingService;

    @GetMapping("/create-link-token")
    public ResponseEntity<LinkTokenResponse> createLinkToken(Authentication authentication) throws IOException {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        String linkToken = plaidClientService.createLinkToken(user.getId().toString());
        return ResponseEntity.ok(new LinkTokenResponse(linkToken));
    }

    @PostMapping("/exchange-public-token")
    public ResponseEntity<?> exchangePublicToken(Authentication authentication, @Valid @RequestBody PublicTokenRequest request) throws IOException {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Map<String, String> tokenMap = plaidClientService.exchangePublicToken(request.getPublicToken());
        PlaidItem plaidItem = new PlaidItem();
        plaidItem.setUserId(user.getId());
        plaidItem.setAccessToken(tokenMap.get("access_token"));
        plaidItem.setItemId(tokenMap.get("item_id"));
        plaidItemRepository.save(plaidItem);

        // Trigger an initial sync right after linking
        transactionProcessingService.processTransactionsForItem(plaidItem);
        
        return ResponseEntity.ok("Bank account linked successfully!");
    }

    @PostMapping("/sync-transactions")
    public ResponseEntity<?> syncTransactions(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        transactionProcessingService.syncTransactionsForUser(user.getId());
        return ResponseEntity.ok("Transaction sync initiated!");
    }
}
