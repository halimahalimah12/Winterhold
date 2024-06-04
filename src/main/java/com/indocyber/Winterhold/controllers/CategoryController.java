package com.indocyber.Winterhold.controllers;

import com.indocyber.Winterhold.dtos.author.AuthorSearchDto;
import com.indocyber.Winterhold.dtos.author.AuthorUpsertRequestDto;
import com.indocyber.Winterhold.dtos.category.CategoryDeleteItemDto;
import com.indocyber.Winterhold.dtos.category.CategorySearchDto;
import com.indocyber.Winterhold.dtos.category.CategoryUpsertRequestDto;
import com.indocyber.Winterhold.services.BookService;
import com.indocyber.Winterhold.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("categories")
public class CategoryController {
    private CategoryService categoryService;
    private BookService bookService;

    public CategoryController(CategoryService categoryService, BookService bookService) {
        this.categoryService = categoryService;
        this.bookService = bookService;
    }

    @GetMapping("")
    public ModelAndView index(@Valid CategorySearchDto dto) {
        ModelAndView mv = new ModelAndView("category/index");
        mv.addObject("categories",categoryService.getAll(dto));
        return mv;
    }

    @GetMapping("insert")
    public ModelAndView insert(CategoryUpsertRequestDto dto){
        ModelAndView mv = new ModelAndView("category/upsert");
        mv.addObject("dto", CategoryUpsertRequestDto.builder().build());
        return mv;
    }
    @PostMapping("upsert")
    private String postInsert(@Valid @ModelAttribute("dto") CategoryUpsertRequestDto dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "category/upsert";
        }
        categoryService.save(dto);
        return "redirect:/categories";
    }

    @GetMapping("update/{id}")
    public  ModelAndView update(@PathVariable String id) {
        ModelAndView mv = new ModelAndView("category/upsert");
        mv.addObject("dto",categoryService.getById(id));
        return mv;
    }

    @GetMapping("delete/{id}")
    public  ModelAndView delete(@PathVariable String id) {
        ModelAndView mv = new ModelAndView("category/delete");
        mv.addObject("category",categoryService.getItemByIdForDelete(id));
        return mv;
    }

    @PostMapping("delete")
    public String postDelete(CategoryDeleteItemDto dto, Model model) {
        Integer dependencies = categoryService.getTotalBook(dto.getName());
        if (dependencies == 0) {
            categoryService.deleteById(dto.getName());
            return "redirect:/categories";
        } else {
            model.addAttribute("dependencies",dependencies);
            return "category/delete-failed";
        }
    }

}
