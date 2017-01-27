package com.teamtreehouse.instateam.web.controller;

import com.teamtreehouse.instateam.model.Role;
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

// This is the Role entity controller. It has methods that receive HTTP POST and GET requests from the view,
// in order to store or retrieve information into or from the database.
// The data is stored/retrieved using the RoleService interface.
// The concepts used here are dependency injection with autowiring.
// This means that the Spring application is passing to the RoleService reference
// a RoleServiceImpl object at runtime.
@Controller
public class RoleController {


    @Autowired
    private RoleService roleService;

    // This method retrieves an HTTP GET request for listing all the available roles stored in the database.
    @RequestMapping("/roles")
    public String listRoles(Model model){
        if(!model.containsAttribute("role")){
            model.addAttribute("role", new Role());
        }
        model.addAttribute("roles", roleService.findAll());
        return "role/index";
    }

    // This method receives an HTTP POST request from the client. It tries to add a new role in the database using
    // the passed information. If the information passed in the form is valid,
    // a positive flash message appears, saying that the place
    // has been successfully added.
    // Otherwise, an error flash message pops up, saying that some or all the information entered is incorrect.
    @RequestMapping(value = "/add-role", method = RequestMethod.POST)
    public String addRole(@Valid Role role, BindingResult result, RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("flash",
                    new FlashMessage("The name you have used for the role is invalid. Please try again.",
                    FlashMessage.Status.FAILURE));
            redirectAttributes.addFlashAttribute("role", role);
            return "redirect:/roles";
        }
        roleService.save(role);
        redirectAttributes.addFlashAttribute("flash",
                new FlashMessage(String.format("Role '%s' has been successfully added.", role.getName()),
                        FlashMessage.Status.SUCCESS));
        return "redirect:/roles";
    }

    // This method receives an HTTP GET request for displaying a detailed page of
    // a specified place from the database.
    @RequestMapping(value = "/roles/{roleId}/detail")
    public String roleDetail(@PathVariable Long roleId, Model model){
        Role role = roleService.findById(roleId);
        model.addAttribute("role", role);
        return "role/detail";
    }

    // This method receives an HTTP POST request for editing an existing role from the database.
    // If the information passed is valid, a positive flash message will be displayed.
    // Otherwise, an error message pops up, saying that the information passed is somehow invalid.
    @RequestMapping(value = "/roles/{roleId}/edit", method = RequestMethod.POST)
    public String editRole(@Valid Role role, BindingResult result, RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("The name you have used for the role is invalid. Please try again.",
                    FlashMessage.Status.FAILURE));
            return String.format("redirect:/roles/%s/detail", role.getId());
        }
        String oldName = roleService.findById(role.getId()).getName();
        roleService.save(role);
        String newName = role.getName();

        redirectAttributes.addFlashAttribute("flash",
                new FlashMessage(String.format("Role '%s' has been renamed to '%s'.", oldName, newName) ,
                        FlashMessage.Status.SUCCESS));

        return String.format("redirect:/roles/%s/detail", role.getId());
    }

    // This method receives an HTTP POST request from the client, to delete a specific role from the database.
    // A positive flash message pops up, saying that the delete operation succeeded as expected.
    @RequestMapping(value = "/roles/{roleId}/delete", method = RequestMethod.POST)
    public String deleteRole(@PathVariable Long roleId, RedirectAttributes redirectAttributes){
        Role role = roleService.findById(roleId);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage(String.format("Role '%s' has been deleted successfuly.",
                role.getName()),
                FlashMessage.Status.SUCCESS));
        roleService.delete(role);
        return "redirect:/roles";
    }
}
