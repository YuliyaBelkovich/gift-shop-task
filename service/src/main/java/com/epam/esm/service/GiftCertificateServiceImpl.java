package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.TagDaoImpl;
import com.epam.esm.dto.GiftCertificateRequest;
import com.epam.esm.dto.GiftCertificateResponse;
import com.epam.esm.dto.GiftCertificateResponseContainer;
import com.epam.esm.models.GiftCertificate;
import com.epam.esm.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateDao giftCertificateCrudDao;
    private TagDao tagCrudDao;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateCrudDao,
                                      TagDao tagCrudDao) {
        this.giftCertificateCrudDao = giftCertificateCrudDao;
        this.tagCrudDao = tagCrudDao;
    }

    public GiftCertificateResponseContainer searchByField(String field, String value) {
        List<GiftCertificate> searchResult = giftCertificateCrudDao.searchByPartOfField(field, value);
        return new GiftCertificateResponseContainer(searchResult.stream()
                .map(giftCertificate -> GiftCertificateResponse.toDto(giftCertificate, tagCrudDao.findByGiftId(giftCertificate.getId()))).collect(Collectors.toList()));
    }

    public GiftCertificateResponseContainer sort(String field, String order) {
        List<GiftCertificate> searchResult = giftCertificateCrudDao.sort(field, order);
        return new GiftCertificateResponseContainer(searchResult.stream()
                .map(giftCertificate -> GiftCertificateResponse.toDto(giftCertificate, tagCrudDao.findByGiftId(giftCertificate.getId()))).collect(Collectors.toList()));
    }

    public GiftCertificateResponse findById(int id) {
        Optional<GiftCertificate> searchResult = giftCertificateCrudDao.findById(id);
        if (searchResult.isPresent()) {
            List<Tag> tagList = tagCrudDao.findByGiftId(searchResult.get().getId());
            return GiftCertificateResponse.toDto(searchResult.get(), tagList);
        } else {
            return null;
        }
    }

    public GiftCertificateResponseContainer findAll() {
        List<GiftCertificate> searchResult = giftCertificateCrudDao.findAll();
        return new GiftCertificateResponseContainer(searchResult.stream()
                .map(giftCertificate -> GiftCertificateResponse.toDto(giftCertificate, ((TagDaoImpl) tagCrudDao).findByGiftId(giftCertificate.getId()))).collect(Collectors.toList()));
    }

    public void save(GiftCertificateRequest certificate) {
        GiftCertificate giftCertificate = certificate.toIdentity(0);
        giftCertificateCrudDao.add(giftCertificate,
                certificate.getTags().stream().map(tag -> Tag.builder().setName(tag).build()).collect(Collectors.toList()));
    }

    public void update(GiftCertificateRequest certificate, int id) {
        GiftCertificate giftCertificate = certificate.toIdentity(id);
        giftCertificateCrudDao.update(giftCertificate,
                certificate.getTags().stream().map(tag -> Tag.builder().setName(tag).build()).collect(Collectors.toList()));
    }

    public void delete(int id) {
        giftCertificateCrudDao.delete(id);
    }
}
