package com.indocyber.Winterhold.controllers;

import com.indocyber.Winterhold.dtos.auth.AuthRegisterDto;
import com.indocyber.Winterhold.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("register")
    public ModelAndView register() {
        var mv = new ModelAndView("auth/register");
        mv.addObject("dto",AuthRegisterDto.builder().build());
        return  mv;

    }

    @PostMapping("register")
    public String register(@Valid @ModelAttribute("dto") AuthRegisterDto dto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }
        authService.register(dto);
        return "redirect:/login";
    }
    @GetMapping("login")
    public ModelAndView login(@RequestParam(required = false) Boolean error) {
        ModelAndView mv = new ModelAndView("auth/login");
        mv.addObject("error",error);
        return mv;
    }

    @GetMapping("")
    public ModelAndView redirectAfterLogin(Authentication authentication){
        return new ModelAndView("home");
    }


}
