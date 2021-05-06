package com.epam.esm.dto.request;

import com.epam.esm.models.GiftCertificate;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

public class GiftCertificateUpdateRequest {

    @Size(min = 2, max = 30, message = "Name length should be between 2 and 30 symbols")
    private String name;
    @Size(min = 2, max = 1000, message = "Description length should be between 2 and 30 symbols")
    private String description;
    @DecimalMin(value = "0", message = "Price can't be less than 0.01$")
    private double price;
    @Min(value = 0, message = "Duration can't be less than 1 day")
    private int duration;
    private List<CertificateTagUpdateRequest> tags;

    public GiftCertificateUpdateRequest() {
    }

    public GiftCertificateUpdateRequest(String name, String description, double price, int duration, List<CertificateTagUpdateRequest> tags) {
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

    public void setTags(List<CertificateTagUpdateRequest> tags) {
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

    public List<CertificateTagUpdateRequest> getTags() {
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

    public static GiftCertificateUpdateRequest toDto(GiftCertificate giftCertificate, List<String> tags) {
        return new GiftCertificateUpdateRequest(giftCertificate.getName(), giftCertificate.getDescription(),
                giftCertificate.getPrice(), giftCertificate.getDuration(), tags.stream().map(tag -> new CertificateTagUpdateRequest("NONE", tag)).collect(Collectors.toList()));
    }
}
