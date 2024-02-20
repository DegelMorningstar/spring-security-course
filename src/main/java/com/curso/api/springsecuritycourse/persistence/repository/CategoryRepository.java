package com.curso.api.springsecuritycourse.persistence.repository;

import com.curso.api.springsecuritycourse.persistence.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
