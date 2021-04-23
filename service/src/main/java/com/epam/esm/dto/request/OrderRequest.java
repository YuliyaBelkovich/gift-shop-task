package com.epam.esm.dto.request;

import java.util.List;
import java.util.Objects;

public class OrderRequest {

    private int userId;
    private List<Integer> giftCertificates;

    public OrderRequest() {
    }

    public OrderRequest(int userId, List<Integer> giftCertificates) {
        this.userId = userId;
        this.giftCertificates = giftCertificates;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Integer> getGiftCertificates() {
        return giftCertificates;
    }

    public void setGiftCertificates(List<Integer> giftCertificates) {
        this.giftCertificates = giftCertificates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderRequest)) {
            return false;
        }
        OrderRequest that = (OrderRequest) o;
        return getUserId() == that.getUserId() && Objects.equals(getGiftCertificates(), that.getGiftCertificates());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getGiftCertificates());
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "userId=" + userId +
                ", giftCertificates=" + giftCertificates +
                '}';
    }
}
