package com.epam.esm.models;

import java.util.List;

public class PageableResponse<T extends Object>{

    private List<T> responses;
    private int currentPage;
    private int lastPage;
    private int pageSize;

    public PageableResponse() {
    }

    public PageableResponse(List<T> responses, int currentPage, int lastPage, int pageSize) {
        this.responses = responses;
        this.currentPage = currentPage;
        this.lastPage = lastPage;
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getResponses() {
        return responses;
    }

    public void setResponses(List<T> responses) {
        this.responses = responses;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }
}
