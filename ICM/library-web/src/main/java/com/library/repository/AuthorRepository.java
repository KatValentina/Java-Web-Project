package com.library.repository;

import com.library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// создаем репозиторий для работы с авторами в базе данных
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    //поиск по имени
    List<Author> findByName(String name);

    //поиск по национальности
    List<Author> findByNational(String national);
}
