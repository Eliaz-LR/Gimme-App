package com.webtp.gimme.controller;

import com.webtp.gimme.dto.request.OfferSearchRequestDto;
import com.webtp.gimme.model.Offer;
import com.webtp.gimme.model.Search;
import com.webtp.gimme.security.CustomerDetails;
import com.webtp.gimme.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import com.webtp.gimme.service.ChatService;
import java.util.UUID;
import com.webtp.gimme.service.PurchaseService;
import com.webtp.gimme.service.OfferService;

@Controller
public class ViewController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private OfferService offerService;

    @GetMapping("/")
    public String index() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/profile-update")
    public String profileUpdate(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomerDetails customerDetails = (CustomerDetails) authentication.getPrincipal();
        model.addAttribute("customer", customerService.getCustomer(customerDetails.getUsername()));
        return "profile-update";
    }

    @GetMapping("/profile-delete")
    public String profileDelete() {
        return "profile-delete";
    }

    @GetMapping("/profile-reception")
    public String profileReception() {
        return "profile-reception";
    }

    @GetMapping("/profile-favoris")
    public String profileFavoris(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomerDetails customerDetails = (CustomerDetails) authentication.getPrincipal();
        model.addAttribute("customer", customerService.getCustomer(customerDetails.getUsername()));
        model.addAttribute("offers", (customerService.getFavoriteOffers(customerDetails.getUsername())));
        return "profile-favoris";
    }

    @GetMapping("/profile-historique")
    public String profileHistorique(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomerDetails customerDetails = (CustomerDetails) authentication.getPrincipal();
        model.addAttribute("purchases", (purchaseService.getPurchasesByCustomer(customerDetails.getUsername())));
        return "profile-historique";
    }

    @GetMapping(value = "/offers", produces = "text/html")
    public String getAllOffersHtml(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomerDetails customerDetails = (CustomerDetails) authentication.getPrincipal();
        model.addAttribute("customer", customerService.getCustomer(customerDetails.getUsername()));
        model.addAttribute("offers", offerService.getOffers());
        return "offers";
    }

    @GetMapping(value = "/offers", params = "search", produces = "text/html")
    public String searchOffersHtml(@ModelAttribute OfferSearchRequestDto offerSearchRequestDto, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomerDetails customerDetails = (CustomerDetails) authentication.getPrincipal();
        model.addAttribute("customer", customerService.getCustomer(customerDetails.getUsername()));
        model.addAttribute("offers", offerService.searchOffers(new Search(offerSearchRequestDto)));
        model.addAttribute("searchDto", offerSearchRequestDto);
        model.addAttribute("categories", Offer.Category.values());
        return "offers-search";
    }

    @GetMapping("/create-offer")
    public String createOffer() {
        return "offers-create";
    }

    @GetMapping("/profile-chat")
    public String profileChat(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomerDetails customerDetails = (CustomerDetails) authentication.getPrincipal();
        model.addAttribute("customer", customerService.getCustomer(customerDetails.getUsername()));
        model.addAttribute("chats", (chatService.getChats(customerDetails.getCustomer().getUsername())));
        return "profile-chat";
    }

    @GetMapping("/chat/{id}")
    public String chatting(Model model, @PathVariable UUID id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomerDetails customerDetails = (CustomerDetails) authentication.getPrincipal();
        model.addAttribute("chat", (chatService.getChat(customerDetails.getCustomer().getUsername(), id)));
        model.addAttribute("username", customerDetails.getCustomer().getUsername());
        return "chatting";
    }
}
