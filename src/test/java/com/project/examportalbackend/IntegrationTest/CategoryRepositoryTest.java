package com.project.examportalbackend.IntegrationTest;

import com.project.examportalbackend.models.Category;
import com.project.examportalbackend.repository.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

// Repository Layer Testing - These tests typically involve testing database
// interactions using @DataJpaTest. We have checked if our repositories can
// save and retrieve entities correctly.
@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void saveCategoryTest() {
        Category category = new Category();
        category.setTitle("Technology");
        Category savedCategory = categoryRepository.save(category);

        assertNotNull(savedCategory);
        assertEquals("Technology", savedCategory.getTitle());
    }

    @Test
    public void findCategoryByIdTest() {
        Category cat = new Category();
        cat.setTitle("Technology");
        Category category = categoryRepository.save(cat);

        Optional<Category> foundCategory = categoryRepository.findById(category.getCatId());

        assertTrue(foundCategory.isPresent());
        assertEquals("Technology", foundCategory.get().getTitle());
    }
}

