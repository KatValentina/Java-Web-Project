package com.library.repository;

import com.library.entity.Copy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CopyRepository extends JpaRepository<Copy, Long> {

    boolean existsByInventoryNumber(String inventoryNumber);
}
