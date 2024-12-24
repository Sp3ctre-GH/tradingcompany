package org.example.tradingcompany.services;

import org.example.tradingcompany.models.Product;
import org.example.tradingcompany.models.ProductGroup;
import org.example.tradingcompany.models.Storage;
import org.example.tradingcompany.repositories.ProductGroupRepository;
import org.example.tradingcompany.repositories.ProductRepository;
import org.example.tradingcompany.repositories.StorageRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final StorageRepository storageRepository;
    private final ProductRepository productRepository;
    private final ProductGroupRepository productGroupRepository;

    public DataInitializer(StorageRepository storageRepository,
                           ProductRepository productRepository,
                           ProductGroupRepository productGroupRepository) {
        this.storageRepository = storageRepository;
        this.productRepository = productRepository;
        this.productGroupRepository = productGroupRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (storageRepository.count() == 0) {
            Storage storage1 = new Storage(null, "Центральний склад", "Київ", "Олександр Петров");
            Storage storage2 = new Storage(null, "Західний склад", "Львів", "Марія Іванова");
            Storage storage3 = new Storage(null, "Південний склад", "Одеса", "Іван Сидоров");

            storageRepository.save(storage1);
            storageRepository.save(storage2);
            storageRepository.save(storage3);
        }

        if (productGroupRepository.count() == 0) {
            ProductGroup group1 = new ProductGroup(null, "Електроніка", "Група товарів, що включає всі електронні пристрої, включаючи мобільні телефони, комп’ютери та іншу техніку.", 10);
            ProductGroup group2 = new ProductGroup(null, "Одяг", "Колекція одягу, включаючи чоловічий, жіночий та дитячий одяг.", 15);
            ProductGroup group3 = new ProductGroup(null, "Продукти харчування", "Група товарів, що включає всі види продуктів харчування, від свіжих до заморожених.", 5);
            ProductGroup group4 = new ProductGroup(null, "Косметика", "Асортимент косметичних товарів, включаючи догляд за шкірою та волоссям, а також декоративну косметику.", 20);

            productGroupRepository.save(group1);
            productGroupRepository.save(group2);
            productGroupRepository.save(group3);
            productGroupRepository.save(group4);
        }

        if (productRepository.count() == 0) {
            Product product1 = new Product(null, "Ноутбук", 15000.00, 10);
            Product product2 = new Product(null, "Телефон", 8000.00, 20);
            Product product3 = new Product(null, "Куртка", 2000.00, 50);
            Product product4 = new Product(null, "Яблука", 30.00, 100);
            Product product5 = new Product(null, "Шампунь", 150.00, 30);

            productRepository.save(product1);
            productRepository.save(product2);
            productRepository.save(product3);
            productRepository.save(product4);
            productRepository.save(product5);
        }
    }
}
