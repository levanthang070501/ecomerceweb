package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.repository.productRepository;

@Service
public class productService {
	@Autowired
	productRepository productRepository;
	public List<Product> getallproduct()
	{
		return productRepository.findAll();
	}
	public void addproduct(Product product)
	{
		productRepository.save(product);
	}
	public void removeProductById(long id)
	{
		productRepository.deleteById(id);
	}
	public Optional<Product> getProductById(long id)
	{
		return productRepository.findById(id);
	}
	public List<Product> getAllProductByCategory(int id)
	{
		return productRepository.findAllByCategory_Id(id);
	}
}
