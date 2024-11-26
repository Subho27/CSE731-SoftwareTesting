package com.project.examportalbackend.ServiceImplTest;

import com.project.examportalbackend.models.Category;
import com.project.examportalbackend.models.Quiz;
import com.project.examportalbackend.repository.QuizRepository;
import com.project.examportalbackend.services.implementation.QuizServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

public class QuizServiceImplTest {

    @Mock
    private QuizRepository quizRepository;

    @InjectMocks
    private QuizServiceImpl quizService;

    private Category category;
    private Quiz quiz1;
    private Quiz quiz2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initializes mocks

        // Set up test data
        category = new Category();
        category.setCatId(1L);
        category.setTitle("General Knowledge");

        quiz1 = new Quiz();
        quiz1.setQuizId(1L);
        quiz1.setTitle("Science Quiz");
        quiz1.setCategory(category);

        quiz2 = new Quiz();
        quiz2.setQuizId(2L);
        quiz2.setTitle("History Quiz");
        quiz2.setCategory(category);
    }

    @Test
    public void testGetQuizByCategory() {
        // Arrange: Mock the behavior of the quizRepository
        when(quizRepository.findByCategory(category)).thenReturn(Arrays.asList(quiz1, quiz2));

        // Act: Call the method
        List<Quiz> quizzes = quizService.getQuizByCategory(category);

        // Assert: Verify the results
        assertNotNull(quizzes);
        assertEquals(2, quizzes.size());
        assertEquals("Science Quiz", quizzes.get(0).getTitle());
        assertEquals("History Quiz", quizzes.get(1).getTitle());

        // Verify that the repository's findByCategory method was called exactly once
        verify(quizRepository, times(1)).findByCategory(category);
    }
}

