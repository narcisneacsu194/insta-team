package com.teamtreehouse.instateam.dao;

import com.teamtreehouse.instateam.model.Role;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
// This repository class extends the GenericDaoImpl class, and implements the RoleDao interface.
@Repository
public class RoleDaoImpl extends GenericDaoImpl<Role>
                        implements RoleDao{

    // The findById is overridden here because the Role entity has to initialize it's collaborator list
    // to prevent data fetching errors/exceptions.
    @Override
    public Role findById(Long roleId){
        Session session = sessionFactory.openSession();
        Role role = session.get(Role.class, roleId);
        Hibernate.initialize(role.getCollaborators());
        session.close();
        return role;
    }

}
