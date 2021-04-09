package com.epam.esm.dto;

import com.epam.esm.models.Tag;

import javax.validation.constraints.Size;

public class TagRequest {
    @Size(min = 0, max = 30)
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

    public static TagRequest toDto(Tag tag){
        return new TagRequest(tag.getName());
    }

    public static Tag toIdentity(TagRequest request) {
        return Tag.builder().setName(request.getName()).build();
    }
}
