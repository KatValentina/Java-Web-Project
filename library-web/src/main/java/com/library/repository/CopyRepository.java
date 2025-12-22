package com.library.repository;

import com.library.entity.Copy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностью {@link Copy}.
 * Предоставляет стандартные CRUD‑операции и взаимодействие с базой данных
 * через Spring Data JPA.
 *
 * Дополнительно содержит метод для проверки уникальности
 * инвентарного номера экземпляра.
 */
@Repository
public interface CopyRepository extends JpaRepository<Copy, Long> {

    /**
     * Проверяет, существует ли копия с указанным инвентарным номером.
     *
     * @param inventoryNumber инвентарный номер экземпляра
     * @return true — если копия с таким номером уже существует; false — если нет
     */
    boolean existsByInventoryNumber(String inventoryNumber);
}
