package com.project.examportalbackend.ControllerTest;

import com.project.examportalbackend.controllers.CategoryController;
import com.project.examportalbackend.models.Category;
import com.project.examportalbackend.models.Quiz;
import com.project.examportalbackend.services.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryControllerTest {

    @Autowired
    private CategoryController categoryController;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void testAddCategory_Success() {
        Category category = new Category();
        category.setTitle("Test Category");
        category.setDescription("This is a test category");

        Mockito.when(categoryService.addCategory(category)).thenReturn(category);

        ResponseEntity<?> response = categoryController.addCategory(category);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(category, response.getBody());
    }

    @Test
    public void testAddCategory_Failure() {
        Category category = new Category();
        category.setTitle("Test Category");
        category.setDescription("Test Description");

        when(categoryService.addCategory(any(Category.class)))
                .thenThrow(new RuntimeException("Mock Exception"));

        ResponseEntity<?> response = categoryController.addCategory(category);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("There is an Exception", response.getBody());

        verify(categoryService, times(1)).addCategory(any(Category.class));
    }

    @Test
    public void testGetCategories_Success() {
        List<Category> categories = new ArrayList<>();
        Category category1 = new Category();
        category1.setTitle("Category 1");
        category1.setDescription("Description 1");
        Category category2 = new Category();
        category2.setTitle("Category 2");
        category2.setDescription("Description 2");
        categories.add(category1);
        categories.add(category2);

        Mockito.when(categoryService.getCategories()).thenReturn(categories);

        ResponseEntity<?> response = categoryController.getCategories();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof List);
        List<Category> responseCategories = (List<Category>) response.getBody();
        assertEquals(categories.size(), responseCategories.size());
    }

    @Test
    public void testGetCategories_Failure() {
        when(categoryService.getCategories()).thenThrow(new RuntimeException("Mock Exception"));

        ResponseEntity<?> response = categoryController.getCategories();

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("There is an Exception", response.getBody());

        verify(categoryService, times(1)).getCategories();
    }

    @Test
    public void testGetCategoryById_Success() {
        Long categoryId = 1L;
        Category category = new Category();
        category.setTitle("Category 1");
        category.setDescription("Description 1");

        Mockito.when(categoryService.getCategory(categoryId)).thenReturn(category);

        ResponseEntity<?> response = categoryController.getCategory(categoryId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(category, response.getBody());
    }

    @Test
    public void testGetCategoryById_NotFound() {
        Long categoryId = 1L;

        Mockito.when(categoryService.getCategory(categoryId)).thenReturn(null);

        ResponseEntity<?> response = categoryController.getCategory(categoryId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetCategoryById_Failure() {
        Long categoryId = 1L;
        when(categoryService.getCategory(categoryId)).thenThrow(new RuntimeException("Mock Exception"));

        ResponseEntity<?> response = categoryController.getCategory(categoryId);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("There is an Exception", response.getBody());

        // Verify
        verify(categoryService, times(1)).getCategory(categoryId);
    }

    @Test
    public void testUpdateCategory_Success() {
        Long categoryId = 1L;
        Category existingCategory = new Category();
        existingCategory.setTitle("Old Category");
        existingCategory.setDescription("Old Description");
        Category updatedCategory = new Category();
        updatedCategory.setTitle("Updated Category");
        updatedCategory.setDescription("Updated Description");
        Mockito.when(categoryService.getCategory(categoryId)).thenReturn(existingCategory);
        Mockito.when(categoryService.updateCategory(updatedCategory)).thenReturn(updatedCategory);

        ResponseEntity<?> response = categoryController.updateCategory(categoryId, updatedCategory);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedCategory, response.getBody());
    }

    @Test
    public void testUpdateCategory_NotFound() {
        Long categoryId = 1L;
        Category updatedCategory = new Category();
        updatedCategory.setTitle("Updated Category");
        updatedCategory.setDescription("Updated Description");
        when(categoryService.getCategory(categoryId)).thenReturn(null);

        ResponseEntity<?> response = categoryController.updateCategory(categoryId, updatedCategory);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Category with id"));
    }

    @Test
    public void testUpdateCategory_Failure() {
        Long categoryId = 1L;
        Category category = new Category();
        category.setCatId(categoryId);
        category.setTitle("Updated Title");
        category.setDescription("Updated Description");

        when(categoryService.getCategory(categoryId)).thenThrow(new RuntimeException("Mock Exception"));

        ResponseEntity<?> response = categoryController.updateCategory(categoryId, category);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        assertEquals("There is an Exception", response.getBody());

        verify(categoryService, times(1)).getCategory(categoryId);
        verify(categoryService, never()).updateCategory(any(Category.class));
    }

    @Test
    public void testDeleteCategory_Success() {
        Long categoryId = 1L;

        doNothing().when(categoryService).deleteCategory(categoryId);

        ResponseEntity<?> response = categoryController.deleteCategory(categoryId);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(true, response.getBody());

        verify(categoryService, times(1)).deleteCategory(categoryId);
    }

    @Test
    public void testDeleteCategory_Failure() {
        Long categoryId = 1L;

        doThrow(new RuntimeException("Mock Exception")).when(categoryService).deleteCategory(categoryId);

        ResponseEntity<?> response = categoryController.deleteCategory(categoryId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        assertEquals("There is an Exception", response.getBody());

        verify(categoryService, times(1)).deleteCategory(categoryId);
    }

    @Test
    public void testToString_Success() {
        // Create a Category object
        Category category = new Category();
        category.setCatId(1L);
        category.setTitle("Science");
        category.setDescription("Category for Science Quizzes");

        // Create Quiz objects and add them to the Category
        Quiz quiz1 = new Quiz();
        quiz1.setTitle("Physics Quiz");
        quiz1.setDescription("A quiz on Physics");

        Quiz quiz2 = new Quiz();
        quiz2.setTitle("Chemistry Quiz");
        quiz2.setDescription("A quiz on Chemistry");

        List<Quiz> quizzes = new ArrayList<>();
        quizzes.add(quiz1);
        quizzes.add(quiz2);

        // Set quizzes list to the Category
        category.setQuizzes(quizzes);

        // Test the toString method (verify if quizzes field is included in the output)
        String toStringOutput = category.toString();

        // Verify that the quizzes list is included in the toString output
        assertTrue(toStringOutput.contains("quizzes"));
        assertTrue(toStringOutput.contains("Physics Quiz"));
        assertTrue(toStringOutput.contains("Chemistry Quiz"));
    }

}
