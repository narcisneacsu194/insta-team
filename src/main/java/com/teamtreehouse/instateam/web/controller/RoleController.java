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

@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/roles")
    public String listRoles(Model model){
        if(!model.containsAttribute("role")){
            model.addAttribute("role", new Role());
        }
        model.addAttribute("roles", roleService.findAll());
        return "role/index";
    }

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
        return "redirect:/roles";
    }

    @RequestMapping(value = "/roles/{roleId}/detail")
    public String roleDetail(@PathVariable Long roleId, Model model){
        Role role = roleService.findById(roleId);
        model.addAttribute("role", role);
        return "role/detail";
    }

    @RequestMapping(value = "/roles/{roleId}/edit", method = RequestMethod.POST)
    public String editRole(@Valid Role role, BindingResult result, RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("The name you have used for the role is invalid. Please try again.",
                    FlashMessage.Status.FAILURE));
            return String.format("redirect:/roles/%s/detail", role.getId());
        }

        roleService.save(role);

        return String.format("redirect:/roles/%s/detail", role.getId());
    }
}
