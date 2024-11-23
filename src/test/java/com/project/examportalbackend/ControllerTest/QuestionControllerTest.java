package com.project.examportalbackend.ControllerTest;

import com.project.examportalbackend.controllers.QuestionController;
import com.project.examportalbackend.models.Question;
import com.project.examportalbackend.models.Quiz;
import com.project.examportalbackend.services.QuestionService;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionControllerTest {

    @Autowired
    private QuestionController questionController;

    @MockBean
    private QuestionService questionService;

    @MockBean
    private QuizService quizService;

    @Test
    public void testAddQuestion_Success() {
        Question question = new Question();
        question.setContent("This is a test question");
        question.setImage(null);
        question.setOption1("Option 1");
        question.setOption2("Option 2");
        question.setOption3("Option 3");
        question.setOption4("Option 4");
        question.setAnswer("Option 1");
        when(questionService.addQuestion(question)).thenReturn(question);

        ResponseEntity<?> response = questionController.addQuestion(question);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(question, response.getBody());
    }

    @Test
    public void testAddQuestion_Failure() {
        Question question = new Question();
        question.setQuesId(1L);
        question.setContent("Example question");

        when(questionService.addQuestion(question)).thenThrow(new RuntimeException("Mock Exception"));

        ResponseEntity<?> response = questionController.addQuestion(question);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        assertEquals("There is an Exception", response.getBody());

        verify(questionService, times(1)).addQuestion(question);
    }

    @Test
    public void testGetQuestions_Success() {
        List<Question> questions = new ArrayList<>();
        Question question1 = new Question();
        question1.setContent("This is a test question1");
        question1.setImage(null);
        question1.setOption1("Option 1");
        question1.setOption2("Option 2");
        question1.setOption3("Option 3");
        question1.setOption4("Option 4");
        question1.setAnswer("Option 1");
        Question question2 = new Question();
        question2.setContent("This is a test question2");
        question2.setImage(null);
        question2.setOption1("Option 1");
        question2.setOption2("Option 2");
        question2.setOption3("Option 3");
        question2.setOption4("Option 4");
        question2.setAnswer("Option 3");
        questions.add(question1);
        questions.add(question2);
        when(questionService.getQuestions()).thenReturn(questions);

        ResponseEntity<?> response = questionController.getQuestions();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof List);
        List<Question> responseQuestions = (List<Question>) response.getBody();
        assertEquals(questions.size(), responseQuestions.size());
    }

    @Test
    public void testGetQuestions_Failure() {
        when(questionService.getQuestions()).thenThrow(new RuntimeException("Mock Exception"));

        ResponseEntity<?> response = questionController.getQuestions();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        assertEquals("There is an Exception", response.getBody());

        verify(questionService, times(1)).getQuestions();
    }

    @Test
    public void testGetQuestionById_Success() {
        Long questionId = 1L;
        Question question = new Question();
        question.setContent("This is a test question");
        question.setImage(null);
        question.setOption1("Option 1");
        question.setOption2("Option 2");
        question.setOption3("Option 3");
        question.setOption4("Option 4");
        question.setAnswer("Option 1");
        when(questionService.getQuestion(questionId)).thenReturn(question);

        ResponseEntity<?> response = questionController.getQuestion(questionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(question, response.getBody());
    }

    @Test
    public void testGetQuestionById_NotFound() {
        Long questionId = 1L;
        when(questionService.getQuestion(questionId)).thenReturn(null);

        ResponseEntity<?> response = questionController.getQuestion(questionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetQuestionById_Failure() {
        Long questionId = 1L;

        when(questionService.getQuestion(questionId)).thenThrow(new RuntimeException("Mock Exception"));

        ResponseEntity<?> response = questionController.getQuestion(questionId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        assertEquals("There is an Exception", response.getBody());

        verify(questionService, times(1)).getQuestion(questionId);
    }

    @Test
    public void testGetQuestionsByQuiz_Success() {
        Long quizId = 1L;
        Quiz quiz = new Quiz();
        quiz.setTitle("Test Quiz");
        quiz.setDescription("...");
        quiz.setIActive(true);
        quiz.setNumOfQuestions(2);
        Set<Question> questions = new HashSet<>();
        Question question1 = new Question();
        question1.setContent("This is a test question1");
        question1.setImage(null);
        question1.setOption1("Option 1");
        question1.setOption2("Option 2");
        question1.setOption3("Option 3");
        question1.setOption4("Option 4");
        question1.setAnswer("Option 1");
        Question question2 = new Question();
        question2.setContent("This is a test question2");
        question2.setImage(null);
        question2.setOption1("Option 1");
        question2.setOption2("Option 2");
        question2.setOption3("Option 3");
        question2.setOption4("Option 4");
        question2.setAnswer("Option 3");
        questions.add(question1);
        questions.add(question2);
        quiz.setQuestions(questions);
        when(quizService.getQuiz(quizId)).thenReturn(quiz);

        ResponseEntity<?> response = questionController.getQuestionsByQuiz(quizId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Set);
        Set<Question> responseQuestions = (Set<Question>) response.getBody();
        assertEquals(questions.size(), responseQuestions.size());
    }

    @Test
    public void testGetQuestionsByQuiz_Failure() {
        Long quizId = 1L;

        when(quizService.getQuiz(quizId)).thenThrow(new RuntimeException("Mock Exception"));

        ResponseEntity<?> response = questionController.getQuestionsByQuiz(quizId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        assertEquals("There is an Exception", response.getBody());

        verify(quizService, times(1)).getQuiz(quizId);
    }

    @Test
    public void testUpdateQuestion_Success() {
        Long questionId = 1L;
        Question existingQuestion = new Question();
        existingQuestion.setQuesId(questionId);
        existingQuestion.setContent("Old Content");

        Question updatedQuestion = new Question();
        updatedQuestion.setQuesId(questionId);
        updatedQuestion.setContent("Updated Content");

        when(questionService.getQuestion(questionId)).thenReturn(existingQuestion);
        when(questionService.updateQuestion(any(Question.class))).thenReturn(updatedQuestion);

        ResponseEntity<?> response = questionController.updateQuestion(questionId, updatedQuestion);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(updatedQuestion, response.getBody());

        verify(questionService, times(1)).getQuestion(questionId);
        verify(questionService, times(1)).updateQuestion(updatedQuestion);
    }

    @Test
    public void testUpdateQuestion_NotFound() {
        Long questionId = 1L;
        Question updatedQuestion = new Question();
        updatedQuestion.setQuesId(questionId);
        updatedQuestion.setContent("Updated Content");

        when(questionService.getQuestion(questionId)).thenReturn(null);

        ResponseEntity<?> response = questionController.updateQuestion(questionId, updatedQuestion);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals("Question with id : 1, doesn't exists", response.getBody());

        verify(questionService, times(1)).getQuestion(questionId);
        verify(questionService, never()).updateQuestion(updatedQuestion);
    }

    @Test
    public void testUpdateQuestion_Failure() {
        Long questionId = 1L;
        Question updatedQuestion = new Question();
        updatedQuestion.setQuesId(questionId);
        updatedQuestion.setContent("Updated Content");

        when(questionService.getQuestion(questionId)).thenReturn(updatedQuestion);
        when(questionService.updateQuestion(any(Question.class))).thenThrow(new RuntimeException("Mock Exception"));

        ResponseEntity<?> response = questionController.updateQuestion(questionId, updatedQuestion);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        assertEquals("There is an Exception", response.getBody());

        verify(questionService, times(1)).getQuestion(questionId);
        verify(questionService, times(1)).updateQuestion(updatedQuestion);
    }


    @Test
    public void testDeleteQuestion_Success() {
        Long questionId = 1L;

        doNothing().when(questionService).deleteQuestion(questionId);

        ResponseEntity<?> response = questionController.deleteQuestion(questionId);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(true, response.getBody());

        verify(questionService, times(1)).deleteQuestion(questionId);
    }

    @Test
    public void testDeleteQuestion_Failure() {
        Long questionId = 1L;

        doThrow(new RuntimeException("Mock Exception")).when(questionService).deleteQuestion(questionId);

        ResponseEntity<?> response = questionController.deleteQuestion(questionId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        assertEquals("There is an Exception", response.getBody());

        verify(questionService, times(1)).deleteQuestion(questionId);
    }
}