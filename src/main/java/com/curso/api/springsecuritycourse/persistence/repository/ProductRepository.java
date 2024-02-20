package com.curso.api.springsecuritycourse.persistence.repository;

import com.curso.api.springsecuritycourse.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
