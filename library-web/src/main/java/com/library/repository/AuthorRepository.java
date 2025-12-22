package com.library.repository;

import com.library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностью {@link Author}.
 * Предоставляет стандартные CRUD‑операции и взаимодействие с базой данных
 * через механизм Spring Data JPA.
 *
 * Наследование от {@link JpaRepository} позволяет автоматически получить:
 * - поиск по ID;
 * - сохранение и обновление сущностей;
 * - удаление;
 * - получение всех записей;
 * - пагинацию и сортировку.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
