package example.drew.carsales.service.impl;

import example.drew.carsales.persistence.entity.Role;
import example.drew.carsales.persistence.repository.RoleRepository;
import example.drew.carsales.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role getByName(String name) {
        return roleRepository.findByName(name).orElse(null);
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }
}
