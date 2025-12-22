package com.library.controller;

import com.library.entity.Oeuvre;
import com.library.service.AuthorService;
import com.library.service.OeuvreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Контроллер для управления литературными произведениями.
 * Обрабатывает операции просмотра списка произведений,
 * создания, редактирования, удаления и отображения подробной информации.
 */
@Controller
@RequestMapping("/oeuvres")
@RequiredArgsConstructor
public class OeuvreController {

    private final OeuvreService oeuvreService;
    private final AuthorService authorService;

    /**
     * Отображает список всех произведений.
     *
     * @param model модель для передачи данных в представление
     * @return HTML‑страница со списком произведений
     */
    @GetMapping
    public String listOeuvres(Model model) {
        model.addAttribute("oeuvres", oeuvreService.getAllOeuvres());
        model.addAttribute("oeuvreCount", oeuvreService.getAllOeuvres().size());
        return "oeuvres/list";
    }

    /**
     * Отображает форму создания нового произведения.
     *
     * @param model модель для передачи данных в представление
     * @return HTML‑страница с формой создания произведения
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("oeuvre", new Oeuvre());
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("action", "create");
        return "oeuvres/form";
    }

    /**
     * Обрабатывает создание нового произведения.
     *
     * @param oeuvre             объект произведения, заполненный из формы
     * @param result             ошибки валидации
     * @param redirectAttributes атрибуты для сообщений после редиректа
     * @param model              модель для передачи данных при ошибке
     * @return перенаправление на список произведений или возврат формы при ошибке
     */
    @PostMapping
    public String createOeuvre(@Valid @ModelAttribute("oeuvre") Oeuvre oeuvre,
                               BindingResult result,
                               RedirectAttributes redirectAttributes,
                               Model model) {

        if (result.hasErrors()) {
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("action", "create");
            return "oeuvres/form";
        }

        oeuvreService.saveOeuvre(oeuvre);
        redirectAttributes.addFlashAttribute("successMessage",
                "Произведение \"" + oeuvre.getTitle() + "\" успешно добавлено");

        return "redirect:/oeuvres";
    }

    /**
     * Отображает форму редактирования произведения.
     *
     * @param id                 идентификатор произведения
     * @param model              модель для передачи данных в представление
     * @param redirectAttributes атрибуты для сообщений после редиректа
     * @return HTML‑страница с формой редактирования или редирект при ошибке
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        Oeuvre oeuvre = oeuvreService.getOeuvreById(id).orElse(null);

        if (oeuvre == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Произведение не найдено");
            return "redirect:/oeuvres";
        }

        model.addAttribute("oeuvre", oeuvre);
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("action", "edit");

        return "oeuvres/form";
    }

    /**
     * Обрабатывает обновление данных произведения.
     *
     * @param id                 идентификатор произведения
     * @param oeuvre             объект произведения из формы
     * @param result             ошибки валидации
     * @param redirectAttributes атрибуты для сообщений после редиректа
     * @param model              модель для передачи данных при ошибке
     * @return перенаправление на список произведений или возврат формы при ошибке
     */
    @PostMapping("/update/{id}")
    public String updateOeuvre(@PathVariable Long id,
                               @Valid @ModelAttribute("oeuvre") Oeuvre oeuvre,
                               BindingResult result,
                               RedirectAttributes redirectAttributes,
                               Model model) {

        if (result.hasErrors()) {
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("action", "edit");
            oeuvre.setId(id);
            return "oeuvres/form";
        }

        try {
            oeuvreService.updateOeuvre(id, oeuvre);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Произведение \"" + oeuvre.getTitle() + "\" успешно обновлено");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/oeuvres";
    }

    /**
     * Удаляет произведение по идентификатору.
     *
     * @param id                 идентификатор произведения
     * @param redirectAttributes атрибуты для сообщений после редиректа
     * @return перенаправление на список произведений
     */
    @GetMapping("/delete/{id}")
    public String deleteOeuvre(@PathVariable Long id,
                               RedirectAttributes redirectAttributes) {

        try {
            Oeuvre oeuvre = oeuvreService.getOeuvreById(id).orElse(null);

            if (oeuvre != null) {
                oeuvreService.deleteOeuvre(id);
                redirectAttributes.addFlashAttribute("successMessage",
                        "Произведение \"" + oeuvre.getTitle() + "\" успешно удалено");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Произведение не найдено");
            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Ошибка при удалении произведения: " + e.getMessage());
        }

        return "redirect:/oeuvres";
    }

    /**
     * Отображает страницу с подробной информацией о произведении.
     *
     * @param id                 идентификатор произведения
     * @param model              модель для передачи данных в представление
     * @param redirectAttributes атрибуты для сообщений после редиректа
     * @return HTML‑страница с деталями произведения или редирект при ошибке
     */
    @GetMapping("/view/{id}")
    public String viewOeuvre(@PathVariable Long id,
                             Model model,
                             RedirectAttributes redirectAttributes) {

        Oeuvre oeuvre = oeuvreService.getOeuvreById(id).orElse(null);

        if (oeuvre == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Произведение не найдено");
            return "redirect:/oeuvres";
        }

        model.addAttribute("oeuvre", oeuvre);
        return "oeuvres/view";
    }
}
