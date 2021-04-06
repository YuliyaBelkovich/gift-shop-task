package com.epam.esm.dto;

import com.epam.esm.models.Tag;

public class TagResponse {
    private int id;
    private String name;

    public TagResponse(){}

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

    public static TagResponse toDto(Tag tag){
        return new TagResponse(tag.getId(),tag.getName());
    }
}
