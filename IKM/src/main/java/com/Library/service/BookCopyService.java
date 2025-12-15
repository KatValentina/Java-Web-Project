package com.Library.service;

import com.Library.entity.BookCopy;
import com.Library.repository.BookCopyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookCopyService {

    private final BookCopyRepository bookCopyRepository;

    public List<BookCopy> getAllCopies() {
        return bookCopyRepository.findAll();
    }

    public BookCopy getCopyById(Long id) {
        return bookCopyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book copy not found with id: " + id));
    }

    @Transactional
    public BookCopy saveCopy(BookCopy copy) {
        return bookCopyRepository.save(copy);
    }

    @Transactional
    public void deleteCopy(Long id) {
        bookCopyRepository.deleteById(id);
    }

    public List<BookCopy> getCopiesByBook(Long bookId) {
        return bookCopyRepository.findByBookId(bookId);
    }

    public List<BookCopy> getCopiesByStatus(String status) {
        return bookCopyRepository.findByStatus(status);
    }

    @Transactional
    public BookCopy borrowCopy(Long id, String borrowerName) {
        BookCopy copy = getCopyById(id);
        if (!"AVAILABLE".equals(copy.getStatus())) {
            throw new RuntimeException("Book is not available");
        }
        copy.setStatus("BORROWED");
        copy.setBorrowerName(borrowerName);
        copy.setBorrowDate(LocalDate.now());
        copy.setDueDate(LocalDate.now().plusWeeks(2));
        return saveCopy(copy);
    }

    @Transactional
    public BookCopy returnCopy(Long id) {
        BookCopy copy = getCopyById(id);
        copy.setStatus("AVAILABLE");
        copy.setBorrowerName(null);
        copy.setReturnDate(LocalDate.now());
        copy.setBorrowDate(null);
        copy.setDueDate(null);
        return saveCopy(copy);
    }

    public long countCopies() {
        return bookCopyRepository.count();
    }
}