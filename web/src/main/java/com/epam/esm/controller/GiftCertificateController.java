package com.epam.esm.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.epam.esm.dto.request.GiftCertificateRequest;
import com.epam.esm.dto.response.GiftCertificateResponse;

import com.epam.esm.dto.request.GiftCertificateUpdateRequest;
import com.epam.esm.models.PageableResponse;
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
    public CollectionModel<GiftCertificateResponse> getAll(@RequestParam(required = false) Map<String,
            String> allParams, @RequestParam(name = "page", defaultValue = "1") int page,
                                                           @RequestParam(name = "pageSize", defaultValue = "20") int pageSize) {
        return addPaginationLinks(service.findAll(allParams, page, pageSize), allParams);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateResponse> getById(@PathVariable("id") int id) {
        return ResponseEntity.status(HttpStatus.OK).body(addLinks(service.findById(id)));
    }

    @PostMapping
    public ResponseEntity<GiftCertificateResponse> createCertificate(@RequestBody @Valid GiftCertificateRequest certificate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(service.save(certificate)));
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

    private GiftCertificateResponse addLinks(GiftCertificateResponse response) {
        response.add(linkTo(GiftCertificateController.class).slash(response.getId()).withSelfRel());
        response.setTags(response.getTags().stream().map(tag -> tag.add(linkTo(TagController.class).slash(tag.getId()).withSelfRel())).collect(Collectors.toSet()));
        return response;
    }

    private CollectionModel<GiftCertificateResponse> addPaginationLinks(PageableResponse<GiftCertificateResponse> response, Map<String, String> allParams) {
        CollectionModel<GiftCertificateResponse> responses = CollectionModel.of(response.getResponses().stream()
                .map(this::addLinks).collect(Collectors.toList()))
                .add(linkTo(methodOn(GiftCertificateController.class).getAll(allParams, response.getCurrentPage(), response.getPageSize())).withSelfRel())
                .add(linkTo(methodOn(GiftCertificateController.class).getAll(allParams, 1, response.getPageSize())).withRel("first_page"))
                .add(linkTo(methodOn(GiftCertificateController.class).getAll(allParams, response.getLastPage(), response.getPageSize())).withRel("last_page"));
        if (response.getResponses().isEmpty() || response.getCurrentPage() == response.getLastPage() && response.getCurrentPage() == response.getCurrentPage()) {
            return responses;
        }
        if (response.getCurrentPage() == 1) {
            responses.add(linkTo(methodOn(GiftCertificateController.class).getAll(allParams, response.getCurrentPage() + 1, response.getPageSize())).withRel("next_page"));
        }
        if (response.getCurrentPage() > 1 && response.getCurrentPage() < response.getLastPage()) {
            responses.add(linkTo(methodOn(GiftCertificateController.class).getAll(allParams, response.getCurrentPage() + 1, response.getPageSize())).withRel("next_page"));
            responses.add(linkTo(methodOn(GiftCertificateController.class).getAll(allParams, response.getCurrentPage() - 1, response.getPageSize())).withRel("previous_page"));
        }
        if (response.getCurrentPage() == response.getLastPage()) {
            responses.add(linkTo(methodOn(GiftCertificateController.class).getAll(allParams, response.getCurrentPage() - 1, response.getPageSize())).withRel("previous_page"));
        }
        return responses;
    }

}
