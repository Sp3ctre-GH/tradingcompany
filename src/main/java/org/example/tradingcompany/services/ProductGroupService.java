package org.example.tradingcompany.services;

import org.example.tradingcompany.models.ProductGroup;
import org.example.tradingcompany.repositories.ProductGroupRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Optional;

@Service
public class ProductGroupService {

    @Autowired
    private ProductGroupRepository productGroupRepository;

    public List<ProductGroup> getAllProductGroups() {
        return productGroupRepository.findAll(Sort.by(Sort.Order.asc("groupId")));
    }

    public Optional<ProductGroup> findProductGroupById(int id) {
        return productGroupRepository.findById(id);
    }

    public void saveProductGroup(ProductGroup productGroup) {
        productGroupRepository.save(productGroup);
    }

    public void updateProductGroup(ProductGroup updatedProductGroup) {
        ProductGroup existingProductGroup = productGroupRepository.findById(updatedProductGroup.getGroupId())
                .orElseThrow(() -> new IllegalArgumentException("Product Group not found"));

        existingProductGroup.setName(updatedProductGroup.getName());
        existingProductGroup.setDescription(updatedProductGroup.getDescription());
        existingProductGroup.setDiscountPercentage(updatedProductGroup.getDiscountPercentage());

        productGroupRepository.save(existingProductGroup);
    }

    public void deleteProductGroup(int id) {
        productGroupRepository.deleteById(id);
    }
}
