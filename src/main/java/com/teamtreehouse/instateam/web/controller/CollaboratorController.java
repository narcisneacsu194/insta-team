package com.teamtreehouse.instateam.web.controller;

import com.teamtreehouse.instateam.model.Collaborator;
import com.teamtreehouse.instateam.service.CollaboratorService;
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
// This is the Collaborator entity controller. It has methods that receive HTTP POST and GET requests from the view,
// in order to store or retrieve information into or from the database.
// The data is stored/retrieved using the CollaboratorService and RoleService interfaces.
// The concepts used here are dependency injection with autowiring.
// This means that the Spring application is passing to the CollaboratorService reference a CollaboratorServiceImpl object
// at runtime, and to the RoleService as well, a RoleServiceImpl object.
@Controller
public class CollaboratorController {
    @Autowired
    private CollaboratorService collaboratorService;

    @Autowired
    private RoleService roleService;

    // This method receives an HTTP GET request for listing all the available collaborators stored in the database.
    @RequestMapping("/collaborators")
    public String listCollaborators(Model model){
        if(!model.containsAttribute("collaborator")){
            model.addAttribute("collaborator", new Collaborator());
        }
        model.addAttribute("collaborators", collaboratorService.findAll());
        model.addAttribute("roles", roleService.findAll());
        return "collaborator/index";
    }

    // This method receives an HTTP POST request from the client. It tries to add a new collaborator in the database using the passed information.
    // If the information passed in the form is valid, a positive flash message appears, saying that the place has been successfully added.
    // Otherwise, an error flash message pops up, saying that some or all the information entered is incorrect.
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

    // This method receives an HTTP GET request for displaying a detailed page of a specified collaborator from the database.
    @RequestMapping(value = "/collaborators/{collaboratorId}/detail")
    public String viewCollaboratorDetails(@PathVariable Long collaboratorId, Model model){
        Collaborator collaborator = collaboratorService.findById(collaboratorId);
        model.addAttribute("collaborator", collaborator);
        model.addAttribute("roles", roleService.findAll());
        return "collaborator/detail";
    }

    // This method receives an HTTP POST request for editing an existing collaborator from the database.
    // If the information passed is valid, a positive flash message will be displayed.
    // Otherwise, an error message pops up, saying that the information passed is somehow invalid.
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

    // This method receives an HTTP POST request from the client, to delete a specific collaborator from the database.
    // A positive flash message pops up, saying that the delete operation succeeded as expected.
    @RequestMapping(value = "/collaborators/{collaboratorId}/delete", method = RequestMethod.POST)
    public String deleteCollaborator(@PathVariable Long collaboratorId, RedirectAttributes redirectAttributes){
        Collaborator collaborator = collaboratorService.findById(collaboratorId);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage(String.format("Collaborator '%s' has been deleted successfully.",
                collaborator.getName()), FlashMessage.Status.SUCCESS));
        collaboratorService.delete(collaborator);
        return "redirect:/collaborators";
    }

}
