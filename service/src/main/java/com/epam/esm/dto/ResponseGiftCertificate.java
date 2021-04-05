package com.epam.esm.dto;

import com.epam.esm.models.GiftCertificate;
import com.epam.esm.models.Tag;

import java.util.List;
import java.util.stream.Collectors;

public class ResponseGiftCertificate {
    private int id;
    private String name;
    private String description;
    private double price;
    private int duration;
    private List<String> tags;

    public ResponseGiftCertificate() {
    }

    public ResponseGiftCertificate(int id, String name, String description, double price, int duration, List<String> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public static ResponseGiftCertificate toDto(GiftCertificate certificate, List<Tag> tags) {

        return new ResponseGiftCertificate.Builder()
                .setId(certificate.getId())
                .setName(certificate.getName())
                .setDescription(certificate.getDescription())
                .setPrice(certificate.getPrice())
                .setDuration(certificate.getDuration())
                .setTags(tags)
                .build();

    }

    public static GiftCertificate toIdentity(ResponseGiftCertificate certificate) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(certificate.getId());
        giftCertificate.setName(certificate.getName());
        giftCertificate.setDuration(certificate.getDuration());
        giftCertificate.setPrice(certificate.getPrice());
        giftCertificate.setDescription(certificate.getDescription());
        return giftCertificate;
    }

    public static class Builder {
        private int id;
        private String name;
        private String description;
        private double price;
        private int duration;
        private List<String> tags;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder setTags(List<Tag> tags) {
            this.tags = tags.stream().map(Tag::getName).collect(Collectors.toList());
            return this;
        }

        public ResponseGiftCertificate build() {
            return new ResponseGiftCertificate(id, name, description, price, duration, tags);
        }
    }
}
