package com.gloryjewel.eatgo.application;

import com.gloryjewel.eatgo.domain.Category;
import com.gloryjewel.eatgo.domain.CategoryRepository;
import com.gloryjewel.eatgo.domain.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories() {
        return (List<Category>) categoryRepository.findAll();
    }

    public Category addCategory(String name) {
        return categoryRepository.save(
                Category.builder()
                        .name(name)
                        .build());
    }
}
