package com.epam.esm.dto;

import java.util.List;
import java.util.Objects;

public class GiftCertificateResponseContainer {
    private List<GiftCertificateResponse> certificateList;

    public GiftCertificateResponseContainer(List<GiftCertificateResponse> certificateList) {
        this.certificateList = certificateList;
    }

    public List<GiftCertificateResponse> getCertificateList() {
        return certificateList;
    }

    public void setCertificateList(List<GiftCertificateResponse> certificateList) {
        this.certificateList = certificateList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GiftCertificateResponseContainer)) return false;
        GiftCertificateResponseContainer that = (GiftCertificateResponseContainer) o;
        return Objects.equals(getCertificateList(), that.getCertificateList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCertificateList());
    }
}
