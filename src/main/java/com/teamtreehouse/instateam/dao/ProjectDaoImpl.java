package com.teamtreehouse.instateam.dao;

import com.teamtreehouse.instateam.model.Project;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
// This repository class extends the GenericDaoImpl class, and implements the ProjectDao interface.
@Repository
public class ProjectDaoImpl extends GenericDaoImpl<Project>
                            implements ProjectDao{

// The findById is overridden here because the Project entity has to initialize it's role and collaborator lists
// to prevent data fetching errors/exceptions.
    @Override
    public Project findById(Long projectId){
        Session session = sessionFactory.openSession();
        Project project = session.get(Project.class, projectId);
        Hibernate.initialize(project.getRolesNeeded());
        Hibernate.initialize(project.getCollaboratorsAssigned());
        session.close();
        return project;
    }

}
