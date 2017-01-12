package com.teamtreehouse.instateam.model;

import javax.persistence.*;
import javax.persistence.criteria.Fetch;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    private String name;

    @NotNull
    @Size(min = 3, max = 255)
    private String description;

    private String status;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Role> rolesNeeded = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Collaborator> collaboratorsAssigned = new ArrayList<>();

    public Project(){

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Role> getRolesNeeded() {
        return rolesNeeded;
    }

    public void setRolesNeeded(List<Role> rolesNeeded) {
        this.rolesNeeded = rolesNeeded;
    }

    public List<Collaborator> getCollaboratorsAssigned() {
        return collaboratorsAssigned;
    }

    public void setCollaboratorsAssigned(List<Collaborator> collaboratorsAssigned) {
        this.collaboratorsAssigned = collaboratorsAssigned;
    }

    public void addRole(Role role){
        rolesNeeded.add(role);
    }

    public void addCollaborator(Collaborator collaborator){
        collaboratorsAssigned.add(collaborator);
    }

}
