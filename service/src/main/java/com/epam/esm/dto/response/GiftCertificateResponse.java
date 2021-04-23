package com.epam.esm.dto.response;

import com.epam.esm.models.GiftCertificate;
import com.epam.esm.models.Tag;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class GiftCertificateResponse extends RepresentationModel<GiftCertificateResponse> {

    private int id;
    private String name;
    private String description;
    private double price;
    private int duration;
    private Set<String> tags;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastUpdateDate;

    private GiftCertificateResponse() {
    }

    private GiftCertificateResponse(int id, String name, String description, double price, int duration, Set<String> tags, LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tags = tags;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
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

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GiftCertificateResponse)) {
            return false;
        }
        GiftCertificateResponse that = (GiftCertificateResponse) o;
        return getId() == that.getId() && Double.compare(that.getPrice(), getPrice()) == 0
                && getDuration() == that.getDuration()
                && Objects.equals(getName(), that.getName())
                && Objects.equals(getDescription(), that.getDescription())
                && Objects.equals(getTags(), that.getTags())
                && Objects.equals(getCreateDate(), that.getCreateDate())
                && Objects.equals(getLastUpdateDate(), that.getLastUpdateDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getPrice(), getDuration(), getTags(), getCreateDate(), getLastUpdateDate());
    }

    public static GiftCertificateResponse toDto(GiftCertificate certificate) {

        return new GiftCertificateResponse.Builder()
                .setId(certificate.getId())
                .setName(certificate.getName())
                .setDescription(certificate.getDescription())
                .setPrice(certificate.getPrice())
                .setDuration(certificate.getDuration())
                .setLastUpdateDate(certificate.getLastUpdateDate())
                .setCreateDate(certificate.getCreateDate())
                .setTags(certificate.getTags())
                .build();

    }

    public static class Builder {
        private int id;
        private String name;
        private String description;
        private double price;
        private int duration;
        private Set<String> tags;
        private LocalDateTime createDate;
        private LocalDateTime lastUpdateDate;

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

        public Builder setTags(Set<Tag> tags) {
            this.tags = tags.stream().map(Tag::getName).collect(Collectors.toSet());
            return this;
        }

        public Builder setCreateDate(LocalDateTime createDate) {
            this.createDate = createDate;
            return this;
        }

        public Builder setLastUpdateDate(LocalDateTime lastUpdateDate) {
            this.lastUpdateDate = lastUpdateDate;
            return this;
        }

        public GiftCertificateResponse build() {
            return new GiftCertificateResponse(id, name, description, price, duration, tags, createDate, lastUpdateDate);
        }
    }
}
