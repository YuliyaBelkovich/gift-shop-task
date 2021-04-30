package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.request.GiftCertificateRequest;
import com.epam.esm.dto.response.GiftCertificateResponse;
import com.epam.esm.dto.request.GiftCertificateUpdateRequest;
import com.epam.esm.exception.ExceptionDefinition;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.models.GiftCertificate;
import com.epam.esm.models.PageableResponse;
import com.epam.esm.models.Tag;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;
    private final TagDao tagDao;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateCrudDao, TagDao tagDao) {
        this.giftCertificateDao = giftCertificateCrudDao;
        this.tagDao = tagDao;
    }

    public GiftCertificateResponse findById(int id) {
        GiftCertificate giftCertificate =
                giftCertificateDao.findById(id).orElseThrow(() -> new ServiceException(ExceptionDefinition.IDENTITY_NOT_FOUND));
        return GiftCertificateResponse.toDto(giftCertificate);
    }

    public PageableResponse<GiftCertificateResponse> findAll(Map<String, String> params, int page, int pageSize) {
        PageableResponse<GiftCertificate> certificates = giftCertificateDao.findAll(params, page, pageSize);
        List<GiftCertificateResponse> responses  = certificates.getResponses().stream().map(GiftCertificateResponse::toDto).collect(Collectors.toList());
        return new PageableResponse<>(responses, certificates.getCurrentPage(), certificates.getLastPage(), certificates.getPageSize());
    }

    public GiftCertificateResponse save(GiftCertificateRequest certificate) {
        tagDao.findByName(certificate.getName()).ifPresent(gift -> {
            throw new ServiceException(ExceptionDefinition.IDENTITY_ALREADY_EXISTS);
        });
        GiftCertificate giftCertificate = certificate.toIdentity();
        Set<Tag> tagSet = giftCertificate.getTags().stream().map(tag -> tagDao.findByName(tag.getName()).orElse(tag)).collect(Collectors.toSet());
        giftCertificate.setTags(tagSet);
        giftCertificateDao.add(giftCertificate);
        return GiftCertificateResponse.toDto(giftCertificateDao.findById(giftCertificate.getId()).orElseThrow(() -> new ServiceException(ExceptionDefinition.IDENTITY_NOT_FOUND)));
    }

    public void update(GiftCertificateUpdateRequest certificate, int id) {
        giftCertificateDao.update(prepareForUpdate(certificate, id));
    }

    private GiftCertificate prepareForUpdate(GiftCertificateUpdateRequest certificate, int id) {
        GiftCertificate certificateToUpdate = giftCertificateDao.findById(id).orElseThrow(() -> new ServiceException(ExceptionDefinition.IDENTITY_NOT_FOUND));
        if (certificate.getName() != null) {
            certificateToUpdate.setName(certificate.getName());
        }
        if (certificate.getDescription() != null) {
            certificateToUpdate.setDescription(certificate.getDescription());
        }
        if (certificate.getDuration() != 0) {
            certificateToUpdate.setDuration(certificate.getDuration());
        }
        if (certificate.getPrice() != 0) {
            certificateToUpdate.setPrice(certificate.getPrice());
        }
        if (certificate.getTags() != null) {
            if (certificate.getTags().stream().filter(tag -> String.valueOf(tag.charAt(0)).equals("+") || String.valueOf(tag.charAt(0)).equals("-")).collect(Collectors.toSet()).size() == certificate.getTags().size()) {
                Set<Tag> newTags = certificateToUpdate.getTags().stream().filter(tag ->
                        certificate.getTags().stream().filter(tag1 -> tag1.substring(1).equals(tag.getName()) && String.valueOf(tag1.charAt(0)).equals("-")).findAny().isEmpty()
                ).collect(Collectors.toSet());
                newTags = Stream.concat(certificate.getTags().stream().filter(tag -> String.valueOf(tag.charAt(0)).equals("+")).map(tag -> {
                    tag = tag.substring(1);
                    return tagDao.findByName(tag).orElse(Tag.builder().setName(tag).build());

                }), newTags.stream()).collect(Collectors.toSet());
                certificateToUpdate.setTags(newTags);
                certificateToUpdate.setLastUpdateDate(LocalDateTime.now());
                return certificateToUpdate;
            } else {
                throw new ServiceException(ExceptionDefinition.TAG_UPDATE_OPERATION_NOT_SPECIFIED);
            }
        }
        return certificateToUpdate;
    }

    public void delete(int id) {
        giftCertificateDao.delete(giftCertificateDao.findById(id).orElseThrow(() -> new ServiceException(ExceptionDefinition.IDENTITY_NOT_FOUND)));
    }

}
