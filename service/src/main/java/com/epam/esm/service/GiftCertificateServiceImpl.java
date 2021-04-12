package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateRequest;
import com.epam.esm.dto.GiftCertificateResponse;
import com.epam.esm.exception.ExceptionManager;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.models.GiftCertificate;
import com.epam.esm.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
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
                giftCertificateCrudDao.findById(id).orElseThrow(() -> new ServiceException(ExceptionManager.IDENTITY_NOT_FOUND));
        List<Tag> tagList = tagCrudDao.findByGiftId(id);
        return GiftCertificateResponse.toDto(giftCertificate, tagList);
    }

    public List<GiftCertificateResponse> findAll() {
        return giftCertificateCrudDao.findAll().stream()
                .map(giftCertificate -> GiftCertificateResponse.toDto(giftCertificate,
                        tagCrudDao.findByGiftId(giftCertificate.getId()))).collect(Collectors.toList());
    }

    public List<GiftCertificateResponse> findAll(Map<String, String> params) {
        return giftCertificateCrudDao.findWithParams(params).stream()
                .map(giftCertificate -> GiftCertificateResponse.toDto(giftCertificate,
                        tagCrudDao.findByGiftId(giftCertificate.getId()))).collect(Collectors.toList());
    }

    public GiftCertificateResponse save(GiftCertificateRequest certificate) {
        validateSave(certificate);
        GiftCertificate giftCertificate = certificate.toIdentity(0);
        if (giftCertificateCrudDao.findByName(giftCertificate.getName()).isEmpty()) {
            giftCertificateCrudDao.add(giftCertificate,
                    certificate.getTags().stream().map(tag -> Tag.builder().setName(tag).build()).collect(Collectors.toList()));
        } else {
            throw new ServiceException(ExceptionManager.IDENTITY_ALREADY_EXISTS);
        }
        return findById(giftCertificate.getId());
    }


    public void update(GiftCertificateRequest certificate, int id) {
        validateUpdate(certificate);
        GiftCertificate giftCertificate = certificate.toIdentity(id);
        Optional.ofNullable(certificate.getTags()).ifPresentOrElse(tags ->
                giftCertificateCrudDao.update(giftCertificate,
                        tags.stream().map(tag -> Tag.builder().setName(tag).build()).collect(Collectors.toList())),
                () -> giftCertificateCrudDao.update(giftCertificate));
    }

    public void delete(int id) {
        giftCertificateCrudDao.delete(id);
    }


    public void validateSave(GiftCertificateRequest request) {
        String message = "";
        boolean hasErrors = false;
        if (request.getName().isEmpty()) {
            message += "name can not be blank; ";
            hasErrors = true;
        }
        if (request.getName().length() >= 29) {
            message += "name length should be less than 30 symbols; ";
            hasErrors = true;
        }
        if (request.getDescription().isEmpty()) {
            message += "description can not be blank; ";
            hasErrors = true;
        }
        if (request.getDescription().length() >= 999) {
            message += "description length should be less than 1000 symbols; ";
            hasErrors = true;
        }
        if (request.getPrice() <= 0) {
            message += "price can not be less than 0; ";
            hasErrors = true;
        }
        if (request.getDuration() <= 1) {
            message += "duration can not be less than 1 day; ";
            hasErrors = true;
        }
        if (hasErrors) {
            throw new ValidationException(message);
        }
    }

    public void validateUpdate(GiftCertificateRequest request) {
        String message = "";
        boolean hasErrors = false;
        if (Optional.ofNullable(request.getName()).isPresent()) {
            if (request.getName().length() >= 29) {
                message += "name length should be less than 30 symbols; ";
                hasErrors = true;
            }
        }
        if (Optional.ofNullable(request.getDescription()).isPresent()) {
            if (request.getDescription().length() >= 999) {
                message += "description length should be less than 1000 symbols; ";
                hasErrors = true;
            }
        }
        if (request.getPrice() < 0) {
            message += "price can not be less than 0; ";
            hasErrors = true;
        }
        if (request.getDuration() < 0) {
            message += "duration can not be less than 1 day; ";
            hasErrors = true;
        }
        if (hasErrors) {
            throw new ValidationException(message);
        }
    }
}
