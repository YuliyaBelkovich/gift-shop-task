package com.epam.esm.dto;

import com.epam.esm.models.Tag;

import java.util.Objects;

public class TagResponse {

    private int id;
    private String name;

    public TagResponse() {
    }

    public TagResponse(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Builder builder() {
        return new Builder();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TagResponse)) {
            return false;
        }
        TagResponse that = (TagResponse) o;
        return getId() == that.getId() && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    public static TagResponse toDto(Tag tag) {
        return new TagResponse(tag.getId(), tag.getName());
    }

    public static class Builder {
        private int id;
        private String name;


        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public TagResponse build() {
            return new TagResponse(id, name);
        }
    }
}
