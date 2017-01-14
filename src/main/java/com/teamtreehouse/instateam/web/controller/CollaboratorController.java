package com.teamtreehouse.instateam.web.controller;

import com.teamtreehouse.instateam.model.Collaborator;
import com.teamtreehouse.instateam.service.CollaboratorService;
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

@Controller
public class CollaboratorController {
    @Autowired
    private CollaboratorService collaboratorService;

    private Logger logger = Logger.getLogger(CollaboratorController.class);

    @Autowired
    private RoleService roleService;

    @RequestMapping("/collaborators")
    public String listCollaborators(Model model){
        if(!model.containsAttribute("collaborator")){
            model.addAttribute("collaborator", new Collaborator());
        }
        model.addAttribute("collaborators", collaboratorService.findAll());
        model.addAttribute("roles", roleService.findAll());
        return "collaborator/index";
    }

    @RequestMapping(value = "/add-collaborator", method = RequestMethod.POST)
    public String addCollaborator(@Valid Collaborator collaborator, BindingResult result, RedirectAttributes redirectAttributes){
        if(result.hasErrors()) {
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("The name of the collaborator must have a lenght between 3 and 20.",
                    FlashMessage.Status.FAILURE));
            redirectAttributes.addFlashAttribute("collaborator", collaborator);
            return "redirect:/collaborators";
        }

        collaboratorService.save(collaborator);

        redirectAttributes.addFlashAttribute("flash", new FlashMessage(String.format("Collaborator '%s' has been added successfully.",
                collaborator.getName()), FlashMessage.Status.SUCCESS));
        return "redirect:/collaborators";
    }

    @RequestMapping(value = "/collaborators/{collaboratorId}/detail")
    public String viewCollaboratorDetails(@PathVariable Long collaboratorId, Model model){
        Collaborator collaborator = collaboratorService.findById(collaboratorId);
        model.addAttribute("collaborator", collaborator);
        model.addAttribute("roles", roleService.findAll());
        return "collaborator/detail";
    }

    @RequestMapping(value = "/collaborators/{collaboratorId}/edit", method = RequestMethod.POST)
    public String editCollaborator(@Valid Collaborator collaborator, BindingResult result,RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("The collaborators name length must be between 3 and 20 characters respectively.",
                    FlashMessage.Status.FAILURE));
            return String.format("redirect:/collaborators/%s/detail", collaborator.getId());
        }
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Collaborator has been successfully edited!", FlashMessage.Status.SUCCESS));
        collaboratorService.save(collaborator);
        return String.format("redirect:/collaborators/%s/detail", collaborator.getId());
    }

    @RequestMapping(value = "/collaborators/{collaboratorId}/delete", method = RequestMethod.POST)
    public String deleteCollaborator(@PathVariable Long collaboratorId, RedirectAttributes redirectAttributes){
        Collaborator collaborator = collaboratorService.findById(collaboratorId);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage(String.format("Collaborator '%s' has been deleted successfully.",
                collaborator.getName()), FlashMessage.Status.SUCCESS));
        collaboratorService.delete(collaborator);
        return "redirect:/collaborators";
    }

}
