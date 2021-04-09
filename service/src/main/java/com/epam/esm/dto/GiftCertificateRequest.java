package com.epam.esm.dto;

import com.epam.esm.models.GiftCertificate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

public class GiftCertificateRequest {

    @NotEmpty(message = "Please, provide a name")
    @Size(min = 2, max = 30, message = "Name should be grater than 2 and no longer than 30 symbols")
    private String name;
    @NotEmpty(message = "Please, provide a description")
    @Size(min = 2, max = 1000, message = "Description should be grater than 2 and no longer than 1000 symbols")
    private String description;
    @Min(value = 0, message = "Price shouldn't be less than zero")
    private double price;
    @Min(value = 1, message = "Duration can't be less than 1 day")
    private int duration;
    private List<String> tags;

    public GiftCertificateRequest() {
    }

    public GiftCertificateRequest(String name, String description, double price, int duration, List<String> tags) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tags = tags;
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

    public GiftCertificate toIdentity(int id) {
        return GiftCertificate.builder()
                .setId(id)
                .setName(name)
                .setDescription(description)
                .setDuration(duration)
                .setPrice(price)
                .build();
    }

    public static GiftCertificateRequest toDto(GiftCertificate giftCertificate, List<String> tags) {
        return new GiftCertificateRequest(giftCertificate.getName(), giftCertificate.getDescription(),
                giftCertificate.getPrice(), giftCertificate.getDuration(), tags);
    }
}
