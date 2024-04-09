package com.rx.ecatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rx.ecatalog.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
}
