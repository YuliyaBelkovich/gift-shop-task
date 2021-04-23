package com.epam.esm.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "order")
public class Order{

    @Id
    @GeneratedValue
    private int id;
    @Column(nullable = false)
    private double cost;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @ManyToOne
    private User user;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "order_certificate",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "gift_certificate_id")})
    private List<GiftCertificate> certificates;

    public Order() {
    }

    public Order(int id, User user, List<GiftCertificate> certificates) {
        this.id = id;
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

    public void setUser(User user) {
        this.user = user;
    }

    public List<GiftCertificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<GiftCertificate> certificates) {
        this.certificates = certificates;
    }
}
