package com.gloryjewel.eatgo.interfaces;

import com.gloryjewel.eatgo.application.CategoryService;
import com.gloryjewel.eatgo.application.RegionService;
import com.gloryjewel.eatgo.domain.Category;
import com.gloryjewel.eatgo.domain.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public List<Category> list(){

        List<Category> categories = categoryService.getCategories();

        return categories;
    }

    @PostMapping("/categories")
    public ResponseEntity create(@RequestBody Category resource) throws URISyntaxException {

        Category category = categoryService.addCategory(resource.getName());

        URI location = new URI("/categories/" + category.getId());
        return ResponseEntity.created(location).body("{}");
    }
}
