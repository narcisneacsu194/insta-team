package com.teamtreehouse.instateam.dao;

import com.teamtreehouse.instateam.model.Role;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDaoImpl extends GenericDaoImpl<Role>
                        implements RoleDao{

    @Override
    public Role findById(Long roleId){
        Session session = sessionFactory.openSession();
        Role role = session.get(Role.class, roleId);
        Hibernate.initialize(role.getCollaborators());
        session.close();
        return role;
    }

}
