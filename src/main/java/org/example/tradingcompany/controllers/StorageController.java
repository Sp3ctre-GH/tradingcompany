package org.example.tradingcompany.controllers;

import org.example.tradingcompany.models.Storage;
import org.example.tradingcompany.services.StorageService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/storages")
public class StorageController {

    @Autowired
    private StorageService storageService;

    private boolean isAdmin(UserDetails userDetails) {
        return userDetails != null && userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    @GetMapping
    public String listStorages(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("storages", storageService.getAllStorages());
        model.addAttribute("isAdmin", isAdmin(userDetails));
        return "storages";
    }

    @GetMapping("/add")
    public String addStorageForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (!isAdmin(userDetails)) {
            return "access-denied";
        }
        model.addAttribute("storage", new Storage());
        return "add-storage";
    }

    @PostMapping("/add")
    public String addStorage(@AuthenticationPrincipal UserDetails userDetails,
                             @Valid @ModelAttribute("storage") Storage storage,
                             BindingResult result) {
        if (!isAdmin(userDetails)) {
            return "access-denied";
        }
        if (result.hasErrors()) {
            return "add-storage";
        }
        storageService.saveStorage(storage);
        return "redirect:/storages";
    }

    @GetMapping("/edit/{id}")
    public String editStorageForm(@AuthenticationPrincipal UserDetails userDetails,
                                  @PathVariable("id") Integer id,
                                  Model model) {
        if (!isAdmin(userDetails)) {
            return "access-denied";
        }
        Storage storage = storageService.findStorageById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Storage not found"));
        model.addAttribute("storage", storage);
        return "edit-storage";
    }

    @PostMapping("/update/{id}")
    public String updateStorage(@AuthenticationPrincipal UserDetails userDetails,
                                @PathVariable("id") Integer id,
                                @Valid @ModelAttribute("storage") Storage storage,
                                BindingResult result) {
        if (!isAdmin(userDetails)) {
            return "access-denied";
        }
        if (result.hasErrors()) {
            return "edit-storage";
        }
        storage.setStorageId(id);
        storageService.updateStorage(storage);
        return "redirect:/storages";
    }

    @GetMapping("/delete/{id}")
    public String deleteStorage(@AuthenticationPrincipal UserDetails userDetails,
                                @PathVariable("id") Integer id) {
        if (!isAdmin(userDetails)) {
            return "access-denied";
        }
        storageService.deleteStorage(id);
        return "redirect:/storages";
    }

    @ExceptionHandler(ResponseStatusException.class)
    public String handleNotFound(ResponseStatusException ex, Model model) {
        model.addAttribute("error", ex.getReason());
        return "error";
    }
}
