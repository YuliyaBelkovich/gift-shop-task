package com.epam.esm.service;

import com.epam.esm.dto.request.GiftCertificateRequest;
import com.epam.esm.dto.response.GiftCertificateResponse;
import com.epam.esm.dto.request.GiftCertificateUpdateRequest;
import com.epam.esm.models.PageableResponse;

import java.util.Map;

public interface GiftCertificateService {

    GiftCertificateResponse save(GiftCertificateRequest certificate);

    void update(GiftCertificateUpdateRequest certificate, int id);

    GiftCertificateResponse findById(int id);

    PageableResponse<GiftCertificateResponse> findAll(Map<String, String> params, int page, int pageSize);

    void delete(int id);
}
