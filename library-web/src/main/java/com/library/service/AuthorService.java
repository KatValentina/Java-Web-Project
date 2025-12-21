package com.library.service;

import com.library.entity.Author;
import com.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional //метод или класс должен выполняться внутри транзакции.
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired //
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // получаем всех авторов
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    //получаем авторов по id
    public Optional<Author> getAuthorsById(Long id) {
        return authorRepository.findById(id);
    }

    //Сохранение автора
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    // обновляем уже существующего автора
    public Author updateAuthor(Long id, Author authorDetails) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Альбом не найден с ID: " + id));

        author.setName(authorDetails.getName());
        author.setBirthDate(authorDetails.getBirthDate());
        author.setNationality(authorDetails.getNationality());
        author.setBiography(authorDetails.getBiography());

        return authorRepository.save(author);
    }

    //удаляем автора
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    //получить количество авторов
    public long getAlbumCount() {
        return authorRepository.count();
    }

}
