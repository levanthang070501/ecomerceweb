package com.example.demo.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.productDTO;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.service.CategoryService;
import com.example.demo.service.productService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;


@Controller
public class controller {
	public String uploadDir=System.getProperty("user.dir")+"/src/main/resources/static/productImages";
	@Autowired
	CategoryService categoryService;
	@Autowired
	productService productService;
	
@RequestMapping("/admin")
	public String adminhome()
	{
		return "adminHome";
	}
@RequestMapping("/admin/categories")
public String getcat(Model model)
{
	model.addAttribute("categories", categoryService.getallcategory());
	return "categories";
}

@GetMapping("/admin/categories/add")
public String getcataddd(Model model)
{
	model.addAttribute("category", new Category());
	return "categoriesAdd";
}

@PostMapping("/admin/categories/add")
public String postcataddd(@ModelAttribute("category") Category category)
{
	categoryService.addCategory(category);
	return "redirect:/admin/categories";
}

@GetMapping("/admin/categories/delete/{id}")
public String deleteCat(@PathVariable int id)
{
	categoryService.removeCategoryById(id);
	return "redirect:/admin/categories";
}

@GetMapping("/admin/categories/update/{id}")
public String updateCat(@PathVariable int id,Model model)
{
	Optional<Category> category=categoryService.getCategoryByid(id);
    if(category.isPresent())
    {
    	model.addAttribute("category",category.get());
    	return "categoriesAdd";
    }
    else
    {
    	return "404";
    }
}
//product section
@GetMapping("/admin/products")
public String products(Model model)
{
	model.addAttribute("products", productService.getallproduct());
	return "products";
}
@GetMapping("/admin/products/add")
public String productAddget(Model model)
{
	model.addAttribute("productDTO", new productDTO());
	model.addAttribute("categories", categoryService.getallcategory());
	return "productsAdd";
}
@PostMapping("/admin/products/add")
public String productAddPost(@ModelAttribute("productDTO") productDTO productDTO,
		                      @RequestParam("productImage") MultipartFile file,
		                      @RequestParam("imgName") String imgName) throws IOException
{
	Product product=new Product();
	product.setId(productDTO.getId());
	product.setName(productDTO.getName());
	product.setCategory(categoryService.getCategoryByid(productDTO.getCategoryId()).get());
	product.setPrice(productDTO.getPrice());
	product.setWeight(productDTO.getWeight());
	product.setDescription(productDTO.getDescription());
	String imageUUID;
	if(!file.isEmpty())
	{
		imageUUID=file.getOriginalFilename();
		Path fileNameAndPath=Paths.get(uploadDir,imageUUID);
		Files.write(fileNameAndPath, file.getBytes());
	}
	else
	{
		imageUUID=imgName;
	}
	product.setImageName(imageUUID);
	productService.addproduct(product);
	return "redirect:/admin/products";
}

@GetMapping("/admin/product/delete/{id}")
public String deleteProduct(@PathVariable long id)
{
	productService.removeProductById(id);
	return "redirect:/admin/products";
}
@GetMapping("/admin/product/update/{id}")
public String updateProduct(@PathVariable long id,Model model)
{
	Product product=productService.getProductById(id).get();
	productDTO productDTO =new productDTO();
	productDTO.setId(product.getId());
	productDTO.setName(product.getName());
	productDTO.setCategoryId(product.getCategory().getId());
	productDTO.setPrice(product.getPrice());
	productDTO.setWeight(product.getWeight());
	productDTO.setDescription(product.getDescription());
	productDTO.setImageName(product.getImageName());
	model.addAttribute("categories", categoryService.getallcategory());
	model.addAttribute("productDTO", productDTO);
	return "productsAdd";
}



}
