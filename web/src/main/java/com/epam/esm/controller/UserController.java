package com.epam.esm.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.epam.esm.dto.response.OrderResponse;
import com.epam.esm.dto.response.UserResponse;
import com.epam.esm.models.PageableResponse;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.stream.Collectors;

/**
 * The type User controller.
 */
@RestController
@Validated
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private UserService service;

    /**
     * Instantiates a new User controller.
     *
     * @param service the user service object
     */
    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    /**
     * Finds all users persisting in the database, supports pagination.
     *
     * @param page     the page number, must be greater than 1
     * @param pageSize the number of elements on each page, must be greater than 1
     * @return the all
     */
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CollectionModel<UserResponse> getAll(@RequestParam(name = "page", defaultValue = "1")
                                                @Min(value = 1, message = "{page.number.less}")
                                                @Max(value = 10000, message = "{page.number.greater}") int page,
                                                @RequestParam(name = "pageSize", defaultValue = "20")
                                                @Min(value = 1, message = "{page.size.less}")
                                                @Max(value = 100, message = "{page.size.greater}") int pageSize) {
        PageableResponse<UserResponse> response = service.findAll(page, pageSize);
        return PagedModel.of(response.getResponses().stream()
                        .map(this::addLinks).collect(Collectors.toList()),
                new PagedModel.PageMetadata(response.getPageSize(),
                        response.getCurrentPage(),
                        response.getTotalElements(),
                        response.getLastPage()))
                .add(linkTo(methodOn(UserController.class)
                        .getAll(response.getCurrentPage(), response.getPageSize())).withSelfRel());
    }

    /**
     * Finds user by its id.
     *
     * @param id the user id
     * @return user
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponse> getById(@PathVariable("id") int id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(service.findById(id)));
    }

    /**
     * Finds all orders of a user by user's id, supports pagination.
     *
     * @param id       the user id
     * @param page     the page number, must be greater than 1
     * @param pageSize the number of elements on each page, must be greater than 1
     * @return the collection of orders
     */
    @GetMapping("/{id}/orders")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #id == authentication.principal.id)")
    public CollectionModel<OrderResponse> getAllOrders
    (@PathVariable("id") int id,
     @RequestParam(name = "page", defaultValue = "1")
     @Min(value = 1, message = "{page.number.less}")
     @Max(value = 10000, message = "{page.number.greater}") int page,
     @RequestParam(name = "pageSize", defaultValue = "20")
     @Min(value = 1, message = "{page.size.less}")
     @Max(value = 100, message = "{page.size.greater}") int pageSize) {
        PageableResponse<OrderResponse> response = service.findOrdersByUserId(id, page, pageSize);
        return PagedModel.of(response.getResponses().stream()
                        .map(this::addOrderLinks).collect(Collectors.toList()),
                new PagedModel.PageMetadata(response.getPageSize(),
                        response.getCurrentPage(),
                        response.getTotalElements(),
                        response.getLastPage())).add(linkTo(methodOn(UserController.class)
                .getAllOrders(id, response.getCurrentPage(), response.getPageSize())).withSelfRel());
    }

    /**
     * Finds a specific order by its id of a user by its id
     *
     * @param userId  the user id
     * @param orderId the order id
     * @return order
     */
    @GetMapping("/{userId}/orders/{orderId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') or(hasRole('ROLE_USER') and #userId == authentication.principal.id)")
    public ResponseEntity<OrderResponse> getOneOrder(@PathVariable("userId") int userId,
                                                     @PathVariable("orderId") int orderId) {
        return ResponseEntity.status(HttpStatus.OK).body(addOrderLinks(service.findOrderById(userId, orderId)));
    }

    private UserResponse addLinks(UserResponse response) {
        return response
                .add(linkTo(UserController.class).slash(response.getId()).withSelfRel())
                .add(linkTo(methodOn(UserController.class)
                        .getAllOrders(response.getId(), 1, 20)).withRel("orders"));
    }

    private OrderResponse addOrderLinks(OrderResponse response) {
        response.add(linkTo(methodOn(OrderController.class).getById(response.getId())).withSelfRel());
        for (Integer certificateId : response.getCertificateIds()) {
            response.add(linkTo(methodOn(GiftCertificateController.class).getById(certificateId)).withRel("certificates"));
        }
        return response;
    }

}
