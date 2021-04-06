package com.epam.esm.dto;

import com.epam.esm.models.Tag;

public class TagRequest {

    private String name;

    public TagRequest(){}

    public TagRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Tag toIdentity(TagRequest request) {
        return Tag.builder().setName(request.getName()).build();
    }
}
