package com.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "oeuvre")
//@ToString(exclude = {"author", "copies"})
public class Oeuvre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя произведения не может быть пустым")
    @Size(max=5000, message = "Название не должно содержать 5000 символов")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Жанр не может быть пустым")
    @Size(max=5000, message = "Название жанра не должно содержать 5000 символов")
    @Column(nullable = false)
    private String genre;

    @Min(value = 1454, message = "Год опубликования произведения должен быть не раньше, чем 1454")
    @Max(value = 2026, message = "Год опубликования должен быть не позже 2026. Вы не можете ввести год, который ещё" +
            "не наступил!")
    @Column(name = "published_year",nullable = false)
    private Integer publishedYear;

    // Произведение - Автор
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;


}
