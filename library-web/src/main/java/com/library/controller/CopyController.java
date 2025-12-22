package com.library.controller;

import com.library.entity.Copy;
import com.library.service.CopyService;
import com.library.service.OeuvreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для управления экземплярами книг (копиями).
 * Обрабатывает операции просмотра списка копий, создания,
 * редактирования, удаления и просмотра деталей конкретной копии.
 */
@Controller
@RequestMapping("/copies")
public class CopyController {

    private final CopyService copyService;
    private final OeuvreService oeuvreService;

    /**
     * Конструктор контроллера с внедрением зависимостей.
     *
     * @param copyService   сервис для работы с копиями
     * @param oeuvreService сервис для работы с произведениями
     */
    public CopyController(CopyService copyService, OeuvreService oeuvreService) {
        this.copyService = copyService;
        this.oeuvreService = oeuvreService;
    }

    /**
     * Отображает список всех копий.
     *
     * @param model модель для передачи данных в представление
     * @return HTML‑страница со списком копий
     */
    @GetMapping
    public String list(Model model) {
        List<Copy> copies = copyService.getAll();
        model.addAttribute("copies", copies);
        model.addAttribute("copyCount", copies.size());
        return "copies/list";
    }

    /**
     * Отображает форму создания новой копии.
     *
     * @param model модель для передачи данных в представление
     * @return HTML‑страница с формой создания копии
     */
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("copy", new Copy());
        model.addAttribute("oeuvres", oeuvreService.getAllOeuvres());
        model.addAttribute("statuses", copyService.getStatuses());
        model.addAttribute("action", "create");
        return "copies/form";
    }

    /**
     * Обрабатывает создание новой копии.
     *
     * @param copy  объект копии, заполненный из формы
     * @param model модель для передачи данных при ошибке
     * @return перенаправление на список копий или возврат формы при ошибке
     */
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

    /**
     * Отображает форму редактирования существующей копии.
     *
     * @param id    идентификатор копии
     * @param model модель для передачи данных в представление
     * @return HTML‑страница с формой редактирования копии
     */
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("copy", copyService.getById(id));
        model.addAttribute("oeuvres", oeuvreService.getAllOeuvres());
        model.addAttribute("statuses", copyService.getStatuses());
        model.addAttribute("action", "edit");
        return "copies/form";
    }

    /**
     * Обрабатывает обновление данных копии.
     *
     * @param id    идентификатор копии
     * @param copy  объект копии, заполненный из формы
     * @param model модель для передачи данных при ошибке
     * @return перенаправление на список копий или возврат формы при ошибке
     */
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

    /**
     * Отображает страницу с подробной информацией о копии.
     *
     * @param id    идентификатор копии
     * @param model модель для передачи данных в представление
     * @return HTML‑страница с деталями копии
     */
    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("copy", copyService.getById(id));
        return "copies/view";
    }

    /**
     * Удаляет копию по идентификатору.
     *
     * @param id идентификатор копии
     * @return перенаправление на список копий
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        copyService.delete(id);
        return "redirect:/copies";
    }
}
