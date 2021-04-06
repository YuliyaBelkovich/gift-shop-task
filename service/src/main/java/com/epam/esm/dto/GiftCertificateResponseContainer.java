package com.epam.esm.dto;

import java.util.List;

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
}
