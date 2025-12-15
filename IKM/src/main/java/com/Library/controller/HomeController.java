package com.Library.controller;

import com.Library.service.AuthorService;
import com.Library.service.BookService;
import com.Library.service.BookCopyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final AuthorService authorService;
    private final BookService bookService;
    private final BookCopyService bookCopyService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("authorsCount", authorService.countAuthors());
        model.addAttribute("booksCount", bookService.countBooks());
        model.addAttribute("copiesCount", bookCopyService.countCopies());
        return "index";
    }
}