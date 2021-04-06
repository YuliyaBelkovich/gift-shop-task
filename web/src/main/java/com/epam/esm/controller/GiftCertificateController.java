package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateRequest;
import com.epam.esm.dto.GiftCertificateResponse;
import com.epam.esm.dto.GiftCertificateResponseContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.epam.esm.service.GiftCertificateService;

import java.util.Map;

@RestController
@RequestMapping(value = "/certificates", produces = "application/json")
public class GiftCertificateController {

    public GiftCertificateService service;

    @Autowired
    public GiftCertificateController(GiftCertificateService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<GiftCertificateResponseContainer> getAll(@RequestParam(required = false) Map<String, String> allParams) {
        if (allParams.containsKey("name")) {
            return ResponseEntity.ok().body(service.searchByField("name", allParams.get("name")));
        }
        if (allParams.containsKey("description")) {
            return ResponseEntity.ok().body(service.searchByField("description", allParams.get("description")));
        }
        if(allParams.containsKey("sort_by") && allParams.containsKey("order")){
            return ResponseEntity.ok().body(service.sort(allParams.get("sort_by"), allParams.get("order")));
        }
        return ResponseEntity.ok().body(service.findAll());
    }
//
//    @GetMapping("/filter")
//    public ResponseEntity<GiftCertificateResponseContainer> filter(@RequestParam Map<String, String> allParams) {
//        if (allParams.containsKey("name")) {
//            return ResponseEntity.ok().body(service.searchByField("name", allParams.get("name")));
//        }
//        if (allParams.containsKey("description")) {
//            return ResponseEntity.ok().body(service.searchByField("description", allParams.get("description")));
//        }
//        return null;
//    }

//    @GetMapping("/sort")
//    public ResponseEntity<GiftCertificateResponseContainer> sort(@RequestParam(name = "sort_by") String sortBy, @RequestParam(name = "order") String order) {
//        return ResponseEntity.ok().body(service.sort(sortBy, order));
//    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<GiftCertificateResponse> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping
    public void createCertificate(@RequestBody GiftCertificateRequest certificate) {
        service.save(certificate);
    }

    @PatchMapping("/{id}")
    public void edit(@RequestBody GiftCertificateRequest certificate, @PathVariable("id") int id) {
        service.update(certificate, id);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        service.delete(id);
    }


}
