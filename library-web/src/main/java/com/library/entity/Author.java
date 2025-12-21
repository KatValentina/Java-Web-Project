package com.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter //Lombok автоматически сгенерирует для Java байт-код, который при сборке
@Setter //в файлы .class, вставляет необходимые для реализации желаемого поведения
@NoArgsConstructor
@AllArgsConstructor
@ToString(includeFieldNames = true)// выводим всю информацию об авторе (id="...", name="...")
@Entity //указывает, что класс представляет собой сущность базы данных.
@Table(name = "author")//имя таблицы в базе, с которой будет связан данный класс
@Builder
public class Author {
    @Id //на поле, которое является первичным ключом сущности в базе данных.
    @GeneratedValue(strategy = GenerationType.IDENTITY)//ключ будет генерироваться автоматически базой данных.
    private Long id;

    @NotBlank(message = "Имя исполнителя не может быть пустым")
    @Size(min = 4,max=150, message = "Имя автора должно содержать от 4 до 150 символов")
    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Past(message = "Дата рождения не должна быть не сегодняшним числом")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "nationality")
    @Size(max=150, message = "Не должна содержать 150 символов")
    private String nationality;

    @Size(max = 500, message = "Биография не должна превышать 500 символов")
    @Column(name = "biography", length = 500)
    private String biography;

    // Автор - Произведения
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Oeuvre> oeuvres = new ArrayList<>();

    public void addOeuvre(Oeuvre oeuvre) {
        oeuvres.add(oeuvre);//добавляем произведение в список произведений автора
        oeuvre.setAuthor(this); //Устанавливает у книги ссылку на этого автора.
    }

    public void removeOeuvre(Oeuvre oeuvre) {
        oeuvres.remove(oeuvre);//Удаляет книгу из списка книг автора.
        oeuvre.setAuthor(null);//Убирает у книги ссылку на автора (author = null).
    }
}

