package com.library.service;

import com.library.entity.Oeuvre;
import com.library.repository.OeuvreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OeuvreService {

    private final OeuvreRepository oeuvreRepository;

    public List<Oeuvre> getAllOeuvres() {
        return oeuvreRepository.findAll();
    }

    public Optional<Oeuvre> getOeuvreById(Long id) {
        return oeuvreRepository.findById(id);
    }

    public Oeuvre saveOeuvre(Oeuvre oeuvre) {
        return oeuvreRepository.save(oeuvre);
    }

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

    public void deleteOeuvre(Long id) {
        if (!oeuvreRepository.existsById(id)) {
            throw new RuntimeException("Произведение не найдено");
        }
        oeuvreRepository.deleteById(id);
    }
}
