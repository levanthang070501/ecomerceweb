package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Product;

public interface productRepository extends JpaRepository<Product, Long>{
List<Product> findAllByCategory_Id(int id);
}
