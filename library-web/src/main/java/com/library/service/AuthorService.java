package com.library.service;

import com.library.entity.Author;
import com.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Сервисный слой для работы с сущностью {@link Author}.
 * Содержит бизнес‑логику, связанную с получением, созданием,
 * обновлением и удалением авторов.
 *
 * Все методы выполняются в рамках транзакции.
 */
@Service
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;

    /**
     * Конструктор сервиса с внедрением зависимости репозитория авторов.
     *
     * @param authorRepository репозиторий для работы с авторами
     */
    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    /**
     * Возвращает список всех авторов.
     *
     * @return список авторов
     */
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    /**
     * Ищет автора по идентификатору.
     *
     * @param id идентификатор автора
     * @return Optional с найденным автором или пустой Optional, если автор не найден
     */
    public Optional<Author> getAuthorsById(Long id) {
        return authorRepository.findById(id);
    }

    /**
     * Сохраняет нового автора в базе данных.
     *
     * @param author объект автора
     * @return сохранённый автор
     */
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    /**
     * Обновляет данные существующего автора.
     *
     * @param id            идентификатор автора
     * @param authorDetails объект с обновлёнными данными
     * @return обновлённый автор
     * @throws RuntimeException если автор с указанным ID не найден
     */
    public Author updateAuthor(Long id, Author authorDetails) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Автор не найден с ID: " + id));

        author.setName(authorDetails.getName());
        author.setBirthDate(authorDetails.getBirthDate());
        author.setNationality(authorDetails.getNationality());
        author.setBiography(authorDetails.getBiography());

        return authorRepository.save(author);
    }

    /**
     * Удаляет автора по идентификатору.
     *
     * @param id идентификатор автора
     */
    @Transactional
    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Автор не найден"));

        authorRepository.delete(author);
    }


    /**
     * Возвращает количество авторов в базе данных.
     *
     * @return число авторов
     */
    public long getAuthorCount() {
        return authorRepository.count();
    }
}
