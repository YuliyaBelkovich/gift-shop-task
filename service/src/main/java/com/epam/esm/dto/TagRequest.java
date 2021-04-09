package com.epam.esm.dto;

import com.epam.esm.models.Tag;

import javax.validation.constraints.Size;
import java.util.Objects;

public class TagRequest {
    @Size(max = 30, message = "Name should be grater than 2 and no longer than 30 symbols")
    private String name;

    public TagRequest() {
    }

    public TagRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TagRequest)) {
            return false;
        }
        TagRequest that = (TagRequest) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    public static TagRequest toDto(Tag tag) {
        return new TagRequest(tag.getName());
    }

    public static Tag toIdentity(TagRequest request) {
        return Tag.builder().setName(request.getName()).build();
    }
}
