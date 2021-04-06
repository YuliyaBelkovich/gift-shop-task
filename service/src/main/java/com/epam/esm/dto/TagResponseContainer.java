package com.epam.esm.dto;

import java.util.List;

public class TagResponseContainer {

    private List<TagResponse> tagResponseList;

    public TagResponseContainer() {
    }

    public TagResponseContainer(List<TagResponse> tagResponseList) {
        this.tagResponseList = tagResponseList;
    }

    public List<TagResponse> getTagResponseList() {
        return tagResponseList;
    }

    public void setTagResponseList(List<TagResponse> tagResponseList) {
        this.tagResponseList = tagResponseList;
    }
}
