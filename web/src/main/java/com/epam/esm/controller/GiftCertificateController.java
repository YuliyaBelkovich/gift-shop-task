package com.epam.esm.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.epam.esm.dto.request.GiftCertificateRequest;
import com.epam.esm.dto.response.GiftCertificateResponse;
import com.epam.esm.dto.request.GiftCertificateUpdateRequest;
import com.epam.esm.models.PageableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.epam.esm.service.GiftCertificateService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Gift certificate controller.
 */
@RestController
@Validated
@RequestMapping(value = "/certificates", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class GiftCertificateController {

    private GiftCertificateService service;

    /**
     * Instantiates a new Gift certificate controller.
     *
     * @param service the gift certificate service object
     */
    @Autowired
    public GiftCertificateController(GiftCertificateService service) {
        this.service = service;
    }

    /**
     * Finds all gift certificates persisting in the database, supports pagination.
     * Supports sorting by name, createDate or lastUpdateDate in ascending or descending order.
     * Supports filtering by several tags, part of description or name.
     * All parameters can be used separately or in conjunction.
     *
     * @param allParams sorting and filtering params
     * @param page      the page number, must be greater than 1
     * @param pageSize  the number of elements on each page, must be greater than 1
     * @return the all
     */
    @GetMapping()
    public CollectionModel<GiftCertificateResponse> getAll
    (@RequestParam(required = false) Map<String, String> allParams,
     @RequestParam(name = "page", defaultValue = "1")
     @Min(value = 1, message = "Page number can't be less than 1")
     @Max(value = 10000, message = "Page number can't be greater than 100000") int page,
     @RequestParam(name = "pageSize", defaultValue = "20")
     @Min(value = 1, message = "Page size can't be less than 1")
     @Max(value = 100, message = "Page size can't be greater than 100") int pageSize) {
        PageableResponse<GiftCertificateResponse> response = service.findAll(allParams, page, pageSize);
        return PagedModel.of(response.getResponses().stream()
                        .map(this::addLinks)
                        .collect(Collectors.toList()),
                new PagedModel.PageMetadata(response.getPageSize(),
                        response.getCurrentPage(),
                        response.getTotalElements(),
                        response.getLastPage()))
                .add(linkTo(methodOn(GiftCertificateController.class)
                        .getAll(allParams, response.getCurrentPage(), response.getPageSize())).withSelfRel());
    }

    /**
     * Finds gift certificate by its id.
     *
     * @param id the gift certificate id
     * @return gift certificate
     */
    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateResponse> getById(@PathVariable("id") int id) {
        return ResponseEntity.status(HttpStatus.OK).body(addLinks(service.findById(id)));
    }

    /**
     * Creates new gift certificate in the database.
     *
     * @param certificate the certificate request object
     * @return newly created gift certificate with generated id and createDate
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GiftCertificateResponse> createCertificate(@RequestBody @Valid GiftCertificateRequest certificate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(service.save(certificate)));
    }

    /**
     * Edits the specific fields of an existing gift certificate.
     *
     * @param certificate the gift certificate request object containing new fields
     * @param id          the gift certificate id
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> edit(@RequestBody @Valid GiftCertificateUpdateRequest certificate, @PathVariable("id") int id) {
        service.update(certificate, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Deletes gift certificate from database by its id.
     *
     * @param id the gift certificate id
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private GiftCertificateResponse addLinks(GiftCertificateResponse response) {
        response.add(linkTo(GiftCertificateController.class).slash(response.getId()).withSelfRel());
        response.setTags(response.getTags()
                .stream()
                .map(tag ->
                        tag.add(linkTo(TagController.class).slash(tag.getId()).withSelfRel()))
                .collect(Collectors.toSet()));
        return response;
    }

}
