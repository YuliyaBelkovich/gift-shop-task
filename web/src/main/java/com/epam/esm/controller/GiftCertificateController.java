package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateRequest;
import com.epam.esm.dto.GiftCertificateResponse;

import com.epam.esm.dto.GiftCertificateUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.epam.esm.service.GiftCertificateService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/certificates", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class GiftCertificateController {

    public GiftCertificateService service;

    @Autowired
    public GiftCertificateController(GiftCertificateService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<GiftCertificateResponse>> getAll(@RequestParam(required = false) Map<String, String> allParams) {
            return ResponseEntity.ok().body(service.findAll(allParams));
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<GiftCertificateResponse> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<GiftCertificateResponse> createCertificate(@RequestBody @Valid GiftCertificateRequest certificate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(certificate));
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
