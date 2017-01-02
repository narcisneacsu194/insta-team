package com.teamtreehouse.instateam.dao;

import com.teamtreehouse.instateam.model.Role;

import java.util.List;

public interface RoleDao {
    List<Role> findAll();
    Role findById(Long id);
    void save(Role role);
    void delete(Role role);
}
