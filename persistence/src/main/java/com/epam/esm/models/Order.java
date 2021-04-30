package com.epam.esm.models;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
@DynamicInsert
public class Order implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private double cost;
    @Column(name = "create_date", columnDefinition = "timestamp not null default CURRENT_TIMESTAMP")
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "order_certificate",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "gift_certificate_id")})
    private List<GiftCertificate> certificates;

    private Order() {
    }

    private Order(int id, double cost, LocalDateTime createDate, User user, List<GiftCertificate> certificates) {
        this.id = id;
        this.cost = cost;
        this.createDate = createDate;
        this.user = user;
        this.certificates = certificates;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
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

    public void setUser(User user) {
        this.user = user;
    }

    public List<GiftCertificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<GiftCertificate> certificates) {
        this.certificates = certificates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        Order order = (Order) o;
        return getId() == order.getId() && Double.compare(order.getCost(), getCost()) == 0 && Objects.equals(getCreateDate(), order.getCreateDate()) && Objects.equals(getUser(), order.getUser()) && Objects.equals(getCertificates(), order.getCertificates());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCost(), getCreateDate(), getUser(), getCertificates());
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", cost=" + cost +
                ", createDate=" + createDate +
                ", user=" + user +
                ", certificates=" + certificates +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private int id;
        private double cost;
        private LocalDateTime createDate;
        private User user;
        private List<GiftCertificate> certificates;

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

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setCertificates(List<GiftCertificate> certificates) {
            this.certificates = certificates;
            return this;
        }

        public Order build() {
            return new Order(id, cost, createDate, user, certificates);
        }
    }
}

