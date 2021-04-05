package com.epam.esm.dto;

import java.util.List;

public class ResponseGiftCertificateContainer {
    private List<ResponseGiftCertificate> certificateList;

    public ResponseGiftCertificateContainer(List<ResponseGiftCertificate> certificateList) {
        this.certificateList = certificateList;
    }

    public List<ResponseGiftCertificate> getCertificateList() {
        return certificateList;
    }

    public void setCertificateList(List<ResponseGiftCertificate> certificateList) {
        this.certificateList = certificateList;
    }
}
