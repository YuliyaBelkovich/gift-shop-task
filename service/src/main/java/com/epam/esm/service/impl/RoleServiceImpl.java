package com.epam.esm.service.impl;

import com.epam.esm.dao.RoleDao;
import com.epam.esm.exception.ExceptionDefinition;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.models.ERole;
import com.epam.esm.models.Role;
import com.epam.esm.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role findByName(ERole erole) {
        return roleDao.findByName(erole.name()).orElseThrow(() -> new ServiceException(ExceptionDefinition.IDENTITY_NOT_FOUND));
    }
}
