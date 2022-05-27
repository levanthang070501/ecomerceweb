package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.global.GlobalData;
import com.example.demo.service.CategoryService;
import com.example.demo.service.productService;

@Controller
public class HomeController {
@Autowired
CategoryService categoryService;
@Autowired
productService productService;

@GetMapping({"/","/home"})
public String home(Model model)
{
	model.addAttribute("cartCount",GlobalData.cart.size());
	return "index";
}
@GetMapping("/shop")
public String shop(Model model)
{
	model.addAttribute("categories",categoryService.getallcategory());
	model.addAttribute("products",productService.getallproduct());
	model.addAttribute("cartCount",GlobalData.cart.size());
	return "shop";
}
@GetMapping("/shop/category/{id}")
public String shopByCategory(Model model,@PathVariable int id)
{
	model.addAttribute("categories",categoryService.getallcategory());
	model.addAttribute("products",productService.getAllProductByCategory(id));
	model.addAttribute("cartCount",GlobalData.cart.size());
	return "shop";
}
@GetMapping("/shop/viewproduct/{id}")
public String viewProduct(Model model,@PathVariable int id)
{
	model.addAttribute("product",productService.getProductById(id).get());
	model.addAttribute("cartCount",GlobalData.cart.size());
	return "viewProduct";
}
}
