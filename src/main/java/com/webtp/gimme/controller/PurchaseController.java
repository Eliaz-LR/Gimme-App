package com.webtp.gimme.controller;

import com.webtp.gimme.model.Purchase;
import com.webtp.gimme.service.PurchaseService;
import com.webtp.gimme.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers/{username}/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping
    public ResponseEntity<?> getPurchases(@PathVariable("username") String username) {
        SecurityUtils.checkAuthorization(username);
        List<Purchase> purchases = purchaseService.getPurchasesByCustomer(username);
        if (purchases.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(purchases);
    }

    @PostMapping("/{offerId}")
    public ResponseEntity<Purchase> purchaseOffer(@PathVariable("username") String username,
                                                  @PathVariable("offerId") UUID offerId) {
        SecurityUtils.checkAuthorization(username);
        Purchase purchase = purchaseService.purchaseOffer(username, offerId);
        return ResponseEntity.ok(purchase);
    }
}
