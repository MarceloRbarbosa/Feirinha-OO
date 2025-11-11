package com.feirinha.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/items")

public class ItemsController {

    @GetMapping()
    public String getItems() {
        return "lista de itens";
    }

    @GetMapping("/{id}")
    public String getItemById(@PathVariable("id") Long id) {
        return "Receita de Id = " + id;
    }

    @PostMapping()
    public String createItem(@RequestBody String body) {
        return body;
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
