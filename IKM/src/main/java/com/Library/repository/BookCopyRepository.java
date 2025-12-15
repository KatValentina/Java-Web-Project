package com.Library.repository;

import com.Library.entity.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    List<BookCopy> findByBookId(Long bookId);
    List<BookCopy> findByStatus(String status);
    Optional<BookCopy> findByInventoryNumber(String inventoryNumber);
    boolean existsByInventoryNumber(String inventoryNumber);
}