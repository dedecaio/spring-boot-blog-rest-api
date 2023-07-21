package com.caioprogramador.blog.service;

import com.caioprogramador.blog.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO addCategory(CategoryDTO categoryDTO);

    //List<CategoryDTO>
    CategoryDTO getCategory(Long id);

    void deleteCategory(Long id);

    List<CategoryDTO> getAllCategories();

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);
}
