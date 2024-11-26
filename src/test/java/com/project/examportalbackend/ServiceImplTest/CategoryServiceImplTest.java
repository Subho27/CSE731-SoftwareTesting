package com.project.examportalbackend.ServiceImplTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.project.examportalbackend.models.Category;
import com.project.examportalbackend.repository.CategoryRepository;
import com.project.examportalbackend.services.implementation.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        category = new Category();
        category.setCatId(1L);
        category.setTitle("Science");
        category.setDescription("Science related subjects");
    }

    @Test
    public void testGetCategory_Success() {
        // Arrange: Mock the findById method to return an Optional containing the category
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        // Act: Call the getCategory method
        Category result = categoryService.getCategory(1L);

        // Assert: Verify the result
        assertNotNull(result);
        assertEquals("Science", result.getTitle());

        // Verify that the findById method was called exactly once
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetCategory_NotFound() {
        // Arrange: Mock the findById method to return an empty Optional
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // Act: Call the getCategory method
        Category result = categoryService.getCategory(1L);

        // Assert: Verify the result is null
        assertNull(result);

        // Verify that the findById method was called exactly once
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateCategory() {
        // Arrange: Mock the save method to return the category as it is (simulating an update)
        when(categoryRepository.save(category)).thenReturn(category);

        // Act: Call the updateCategory method
        Category result = categoryService.updateCategory(category);

        // Assert: Verify the result
        assertNotNull(result);
        assertEquals("Science", result.getTitle());

        // Verify that the save method was called exactly once
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void testDeleteCategory() {
        // Arrange: Mock the getCategory method to return the category
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        // Act: Call the deleteCategory method
        categoryService.deleteCategory(1L);

        // Assert: Verify that the delete method was called once
        verify(categoryRepository, times(1)).delete(category);
    }


}

