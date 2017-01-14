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

@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CollaboratorService collaboratorService;

    @RequestMapping("/")
    public String listProjects(Model model){
        model.addAttribute("projects", projectService.findAll());
        return "project/index";
    }

    @RequestMapping("/projects/{projectId}/detail")
    public String projectDetails(@PathVariable Long projectId, Model model){
        Project project = projectService.findById(projectId);
        model.addAttribute("project", project);
        model.addAttribute("roles", project.getRolesNeeded());
        model.addAttribute("collaborators", project.getCollaboratorsAssigned());
        return "project/project_detail";
    }

    @RequestMapping(value = "/project-form")
    public String projectAddForm(Model model){
        if(!model.containsAttribute("project")){
            model.addAttribute("project", new Project());
        }

        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("action", "add-project");
        return "project/edit_project";
    }

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

    @RequestMapping(value = "/projects/{projectId}/collaborators")
    public String editProjectCollaborators(@PathVariable Long projectId, Model model){
        Project project = projectService.findById(projectId);
        List<Role> roles = new ArrayList<>();

        for(Role role : project.getRolesNeeded()){
            roles.add(roleService.findById(role.getId()));
        }

        model.addAttribute("roles", roles);
        model.addAttribute("project", project);
        return "project/project_collaborators";
    }

    @RequestMapping(value = "/projects/{projectId}/edit")
    public String projectEditForm(@PathVariable Long projectId, Model model){
        Project project = projectService.findById(projectId);
        model.addAttribute("project", project);
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("action", "edit-project");
        return "project/edit_project";
    }

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
        redirectAttributes.addFlashAttribute("flash", new FlashMessage(String.format("Project collaborators have been added/edited successfully."),
                FlashMessage.Status.SUCCESS));
        return String.format("redirect:/projects/%s/detail", project.getId());
    }

    @RequestMapping(value = "/projects/{projectId}/delete", method = RequestMethod.POST)
    public String deleteProject(@PathVariable Long projectId, RedirectAttributes redirectAttributes){
        Project project = projectService.findById(projectId);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage(String.format("Project '%s' has been deleted successfully.", project.getName()),
                FlashMessage.Status.SUCCESS));
        projectService.delete(project);
        return "redirect:/";
    }
}
