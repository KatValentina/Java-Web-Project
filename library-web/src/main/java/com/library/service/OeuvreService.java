package com.library.service;

import com.library.entity.Oeuvre;
import com.library.repository.OeuvreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервисный слой для работы с сущностью {@link Oeuvre}.
 * Содержит бизнес‑логику, связанную с получением, созданием,
 * обновлением и удалением литературных произведений.
 */
@Service
@RequiredArgsConstructor
public class OeuvreService {

    private final OeuvreRepository oeuvreRepository;

    /**
     * Возвращает список всех произведений.
     *
     * @return список произведений
     */
    public List<Oeuvre> getAllOeuvres() {
        return oeuvreRepository.findAll();
    }

    /**
     * Ищет произведение по идентификатору.
     *
     * @param id идентификатор произведения
     * @return Optional с найденным произведением или пустой Optional, если произведение не найдено
     */
    public Optional<Oeuvre> getOeuvreById(Long id) {
        return oeuvreRepository.findById(id);
    }

    /**
     * Сохраняет новое произведение в базе данных.
     *
     * @param oeuvre объект произведения
     * @return сохранённое произведение
     */
    public Oeuvre saveOeuvre(Oeuvre oeuvre) {
        return oeuvreRepository.save(oeuvre);
    }

    /**
     * Обновляет данные существующего произведения.
     *
     * @param id      идентификатор произведения
     * @param updated объект с обновлёнными данными
     * @return обновлённое произведение
     * @throws RuntimeException если произведение с указанным ID не найдено
     */
    public Oeuvre updateOeuvre(Long id, Oeuvre updated) {
        return oeuvreRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(updated.getTitle());
                    existing.setGenre(updated.getGenre());
                    existing.setPublishedYear(updated.getPublishedYear());
                    existing.setAuthor(updated.getAuthor());
                    return oeuvreRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Произведение не найдено"));
    }

    /**
     * Удаляет произведение по идентификатору.
     *
     * @param id идентификатор произведения
     * @throws RuntimeException если произведение не найдено
     */
    public void deleteOeuvre(Long id) {
        if (!oeuvreRepository.existsById(id)) {
            throw new RuntimeException("Произведение не найдено");
        }
        oeuvreRepository.deleteById(id);
    }
}
