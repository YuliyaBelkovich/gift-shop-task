package com.epam.esm.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.epam.esm.dto.request.TagRequest;
import com.epam.esm.dto.response.TagResponse;
import com.epam.esm.models.PageableResponse;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/tags", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class TagController {

    public TagService service;

    @Autowired
    public TagController(TagService service) {
        this.service = service;
    }

    @GetMapping
    public CollectionModel<TagResponse> findAll(@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "pageSize", defaultValue = "20") int pageSize) {
        return addPaginationLinks(service.findAll(page, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponse> findById(@PathVariable("id") int id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/top")
    public ResponseEntity<TagResponse> getMostPopularTag() {
        return null;
    }

    @PostMapping
    public ResponseEntity<TagResponse> add(@RequestBody @Valid TagRequest tag) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(tag));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public TagResponse addLinks(TagResponse response) {
        return response.add(linkTo(TagController.class).slash(response.getId()).withSelfRel());
    }

    public CollectionModel<TagResponse> addPaginationLinks(PageableResponse<TagResponse> response) {
        CollectionModel<TagResponse> responses = CollectionModel.of(response.getResponses().stream().map(this::addLinks).collect(Collectors.toList()));
        responses.add(linkTo(methodOn(TagController.class).findAll(response.getCurrentPage(), response.getPageSize())).withSelfRel())
                .add(linkTo(methodOn(TagController.class).findAll(1, response.getPageSize())).withRel("first_page"))
                .add(linkTo(methodOn(TagController.class).findAll(response.getLastPage(), response.getPageSize())).withRel("last_page"));
        if (response.getResponses().isEmpty() || response.getCurrentPage() == response.getLastPage() && response.getCurrentPage() == response.getCurrentPage()) {
            return responses;
        }

        if (response.getCurrentPage() == 1) {
            responses.add(linkTo(methodOn(TagController.class).findAll(response.getCurrentPage() + 1, response.getPageSize())).withRel("next_page"));
        }
        if (response.getCurrentPage() > 1 && response.getCurrentPage() < response.getLastPage()) {
            responses.add(linkTo(methodOn(TagController.class).findAll(response.getCurrentPage() + 1, response.getPageSize())).withRel("next_page"));
            responses.add(linkTo(methodOn(TagController.class).findAll(response.getCurrentPage() - 1, response.getPageSize())).withRel("previous_page"));
        }
        if (response.getCurrentPage() == response.getLastPage()) {
            responses.add(linkTo(methodOn(TagController.class).findAll(response.getCurrentPage() - 1, response.getPageSize())).withRel("previous_page"));
        }
        return responses;

    }
}
