package com.project.examportalbackend.ServiceImplTest;

import com.project.examportalbackend.models.Question;
import com.project.examportalbackend.models.Quiz;
import com.project.examportalbackend.repository.QuestionRepository;
import com.project.examportalbackend.repository.QuizRepository;
import com.project.examportalbackend.services.implementation.QuestionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

public class QuestionServiceImplTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QuizRepository quizRepository;

    @InjectMocks
    private QuestionServiceImpl questionService;

    private Question question;
    private Quiz quiz;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initializes mocks

        // Set up a quiz object for tests
        quiz = new Quiz();
        quiz.setQuizId(1L);
        quiz.setNumOfQuestions(5);

        // Set up a question object for tests
        question = new Question();
        question.setQuesId(1L);
        question.setContent("Sample Question?");
        question.setOption1("Option A");
        question.setOption2("Option B");
        question.setOption3("Option C");
        question.setOption4("Option D");
        question.setAnswer("Option A");
        question.setQuiz(quiz);
    }

    @Test
    public void testAddQuestion() {
        // Arrange: Mock the findById and save methods
        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));
        when(questionRepository.save(question)).thenReturn(question);
        when(quizRepository.save(quiz)).thenReturn(quiz);

        // Act: Call the addQuestion method
        Question result = questionService.addQuestion(question);

        // Assert: Verify the result and interactions
        assertNotNull(result);
        assertEquals("Sample Question?", result.getContent());
        assertEquals(6, quiz.getNumOfQuestions()); // The number of questions should be incremented

        // Verify that the findById and save methods were called correctly
        verify(quizRepository, times(1)).findById(1L);
        verify(quizRepository, times(1)).save(quiz);
        verify(questionRepository, times(1)).save(question);
    }

    @Test
    public void testGetQuestions() {
        // Arrange: Mock the findAll method
        when(questionRepository.findAll()).thenReturn(List.of(question));

        // Act: Call the getQuestions method
        List<Question> result = questionService.getQuestions();

        // Assert: Verify the result
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Sample Question?", result.get(0).getContent());

        // Verify that the findAll method was called
        verify(questionRepository, times(1)).findAll();
    }

    @Test
    public void testGetQuestion() {
        // Arrange: Mock the findById method to return the Optional containing the question
        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));

        // Act: Call the getQuestion method
        Question result = questionService.getQuestion(1L);

        // Assert: Verify the result
        assertNotNull(result);
        assertEquals("Sample Question?", result.getContent());

        // Verify that the findById method was called exactly once
        verify(questionRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetQuestionNotFound() {
        // Arrange: Mock the findById method to return empty
        when(questionRepository.findById(1L)).thenReturn(Optional.empty());

        // Act: Call the getQuestion method
        Question result = questionService.getQuestion(1L);

        // Assert: Verify the result
        assertNull(result);

        // Verify that the findById method was called
        verify(questionRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateQuestion() {
        // Arrange: Mock the save method
        when(questionRepository.save(question)).thenReturn(question);

        // Act: Call the updateQuestion method
        Question result = questionService.updateQuestion(question);

        // Assert: Verify the result
        assertNotNull(result);
        assertEquals("Sample Question?", result.getContent());

        // Verify that the save method was called
        verify(questionRepository, times(1)).save(question);
    }

    @Test
    public void testDeleteQuestion() {
        // Arrange: Mock the getQuestion method and deleteById method
        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
        doNothing().when(questionRepository).delete(question);

        // Act: Call the deleteQuestion method
        questionService.deleteQuestion(1L);

        // Assert: Verify the delete method was called
        verify(questionRepository, times(1)).delete(question);
    }

    @Test
    public void testGetQuestionsByQuiz() {
        // Arrange: Mock the findByQuiz method
        when(questionRepository.findByQuiz(quiz)).thenReturn(List.of(question));

        // Act: Call the getQuestionsByQuiz method
        List<Question> result = questionService.getQuestionsByQuiz(quiz);

        // Assert: Verify the result
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Sample Question?", result.get(0).getContent());

        // Verify that the findByQuiz method was called
        verify(questionRepository, times(1)).findByQuiz(quiz);
    }
}

