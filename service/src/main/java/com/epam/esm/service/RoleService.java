package com.epam.esm.service;

import com.epam.esm.models.ERole;
import com.epam.esm.models.Role;

public interface RoleService {

    Role findByName(ERole erole);
}
