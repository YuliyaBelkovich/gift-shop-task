package com.epam.esm.dto.request;

import java.util.Objects;

public class CertificateTagUpdateRequest {

    private String operation;
    private String name;

    public CertificateTagUpdateRequest() {
    }

    public CertificateTagUpdateRequest(String operation, String name) {
        this.operation = operation;
        this.name = name;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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
        if (!(o instanceof CertificateTagUpdateRequest)) {
            return false;
        }
        CertificateTagUpdateRequest that = (CertificateTagUpdateRequest) o;
        return Objects.equals(getOperation(), that.getOperation()) && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOperation(), getName());
    }

    @Override
    public String toString() {
        return "CertificateTagUpdateRequest{" +
                "operation='" + operation + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
