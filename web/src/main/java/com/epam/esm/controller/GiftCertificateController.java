package com.epam.esm.controller;

import com.epam.esm.dto.RequestGiftCertificate;
import com.epam.esm.dto.ResponseGiftCertificate;
import com.epam.esm.dto.ResponseGiftCertificateContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.epam.esm.service.GiftCertificateService;

@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {

    public GiftCertificateService service;

    @Autowired
    public GiftCertificateController(GiftCertificateService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<ResponseGiftCertificateContainer> getAll(){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(service.findAll());
    }

//    @GetMapping("/{tag}")
//    public ResponseEntity<ResponseGiftCertificateContainer> getByTagName(@PathVariable("tag") String tagName){
//        return null;
//    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<ResponseGiftCertificate> getById(@PathVariable("id") int id, Model model){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(service.findById(id));
    }

    @PostMapping()
    public void createCertificate(RequestGiftCertificate certificate){

    }

    @PatchMapping("/{id}")
    public void edit(RequestGiftCertificate certificate){

    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id){

    }



}
