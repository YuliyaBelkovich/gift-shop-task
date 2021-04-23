package com.epam.esm.dao;

import com.epam.esm.models.GiftCertificate;
import com.epam.esm.models.Tag;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GiftCertificateDao {

    List<GiftCertificate> findAll();

    Optional<GiftCertificate> findById(int id);

    GiftCertificate add(GiftCertificate certificate);

    void update(GiftCertificate certificate);

    void delete(int id);


}
