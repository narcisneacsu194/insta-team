package com.teamtreehouse.instateam.web.controller;

import com.teamtreehouse.instateam.service.ProjectService;
import com.teamtreehouse.instateam.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/")
    public String listProjects(Model model){
        model.addAttribute(projectService.findAll());
        return "project/index";
    }

    @RequestMapping("/add-project")
    public String addProject(Model model){
        model.addAttribute("roles", roleService.findAll());
        return "project/edit_project";
    }
}
