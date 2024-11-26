package com.project.examportalbackend.ServiceImplTest;

import com.project.examportalbackend.models.QuizResult;
import com.project.examportalbackend.repository.QuizResultRepository;
import com.project.examportalbackend.services.implementation.QuizResultServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class QuizResultServiceImplTest {

    @Mock
    private QuizResultRepository quizResultRepository;

    @InjectMocks
    private QuizResultServiceImpl quizResultService;

    private QuizResult quizResult;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initializes mocks

        // Set up test data
        quizResult = new QuizResult();
        quizResult.setQuizResId(1L);
        quizResult.setUserId(100L);
        quizResult.setTotalObtainedMarks(80.5f);
        quizResult.setAttemptDatetime("2024-11-01 10:00:00");
    }

    @Test
    public void testAddQuizResult() {
        // Arrange: Mock the save method to return the quizResult
        when(quizResultRepository.save(quizResult)).thenReturn(quizResult);

        // Act: Call the addQuizResult method
        QuizResult result = quizResultService.addQuizResult(quizResult);

        // Assert: Verify the result
        assertNotNull(result);
        assertEquals(1L, result.getQuizResId());
        assertEquals(100L, result.getUserId());
        assertEquals(80.5f, result.getTotalObtainedMarks());
        assertEquals("2024-11-01 10:00:00", result.getAttemptDatetime());

        // Verify that the save method was called exactly once
        verify(quizResultRepository, times(1)).save(quizResult);
    }

    @Test
    public void testGetQuizResults() {
        // Arrange: Mock the findAll method to return a list of quiz results
        List<QuizResult> quizResults = new ArrayList<>();
        quizResults.add(quizResult);
        when(quizResultRepository.findAll()).thenReturn(quizResults);

        // Act: Call the getQuizResults method
        List<QuizResult> result = quizResultService.getQuizResults();

        // Assert: Verify the results
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getQuizResId());

        // Verify that the findAll method was called exactly once
        verify(quizResultRepository, times(1)).findAll();
    }

    @Test
    public void testGetQuizResultsByUser() {
        // Arrange: Mock the findByUserId method to return a list of quiz results
        List<QuizResult> quizResultsByUser = new ArrayList<>();
        quizResultsByUser.add(quizResult);
        when(quizResultRepository.findByUserId(100L)).thenReturn(quizResultsByUser);

        // Act: Call the getQuizResultsByUser method
        List<QuizResult> result = quizResultService.getQuizResultsByUser(100L);

        // Assert: Verify the results
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(100L, result.get(0).getUserId());

        // Verify that the findByUserId method was called exactly once
        verify(quizResultRepository, times(1)).findByUserId(100L);
    }
}

