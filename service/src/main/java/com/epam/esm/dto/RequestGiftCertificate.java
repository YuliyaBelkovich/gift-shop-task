package com.epam.esm.dto;

import com.epam.esm.models.GiftCertificate;
import com.epam.esm.models.Tag;

import java.util.List;

public class RequestGiftCertificate {
    public int id;
    private String name;
    private String description;
    private double price;
    private int duration;
    private List<String> tags;


    public RequestGiftCertificate(int id, String name, String description, double price, int duration, List<String> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tags = tags;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getDuration() {
        return duration;
    }

    public List<String> getTags() {
        return tags;
    }

    public GiftCertificate toIdentity(){
        return GiftCertificate.builder().setId(id)
                .setDescription(description)
                .setDuration(duration)
                .setPrice(price)
                .build();
    }
}
