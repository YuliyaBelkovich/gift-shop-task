package com.epam.esm.dao;

import com.epam.esm.models.GiftCertificate;
import com.epam.esm.models.Identifiable;
import com.epam.esm.models.Tag;

import java.util.List;

public interface GiftCertificateDao extends CrudDao<GiftCertificate> {

    List<GiftCertificate> findByTag(Tag tag);

    void add(GiftCertificate certificate, List<Tag> tags);

    void update(GiftCertificate certificate, List<Tag> tags);

    List<GiftCertificate> searchByPartOfField(String field, String value);

    List<GiftCertificate> sort(String field, String order);

}
