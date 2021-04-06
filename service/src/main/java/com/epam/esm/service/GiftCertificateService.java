package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateRequest;
import com.epam.esm.dto.GiftCertificateResponse;
import com.epam.esm.dto.GiftCertificateResponseContainer;

public interface GiftCertificateService {
    GiftCertificateResponseContainer findAll();

    void save(GiftCertificateRequest certificate);

    void update(GiftCertificateRequest certificate, int id);

    void delete(int id);

    GiftCertificateResponse findById(int id);

    GiftCertificateResponseContainer searchByField(String field, String value);

    GiftCertificateResponseContainer sort(String field, String order);
}
