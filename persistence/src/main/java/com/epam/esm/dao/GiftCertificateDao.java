package com.epam.esm.dao;

import com.epam.esm.models.GiftCertificate;
import com.epam.esm.models.Identifiable;
import com.epam.esm.models.Tag;

import java.util.List;

public interface GiftCertificateDao extends CrudDao<GiftCertificate> {

    List<GiftCertificate> findByTag(Tag tag);

    public void add(GiftCertificate certificate, List<Tag> tags);

    public void update(GiftCertificate certificate, List<Tag> tags);

}
