package com.teamtreehouse.instateam.web.controller;

import com.teamtreehouse.instateam.model.Collaborator;
import com.teamtreehouse.instateam.model.Project;
import com.teamtreehouse.instateam.model.Role;
import com.teamtreehouse.instateam.service.CollaboratorService;
import com.teamtreehouse.instateam.service.ProjectService;
import com.teamtreehouse.instateam.service.RoleService;
import com.teamtreehouse.instateam.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
// This is the Project entity controller. It has methods that receive HTTP POST and GET requests from the view,
// in order to store or retrieve information into or from the database.
// The data is stored/retrieved using the ProjectService, RoleService, and
// CollaboratorService interfaces. The concepts used here are dependency injection
// with autowiring. This means that the Spring application is passing to the ProjectService,
// RoleService and CollaboratorService a ProjectServiceImpl, RoleServiceImpl and
// CollaboratorServiceImpl object at runtime.
@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CollaboratorService collaboratorService;

    // This method receives an HTTP GET request for listing all the available projects stored in the database.
    @RequestMapping("/")
    public String listProjects(Model model){
        model.addAttribute("projects", projectService.findAll());
        return "project/index";
    }

    // This method maps the positions in a list of the collaborators in regard of the needed roles.
    private List<Collaborator> getCollaboratorListThatHasASizeEqualToTheRoleListSize(Project project){
        List<Collaborator> collaboratorListThatHasASizeEqualToTheRoleListSize = new ArrayList<>();
        boolean isRoleIdEqualToTheCollaboratorRoleId;

        for(Role role : project.getRolesNeeded()){
            isRoleIdEqualToTheCollaboratorRoleId = false;

            for(Collaborator collaborator : project.getCollaboratorsAssigned()){
                if(collaborator.getRole().getId().equals(role.getId())){
                    isRoleIdEqualToTheCollaboratorRoleId = true;

                    collaboratorListThatHasASizeEqualToTheRoleListSize.add(collaborator);

                    break;
                }
            }

            if(!isRoleIdEqualToTheCollaboratorRoleId){
                collaboratorListThatHasASizeEqualToTheRoleListSize.add(null);
            }
        }

        return collaboratorListThatHasASizeEqualToTheRoleListSize;
    }

    // This method receives an HTTP GET request for displaying a detailed page of a specified project from
    // the database.
    @RequestMapping("/projects/{projectId}/detail")
    public String projectDetails(@PathVariable Long projectId, Model model){
        Project project = projectService.findById(projectId);
        model.addAttribute("project", project);
        model.addAttribute("roles", project.getRolesNeeded());
        model.addAttribute("collaborators", getCollaboratorListThatHasASizeEqualToTheRoleListSize(project));
        return "project/project_detail";
    }

    // This method receives an HTTP GET request for displaying a form to add a new project.
    @RequestMapping(value = "/project-form")
    public String projectAddForm(Model model){
        if(!model.containsAttribute("project")){
            model.addAttribute("project", new Project());
        }

        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("action", "add-project");
        return "project/edit_project";
    }

    // This method receives an HTTP POST request from the client. It tries to add a new project in the database using
    // the passed information. If the information passed in the form is valid, a positive flash message appears, saying that the project
    // has been successfully added. Otherwise, an error flash message pops up, saying that some or all the information entered is incorrect.
    @RequestMapping(value = "/add-project", method = RequestMethod.POST)
    public String addProject(@Valid Project project, BindingResult result, RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("The project information is invalid. Please try again.",
                    FlashMessage.Status.FAILURE));
            redirectAttributes.addFlashAttribute("project", project);
            return "redirect:/project-form";
        }
        project.setDateCreated(new Date());
        projectService.save(project);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage(String.format("Project '%s' has been added successfully.", project.getName()),
                FlashMessage.Status.SUCCESS));
        redirectAttributes.addFlashAttribute("project", project);
        return String.format("redirect:/projects/%s/detail", project.getId());
    }

    // This method receives an HTTP GET request from the client. It displays all the needed roles of a specific project, and beside them
    // all the available collaborators in a drop-down. If a role has a collaborator associated with in the project, that collaborator will
    // be the first option displayed in the drop-down.
    @RequestMapping(value = "/projects/{projectId}/collaborators")
    public String editProjectCollaborators(@PathVariable Long projectId, Model model){
        Project project = projectService.findById(projectId);
        List<Role> roles = new ArrayList<>();

        for(Role role : project.getRolesNeeded()){
            roles.add(roleService.findById(role.getId()));
        }

        project.setCollaboratorsAssigned(getCollaboratorListThatHasASizeEqualToTheRoleListSize(project));

        model.addAttribute("roles", roles);
        model.addAttribute("project", project);
        return "project/project_collaborators";
    }

    // This method receives an HTTP GET request for displaying a form for editing an existing project from the database.
    @RequestMapping(value = "/projects/{projectId}/edit")
    public String projectEditForm(@PathVariable Long projectId, Model model){
        Project project = projectService.findById(projectId);
        model.addAttribute("project", project);
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("action", "edit-project");
        return "project/edit_project";
    }

    // This method receives an HTTP POST request for editing an existing project from the database.
    // If the information passed is valid, a positive flash message will be displayed.
    // Otherwise, an error message pops up, saying that the information passed is somehow invalid.
    @RequestMapping(value = "/edit-project", method = RequestMethod.POST)
    public String editProject(@Valid Project project, BindingResult result, RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("flash",
                    new FlashMessage("The project information provided is invalid. Try again",
                            FlashMessage.Status.FAILURE));
            redirectAttributes.addFlashAttribute("project", project);
            return String.format("redirect:/projects/%s/edit", project.getId());
        }
        String oldName = projectService.findById(project.getId()).getName();
        project.setDateCreated(new Date());
        projectService.save(project);
        redirectAttributes.addFlashAttribute("flash",
                new FlashMessage(String.format("Project %s has been successfully edited.", oldName),
                        FlashMessage.Status.SUCCESS));
        return String.format("redirect:/projects/%s/detail", project.getId());
    }

    // This method receives an HTTP POST request that assigns a collaborator to a needed role from a project.
    // A positive flash message pops up, saying that the collaborators have been edited successfully.
    @RequestMapping(value = "/edit-collaborators/{projectId}", method = RequestMethod.POST)
    public String editCollaborators(@PathVariable Long projectId, Project project, RedirectAttributes redirectAttributes){
        Project actualProject = projectService.findById(projectId);
        List<Collaborator> collaborators = project.getCollaboratorsAssigned();
        List<Collaborator> actualCollaborators = new ArrayList<>();
        for(Collaborator collaborator : collaborators){
            actualCollaborators.add(collaboratorService.findById(collaborator.getId()));
        }
        actualProject.setCollaboratorsAssigned(actualCollaborators);
        projectService.save(actualProject);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Project collaborators have been added/edited successfully.",
                FlashMessage.Status.SUCCESS));
        return String.format("redirect:/projects/%s/detail", projectId);
    }

    // This method receives an HTTP POST request from the client, to delete a specific project from the database.
    // A positive flash message pops up, saying that the delete operation succeeded as expected.
    @RequestMapping(value = "/projects/{projectId}/delete", method = RequestMethod.POST)
    public String deleteProject(@PathVariable Long projectId, RedirectAttributes redirectAttributes){
        Project project = projectService.findById(projectId);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage(String.format("Project '%s' has been deleted successfully.", project.getName()),
                FlashMessage.Status.SUCCESS));
        projectService.delete(project);
        return "redirect:/";
    }
}
