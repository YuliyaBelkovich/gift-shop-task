package com.epam.esm.service;

import com.epam.esm.dto.ResponseGiftCertificate;


public interface IdentifiableService {
    public ResponseGiftCertificate findById(int id);
}
