package com.teamtreehouse.instateam.dao;

import com.teamtreehouse.instateam.model.Project;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectDaoImpl extends GenericDaoImpl<Project>
                            implements ProjectDao{

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
