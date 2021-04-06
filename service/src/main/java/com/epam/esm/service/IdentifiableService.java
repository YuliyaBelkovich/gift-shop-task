package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateResponse;


public interface IdentifiableService {
    public GiftCertificateResponse findById(int id);
}
