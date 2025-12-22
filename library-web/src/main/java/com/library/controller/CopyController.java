package com.library.controller;

import com.library.entity.Copy;
import com.library.service.CopyService;
import com.library.service.OeuvreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/copies")
public class CopyController {

    private final CopyService copyService;
    private final OeuvreService oeuvreService;

    public CopyController(CopyService copyService, OeuvreService oeuvreService) {
        this.copyService = copyService;
        this.oeuvreService = oeuvreService;
    }

    @GetMapping
    public String list(Model model) {
        List<Copy> copies = copyService.getAll();
        model.addAttribute("copies", copies);
        model.addAttribute("copyCount", copies.size());
        return "copies/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("copy", new Copy());
        model.addAttribute("oeuvres", oeuvreService.getAllOeuvres());
        model.addAttribute("statuses", copyService.getStatuses());
        model.addAttribute("action", "create");
        return "copies/form";
    }

    @PostMapping
    public String create(@ModelAttribute Copy copy, Model model) {
        try {
            copyService.create(copy);
            return "redirect:/copies";
        } catch (IllegalArgumentException e) {
            model.addAttribute("copy", copy);
            model.addAttribute("oeuvres", oeuvreService.getAllOeuvres());
            model.addAttribute("statuses", copyService.getStatuses());
            model.addAttribute("action", "create");
            model.addAttribute("errorMessage", e.getMessage());
            return "copies/form";
        }
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("copy", copyService.getById(id));
        model.addAttribute("oeuvres", oeuvreService.getAllOeuvres());
        model.addAttribute("statuses", copyService.getStatuses());
        model.addAttribute("action", "edit");
        return "copies/form";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Copy copy, Model model) {
        try {
            copyService.update(id, copy);
            return "redirect:/copies";
        } catch (IllegalArgumentException e) {
            model.addAttribute("copy", copy);
            model.addAttribute("oeuvres", oeuvreService.getAllOeuvres());
            model.addAttribute("statuses", copyService.getStatuses());
            model.addAttribute("action", "edit");
            model.addAttribute("errorMessage", e.getMessage());
            return "copies/form";
        }
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("copy", copyService.getById(id));
        return "copies/view";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        copyService.delete(id);
        return "redirect:/copies";
    }
}
