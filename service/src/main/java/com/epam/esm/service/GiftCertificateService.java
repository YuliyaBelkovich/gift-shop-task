package com.epam.esm.service;

import com.epam.esm.dao.CrudDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.RequestGiftCertificate;
import com.epam.esm.dto.ResponseGiftCertificate;
import com.epam.esm.dto.ResponseGiftCertificateContainer;
import com.epam.esm.models.GiftCertificate;
import com.epam.esm.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class GiftCertificateService implements IdentifiableService {

    private CrudDao<GiftCertificate> giftCertificateCrudDao;
    private CrudDao<Tag> tagCrudDao;

    @Autowired
    public GiftCertificateService(@Qualifier("giftCertificateDao") CrudDao<GiftCertificate> giftCertificateCrudDao,
                                  @Qualifier("tagDao") CrudDao<Tag> tagCrudDao) {
        this.giftCertificateCrudDao = giftCertificateCrudDao;
        this.tagCrudDao = tagCrudDao;
    }

    public ResponseGiftCertificate findById(int id) {
        Optional<GiftCertificate> searchResult = giftCertificateCrudDao.findById(id);
        if (searchResult.isPresent()) {
            List<Tag> tagList = ((TagDao) tagCrudDao).findByGiftId(searchResult.get().getId());
            return ResponseGiftCertificate.toDto(searchResult.get(), tagList);
        } else {
            return null;
        }
    }

    public ResponseGiftCertificateContainer findAll() {
        List<GiftCertificate> searchResult = giftCertificateCrudDao.findAll();
        return new ResponseGiftCertificateContainer(searchResult.stream()
                .map(giftCertificate -> ResponseGiftCertificate.toDto(giftCertificate, ((TagDao) tagCrudDao).findByGiftId(giftCertificate.getId()))).collect(Collectors.toList()));
    }

    public void save(RequestGiftCertificate certificate) {
        GiftCertificate giftCertificate = certificate.toIdentity();
        giftCertificateCrudDao.add(giftCertificate,
                certificate.getTags().stream().map(tag -> Tag.builder().setName(tag).build()).collect(Collectors.toList()));
    }

    public void update(RequestGiftCertificate certificate) {
        GiftCertificate giftCertificate = certificate.toIdentity();
        giftCertificateCrudDao.update(giftCertificate,
                certificate.getTags().stream().map(tag -> Tag.builder().setName(tag).build()).collect(Collectors.toList()));
    }
}
