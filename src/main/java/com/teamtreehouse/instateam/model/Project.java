package com.teamtreehouse.instateam.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
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

    private Date dateCreated;

    @ManyToMany
    private List<Role> rolesNeeded = new ArrayList<>();

    @ManyToMany(targetEntity = Collaborator.class)
    @JoinTable(name = "project_collaborator", joinColumns = {@JoinColumn(name = "project_id")},
            inverseJoinColumns = {@JoinColumn(name = "collaboratorsassigned_id")})
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
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
