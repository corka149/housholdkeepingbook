package org.corka.housholdkeepingbook.domain.payoff;

import lombok.extern.slf4j.Slf4j;
import org.corka.housholdkeepingbook.domain.category.CategoryService;
import org.corka.housholdkeepingbook.misc.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("payoff")
public class PayoffController {

    private PayoffService payoffService;

    private CategoryService  categoryService;

    @Autowired
    public PayoffController(PayoffService payoffService, CategoryService categoryService) {
        this.payoffService = payoffService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String viewPayoffForm(Model model) {
        model.addAttribute("newPayoff", new PayoffDto());
        model.addAttribute("categories", this.categoryService.getAllActiveCategories());
        model.addAttribute("payoffs",this.payoffService.getAllActivePayoffs());
        model.addAttribute("lastPayoffs", this.payoffService.getLatestPayoffs(5));
        model.addAttribute("months", Range.getIntegerRange(1, 13));

        return "payoff";
    }

    @PostMapping
    public String addPayoff(@ModelAttribute PayoffDto newPayoff, Model model, Principal principal) {
        this.payoffService.addPayoff(newPayoff, principal.getName());
        return this.viewPayoffForm(model);
    }

    @GetMapping("/{payoffId}/delete")
    public String deletePayoff(@PathVariable long payoffId) {
        this.payoffService.deletePayoff(payoffId);
        return "redirect:..";
    }
}
