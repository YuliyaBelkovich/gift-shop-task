package com.epam.esm.dao;

import com.epam.esm.models.GiftCertificate;
import com.epam.esm.models.Tag;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GiftCertificateDao extends CrudDao<GiftCertificate>{

    List<GiftCertificate> findAll(Map<String, String> params);

    void update(GiftCertificate certificate);

    Optional<GiftCertificate> findByName(String name);

}
