package com.gloryjewel.eatgo.domain;

import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Category save(Category category);
}
