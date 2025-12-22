package com.library.controller;

import com.library.entity.Author;
import com.library.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Контроллер для управления авторами.
 * Обрабатывает запросы, связанные с отображением списка авторов,
 * созданием, редактированием, удалением и просмотром деталей автора.
 */
@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    /**
     * Конструктор контроллера с внедрением зависимости AuthorService.
     *
     * @param authorService сервис для работы с авторами
     */
    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * Отображает список всех авторов.
     *
     * @param model модель для передачи данных в представление
     * @return HTML‑страница со списком авторов
     */
    @GetMapping
    public String listAuthors(Model model) {
        List<Author> authors = authorService.getAllAuthors();
        model.addAttribute("authors", authors);
        model.addAttribute("authorCount", authors.size());
        return "authors/list";
    }

    /**
     * Отображает форму создания нового автора.
     *
     * @param model модель для передачи данных в представление
     * @return HTML‑страница с формой создания автора
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("author", new Author());
        model.addAttribute("action", "create");
        return "authors/form";
    }

    /**
     * Обрабатывает отправку формы создания автора.
     *
     * @param author              объект автора, заполненный из формы
     * @param result              ошибки валидации
     * @param redirectAttributes  атрибуты для передачи сообщений после редиректа
     * @param model               модель для передачи данных в представление
     * @return перенаправление на список авторов или возврат формы при ошибках
     */
    @PostMapping
    public String createAuthor(@Valid @ModelAttribute("author") Author author,
                               BindingResult result,
                               RedirectAttributes redirectAttributes,
                               Model model) {

        if (result.hasErrors()) {
            model.addAttribute("action", "create");
            return "authors/form";
        }

        authorService.saveAuthor(author);
        redirectAttributes.addFlashAttribute("successMessage",
                "Автор " + author.getName() + " успешно добавлен");
        return "redirect:/authors";
    }

    /**
     * Отображает форму редактирования автора.
     *
     * @param id                 идентификатор автора
     * @param model              модель для передачи данных в представление
     * @param redirectAttributes атрибуты для сообщений после редиректа
     * @return HTML‑страница с формой редактирования или редирект при ошибке
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        Author author = authorService.getAuthorsById(id).orElse(null);

        if (author == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Автор не найден");
            return "redirect:/authors";
        }

        model.addAttribute("author", author);
        model.addAttribute("action", "edit");
        return "authors/form";
    }

    /**
     * Обрабатывает обновление данных автора.
     *
     * @param id                 идентификатор автора
     * @param author             объект автора из формы
     * @param result             ошибки валидации
     * @param redirectAttributes атрибуты для сообщений после редиректа
     * @param model              модель для передачи данных в представление
     * @return перенаправление на список авторов или возврат формы при ошибках
     */
    @PostMapping("/update/{id}")
    public String updateAuthor(@PathVariable("id") Long id,
                               @Valid @ModelAttribute("author") Author author,
                               BindingResult result,
                               RedirectAttributes redirectAttributes,
                               Model model) {

        if (result.hasErrors()) {
            model.addAttribute("action", "edit");
            author.setId(id);
            return "authors/form";
        }

        try {
            authorService.updateAuthor(id, author);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Автор " + author.getName() + " успешно обновлен");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/authors";
    }

    /**
     * Удаляет автора по идентификатору.
     *
     * @param id                 идентификатор автора
     * @param redirectAttributes атрибуты для сообщений после редиректа
     * @return перенаправление на список авторов
     */
    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable("id") Long id,
                               RedirectAttributes redirectAttributes) {

        try {
            Author author = authorService.getAuthorsById(id).orElse(null);

            if (author != null) {
                authorService.deleteAuthor(id);
                redirectAttributes.addFlashAttribute("successMessage",
                        "Автор " + author.getName() + " успешно удален");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Автор не найден");
            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Ошибка при удалении автора: " + e.getMessage());
        }

        return "redirect:/authors";
    }

    /**
     * Отображает страницу с подробной информацией об авторе.
     *
     * @param id                 идентификатор автора
     * @param model              модель для передачи данных в представление
     * @param redirectAttributes атрибуты для сообщений после редиректа
     * @return HTML‑страница с деталями автора или редирект при ошибке
     */
    @GetMapping("/view/{id}")
    public String viewAuthor(@PathVariable("id") Long id,
                             Model model,
                             RedirectAttributes redirectAttributes) {

        Author author = authorService.getAuthorsById(id).orElse(null);

        if (author == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Автор не найден");
            return "redirect:/authors";
        }

        model.addAttribute("author", author);
        return "authors/view";
    }
}
