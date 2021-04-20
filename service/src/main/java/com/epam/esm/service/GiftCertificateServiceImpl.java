package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateRequest;
import com.epam.esm.dto.GiftCertificateResponse;
import com.epam.esm.dto.GiftCertificateUpdateRequest;
import com.epam.esm.exception.ExceptionDefinition;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.models.GiftCertificate;
import com.epam.esm.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateDao giftCertificateCrudDao;
    private TagDao tagCrudDao;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateCrudDao, TagDao tagCrudDao) {
        this.giftCertificateCrudDao = giftCertificateCrudDao;
        this.tagCrudDao = tagCrudDao;
    }

    public GiftCertificateResponse findById(int id) {
        GiftCertificate giftCertificate =
                giftCertificateCrudDao.findById(id).orElseThrow(() -> new ServiceException(ExceptionDefinition.IDENTITY_NOT_FOUND));
        List<Tag> tagList = tagCrudDao.findByGiftId(id);
        return GiftCertificateResponse.toDto(giftCertificate, tagList);
    }

    public List<GiftCertificateResponse> findAll(Map<String, String> params) {
        if (params.isEmpty()) {
            return giftCertificateCrudDao.findAll().stream()
                    .map(giftCertificate -> GiftCertificateResponse.toDto(giftCertificate,
                            tagCrudDao.findByGiftId(giftCertificate.getId()))).collect(Collectors.toList());
        } else {
            return giftCertificateCrudDao.findWithParams(params).stream()
                    .map(giftCertificate -> GiftCertificateResponse.toDto(giftCertificate,
                            tagCrudDao.findByGiftId(giftCertificate.getId()))).collect(Collectors.toList());
        }
    }

    public GiftCertificateResponse save(GiftCertificateRequest certificate) {
        GiftCertificate giftCertificate = certificate.toIdentity(0);
        giftCertificateCrudDao.findByName(giftCertificate.getName()).ifPresent(giftCertificate1 -> {
            throw new ServiceException(ExceptionDefinition.IDENTITY_ALREADY_EXISTS);
        });
        giftCertificateCrudDao.add(giftCertificate,
                certificate.getTags().stream().map(tag -> Tag.builder().setName(tag).build()).collect(Collectors.toList()));
        return findById(giftCertificate.getId());
    }


    public void update(GiftCertificateUpdateRequest certificate, int id) {
        GiftCertificate giftCertificate = certificate.toIdentity(id);
        Optional.ofNullable(certificate.getTags()).ifPresentOrElse(tags ->
                        giftCertificateCrudDao.update(giftCertificate,
                                tags.stream().map(tag -> Tag.builder().setName(tag).build()).collect(Collectors.toList())),
                () -> giftCertificateCrudDao.update(giftCertificate));
    }

    public void delete(int id) {
        giftCertificateCrudDao.delete(id);
    }
}
