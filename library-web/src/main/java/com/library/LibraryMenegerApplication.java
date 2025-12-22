package com.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс приложения LibraryManager.
 * Запускает Spring Boot и инициализирует все компоненты приложения.
 */
@SpringBootApplication
public class LibraryMenegerApplication {

    /**
     * Точка входа в приложение.
     * Выполняет запуск Spring Boot.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        SpringApplication.run(LibraryMenegerApplication.class, args);
    }
}
