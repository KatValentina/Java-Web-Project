package com.library.repository;

import com.library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// создаем репозиторий для работы с авторами в базе данных
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

}

