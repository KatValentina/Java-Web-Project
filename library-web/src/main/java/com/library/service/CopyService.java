package com.library.service;

import com.library.entity.Copy;
import com.library.repository.CopyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервисный слой для работы с сущностью {@link Copy}.
 * Содержит бизнес‑логику, связанную с созданием, обновлением,
 * получением и удалением экземпляров произведений.
 */
@Service
public class CopyService {

    private final CopyRepository copyRepository;

    /**
     * Конструктор сервиса с внедрением зависимости репозитория копий.
     *
     * @param copyRepository репозиторий для работы с экземплярами произведений
     */
    public CopyService(CopyRepository copyRepository) {
        this.copyRepository = copyRepository;
    }

    /**
     * Возвращает список всех копий.
     *
     * @return список экземпляров произведений
     */
    public List<Copy> getAll() {
        return copyRepository.findAll();
    }

    /**
     * Ищет копию по идентификатору.
     *
     * @param id идентификатор копии
     * @return найденная копия
     * @throws RuntimeException если копия не найдена
     */
    public Copy getById(Long id) {
        return copyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Копия не найдена"));
    }

    /**
     * Создаёт новую копию произведения.
     * Перед сохранением проверяет уникальность инвентарного номера.
     *
     * @param copy объект копии
     * @return сохранённая копия
     * @throws IllegalArgumentException если инвентарный номер уже существует
     */
    public Copy create(Copy copy) {
        if (copyRepository.existsByInventoryNumber(copy.getInventoryNumber())) {
            throw new IllegalArgumentException("Копия с таким инвентарным номером уже существует");
        }
        return copyRepository.save(copy);
    }

    /**
     * Обновляет данные существующей копии.
     * Проверяет уникальность инвентарного номера, если он был изменён.
     *
     * @param id      идентификатор копии
     * @param updated объект с обновлёнными данными
     * @return обновлённая копия
     * @throws IllegalArgumentException если новый инвентарный номер уже существует
     */
    public Copy update(Long id, Copy updated) {
        Copy existing = getById(id);

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

    /**
     * Удаляет копию по идентификатору.
     *
     * @param id идентификатор копии
     */
    public void delete(Long id) {
        copyRepository.deleteById(id);
    }

    /**
     * Возвращает список возможных статусов экземпляра.
     *
     * @return список статусов
     */
    public List<String> getStatuses() {
        return List.of("Доступно", "Выдано", "Утеряно", "Повреждено");
    }
}
