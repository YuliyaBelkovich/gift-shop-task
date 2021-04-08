package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateRequest;
import com.epam.esm.dto.GiftCertificateResponse;
import com.epam.esm.dto.GiftCertificateResponseContainer;
import com.epam.esm.exception.IdentityAlreadyExistsException;
import com.epam.esm.exception.IdentityNotFoundException;
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
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateCrudDao,
                                      TagDao tagCrudDao) {
        this.giftCertificateCrudDao = giftCertificateCrudDao;
        this.tagCrudDao = tagCrudDao;
    }

    public GiftCertificateResponse findById(int id) {
        Optional<GiftCertificate> searchResult = giftCertificateCrudDao.findById(id);
        if (searchResult.isPresent()) {
            List<Tag> tagList = tagCrudDao.findByGiftId(searchResult.get().getId());
            return GiftCertificateResponse.toDto(searchResult.get(), tagList);
        } else {
            throw new IdentityNotFoundException(id);
        }
    }

    public GiftCertificateResponseContainer findAll() {
        List<GiftCertificate> searchResult = giftCertificateCrudDao.findAll();
        return new GiftCertificateResponseContainer(searchResult.stream()
                .map(giftCertificate -> GiftCertificateResponse.toDto(giftCertificate, tagCrudDao.findByGiftId(giftCertificate.getId()))).collect(Collectors.toList()));
    }
    public GiftCertificateResponseContainer findAll(Map<String, String> params){
        List<GiftCertificate> searchResult = giftCertificateCrudDao.findWithParams(params);
        return new GiftCertificateResponseContainer(searchResult.stream()
                .map(giftCertificate -> GiftCertificateResponse.toDto(giftCertificate, tagCrudDao.findByGiftId(giftCertificate.getId()))).collect(Collectors.toList()));
    }

    public void save(GiftCertificateRequest certificate) {
        GiftCertificate giftCertificate = certificate.toIdentity(0);
        if(giftCertificateCrudDao.findByName(giftCertificate.getName()).isEmpty()) {
            giftCertificateCrudDao.add(giftCertificate,
                    certificate.getTags().stream().map(tag -> Tag.builder().setName(tag).build()).collect(Collectors.toList()));
        } else {
            throw new IdentityAlreadyExistsException(certificate.toString());
        }
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
