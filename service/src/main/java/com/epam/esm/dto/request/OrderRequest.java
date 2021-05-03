package com.epam.esm.dto.request;

import com.epam.esm.models.GiftCertificate;
import com.epam.esm.models.Order;
import com.epam.esm.models.User;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrderRequest {
    @NotNull(message = "Please, provide user id")
    @Min(value = 1, message = "User id can't be less than 1")
    private int userId;
    @NotNull(message = "Please, indicate the certificates to purchase")
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

    public Order toIdentity() {
        return Order.builder()
                .setUser(User.builder().setId(userId).build())
                .setCertificates(giftCertificates.stream()
                        .map(gift -> GiftCertificate.builder()
                                .setId(gift).build()).collect(Collectors.toList())).build();
    }

    public static OrderRequest toDto(Order order) {
        return new OrderRequest(order.getUser().getId(), order.getCertificates().stream().map(GiftCertificate::getId).collect(Collectors.toList()));
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
