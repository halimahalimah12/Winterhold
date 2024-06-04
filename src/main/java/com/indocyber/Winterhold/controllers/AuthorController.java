package com.indocyber.Winterhold.controllers;

import com.indocyber.Winterhold.dtos.author.AuthorDeleteItemDto;
import com.indocyber.Winterhold.dtos.author.AuthorSearchDto;
import com.indocyber.Winterhold.dtos.author.AuthorUpsertRequestDto;
import com.indocyber.Winterhold.services.AuthorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("authors")
public class AuthorController {
    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("")
    public ModelAndView index(AuthorSearchDto dto) {
        ModelAndView mv = new ModelAndView("author/index");
        mv.addObject("authors",authorService.getAll(dto));
        return mv;
    }
    @GetMapping("insert")
    public ModelAndView insert(AuthorUpsertRequestDto dto){
        ModelAndView mv = new ModelAndView("author/upsert");
        mv.addObject("dto", AuthorUpsertRequestDto.builder().build());
        return mv;
    }
    @PostMapping("upsert")
    private String postInsert(@Valid @ModelAttribute("dto") AuthorUpsertRequestDto dto, BindingResult bindingResult,Model model){
        if (bindingResult.hasErrors()) {
            return "author/upsert";
        }

        authorService.save(dto);
        return "redirect:/authors";
    }

    @GetMapping("update/{id}")
    public  ModelAndView update(@PathVariable Integer id) {
        ModelAndView mv = new ModelAndView("author/upsert");
        mv.addObject("dto",authorService.getById(id));
        return mv;
    }

    @GetMapping("delete/{id}")
    public  ModelAndView delete(@PathVariable Integer id) {
        ModelAndView mv = new ModelAndView("author/delete");
        mv.addObject("author",authorService.getItemByIdForDelete(id));
        return mv;
    }

    @PostMapping("delete")
    public String postDelete(AuthorDeleteItemDto dto, Model model) {
        Integer dependencies =  authorService.countBookByAuthor(dto.getId());
        if (dependencies == 0) {
            authorService.deleteById(dto.getId());
            return "redirect:/authors";
        } else {
            model.addAttribute("dependencies",dependencies);
            return "author/delete-failed";
        }
    }

    @GetMapping("detail/{id}")
    public ModelAndView detail(@PathVariable Integer id) {
        ModelAndView mv = new ModelAndView("author/detail");
        mv.addObject("author",authorService.getById(id));
        mv.addObject("books",authorService.authorsBook(id));
    return mv;
    }



}
