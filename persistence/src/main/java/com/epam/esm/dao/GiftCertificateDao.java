package com.epam.esm.dao;

import com.epam.esm.models.GiftCertificate;
import com.epam.esm.models.PageableResponse;
import com.epam.esm.models.Tag;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GiftCertificateDao extends CrudDao<GiftCertificate>{

    PageableResponse<GiftCertificate> findAll(Map<String, String> params, int page, int pageSize);

    void update(GiftCertificate certificate);

    Optional<GiftCertificate> findByName(String name);

}
