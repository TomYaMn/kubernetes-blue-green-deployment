package com.tomyamn.bespringboot.controller;

import com.tomyamn.bespringboot.model.Item;
import com.tomyamn.bespringboot.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items") // Base path for this controller
public class ItemController {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // GET all items
    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        try {
            List<Item> items = itemRepository.findAll();
            if (items.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET item by ID
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable("id") long id) {
        Optional<Item> itemData = itemRepository.findById(id);
        return itemData.map(item -> new ResponseEntity<>(item, HttpStatus.OK))
                       .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // POST a new item
    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        try {
            Item _item = itemRepository.save(new Item(item.getName(), item.getDescription()));
            return new ResponseEntity<>(_item, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT to update an item by ID
    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable("id") long id, @RequestBody Item item) {
        Optional<Item> itemData = itemRepository.findById(id);

        if (itemData.isPresent()) {
            Item _item = itemData.get();
            _item.setName(item.getName());
            _item.setDescription(item.getDescription());
            return new ResponseEntity<>(itemRepository.save(_item), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE an item by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable("id") long id) {
        try {
            itemRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // This might happen if the item doesn't exist, or other DB constraint issues
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE all items
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllItems() {
        try {
            itemRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Simple health check endpoint for Kubernetes probes
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return new ResponseEntity<>("{\"status\":\"UP\"}", HttpStatus.OK);
    }
}