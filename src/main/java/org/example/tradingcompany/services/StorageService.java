package org.example.tradingcompany.services;

import org.example.tradingcompany.models.Storage;
import org.example.tradingcompany.repositories.StorageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Optional;

@Service
public class StorageService {

    @Autowired
    private StorageRepository storageRepository;

    public List<Storage> getAllStorages() {
        return storageRepository.findAll(Sort.by(Sort.Order.asc("storageId")));
    }

    public Optional<Storage> findStorageById(int id) {
        return storageRepository.findById(id);
    }

    public void saveStorage(Storage storage) {
        storageRepository.save(storage);
    }


    public void updateStorage(Storage updatedStorage) {
        Storage existingStorage = storageRepository.findById(updatedStorage.getStorageId())
                .orElseThrow(() -> new IllegalArgumentException("Storage not found"));

        existingStorage.setTitle(updatedStorage.getTitle());
        existingStorage.setLocation(updatedStorage.getLocation());
        existingStorage.setManager(updatedStorage.getManager());


        storageRepository.save(existingStorage);
    }

    public void deleteStorage(int id) {
        storageRepository.deleteById(id);
    }
}
