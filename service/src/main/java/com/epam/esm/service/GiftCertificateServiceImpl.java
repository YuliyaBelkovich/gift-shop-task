package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateRequest;
import com.epam.esm.dto.GiftCertificateResponse;
import com.epam.esm.dto.GiftCertificateResponseContainer;
import com.epam.esm.exception.ExceptionManager;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.models.GiftCertificate;
import com.epam.esm.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateDao giftCertificateCrudDao;
    private TagDao tagCrudDao;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateCrudDao,
                                      TagDao tagCrudDao) {
        this.giftCertificateCrudDao = giftCertificateCrudDao;
        this.tagCrudDao = tagCrudDao;
    }

    public GiftCertificateResponse findById(int id) {
        GiftCertificate giftCertificate = giftCertificateCrudDao.findById(id).orElseThrow(() -> new ServiceException(ExceptionManager.IDENTITY_NOT_FOUND));
        List<Tag> tagList = tagCrudDao.findByGiftId(id);
        return GiftCertificateResponse.toDto(giftCertificate, tagList);
    }

    public GiftCertificateResponseContainer findAll() {
        List<GiftCertificate> searchResult = giftCertificateCrudDao.findAll();
        return new GiftCertificateResponseContainer(searchResult.stream()
                .map(giftCertificate -> GiftCertificateResponse.toDto(giftCertificate, tagCrudDao.findByGiftId(giftCertificate.getId()))).collect(Collectors.toList()));
    }

    public GiftCertificateResponseContainer findAll(Map<String, String> params) {
        List<GiftCertificate> searchResult = giftCertificateCrudDao.findWithParams(params);
        return new GiftCertificateResponseContainer(searchResult.stream()
                .map(giftCertificate -> GiftCertificateResponse.toDto(giftCertificate, tagCrudDao.findByGiftId(giftCertificate.getId()))).collect(Collectors.toList()));
    }

    public void save(@Valid GiftCertificateRequest certificate) {
        GiftCertificate giftCertificate = certificate.toIdentity(0);
        if (giftCertificateCrudDao.findByName(giftCertificate.getName()).isEmpty()) {
            giftCertificateCrudDao.add(giftCertificate,
                    certificate.getTags().stream().map(tag -> Tag.builder().setName(tag).build()).collect(Collectors.toList()));
        } else {
            throw new ServiceException(ExceptionManager.IDENTITY_ALREADY_EXISTS);
        }
    }

    public GiftCertificateResponseContainer findByTag(String tag) {
        List<GiftCertificate> searchResult = giftCertificateCrudDao.findByTag(tag);
        return new GiftCertificateResponseContainer(searchResult.stream()
                .map(giftCertificate -> GiftCertificateResponse.toDto(giftCertificate, tagCrudDao.findByGiftId(giftCertificate.getId()))).collect(Collectors.toList()));
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
