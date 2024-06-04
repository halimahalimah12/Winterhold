package com.indocyber.Winterhold.controllers;

import com.indocyber.Winterhold.dtos.customer.CustomerDeleteItemDto;
import com.indocyber.Winterhold.dtos.customer.CustomerSearchDto;
import com.indocyber.Winterhold.dtos.customer.CustomerUpsertRequestDto;
import com.indocyber.Winterhold.models.Gender;
import com.indocyber.Winterhold.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("customers")
public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("")
    public ModelAndView index(@Valid CustomerSearchDto dto) {
        ModelAndView mv = new ModelAndView("customer/index");
        mv.addObject("customers", customerService.getAll(dto));
        return mv;
    }

    @GetMapping("insert")
    public ModelAndView insert(CustomerUpsertRequestDto dto) {
        ModelAndView mv = new ModelAndView("customer/upsert");
        mv.addObject("genders", Gender.values());
        mv.addObject("dto", CustomerUpsertRequestDto.builder().build());
        return mv;
    }

    @PostMapping("upsert")
    private String postInsert(@Valid @ModelAttribute("dto") CustomerUpsertRequestDto dto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genders", Gender.values());
            return "customer/upsert";
        }
        customerService.save(dto);
        return "redirect:/customers";
    }

    @GetMapping("update/{id}")
    public ModelAndView update(@PathVariable String id) {
        ModelAndView mv = new ModelAndView("customer/upsert");
        mv.addObject("dto", customerService.getCutomerById(id));
        return mv;
    }

    @GetMapping("delete/{id}")
    public ModelAndView delete(@PathVariable String id) {
        ModelAndView mv = new ModelAndView("customer/delete");
        mv.addObject("customer", customerService.getItemByIdForDelete(id));
        return mv;
    }

    @PostMapping("delete")
    public String postDelete(CustomerDeleteItemDto dto, Model model) {
        Integer dependencies = customerService.countCustomerInLoan(dto.getMembershipNumber());
        if (dependencies == 0) {
            customerService.deleteById(dto.getMembershipNumber());
            return "redirect:/customers";
        } else {
            model.addAttribute("dependencies",dependencies);
            return "customer/delete-failed";
        }
    }
}
