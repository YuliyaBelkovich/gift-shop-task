package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateRequest;
import com.epam.esm.dto.GiftCertificateResponse;
import com.epam.esm.dto.GiftCertificateResponseContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.epam.esm.service.GiftCertificateService;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping(value = "/certificates", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class GiftCertificateController {

    public GiftCertificateService service;

    @Autowired
    public GiftCertificateController(GiftCertificateService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<GiftCertificateResponse>> getAll(@RequestParam(required = false) Map<String, String> allParams) {
        if (allParams.isEmpty()) {
            return ResponseEntity.ok(service.findAll());
        } else {
            return ResponseEntity.ok().body(service.findAll(allParams));
        }
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<GiftCertificateResponse> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping
    public void createCertificate(@RequestBody @Valid GiftCertificateRequest certificate, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new ValidationException();
        }
        service.save(certificate);
    }

    @PatchMapping("/{id}")
    public void edit(@RequestBody @Valid GiftCertificateRequest certificate, @PathVariable("id") int id, BindingResult bindingResult) {
        service.update(certificate, id);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        service.delete(id);
    }


}
