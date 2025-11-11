package com.feirinha.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

import com.feirinha.api.dtos.ItemDTO;
import com.feirinha.api.models.ItemModel;
import com.feirinha.api.services.ItemService;

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

    final ItemService itemService;

    ItemsController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping()
    public ResponseEntity<Object> getItems() {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.getItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getItemById(@PathVariable("id") Long id) {
        Optional<ItemModel> item = itemService.getItemById(id);

        if (!item.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Este item não existe");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(item.get());
        }
    }

    @PostMapping()
    public ResponseEntity<Object> createItem(@RequestBody @Valid ItemDTO body) {
        Optional<ItemModel> item = itemService.createItem(body);

        if (!item.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Este item já existe");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(item.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateItem(@PathVariable("id") Long id, @RequestBody @Valid ItemDTO body) {
        try {
            Optional<ItemModel> item = itemService.updateItem(id, body);

            if (!item.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item não encontrado");
            }

            return ResponseEntity.status(HttpStatus.OK).body(item.get());

        } catch (RuntimeException e) {
            if (e.getMessage().equals("DuplicateItem")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um item com este nome");
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar item");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteItem(@PathVariable("id") Long id) {
        boolean deleted = itemService.deleteItem(id);

        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item não encontrado");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
