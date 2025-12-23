package com.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Сущность, представляющая экземпляр (копию) литературного произведения.
 * Каждая копия имеет уникальный инвентарный номер, статус
 * и принадлежит конкретному произведению.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "copy")
public class Copy {

    /**
     * Уникальный идентификатор копии.
     * Генерируется автоматически базой данных.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Инвентарный номер экземпляра.
     * Требования:
     * - не пустой;
     * - длина строго 6 символов;
     * - должен быть уникальным в базе данных.
     */
    @NotBlank(message = "Инвентарный номер не может быть пустым")
    @Size(max = 6, message = "Инвентарный номер не должен содержать больше 6 символов")
    @Size(min = 5, message = "Инвентарный номер должен содержать 6 символов")
    @Column(name = "inventory_number", nullable = false, unique = true)
    private String inventoryNumber;

    /**
     * Статус экземпляра.
     * По умолчанию — «Доступно».
     * Может принимать значения, определённые в CopyService.
     */
    @Column(nullable = false)
    @Builder.Default
    private String status = "Доступно";

    /**
     *  Произведение, к которому относится экземпляр
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oeuvre_id")
    private Oeuvre oeuvre;
}
