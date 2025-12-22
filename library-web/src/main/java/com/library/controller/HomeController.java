package com.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер главной страницы приложения.
 * Отвечает за отображение стартового экрана библиотеки.
 */
@Controller
public class HomeController {

    /**
     * Обрабатывает запрос на главную страницу сайта.
     *
     * @param model модель для передачи данных в представление
     * @return HTML‑страница home.html
     */
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Библиотека");
        return "home";
    }
}
