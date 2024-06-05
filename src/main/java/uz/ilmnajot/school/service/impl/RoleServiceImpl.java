package uz.ilmnajot.school.service.impl;

import org.springframework.stereotype.Service;
import uz.ilmnajot.school.repository.RoleRepository;
import uz.ilmnajot.school.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
}
