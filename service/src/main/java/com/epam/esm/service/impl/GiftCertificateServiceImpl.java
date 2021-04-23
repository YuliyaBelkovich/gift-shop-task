package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.TagDaoImpl;
import com.epam.esm.dto.request.GiftCertificateRequest;
import com.epam.esm.dto.response.GiftCertificateResponse;
import com.epam.esm.dto.request.GiftCertificateUpdateRequest;
import com.epam.esm.exception.ExceptionDefinition;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.models.GiftCertificate;
import com.epam.esm.models.Tag;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateDao giftCertificateCrudDao;
    private TagDao tagDao;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateCrudDao, TagDao tagDao) {
        this.giftCertificateCrudDao = giftCertificateCrudDao;
        this.tagDao = tagDao;
    }

    public GiftCertificateResponse findById(int id) {
        GiftCertificate giftCertificate =
                giftCertificateCrudDao.findById(id).orElseThrow(() -> new ServiceException(ExceptionDefinition.IDENTITY_NOT_FOUND));
        return GiftCertificateResponse.toDto(giftCertificate);
    }

    public List<GiftCertificateResponse> findAll(Map<String, String> params) {
        return giftCertificateCrudDao.findAll().stream().map(GiftCertificateResponse::toDto).collect(Collectors.toList());
    }

    public GiftCertificateResponse save(GiftCertificateRequest certificate) {
        GiftCertificate giftCertificate = certificate.toIdentity();
        Set<Tag> tagSet = giftCertificate.getTags();
        tagSet.forEach(tag ->{
            tagSet.remove(tag);
            tagSet.add(tagDao.findByName(tag.getName()).orElse(tag));
        });
        System.out.println(tagSet.toArray()[0]);
        return GiftCertificateResponse.toDto(giftCertificateCrudDao.add(giftCertificate));
    }

    public void update(GiftCertificateUpdateRequest certificate, int id) {
        giftCertificateCrudDao.update(certificate.toIdentity(id));
    }

    public void delete(int id) {
        giftCertificateCrudDao.delete(id);
    }

}
