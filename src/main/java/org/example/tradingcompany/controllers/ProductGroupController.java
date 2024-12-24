package org.example.tradingcompany.controllers;

import org.example.tradingcompany.models.ProductGroup;
import org.example.tradingcompany.services.ProductGroupService;

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
@RequestMapping("/product-groups")
public class ProductGroupController {

    @Autowired
    private ProductGroupService productGroupService;

    private boolean isAdmin(UserDetails userDetails) {
        return userDetails != null && userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    @GetMapping
    public String listProductGroups(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("productGroups", productGroupService.getAllProductGroups());
        model.addAttribute("isAdmin", isAdmin(userDetails));
        return "product-groups";
    }

    @GetMapping("/add")
    public String addProductGroupForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (!isAdmin(userDetails)) {
            return "access-denied";
        }
        model.addAttribute("productGroup", new ProductGroup());
        return "add-product-group";
    }

    @PostMapping("/add")
    public String addProductGroup(@AuthenticationPrincipal UserDetails userDetails,
                                  @Valid @ModelAttribute("productGroup") ProductGroup productGroup,
                                  BindingResult result) {
        if (!isAdmin(userDetails)) {
            return "access-denied";
        }
        if (result.hasErrors()) {
            return "add-product-group";
        }
        productGroupService.saveProductGroup(productGroup);
        return "redirect:/product-groups";
    }

    @GetMapping("/edit/{id}")
    public String editProductGroupForm(@AuthenticationPrincipal UserDetails userDetails,
                                       @PathVariable("id") Integer id,
                                       Model model) {
        if (!isAdmin(userDetails)) {
            return "access-denied";
        }
        ProductGroup productGroup = productGroupService.findProductGroupById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Group not found"));
        model.addAttribute("productGroup", productGroup);
        return "edit-product-group";
    }

    @PostMapping("/update/{id}")
    public String updateProductGroup(@AuthenticationPrincipal UserDetails userDetails,
                                     @PathVariable("id") Integer id,
                                     @Valid @ModelAttribute("productGroup") ProductGroup productGroup,
                                     BindingResult result) {
        if (!isAdmin(userDetails)) {
            return "access-denied";
        }
        if (result.hasErrors()) {
            return "edit-product-group";
        }
        productGroup.setGroupId(id);
        productGroupService.updateProductGroup(productGroup);
        return "redirect:/product-groups";
    }

    @GetMapping("/delete/{id}")
    public String deleteProductGroup(@AuthenticationPrincipal UserDetails userDetails,
                                     @PathVariable("id") Integer id) {
        if (!isAdmin(userDetails)) {
            return "access-denied";
        }
        productGroupService.deleteProductGroup(id);
        return "redirect:/product-groups";
    }

    @ExceptionHandler(ResponseStatusException.class)
    public String handleNotFound(ResponseStatusException ex, Model model) {
        model.addAttribute("error", ex.getReason());
        return "error";
    }
}
