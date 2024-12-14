package com.webtp.gimme.repository;

import com.webtp.gimme.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {

    List<Purchase> findByCustomerUsername(String username);

}
