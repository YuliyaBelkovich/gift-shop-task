package com.epam.esm.dto.response;

import com.epam.esm.models.Order;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.Objects;

public class OrderResponse extends RepresentationModel<OrderResponse> {

    private int id;
    private double cost;
    private LocalDateTime createDate;

    public OrderResponse() {
    }

    public OrderResponse(int id, double cost, LocalDateTime createDate) {
        this.id = id;
        this.cost = cost;
        this.createDate = createDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderResponse)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        OrderResponse that = (OrderResponse) o;
        return getId() == that.getId() && Double.compare(that.getCost(), getCost()) == 0 && Objects.equals(getCreateDate(), that.getCreateDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getCost(), getCreateDate());
    }

    @Override
    public String toString() {
        return "OrderResponse{" +
                "id=" + id +
                ", cost=" + cost +
                ", createDate=" + createDate +
                '}';
    }

    public static OrderResponse toDto(Order order) {
        return builder()
                .setId(order.getId())
                .setCost(order.getCost())
                .setCreateDate(order.getCreateDate())
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private int id;
        private double cost;
        private LocalDateTime createDate;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setCost(double cost) {
            this.cost = cost;
            return this;
        }

        public Builder setCreateDate(LocalDateTime createDate) {
            this.createDate = createDate;
            return this;
        }

        public OrderResponse build() {
            return new OrderResponse(id, cost, createDate);
        }
    }
}
