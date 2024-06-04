package com.indocyber.Winterhold.controllers;

import com.indocyber.Winterhold.dtos.author.AuthorUpsertRequestDto;
import com.indocyber.Winterhold.dtos.book.BookSearchDto;
import com.indocyber.Winterhold.dtos.book.BookUpsertRequestDto;
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
@RequestMapping("category/{id}/books")
public class BookController {
    private CategoryService categoryService;
    private BookService bookService;


    public BookController(CategoryService categoryService, BookService bookService) {
        this.categoryService = categoryService;
        this.bookService = bookService;
    }

    @GetMapping("")
    public ModelAndView index(@PathVariable String id, BookSearchDto dto) {
        ModelAndView mv = new ModelAndView("book/index");
        mv.addObject("dto",dto);
        mv.addObject("category",categoryService.getById(id));
        mv.addObject("books",bookService.getAllBook(id,dto));
        return mv;
    }

    @GetMapping("addBook")
    public ModelAndView addBook (@PathVariable String id ) {
        ModelAndView mv = new ModelAndView("book/upsert");
        mv.addObject("category",categoryService.getById(id));
        mv.addObject("authorDropdown",bookService.getAuthorDropdown());
        mv.addObject("dto", BookUpsertRequestDto.builder().build());
        return mv;
    }

    @PostMapping("upsert")
    private String postInsert(@Valid @ModelAttribute("dto") BookUpsertRequestDto dto, BindingResult bindingResult, Model model){
        model.addAttribute("category",categoryService.getById(dto.getCategoryId()));
        model.addAttribute("authorDropdown",bookService.getAuthorDropdown());
        bookService.save(dto);
        return "redirect:/category/{id}/books";
    }

    @GetMapping("delete/{codeBook}")
    public ModelAndView delete (@PathVariable String codeBook,@PathVariable String id) {
        ModelAndView mv = new ModelAndView("book/delete");
        mv.addObject("book",bookService.getById(codeBook));
        return mv;
    }
    @GetMapping("update/{codeBook}")
    public  ModelAndView update(@PathVariable String codeBook, @PathVariable String id) {
        ModelAndView mv = new ModelAndView("book/upsert");
        mv.addObject("category",categoryService.getById(id));
        mv.addObject("dto",bookService.getById(codeBook));
        mv.addObject("authorDropdown",bookService.getAuthorDropdown());
        return mv;
    }
    @PostMapping("delete")
    public String postDelete(BookUpsertRequestDto dto, Model model) {
        Integer dependencies =  bookService.countBookInLoan(dto.getCode());
        if (dependencies == 0) {
            bookService.deleteById(dto.getCode());
            return "redirect:/category/{id}/books";
        } else {
            model.addAttribute("dependencies",dependencies);
            return "author/delete-failed";
        }
    }


}
