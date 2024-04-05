package com.rx.ecatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rx.ecatalog.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
}
