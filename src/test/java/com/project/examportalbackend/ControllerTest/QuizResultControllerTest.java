package com.project.examportalbackend.ControllerTest;

import com.project.examportalbackend.DTO.QuizResultDTO;
import com.project.examportalbackend.controllers.QuizResultController;
import com.project.examportalbackend.models.Question;
import com.project.examportalbackend.models.Quiz;
import com.project.examportalbackend.models.QuizResult;
import com.project.examportalbackend.models.User;
import com.project.examportalbackend.repository.UserRepository;
import com.project.examportalbackend.services.QuestionService;
import com.project.examportalbackend.services.QuizResultService;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuizResultControllerTest {

    @Autowired
    private QuizResultController quizResultController;

    @MockBean
    private QuizResultService quizResultService;

    @MockBean
    private QuizService quizService;

    @MockBean
    private QuestionService questionService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testSubmitQuizNoAnswers_Success() {
        Long userId = 1L;
        Long quizId = 1L;
        HashMap<String, String> answers = new HashMap<>();

        Quiz quiz = new Quiz();
        quiz.setQuizId(quizId);
        when(quizService.getQuiz(quizId)).thenReturn(quiz);

        ResponseEntity<?> response = quizResultController.submitQuiz(userId, quizId, answers);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        verify(questionService, times(0)).getQuestion(anyLong());
        verify(quizResultService, times(1)).addQuizResult(any());
    }

    @Test
    public void testSubmitQuizIncorrectAnswer_Success() {
        Long userId = 1L;
        Long quizId = 1L;
        HashMap<String, String> answers = new HashMap<>();
        answers.put("1", "WrongAnswer");

        Quiz quiz = new Quiz();
        quiz.setQuizId(quizId);
        when(quizService.getQuiz(quizId)).thenReturn(quiz);

        Question question = new Question();
        question.setAnswer("CorrectAnswer");
        when(questionService.getQuestion(anyLong())).thenReturn(question);

        ResponseEntity<?> response = quizResultController.submitQuiz(userId, quizId, answers);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        verify(questionService, times(1)).getQuestion(anyLong());
        verify(quizResultService, times(1)).addQuizResult(any());
    }

    @Test
    public void testSubmitQuizCorrectAnswerButNoMarking_Success() {
        Long userId = 1L;
        Long quizId = 1L;
        HashMap<String, String> answers = new HashMap<>();
        answers.put("1", "CorrectAnswer");

        Quiz quiz = new Quiz();
        quiz.setQuizId(quizId);
        when(quizService.getQuiz(quizId)).thenReturn(quiz);

        Question question = new Question();
        question.setAnswer("CorrectAnswer");
        when(questionService.getQuestion(anyLong())).thenReturn(question);

        ResponseEntity<?> response = quizResultController.submitQuiz(userId, quizId, answers);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        verify(questionService, times(1)).getQuestion(anyLong());
        verify(quizResultService, times(1)).addQuizResult(any());
    }

    @Test
    public void testSubmitQuizAllCorrect_Success() {
        Long userId = 1L;
        Long quizId = 1L;
        HashMap<String, String> answers = new HashMap<>();
        answers.put("1", "CorrectAnswer");

        Quiz quiz = new Quiz();
        quiz.setQuizId(quizId);
        when(quizService.getQuiz(quizId)).thenReturn(quiz);

        Question question = new Question();
        question.setAnswer("CorrectAnswer");
        when(questionService.getQuestion(anyLong())).thenReturn(question);

        ResponseEntity<?> response = quizResultController.submitQuiz(userId, quizId, answers);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        verify(questionService, times(1)).getQuestion(anyLong());
        verify(quizResultService, times(1)).addQuizResult(any());
    }

    @Test
    public void testSubmitQuiz_Failure() {
        Long userId = 1L;
        Long quizId = 1L;
        HashMap<String, String> answers = new HashMap<>();
        answers.put("1", "CorrectAnswer");

        when(quizService.getQuiz(quizId)).thenThrow(new RuntimeException("Mock Exception"));

        ResponseEntity<?> response = quizResultController.submitQuiz(userId, quizId, answers);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        assertEquals("There is an Exception", response.getBody());
        verify(quizService, times(1)).getQuiz(quizId);
        verify(questionService, times(0)).getQuestion(anyLong());
        verify(quizResultService, times(0)).addQuizResult(any());
    }

    @Test
    public void testGetQuizResults_Success() {
        Long userId = 1L;
        QuizResult quizResult1 = new QuizResult();
        quizResult1.setUserId(userId);
        QuizResult quizResult2 = new QuizResult();
        quizResult2.setUserId(userId);
        List<QuizResult> quizResults = Arrays.asList(quizResult1, quizResult2);

        when(quizResultService.getQuizResultsByUser(userId)).thenReturn(quizResults);

        ResponseEntity<?> response = quizResultController.getQuizResults(userId);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(quizResults, response.getBody());
        verify(quizResultService, times(1)).getQuizResultsByUser(userId);
    }

    @Test
    public void testGetQuizResults_Failure() {
        Long userId = 1L;

        when(quizResultService.getQuizResultsByUser(userId)).thenThrow(new RuntimeException("Mock Exception"));

        ResponseEntity<?> response = quizResultController.getQuizResults(userId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        assertEquals("There is an Exception", response.getBody());
        verify(quizResultService, times(1)).getQuizResultsByUser(userId);
    }

    @Test
    public void testGetQuizResultsNoIteration_Success() {
        Long userId = 1L;
        List<QuizResult> quizResultsList = new ArrayList<>();
        when(quizResultService.getQuizResults()).thenReturn(quizResultsList);

        ResponseEntity<?> response = quizResultController.getQuizResults();

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(new ArrayList<>(), response.getBody());
        verify(quizResultService, times(1)).getQuizResults();
    }

    @Test
    public void testGetQuizResultsFullExecution_Success() {
        Long userId = 1L;
        QuizResult quizResult = new QuizResult();
        quizResult.setUserId(userId);
        quizResult.setAttemptDatetime("2024-11-23 12:00:00");
        quizResult.setQuiz(new Quiz());
        quizResult.setQuizResId(1L);
        quizResult.setTotalObtainedMarks(50);

        List<QuizResult> quizResultsList = Arrays.asList(quizResult);
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");

        when(quizResultService.getQuizResults()).thenReturn(quizResultsList);
        when(userRepository.findUserByUserId(userId)).thenReturn(user);

        ResponseEntity<?> response = quizResultController.getQuizResults();

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        List<QuizResultDTO> students = (List<QuizResultDTO>) response.getBody();
        assertEquals(1, students.size());
        assertEquals("John Doe", students.get(0).getName());
        assertEquals("2024-11-23 12:00:00", students.get(0).getAttemptDatetime());
        assertEquals(50, students.get(0).getTotalObtainedMarks());
        verify(quizResultService, times(1)).getQuizResults();
        verify(userRepository, times(1)).findUserByUserId(userId);
    }

    @Test
    public void testGetQuizResultsEx_Failure() {
        when(quizResultService.getQuizResults()).thenThrow(new RuntimeException("Mock Exception"));

        ResponseEntity<?> response = quizResultController.getQuizResults();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        assertEquals("There is an Exception", response.getBody());
        verify(quizResultService, times(1)).getQuizResults();
    }

}
