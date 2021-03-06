package com.teamtreehouse.instateam.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
// This pojo entity has variable, getters and setters that describe a Role.
// Basic validation is present for the name and description fields.
// It has a One to many relationship with the Collaborator entity.
// It also has a ManyToMany relationship with the Project entity.
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 32)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role", fetch = FetchType.LAZY)
    private List<Collaborator> collaborators = new ArrayList<>();

    @ManyToMany(mappedBy = "rolesNeeded", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Project> projects;

    public Role(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Collaborator> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<Collaborator> collaborators) {
        this.collaborators = collaborators;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (id != role.id) return false;
        return name != null ? name.equals(role.name) : role.name == null;

    }

}
