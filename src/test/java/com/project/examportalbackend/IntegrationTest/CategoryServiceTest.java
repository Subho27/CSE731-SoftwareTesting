package com.project.examportalbackend.IntegrationTest;

import com.project.examportalbackend.models.Category;
import com.project.examportalbackend.repository.CategoryRepository;
import com.project.examportalbackend.services.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// Service Layer Testing - This ensures that business logic works as
// expected. We have used @MockBean to mock dependencies for isolated testing.
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    public void addCategoryTest() {
        Category category = new Category();
        category.setTitle("Technology");

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category createdCategory = categoryService.addCategory(category);

        assertNotNull(createdCategory);
        assertEquals("Technology", createdCategory.getTitle());
    }

    @Test
    public void getCategoriesTest() {
        Category cat1 = new Category();
        Category cat2 = new Category();
        cat1.setTitle("Technology");
        cat2.setTitle("Science");

        List<Category> categories = Arrays.asList(cat1, cat2);
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.getCategories();

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}

