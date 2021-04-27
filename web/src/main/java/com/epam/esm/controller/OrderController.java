package com.epam.esm.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.epam.esm.dto.request.OrderRequest;
import com.epam.esm.dto.response.OrderResponse;
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
    public CollectionModel<OrderResponse> getAll() {
        return CollectionModel.of(service.findAll().stream().map(this::addSelfLink).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getById(@PathVariable("id") int id) {
        OrderResponse response = service.findById(id);
        return ResponseEntity.ok(addSelfLink(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid OrderRequest orderRequest) {
        OrderResponse response = service.save(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(addSelfLink(response));
    }

    private OrderResponse addSelfLink(OrderResponse response){
        return response.add(linkTo(OrderController.class).slash(response.getId()).withSelfRel());
    }
}
