package example.drew.carsales.service;

import example.drew.carsales.persistence.entity.Role;

import java.util.List;

public interface RoleService {

    void save(Role role);
    Role getByName(String name);
    List<Role> getAll();

}
