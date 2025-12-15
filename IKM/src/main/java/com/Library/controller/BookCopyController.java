package com.Library.controller;

import com.Library.entity.BookCopy;
import com.Library.service.BookCopyService;
import com.Library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/copies")
@RequiredArgsConstructor
public class BookCopyController {

    private final BookCopyService bookCopyService;
    private final BookService bookService;

    @GetMapping
    public String listCopies(Model model) {
        model.addAttribute("copies", bookCopyService.getAllCopies());
        return "copies/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("copy", new BookCopy());
        model.addAttribute("books", bookService.getAllBooks());
        return "copies/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("copy", bookCopyService.getCopyById(id));
        model.addAttribute("books", bookService.getAllBooks());
        return "copies/form";
    }

    @PostMapping("/save")
    public String saveCopy(@ModelAttribute BookCopy copy) {
        bookCopyService.saveCopy(copy);
        return "redirect:/copies";
    }

    @GetMapping("/delete/{id}")
    public String deleteCopy(@PathVariable Long id) {
        bookCopyService.deleteCopy(id);
        return "redirect:/copies";
    }

    @GetMapping("/borrow/{id}")
    public String showBorrowForm(@PathVariable Long id, Model model) {
        model.addAttribute("copy", bookCopyService.getCopyById(id));
        return "copies/borrow";
    }

    @PostMapping("/borrow/{id}")
    public String borrowCopy(@PathVariable Long id, @RequestParam String borrowerName) {
        bookCopyService.borrowCopy(id, borrowerName);
        return "redirect:/copies";
    }

    @GetMapping("/return/{id}")
    public String returnCopy(@PathVariable Long id) {
        bookCopyService.returnCopy(id);
        return "redirect:/copies";
    }

    @GetMapping("/book/{bookId}")
    public String getCopiesByBook(@PathVariable Long bookId, Model model) {
        model.addAttribute("copies", bookCopyService.getCopiesByBook(bookId));
        return "copies/list";
    }
}