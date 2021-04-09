package com.epam.esm.dto;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagResponseContainer)) return false;
        TagResponseContainer that = (TagResponseContainer) o;
        return Objects.equals(getTagResponseList(), that.getTagResponseList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTagResponseList());
    }

    public void setTagResponseList(List<TagResponse> tagResponseList) {
        this.tagResponseList = tagResponseList;
    }
}
