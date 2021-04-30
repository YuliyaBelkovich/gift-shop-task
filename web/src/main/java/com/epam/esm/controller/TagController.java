package com.epam.esm.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.epam.esm.dto.request.TagRequest;
import com.epam.esm.dto.response.OrderResponse;
import com.epam.esm.dto.response.TagResponse;
import com.epam.esm.models.PageableResponse;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
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
        PageableResponse<TagResponse> response = service.findAll(page, pageSize);
        return PagedModel.of(response.getResponses().stream()
                .map(this::addLinks).collect(Collectors.toList()), new PagedModel.PageMetadata(response.getPageSize(), response.getCurrentPage(), response.getTotalElements(), response.getLastPage())).add(linkTo(methodOn(TagController.class).findAll(response.getCurrentPage(), response.getPageSize())).withSelfRel());
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
}
