package com.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Сущность, представляющая литературное произведение.
 * Содержит основную информацию о произведении, включая название,
 * жанр, год публикации и связь с автором.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "oeuvre")
public class Oeuvre {

    /**
     * Уникальный идентификатор произведения.
     * Генерируется автоматически базой данных.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название произведения.
     * Требования:
     * - не пустое;
     * - длина не более 5000 символов.
     */
    @NotBlank(message = "Имя произведения не может быть пустым")
    @Size(max = 5000, message = "Название не должно содержать более 5000 символов")
    @Column(nullable = false)
    private String title;

    /**
     * Жанр произведения.
     * Требования:
     * - не пустой;
     * - длина не более 5000 символов.
     */
    @NotBlank(message = "Жанр не может быть пустым")
    @Size(max = 5000, message = "Название жанра не должно содержать более 5000 символов")
    @Column(nullable = false)
    private String genre;

    /**
     * Год публикации произведения.
     * Требования:
     * - не ранее 1454 года (год изобретения печатного станка);
     * - не позднее 2026 года.
     */
    @Min(value = 1454, message = "Год опубликования произведения должен быть не раньше 1454")
    @Max(value = 2026, message = "Год опубликования должен быть не позже 2026")
    @Column(name = "published_year", nullable = false)
    private Integer publishedYear;

    /**
     * Автор произведения.
     * Связь многие‑к‑одному.
     * Загрузка ленивой стратегией для оптимизации производительности.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;
}
