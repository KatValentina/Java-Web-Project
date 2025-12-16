package com.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter //Lombok автоматически сгенерирует для Java байт-код, который при сборке
@Setter //в файлы .class, вставляет необходимые для реализации желаемого поведения
@NoArgsConstructor
@AllArgsConstructor
@ToString(includeFieldNames = true)// выводим всю информацию об авторе (id="...", name="...")
@Entity //указывает, что класс представляет собой сущность базы данных.
@Table(name = "author")//имя таблицы в базе, с которой будет связан данный класс
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
    @Size(max=150, message = "Должна содержать хотя бы 150 символов")
    private String nationality;

    @Size(max = 500, message = "Биография не должна превышать 500 символов")
    @Column(name = "biography", length = 500)
    private String biography;

}

