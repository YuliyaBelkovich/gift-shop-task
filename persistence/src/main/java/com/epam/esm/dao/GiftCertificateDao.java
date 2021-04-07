package com.epam.esm.dao;

import com.epam.esm.models.GiftCertificate;
import com.epam.esm.models.Tag;

import java.util.List;
import java.util.Map;

public interface GiftCertificateDao extends CrudDao<GiftCertificate> {

    List<GiftCertificate> findByTag(String tag);

    void add(GiftCertificate certificate, List<Tag> tags);

    void update(GiftCertificate certificate, List<Tag> tags);

    List<GiftCertificate> findWithParams(Map<String, String> params);

}
