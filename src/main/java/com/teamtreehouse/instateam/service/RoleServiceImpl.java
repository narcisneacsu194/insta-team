package com.teamtreehouse.instateam.service;

import com.teamtreehouse.instateam.dao.RoleDao;
import com.teamtreehouse.instateam.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
// This service class implements the RoleService interface.
// The methods are named exactly after the ones used in the DAO implementation classes.
// These ones just call their counterparts from the DAO implementation classes.
// The service classes constitute a layer between the controller and the DAO layer.
// The DAO is a layer between the Spring application and the database.
@Service
public class RoleServiceImpl implements RoleService{

    // The RoleDao object is autowired/injected at runtime.
    @Autowired
    private RoleDao roleDao;

    // This method retrieves a list with all the available role objects from the database.
    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    // This method retrieves a specific role object from the database, using an identifier.
    @Override
    public Role findById(Long id) {
        return roleDao.findById(id);
    }

    // This method saves a new role object or updates an existing one from the database.
    @Override
    public void save(Role role) {
        roleDao.save(role);
    }

    // This method deletes an existing role object from the database.
    @Override
    public void delete(Role role) {
        roleDao.delete(role);
    }
}
