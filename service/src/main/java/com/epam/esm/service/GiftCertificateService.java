package com.epam.esm.service;

import com.epam.esm.dto.request.GiftCertificateRequest;
import com.epam.esm.dto.response.GiftCertificateResponse;
import com.epam.esm.dto.request.GiftCertificateUpdateRequest;

import java.util.List;
import java.util.Map;

public interface GiftCertificateService {

    GiftCertificateResponse save(GiftCertificateRequest certificate);

    void update(GiftCertificateUpdateRequest certificate, int id);

    GiftCertificateResponse findById(int id);

    List<GiftCertificateResponse> findAll(Map<String, String> params);

    void delete(int id);
}
