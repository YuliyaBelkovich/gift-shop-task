package com.epam.esm.controller;

import com.epam.esm.dto.TagRequest;
import com.epam.esm.dto.TagResponse;
import com.epam.esm.dto.TagResponseContainer;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tags", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class TagController {

    public TagService service;

    @Autowired
    public TagController(TagService service) {
        this.service = service;
    }


    @GetMapping
    public ResponseEntity<TagResponseContainer> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponse> findById(@PathVariable("id") int id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public void add(@RequestBody TagRequest tag) {
        service.save(tag);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        service.delete(id);
    }
}
