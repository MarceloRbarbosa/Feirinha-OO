package com.feirinha.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

import com.feirinha.api.dtos.ItemDTO;
import com.feirinha.api.models.ItemModel;
import com.feirinha.api.repositories.ItemRepository;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> getItems() {
        return ResponseEntity.status(HttpStatus.OK).body(itemRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getItemById(@PathVariable("id") Long id) {
        Optional<ItemModel> item = itemRepository.findById(id);

        if (!item.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Este item não existe");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(item.get());
        }
    }

    @PostMapping()
    public ResponseEntity<Object> createItem(@RequestBody @Valid ItemDTO body) {
        ItemModel item = new ItemModel(body);
        itemRepository.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateItem(@PathVariable("id") Long id, @RequestBody @Valid ItemDTO body) {
        Optional<ItemModel> item = itemRepository.findById(id);

        if (!item.isPresent()) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        ItemModel newItem = new ItemModel(body);
        newItem.setId(id);
        itemRepository.save(newItem);
        return ResponseEntity.status(HttpStatus.OK).body(item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteItem(@PathVariable("id") Long id) {
        itemRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
