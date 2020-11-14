package com.demo.springlogin.controllers;

import com.demo.springlogin.models.User;
import com.demo.springlogin.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByUsername(user.getUsername());
        if(userExists != null){
            bindingResult
                    .rejectValue("username",
                                "error.user",
                                "Username taken");
        }
        if(bindingResult.hasErrors())
            modelAndView.setViewName("registration");
        else{
            userService.saveUser(user);
            modelAndView
                    .addObject("successMessage", "User has been registered successfully")
                    .addObject("user", new User())
                    .setViewName("registration");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/admin/home", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(authentication.getName());
        modelAndView
                .addObject("username", "Welcome "+ user.getUsername() + "\nName : "+ user.getFirstName()+" "+ user.getLastName()+"\nEmail : "+ user.getEmail())
                .addObject("adminMessage", "Content Available For Admins")
                .setViewName("admin/home");
        return modelAndView;
    }
}
