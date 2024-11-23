package com.project.examportalbackend.ControllerTest;

import com.project.examportalbackend.controllers.QuizController;
import com.project.examportalbackend.models.Category;
import com.project.examportalbackend.models.Quiz;
import com.project.examportalbackend.services.CategoryService;
import com.project.examportalbackend.services.QuizService;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class QuizControllerTest {

    @Autowired
    private QuizController quizController;

    @MockBean
    private QuizService quizService;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void testAddQuiz_Success() {
        Quiz quiz = new Quiz();
        quiz.setTitle("Sample Quiz");
        quiz.setDescription("Sample Description");

        when(quizService.addQuiz(quiz)).thenReturn(quiz);

        ResponseEntity<?> response = quizController.addQuiz(quiz);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(quiz, response.getBody());

        verify(quizService, times(1)).addQuiz(quiz);
    }

    @Test
    public void testAddQuiz_Failure() {
        Quiz quiz = new Quiz();
        quiz.setTitle("Sample Quiz");
        quiz.setDescription("Sample Description");

        when(quizService.addQuiz(quiz)).thenThrow(new RuntimeException("Mock Exception"));

        ResponseEntity<?> response = quizController.addQuiz(quiz);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        assertEquals("There is an Exception", response.getBody());

        verify(quizService, times(1)).addQuiz(quiz);
    }

    @Test
    public void testGetQuizzes_Success() {
        Quiz quiz1 = new Quiz();
        quiz1.setTitle("Quiz 1");
        quiz1.setDescription("Description for Quiz 1");

        Quiz quiz2 = new Quiz();
        quiz2.setTitle("Quiz 2");
        quiz2.setDescription("Description for Quiz 2");

        List<Quiz> quizzes = List.of(quiz1, quiz2);

        when(quizService.getQuizzes()).thenReturn(quizzes);

        ResponseEntity<?> response = quizController.getQuizzes();

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(quizzes, response.getBody());

        verify(quizService, times(1)).getQuizzes();
    }

    @Test
    public void testGetQuizzes_Failure() {
        when(quizService.getQuizzes()).thenThrow(new RuntimeException("Mock Exception"));

        ResponseEntity<?> response = quizController.getQuizzes();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        assertEquals("There is an Exception", response.getBody());

        verify(quizService, times(1)).getQuizzes();
    }

    @Test
    public void testGetQuiz_Success() {
        Long quizId = 1L;
        Quiz quiz = new Quiz();
        quiz.setQuizId(quizId);
        quiz.setTitle("Sample Quiz");
        quiz.setDescription("Description for Sample Quiz");

        when(quizService.getQuiz(quizId)).thenReturn(quiz);

        ResponseEntity<?> response = quizController.getQuiz(quizId);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(quiz, response.getBody());

        verify(quizService, times(1)).getQuiz(quizId);
    }

    @Test
    public void testGetQuiz_Failure() {
        Long quizId = 1L;
        when(quizService.getQuiz(quizId)).thenThrow(new RuntimeException("Mock Exception"));

        ResponseEntity<?> response = quizController.getQuiz(quizId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        assertEquals("There is an Exception", response.getBody());

        verify(quizService, times(1)).getQuiz(quizId);
    }

    @Test
    public void testGetQuizByCategory_Success() {
        Long catId = 1L;
        Category category = new Category();
        category.setCatId(catId);
        category.setTitle("Sample Category");

        Quiz quiz1 = new Quiz();
        quiz1.setTitle("Quiz 1");
        Quiz quiz2 = new Quiz();
        quiz2.setTitle("Quiz 2");

        List<Quiz> quizzes = new ArrayList<>();
        quizzes.add(quiz1);
        quizzes.add(quiz2);

        when(categoryService.getCategory(catId)).thenReturn(category);

        when(quizService.getQuizByCategory(category)).thenReturn(quizzes);

        ResponseEntity<?> response = quizController.getQuizByCategory(catId);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(quizzes, response.getBody());

        verify(categoryService, times(1)).getCategory(catId);
        verify(quizService, times(1)).getQuizByCategory(category);
    }

    @Test
    public void testGetQuizByCategory_Failure() {
        Long catId = 1L;
        when(categoryService.getCategory(catId)).thenThrow(new RuntimeException("Mock Exception"));

        ResponseEntity<?> response = quizController.getQuizByCategory(catId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        assertEquals("There is an Exception", response.getBody());

        verify(categoryService, times(1)).getCategory(catId);
    }

    @Test
    public void testUpdateQuiz_Success() {
        Long quizId = 1L;
        Quiz existingQuiz = new Quiz();
        existingQuiz.setQuizId(quizId);
        Quiz updatedQuiz = new Quiz();

        when(quizService.getQuiz(quizId)).thenReturn(existingQuiz);
        when(quizService.updateQuiz(updatedQuiz)).thenReturn(updatedQuiz);

        ResponseEntity<?> response = quizController.updateQuiz(quizId, updatedQuiz);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(updatedQuiz, response.getBody());

        verify(quizService, times(1)).getQuiz(quizId);
        verify(quizService, times(1)).updateQuiz(updatedQuiz);
    }

    @Test
    public void testUpdateQuiz_NotFound() {
        Long quizId = 1L;
        Quiz updatedQuiz = new Quiz();

        when(quizService.getQuiz(quizId)).thenReturn(null);

        ResponseEntity<?> response = quizController.updateQuiz(quizId, updatedQuiz);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals("Quiz with id : 1, doesn't exists", response.getBody());

        verify(quizService, times(1)).getQuiz(quizId);
        verify(quizService, times(0)).updateQuiz(updatedQuiz);
    }

    @Test
    public void testUpdateQuiz_Failure() {
        Long quizId = 1L;
        Quiz updatedQuiz = new Quiz();

        when(quizService.getQuiz(quizId)).thenReturn(new Quiz());
        doThrow(new RuntimeException("Mock Exception")).when(quizService).updateQuiz(updatedQuiz);

        ResponseEntity<?> response = quizController.updateQuiz(quizId, updatedQuiz);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        assertEquals("There is an Exception", response.getBody());

        verify(quizService, times(1)).getQuiz(quizId);
        verify(quizService, times(1)).updateQuiz(updatedQuiz);
    }

    @Test
    public void testDeleteQuiz_Success() {
        Long quizId = 1L;

        ResponseEntity<?> response = quizController.deleteQuiz(quizId);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(true, response.getBody());

        verify(quizService, times(1)).deleteQuiz(quizId);
    }

    @Test
    public void testDeleteQuiz_Failure() {
        Long quizId = 1L;
        doThrow(new RuntimeException("Mock Exception")).when(quizService).deleteQuiz(quizId);

        ResponseEntity<?> response = quizController.deleteQuiz(quizId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        assertEquals("There is an Exception", response.getBody());

        verify(quizService, times(1)).deleteQuiz(quizId);
    }

}
