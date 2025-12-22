package com.library.service;

import com.library.entity.Copy;
import com.library.repository.CopyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CopyService {

    private final CopyRepository copyRepository;

    public CopyService(CopyRepository copyRepository) {
        this.copyRepository = copyRepository;
    }

    public List<Copy> getAll() {
        return copyRepository.findAll();
    }

    public Copy getById(Long id) {
        return copyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Копия не найдена"));
    }

    public Copy create(Copy copy) {
        if (copyRepository.existsByInventoryNumber(copy.getInventoryNumber())) {
            throw new IllegalArgumentException("Копия с таким инвентарным номером уже существует");
        }
        return copyRepository.save(copy);
    }

    public Copy update(Long id, Copy updated) {
        Copy existing = getById(id);

        // Проверяем уникальность только если номер изменился
        if (!existing.getInventoryNumber().equals(updated.getInventoryNumber())) {
            if (copyRepository.existsByInventoryNumber(updated.getInventoryNumber())) {
                throw new IllegalArgumentException("Копия с таким инвентарным номером уже существует");
            }
        }

        existing.setInventoryNumber(updated.getInventoryNumber());
        existing.setStatus(updated.getStatus());
        existing.setOeuvre(updated.getOeuvre());

        return copyRepository.save(existing);
    }

    public void delete(Long id) {
        copyRepository.deleteById(id);
    }

    public List<String> getStatuses() {
        return List.of("Доступно", "Выдано", "Утеряно", "Повреждено");
    }
}
