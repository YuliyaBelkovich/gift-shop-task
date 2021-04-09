package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateRequest;
import com.epam.esm.dto.GiftCertificateResponse;

import java.util.List;
import java.util.Map;

public interface GiftCertificateService {
    List<GiftCertificateResponse> findAll();

    GiftCertificateResponse save(GiftCertificateRequest certificate);

    void update(GiftCertificateRequest certificate, int id);

    void delete(int id);

    GiftCertificateResponse findById(int id);

    List<GiftCertificateResponse> findAll(Map<String, String> params);
}
