package com.epam.esm.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.epam.esm.dto.request.GiftCertificateRequest;
import com.epam.esm.dto.response.GiftCertificateResponse;

import com.epam.esm.dto.request.GiftCertificateUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.epam.esm.service.GiftCertificateService;

import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/certificates", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class GiftCertificateController {

    public GiftCertificateService service;

    @Autowired
    public GiftCertificateController(GiftCertificateService service) {
        this.service = service;
    }

    @GetMapping()
    public CollectionModel<GiftCertificateResponse> getAll(@RequestParam(required = false) Map<String, String> allParams) {
        return CollectionModel.of(service.findAll(allParams).stream()
                .peek(response -> response.add(linkTo(GiftCertificateController.class).slash(response.getId()).withSelfRel())).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateResponse> getById(@PathVariable("id") int id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id).add(linkTo(GiftCertificateController.class).slash(id).withSelfRel()));
    }

    @PostMapping
    public ResponseEntity<GiftCertificateResponse> createCertificate(@RequestBody @Valid GiftCertificateRequest certificate) {
        GiftCertificateResponse response = service.save(certificate);
        return ResponseEntity.status(HttpStatus.CREATED).body(response.add(linkTo(GiftCertificateController.class).slash(response.getId()).withSelfRel()));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> edit(@RequestBody @Valid GiftCertificateUpdateRequest certificate, @PathVariable("id") int id) {
        service.update(certificate, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
