package com.feirinha.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

import com.feirinha.api.dtos.ItemDTO;
import com.feirinha.api.models.ItemModel;
import com.feirinha.api.repositories.ItemRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/items")

public class ItemsController {

    final ItemRepository itemRepository;

    ItemsController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping()
    public List<ItemModel> getItems() {
        return itemRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<ItemModel> getItemById(@PathVariable("id") Long id) {
        Optional<ItemModel> item = itemRepository.findById(id);

        if (!item.isPresent()) {
            return Optional.empty();
        } else {
            return Optional.of(item.get());
        }
    }

    @PostMapping()
    public void createItem(@RequestBody @Valid ItemDTO body) {
        ItemModel item = new ItemModel(body);
        itemRepository.save(item);
    }

    @PutMapping("/{id}")
    public String updateItem(@PathVariable("id") Long id, @RequestBody String body) {
        return "item " + id + " atualizado para " + body;
    }

    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable("id") Long id) {
        return id + " deletado";
    }

}
