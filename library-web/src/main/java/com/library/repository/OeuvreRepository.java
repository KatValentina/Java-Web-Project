package com.library.repository;

import com.library.entity.Oeuvre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностью {@link Oeuvre}.
 * Предоставляет стандартные CRUD‑операции и взаимодействие с базой данных
 * через Spring Data JPA.
 *
 * Наследование от {@link JpaRepository} позволяет автоматически получить:
 * - поиск по идентификатору;
 * - сохранение и обновление сущностей;
 * - удаление;
 * - получение всех записей;
 * - поддержку пагинации и сортировки.
 */
@Repository
public interface OeuvreRepository extends JpaRepository<Oeuvre, Long> {
}
