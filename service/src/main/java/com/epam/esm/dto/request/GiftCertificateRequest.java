package com.epam.esm.dto.request;

import com.epam.esm.models.GiftCertificate;
import com.epam.esm.models.Tag;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GiftCertificateRequest {

    @NotNull(message = "Please, provide a name")
    @Size(min = 2, max = 30, message = "Name length should be between 2 and 30 symbols")
    private String name;
    @NotNull(message = "Please, provide a name")
    @Size(min = 2, max = 1000, message = "Description length should be between 2 and 30 symbols")
    private String description;
    @DecimalMin(value = "0.01", message = "Price can't be less than 0.01$")
    private double price;
    @Min(value = 1, message = "Duration can't be less than 1 day")
    private int duration;
    private Set<String> tags;

    public GiftCertificateRequest() {
    }

    public GiftCertificateRequest(String name, String description, double price, int duration, Set<String> tags) {
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

    public void setTags(Set<String> tags) {
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

    public Set<String> getTags() {
        return tags;
    }

    public GiftCertificate toIdentity() {
        return GiftCertificate.builder()
                .setName(name)
                .setDescription(description)
                .setDuration(duration)
                .setPrice(price)
                .setTags(tags.stream().map(tag-> Tag.builder().setName(tag).build()).collect(Collectors.toSet()))
                .build();
    }

    public static GiftCertificateRequest toDto(GiftCertificate giftCertificate, Set<String> tags) {
        return new GiftCertificateRequest(giftCertificate.getName(), giftCertificate.getDescription(),
                giftCertificate.getPrice(), giftCertificate.getDuration(), tags);
    }
}
