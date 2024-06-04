package com.indocyber.Winterhold.controllers;

import com.indocyber.Winterhold.dtos.customer.CustomerDeleteItemDto;
import com.indocyber.Winterhold.dtos.loan.LoanRowItemDto;
import com.indocyber.Winterhold.dtos.loan.LoanSearchDto;
import com.indocyber.Winterhold.dtos.loan.LoanUpsertRequestDto;
import com.indocyber.Winterhold.models.Gender;
import com.indocyber.Winterhold.services.LoanService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("loans")
public class LoanController {
    private LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("")
    public ModelAndView index(@Valid LoanSearchDto dto) {
        ModelAndView mv = new ModelAndView("loan/index");
        mv.addObject("loans", loanService.getAll(dto));
        return mv;
    }

    @GetMapping("insert")
    public ModelAndView insert(LoanUpsertRequestDto dto) {
        ModelAndView mv = new ModelAndView("loan/upsert");
        mv.addObject("customerDropdown",loanService.getCustomerDropdown());
        mv.addObject("bookDropdown", loanService.getBookDropdown());
        mv.addObject("dto", LoanUpsertRequestDto.builder().build());
        return mv;
    }

    @PostMapping("upsert")
    private String postInsert(@Valid @ModelAttribute("dto") LoanUpsertRequestDto dto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("customerDropdown",loanService.getCustomerDropdown());
            model.addAttribute("bookDropdown", loanService.getBookDropdown());
            return "loan/upsert";
        }
        loanService.save(dto);
        return "redirect:/loans";
    }

    @GetMapping("{id}/update")
    public ModelAndView update(@PathVariable Integer id) {
        ModelAndView mv = new ModelAndView("loan/upsert");
        mv.addObject("customerDropdown",loanService.getCustomerDropdown());
        mv.addObject("bookDropdown", loanService.getBookDropdown());
        mv.addObject("dto", loanService.getLoanById(id));
        return mv;
    }


    @GetMapping("{id}/detail")
    public ModelAndView detail(@PathVariable Integer id) {
        ModelAndView mv = new ModelAndView("loan/detail");
        mv.addObject("detailLoan",loanService.getById(id));
        return mv;
    }
    @GetMapping("{id}/return")
    private ModelAndView confirmRetrun(@PathVariable Integer id) {
        ModelAndView mv = new ModelAndView("loan/return");
        mv.addObject("loan",loanService.getById(id));
        mv.addObject("id",id);
        return mv;
    }

    @PostMapping("return")
    private String returnBook(LoanRowItemDto dto){
        loanService.returnBook(dto.getId());
        return "redirect:/loans";
    }







}
