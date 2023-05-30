package com.blog.services;

import java.util.List;

import com.blog.payload.CategoryDto;

public interface CategoryService {

	// Create category
	public CategoryDto createCategory(CategoryDto categoryDto);
	
	// Update Category.
	
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	
	// Delete Category
	
	public void deleteCategory(Integer categoryId);
	
	// get category by Id
	
	public CategoryDto getCategory(Integer categoryId);
	
	
	
	// get All Category.
	
	public List<CategoryDto> allCategories();
	
	
}
