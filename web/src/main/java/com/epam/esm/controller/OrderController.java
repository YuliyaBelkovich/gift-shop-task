package com.epam.esm.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.epam.esm.dto.request.OrderRequest;
import com.epam.esm.dto.response.OrderResponse;
import com.epam.esm.models.PageableResponse;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    private OrderService service;

    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public CollectionModel<OrderResponse> getAll(@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "pageSize", defaultValue = "20") int pageSize) {
        return addPaginationLinks(service.findAll(page, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getById(@PathVariable("id") int id) {
        OrderResponse response = service.findById(id);
        return ResponseEntity.ok(addLinks(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid OrderRequest orderRequest) {
        OrderResponse response = service.save(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(response));
    }

    private OrderResponse addLinks(OrderResponse response) {
        response.add(linkTo(OrderController.class).slash(response.getId()).withSelfRel());
        for (Integer certificateId : response.getCertificateIds()) {
            response.add(linkTo(methodOn(GiftCertificateController.class).getById(certificateId)).withRel("certificates"));
        }
        return response;
    }

    private CollectionModel<OrderResponse> addPaginationLinks(PageableResponse<OrderResponse> response) {
        CollectionModel<OrderResponse> responses = CollectionModel.of(response.getResponses().stream().map(this::addLinks).collect(Collectors.toList()));
        responses.add(linkTo(methodOn(OrderController.class).getAll(response.getCurrentPage(), response.getPageSize())).withSelfRel())
                .add(linkTo(methodOn(OrderController.class).getAll(1, response.getPageSize())).withRel("first_page"))
                .add(linkTo(methodOn(OrderController.class).getAll(response.getLastPage(), response.getPageSize())).withRel("last_page"));
        if (response.getResponses().isEmpty() || response.getCurrentPage() == response.getLastPage() && response.getCurrentPage() == response.getCurrentPage()) {
            return responses;
        }
        if (response.getCurrentPage() == 1) {
            responses.add(linkTo(methodOn(OrderController.class).getAll(response.getCurrentPage() + 1, response.getPageSize())).withRel("next_page"));
        }
        if (response.getCurrentPage() > 1 && response.getCurrentPage() < response.getLastPage()) {
            responses.add(linkTo(methodOn(OrderController.class).getAll(response.getCurrentPage() + 1, response.getPageSize())).withRel("next_page"));
            responses.add(linkTo(methodOn(OrderController.class).getAll(response.getCurrentPage() - 1, response.getPageSize())).withRel("previous_page"));
        }
        if (response.getCurrentPage() == response.getLastPage()) {
            responses.add(linkTo(methodOn(OrderController.class).getAll(response.getCurrentPage() - 1, response.getPageSize())).withRel("previous_page"));
        }
        return responses;
    }
}
