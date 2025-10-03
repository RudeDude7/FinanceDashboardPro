package com.example.financedashboardpro.repository;

import com.example.financedashboardpro.model.PlaidItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlaidItemRepository extends JpaRepository<PlaidItem, Long> {
    List<PlaidItem> findByUserId(Long userId);
}
