package com.epam.esm.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.epam.esm.dto.response.OrderResponse;
import com.epam.esm.dto.response.UserResponse;
import com.epam.esm.models.PageableResponse;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public CollectionModel<UserResponse> getAll(@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "pageSize", defaultValue = "20") int pageSize) {
        return addUserPaginationLinks(service.findAll(page, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable("id") int id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(service.findById(id)));
    }

    @GetMapping("/{id}/orders")
    public CollectionModel<OrderResponse> getAllOrders(@PathVariable("id") int id, @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "pageSize", defaultValue = "20") int pageSize) {
        return addOrderPaginationLinks(service.findOrdersByUserId(id, page, pageSize), id);
    }

    @GetMapping("/{userId}/orders/{orderId}")
    private ResponseEntity<OrderResponse> getOneOrder(@PathVariable("userId") int userId, @PathVariable("orderId") int orderId) {
        return ResponseEntity.status(HttpStatus.OK).body(addOrderLinks(service.findOrderById(userId, orderId), userId));
    }

    private UserResponse addLinks(UserResponse response) {
        return response
                .add(linkTo(UserController.class).slash(response.getId()).withSelfRel())
                .add(linkTo(methodOn(UserController.class).getAllOrders(response.getId(), 1, 20)).withRel("orders"));
    }

    private OrderResponse addOrderLinks(OrderResponse response, int userId) {
        response.add(linkTo(methodOn(UserController.class).getOneOrder(userId, response.getId())).withSelfRel());
        for (Integer certificateId : response.getCertificateIds()) {
            response.add(linkTo(methodOn(GiftCertificateController.class).getById(certificateId)).withRel("certificates"));
        }
        return response;
    }

    private CollectionModel<UserResponse> addUserPaginationLinks(PageableResponse<UserResponse> response) {
        CollectionModel<UserResponse> responses = CollectionModel.of(response.getResponses().stream().map(this::addLinks).collect(Collectors.toList()));
        responses.add(linkTo(methodOn(UserController.class).getAll(response.getCurrentPage(), response.getPageSize())).withSelfRel())
                .add(linkTo(methodOn(UserController.class).getAll(1, response.getPageSize())).withRel("first_page"))
                .add(linkTo(methodOn(UserController.class).getAll(response.getLastPage(), response.getPageSize())).withRel("last_page"));
        if (response.getResponses().isEmpty() || response.getCurrentPage() == response.getLastPage() && response.getCurrentPage() == response.getCurrentPage()) {
            return responses;
        }
        if (response.getCurrentPage() == 1) {
            responses.add(linkTo(methodOn(UserController.class).getAll(response.getCurrentPage() + 1, response.getPageSize())).withRel("next_page"));
        }
        if (response.getCurrentPage() > 1 && response.getCurrentPage() < response.getLastPage()) {
            responses.add(linkTo(methodOn(UserController.class).getAll(response.getCurrentPage() + 1, response.getPageSize())).withRel("next_page"));
            responses.add(linkTo(methodOn(UserController.class).getAll(response.getCurrentPage() - 1, response.getPageSize())).withRel("previous_page"));
        }
        if (response.getCurrentPage() == response.getLastPage()) {
            responses.add(linkTo(methodOn(UserController.class).getAll(response.getCurrentPage() - 1, response.getPageSize())).withRel("previous_page"));
        }
        return responses;
    }

    private CollectionModel<OrderResponse> addOrderPaginationLinks(PageableResponse<OrderResponse> response, int userId) {
        CollectionModel<OrderResponse> responses = CollectionModel.of(response.getResponses().stream().map(order -> addOrderLinks(order, userId)).collect(Collectors.toList()));
        responses.add(linkTo(methodOn(UserController.class).getAllOrders(userId, response.getCurrentPage(), response.getPageSize())).withSelfRel())
                .add(linkTo(methodOn(UserController.class).getAllOrders(userId, 1, response.getPageSize())).withRel("first_page"))
                .add(linkTo(methodOn(UserController.class).getAllOrders(userId, response.getLastPage(), response.getPageSize())).withRel("last_page"));
        if (response.getResponses().isEmpty() || response.getCurrentPage() == response.getLastPage() && response.getCurrentPage() == response.getCurrentPage()) {
            return responses;
        }
        if (response.getCurrentPage() == 1) {
            responses.add(linkTo(methodOn(UserController.class).getAllOrders(userId, response.getCurrentPage() + 1, response.getPageSize())).withRel("next_page"));
        }
        if (response.getCurrentPage() > 1 && response.getCurrentPage() < response.getLastPage()) {
            responses.add(linkTo(methodOn(UserController.class).getAllOrders(userId, response.getCurrentPage() + 1, response.getPageSize())).withRel("next_page"));
            responses.add(linkTo(methodOn(UserController.class).getAllOrders(userId, response.getCurrentPage() - 1, response.getPageSize())).withRel("previous_page"));
        }
        if (response.getCurrentPage() == response.getLastPage()) {
            responses.add(linkTo(methodOn(UserController.class).getAllOrders(userId, response.getCurrentPage() - 1, response.getPageSize())).withRel("previous_page"));
        }
        return responses;
    }

}
