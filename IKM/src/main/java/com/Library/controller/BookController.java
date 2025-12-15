package com.Library.controller;

import com.Library.entity.Author;
import com.Library.entity.Book;
import com.Library.service.AuthorService;
import com.Library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    @GetMapping
    public String listBooks(Model model) {
        try {
            model.addAttribute("books", bookService.getAllBooks());
            return "books/list";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.getAllAuthors());
        return "books/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.getAllAuthors());
        return "books/form";
    }

    @PostMapping("/save")
    public String saveBook(@ModelAttribute Book book,
                           @RequestParam Long authorId,
                           Model model) {

        try {
            Author author = authorService.getAuthorById(authorId);
            book.setAuthor(author);

            bookService.saveBook(book);
            return "redirect:/books";

        } catch (Exception e) {
            model.addAttribute("error", "Error saving book: " + e.getMessage());
            model.addAttribute("authors", authorService.getAllAuthors());
            return "books/form";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam String title, Model model) {
        model.addAttribute("books", bookService.searchBooksByTitle(title));
        return "books/list";
    }

    @GetMapping("/author/{authorId}")
    public String getBooksByAuthor(@PathVariable Long authorId, Model model) {
        model.addAttribute("books", bookService.getBooksByAuthor(authorId));
        return "books/list";
    }
}