package com.epam.esm.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.epam.esm.dto.request.OrderRequest;
import com.epam.esm.dto.response.OrderResponse;
import com.epam.esm.models.PageableResponse;
import com.epam.esm.service.OrderService;
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
 * The type Order controller.
 */
@RestController
@Validated
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    private OrderService service;

    /**
     * Instantiates a new Order controller.
     *
     * @param service the order service object
     */
    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }

    /**
     * Finds all existing orders, supports pagination
     *
     * @param page     the page number, must be greater than 1
     * @param pageSize the number of elements on each page, must be grater than 1
     * @return collection of orders
     */
    @GetMapping
    public CollectionModel<OrderResponse> getAll(@RequestParam(name = "page", defaultValue = "1") @Min(value = 1, message = "Page number can't be less then 1") int page, @RequestParam(name = "pageSize", defaultValue = "20") @Min(value = 1, message = "Page size can't be less then 1") int pageSize) {
        PageableResponse<OrderResponse> response = service.findAll(page, pageSize);
        return PagedModel.of(response.getResponses().stream()
                .map(this::addLinks).collect(Collectors.toList()), new PagedModel.PageMetadata(response.getPageSize(), response.getCurrentPage(), response.getTotalElements(), response.getLastPage())).add(linkTo(methodOn(OrderController.class).getAll(response.getCurrentPage(), response.getPageSize())).withSelfRel());
    }

    /**
     * Finds order by its id
     *
     * @param id the order id
     * @return order object
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getById(@PathVariable("id") int id) {
        OrderResponse response = service.findById(id);
        return ResponseEntity.ok(addLinks(response));
    }

    /**
     * Deletes order from database by its id
     *
     * @param id the order id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Creates new order in the database
     *
     * @param orderRequest the order request object in JSON format, without id
     * @return newly created order with the generated id and createDate
     */
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
}
