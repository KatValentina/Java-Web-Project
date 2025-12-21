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

//Controller (Контроллер) обрабатывает запрос пользователя,
// создаёт соответствующую Модель и передаёт её для отображения в Вид.

@Controller
@RequestMapping("/authors")// указывает, по какому пути будет находиться определённый ресурс или выполняться логика.
public class AuthorController {
    private final AuthorService authorService;

    @Autowired //автоматически внедрять зависимости в классы
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // главная страница - список всех aвторов
    @GetMapping //предназначенная для обработки HTTP-запросов типа GET.
    public String listAuthors(Model model) {
        List<Author> authors = authorService.getAllAuthors();
        model.addAttribute("authors", authors);
        model.addAttribute("authorCount", authors.size());
        return "authors/list";//Отрендерить страницу authors/list.html и передать туда данные
        //Метод addAttribute используется для передачи данных
        // между представлением и контроллером приложения Spring MVC.

        //Model — компонент архитектуры Model-View-Controller
        // (Модель — Отображение — Контроллер).
        // Задача модели — хранить данные, которые передаются от
        // контроллера к представлению. Основная задача —
        // быть «контейнером» данных
    }

    //страница добавления нового автора
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("author", new Author());
        model.addAttribute("action", "create");
        return "authors/form";//Отрендерить страницу author/form.html и передать туда данные
    }

    //обработка создания нового автора
    @PostMapping
    public String createAuthor(@Valid @ModelAttribute("author") Author author,
                               BindingResult result,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        //@Valid - проверяем объект автор на правила валидации(например поле имя не пустое)
        //@ModelAttribute("author") Author author - Возьми данные из формы (HTML form),
        // создай объект Author и положи его в переменную author. author - это ключ,
        // под которым объект будет доступен в шаблоне.
        //BindingResult result - объект, который хранит ошибки валидации.
        //RedirectAttributes - используют для сообщений об успехе.

        if (result.hasErrors()) { //“Есть ли ошибки валидации?”
            model.addAttribute("action", "create"); //если есть ошибка,
            //Передаём в модель переменную "action" со значением "create". Форма используется для создания
            return "authors/form";//показываем форму с заполнением данных об авторе, но с ошибкой
        }

        authorService.saveAuthor(author);//Сохраняем автора в базе данных.
        //Flash‑атрибут — это сообщение, которое живёт один redirect.
        redirectAttributes.addFlashAttribute("successMessage",
                "Автор "+ author.getName()+" успешно добавлен");
        return "redirect:/authors"; //после создания автора переводим на страницу с авторами
    }

    //страница с редактированием автора
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        //@PathVariable("id") - “Возьми число из URL и положи его в переменную id”.
        Author author = authorService.getAuthorsById(id)
                .orElse(null);

        if (author == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Автор не найден");
            return "redirect:/authors";//вернулись на страницу с авторами
        }

        model.addAttribute("author", author);//Передаём объект автора в HTML‑форму.
        //Форма сможет заполнить поля:
        model.addAttribute("action", "edit");//Передаём в шаблон информацию, что это режим редактирования, а не создания.
        return "authors/form";//Показать форму редактирования автора
    }

    //обработка обновления автора
    @PostMapping("/update/{id}")
    public String updateAuthor(@PathVariable("id") Long id,@Valid @ModelAttribute("author") Author author,
                              BindingResult result,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (result.hasErrors()) { //“Есть ли ошибки валидации?”
            model.addAttribute("action", "edit");
            author.setId(id);//Мы вручную возвращаем id автору,
            // чтобы форма знала, какой объект редактируется
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

    // удаление автора
    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
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

    // просмотр деталей автора
    @GetMapping("/view/{id}")
    public String viewAuthor(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Author author = authorService.getAuthorsById(id).orElse(null);

        if (author == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Автор не найден");
            return "redirect:/authors";
        }

        model.addAttribute("author", author);
        return "authors/view";
    }

}

