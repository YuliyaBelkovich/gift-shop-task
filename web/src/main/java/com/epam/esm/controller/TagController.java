package com.epam.esm.controller;

import com.epam.esm.dto.TagRequest;
import com.epam.esm.dto.TagResponse;
import com.epam.esm.dto.TagResponseContainer;
import com.epam.esm.exception.IdentityAlreadyExistsException;
import com.epam.esm.exception.IdentityNotFoundException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tags", produces = "application/json")
public class TagController {

    public TagService service;

    @Autowired
    public TagController(TagService service) {
        this.service = service;
    }

    @ExceptionHandler(IdentityNotFoundException.class)
    public ResponseEntity<Error> giftNotFound(IdentityNotFoundException e) {
        Error error = new Error(e.getId() + 4040, "Tag id[" + e.getId() + "] not found");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IdentityAlreadyExistsException.class)
    public ResponseEntity<Error> giftAlreadyExists(IdentityAlreadyExistsException e) {
        Error error = new Error(4090, e.getIdentity() + " already exists");
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
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
