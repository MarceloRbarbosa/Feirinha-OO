package com.feirinha.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.feirinha.api.dtos.ItemDTO;
import com.feirinha.api.models.ItemModel;
import com.feirinha.api.repositories.ItemRepository;

@Service
public class ItemService {

    final ItemRepository itemRepository;

    ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<ItemModel> getItems() {
        return itemRepository.findAll();
    }

    public Optional<ItemModel> getItemById(@PathVariable("id") Long id) {
        Optional<ItemModel> item = itemRepository.findById(id);

        if (!item.isPresent()) {
            return Optional.empty();
        } else {
            return item;
        }
    }

    public Optional<ItemModel> createItem(ItemDTO body) {

        if (itemRepository.existsByName(body.getName())) {
            return Optional.empty();
        }
        ItemModel item = new ItemModel(body);
        itemRepository.save(item);
        return Optional.of(item);
    }

    public Optional<ItemModel> updateItem(Long id, ItemDTO body) {
        Optional<ItemModel> item = itemRepository.findById(id);

        if (!item.isPresent()) {
            return Optional.empty();
        }

        if (itemRepository.existsByName(body.getName()) && !item.get().getName().equals(body.getName())) {
            throw new RuntimeException("DuplicateItem");
        }

        ItemModel updatedItem = item.get();
        updatedItem.setName(body.getName());
        updatedItem.setQuantity(body.getQuantity());

        itemRepository.save(updatedItem);
        return Optional.of(updatedItem);
    }

    public Boolean deleteItem(Long id) {
        Optional<ItemModel> item = itemRepository.findById(id);

        if (!item.isPresent()) {
            return false;
        }

        itemRepository.deleteById(id);
        return true;
    }
}
