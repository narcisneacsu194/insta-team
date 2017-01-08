package com.teamtreehouse.instateam.web.controller;

import com.teamtreehouse.instateam.service.CollaboratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CollaboratorController {
    @Autowired
    private CollaboratorService collaboratorService;

    @RequestMapping("/collaborators")
    public String listCollaborators(Model model){
        model.addAttribute("collaborators", collaboratorService.findAll());
        return "collaborator/index";
    }
}
