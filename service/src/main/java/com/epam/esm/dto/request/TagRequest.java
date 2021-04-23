package com.epam.esm.dto.request;

import com.epam.esm.models.Tag;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class TagRequest {

    @NotNull
    @Size(min = 2, max = 30, message = "Name length should be between 2 and 30 symbols")
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
