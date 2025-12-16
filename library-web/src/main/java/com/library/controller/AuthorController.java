package com.library.controller;

import com.library.entity.Author;
import com.library.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/authors")// указывает, по какому пути будет находиться определённый ресурс или выполняться логика.
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // главная страница - список всех aвторов
    @GetMapping
    public String listAuthors(Model model) {
        List<Author> authors = authorService.getAllAuthors();
        model.addAttribute("authors", authors);
        model.addAttribute("authorCount", authors.size());
        return "albums/list";
    }

    //страница добавления нового автора
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("author", new Author());
        model.addAttribute("action", "create");
        return "albums/form";
    }

    //обработка создания нового автора
    @PostMapping
    public String createAuthor(@Valid @ModelAttribute("author") Author author,
                               BindingResult result,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("action", "create");
            return "albums/form";
        }

        authorService.saveAuthor(author);
        redirectAttributes.addFlashAttribute("successMessage",
                "Автор "+ author.getName()+" успешно добавлен");
        return "redirect:/authors";
    }



}
