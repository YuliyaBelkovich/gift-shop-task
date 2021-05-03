package com.epam.esm.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.epam.esm.dto.request.TagRequest;
import com.epam.esm.dto.response.TagResponse;
import com.epam.esm.models.PageableResponse;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.stream.Collectors;

/**
 * The type Tag controller.
 */
@RestController
@Validated
@RequestMapping(value = "/tags", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class TagController {

    private TagService service;

    /**
     * Instantiates a new Tag controller.
     *
     * @param service the tag service object
     */
    @Autowired
    public TagController(TagService service) {
        this.service = service;
    }

    /**
     * Finds all persisting tags in the database, supports pagination.
     *
     * @param page     the page number, must be greater than 1
     * @param pageSize the number of elements on each page, must be greater than 1
     * @return the collection of orders
     */
    @GetMapping
    public CollectionModel<TagResponse> findAll
    (@RequestParam(name = "page", defaultValue = "1")
     @Min(value = 1, message = "Page number can't be less then 1") int page,
     @RequestParam(name = "pageSize", defaultValue = "20")
     @Min(value = 1, message = "Page size can't be less then 1") int pageSize) {
        PageableResponse<TagResponse> response = service.findAll(page, pageSize);
        return PagedModel.of(response.getResponses().stream()
                        .map(this::addLinks).collect(Collectors.toList()),
                new PagedModel.PageMetadata(response.getPageSize(),
                        response.getCurrentPage(),
                        response.getTotalElements(),
                        response.getLastPage()))
                .add(linkTo(methodOn(TagController.class)
                        .findAll(response.getCurrentPage(), response.getPageSize())).withSelfRel());
    }

    /**
     * Finds tag by its id
     *
     * @param id the tag id
     * @return tag
     */
    @GetMapping("/{id}")
    public ResponseEntity<TagResponse> findById(@PathVariable("id") int id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * Finds most widely used tag of a user with the highest cost of all orders
     *
     * @return tag
     */
    @GetMapping("/top")
    public ResponseEntity<TagResponse> getMostPopularTag() {
        return ResponseEntity.ok(service.getMostWidelyUsedTag());
    }

    /**
     * Creates new tag in the database
     *
     * @param tag the tag request object in JSON format
     * @return newly created tag with the generated id
     */
    @PostMapping
    public ResponseEntity<TagResponse> add(@RequestBody @Valid TagRequest tag) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(tag));
    }

    /**
     * Delete tag by its id
     *
     * @param id the tag id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private TagResponse addLinks(TagResponse response) {
        return response.add(linkTo(TagController.class).slash(response.getId()).withSelfRel());
    }
}
