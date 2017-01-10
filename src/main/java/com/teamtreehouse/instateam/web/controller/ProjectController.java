package com.teamtreehouse.instateam.web.controller;

import com.teamtreehouse.instateam.model.Collaborator;
import com.teamtreehouse.instateam.model.Project;
import com.teamtreehouse.instateam.model.Role;
import com.teamtreehouse.instateam.service.CollaboratorService;
import com.teamtreehouse.instateam.service.ProjectService;
import com.teamtreehouse.instateam.service.RoleService;
import com.teamtreehouse.instateam.web.FlashMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

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
        Collaborator collaborator = collaboratorService.findById(50L);
        String collaboratorName = collaborator.getName();
        Role role = collaborator.getRole();
        String roleName = role.getName();
//        List<Role> roleList = project.getRolesNeeded();
//        String var;
//        for(Role role : roleList){
//            var = role.getName();
//        }
        model.addAttribute("roles", project.getRolesNeeded());
//        model.addAttribute("collaborators", collaboratorService.findAll());
        return "project/project_detail";
    }

    @RequestMapping(value = "/project-form")
    public String projectForm(Model model){
        if(!model.containsAttribute("project")){
            model.addAttribute("project", new Project());
        }

        model.addAttribute("roles", roleService.findAll());
        return "project/edit_project";
    }

    @RequestMapping(value = "/add-project", method = RequestMethod.POST)
    public String addProject(@Valid Project project, BindingResult result, RedirectAttributes redirectAttributes){
//        if(result.hasErrors()){
//            redirectAttributes.addFlashAttribute("flash", new FlashMessage("The project information is invalid. Please try again.",
//                    FlashMessage.Status.FAILURE));
//            redirectAttributes.addFlashAttribute("project", project);
//            return "redirect:/project-form";
//        }
//        List<Role> roleList = project.getRolesNeeded();
//        String var;
//        for(Role role : roleList){
//            var = role.getName();
//        }
        projectService.save(project);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage(String.format("Project %s has been added successfully.", project.getName()),
                FlashMessage.Status.SUCCESS));
        redirectAttributes.addFlashAttribute("project", project);
        return String.format("redirect:/projects/%s/detail", project.getId());
    }
}
