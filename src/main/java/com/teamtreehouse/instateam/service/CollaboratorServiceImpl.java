package com.teamtreehouse.instateam.service;

import com.teamtreehouse.instateam.dao.CollaboratorDao;
import com.teamtreehouse.instateam.model.Collaborator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
// This service class implements the CollaboratorService interface
// The methods are named exactly after the ones used in the DAO implementation classes.
// These ones just call their counterparts from the DAO implementation classes.
// The service classes constitute a layer between the controller and the DAO layer.
// The DAO is a layer between the Spring application and the database.
@Service
public class CollaboratorServiceImpl implements CollaboratorService{

    // The collaboratorDao object is autowired/injected at runtime.
    @Autowired
    private CollaboratorDao collaboratorDao;

    // This method retrieves a list with all the available collaborators from the database.
    @Override
    public List<Collaborator> findAll() {
        return collaboratorDao.findAll();
    }

    // This method retrieves a a specific collaborator object from the database.
    @Override
    public Collaborator findById(Long id) {
        return collaboratorDao.findById(id);
    }

    // This method saves a new collaborator object or updates an existing one from the database.
    @Override
    public void save(Collaborator collaborator) {
        collaboratorDao.save(collaborator);
    }

    // This method deletes an existing collaborator from the database.
    @Override
    public void delete(Collaborator collaborator) {
        collaboratorDao.delete(collaborator);
    }
}
