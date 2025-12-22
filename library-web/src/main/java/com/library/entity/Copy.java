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
@Table(name = "copy")
public class Copy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Инвентарный номер не может быть пустым")
    @Size(max=6, message = "Инвентарный номер не должен содержать больше 6 символов")
    @Column(name = "inventory_number", nullable = false, unique = true)
    private String inventoryNumber;

    @Column(nullable = false)
    @Builder.Default
    private String status = "Доступно";

    // произведения - копии
    @ManyToOne
    @JoinColumn(name = "oeuvre_id", nullable = false)
    private Oeuvre oeuvre;

}
