package com.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Сущность, представляющая автора литературных произведений.
 * Содержит основную информацию об авторе, а также связь с его произведениями.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(includeFieldNames = true)
@Entity
@Table(name = "author")
@Builder
public class Author {

    /**
     * Уникальный идентификатор автора.
     * Генерируется автоматически базой данных.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Полное имя автора.
     * Требования:
     * - не пустое;
     * - длина от 4 до 150 символов;
     * - только буквы (латиница или кириллица);
     * - допускается один пробел между словами.
     */
    @NotBlank(message = "Имя автора не может быть пустым")
    @Size(min = 4, max = 150, message = "Имя автора должно содержать от 4 до 150 символов")
    @Pattern(
            regexp = "^[A-Za-zА-Яа-яЁё]+(?: [A-Za-zА-Яа-яЁё]+)*$",
            message = "Имя может содержать только буквы и один пробел между словами"
    )
    @Column(name = "name", nullable = false, length = 150)
    private String name;

    /**
     * Дата рождения автора.
     * Должна быть в прошлом.
     */
    @Past(message = "Дата рождения должна быть в прошлом")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    /**
     * Национальность автора.
     * Максимальная длина — 150 символов.
     */
    @Column(name = "nationality")
    @Size(max = 150, message = "Национальность не должна превышать 150 символов")
    private String nationality;

    /**
     * Краткая биография автора.
     * Максимальная длина — 500 символов.
     */
    @Size(max = 500, message = "Биография не должна превышать 500 символов")
    @Column(name = "biography", length = 500)
    private String biography;

    /**
     * Список произведений, написанных автором.
     * Связь один-ко-многим.
     * orphanRemoval = true — удаляет произведения, если они больше не связаны с автором.
     */
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Oeuvre> oeuvres = new ArrayList<>();

    /**
     * Добавляет произведение автору и устанавливает связь в обе стороны.
     *
     * @param oeuvre произведение, которое нужно добавить
     */
    public void addOeuvre(Oeuvre oeuvre) {
        oeuvres.add(oeuvre);
        oeuvre.setAuthor(this);
    }

    /**
     * Удаляет произведение у автора и разрывает связь.
     *
     * @param oeuvre произведение, которое нужно удалить
     */
    public void removeOeuvre(Oeuvre oeuvre) {
        oeuvres.remove(oeuvre);
        oeuvre.setAuthor(null);
    }
}
