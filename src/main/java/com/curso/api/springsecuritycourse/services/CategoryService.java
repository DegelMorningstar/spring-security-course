package com.curso.api.springsecuritycourse.services;

import com.curso.api.springsecuritycourse.dto.SaveCategory;
import com.curso.api.springsecuritycourse.persistence.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryService {
    Page<Category> findAll(Pageable pageable);

    Optional<Category> findOneById(Long categoryId);

    Category createOne(SaveCategory saveCategory);

    Category updateOneById(Long categoryId, SaveCategory saveCategory);

    Category disableOneById(Long categoryId);
}
