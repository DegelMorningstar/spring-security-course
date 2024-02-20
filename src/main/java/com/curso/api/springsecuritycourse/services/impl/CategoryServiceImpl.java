package com.curso.api.springsecuritycourse.services.impl;

import com.curso.api.springsecuritycourse.dto.SaveCategory;
import com.curso.api.springsecuritycourse.dto.SaveProduct;
import com.curso.api.springsecuritycourse.exception.ObjectNotFoundException;
import com.curso.api.springsecuritycourse.persistence.entity.Category;
import com.curso.api.springsecuritycourse.persistence.entity.Product;
import com.curso.api.springsecuritycourse.persistence.repository.CategoryRepository;
import com.curso.api.springsecuritycourse.persistence.repository.ProductRepository;
import com.curso.api.springsecuritycourse.services.CategoryService;
import com.curso.api.springsecuritycourse.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Optional<Category> findOneById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @Override
    public Category createOne(SaveCategory saveCategory) {
        Category category = new Category();
        category.setName(saveCategory.getName());
        category.setStatus(Category.CategoryStatus.ENABLED);
        return categoryRepository.save(category);
    }

    @Override
    public Category updateOneById(Long categoryId, SaveCategory saveCategory) {
        Category categoryFromDB = categoryRepository.findById(categoryId)
                .orElseThrow( () -> new ObjectNotFoundException(
                        "Category not found with id "+categoryId
                ));
        categoryFromDB.setName(saveCategory.getName());
        return categoryRepository.save(categoryFromDB);
    }

    @Override
    public Category disableOneById(Long categoryId) {
        Category categoryFromDB = categoryRepository.findById(categoryId)
                .orElseThrow( () -> new ObjectNotFoundException(
                        "Category not found with id "+categoryId
                ));
        categoryFromDB.setStatus(Category.CategoryStatus.DISABLED);
        return categoryRepository.save(categoryFromDB);
    }
}
