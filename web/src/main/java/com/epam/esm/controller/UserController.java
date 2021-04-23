package com.epam.esm.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.epam.esm.dto.response.OrderResponse;
import com.epam.esm.dto.response.UserResponse;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public CollectionModel<UserResponse> getAll() {
        return CollectionModel.of(service.findAll().stream().peek(user -> {
            user.add(linkTo(UserController.class).slash(user.getId()).withSelfRel());
            user.add(linkTo(methodOn(UserController.class).getAllOrders(user.getId())).withSelfRel());
        }).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable("id") int id) {
        UserResponse response = service.findById(id).add(linkTo(UserController.class).slash(id).withSelfRel());
        response.add(linkTo(methodOn(UserController.class).getAllOrders(id)).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderResponse>> getAllOrders(@PathVariable("id") int id) {
        return null;
    }

    @GetMapping("/{userId}/orders/{orderId}")
    private ResponseEntity<OrderResponse> getOneOrder(@PathVariable("userId") int userId, @PathVariable("orderId") int orderId) {
        return null;
    }
}
