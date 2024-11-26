package com.project.examportalbackend.ControllerTest.ModelTest;

import com.project.examportalbackend.models.Quiz;
import com.project.examportalbackend.models.QuizResult;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuizResultTest {

    @Test
    public void testAllArgsConstructor() {
        // Use the AllArgsConstructor to create a QuizResult instance
        Long userId = 101L;
        float totalObtainedMarks = 85.5f;
        String attemptDatetime = "2024-11-26 10:00:00";
        Quiz quiz = new Quiz();  // Assuming you have a valid Quiz object

        QuizResult quizResult = new QuizResult(1L, userId, totalObtainedMarks, attemptDatetime, quiz);

        // Assert that the fields are properly initialized by the constructor
        assertEquals(userId, quizResult.getUserId());
        assertEquals(totalObtainedMarks, quizResult.getTotalObtainedMarks());
        assertEquals(attemptDatetime, quizResult.getAttemptDatetime());
        assertEquals(quiz, quizResult.getQuiz());
    }

    @Test
    public void testToString() {
        // Use the AllArgsConstructor to create a QuizResult instance
        Long userId = 101L;
        float totalObtainedMarks = 85.5f;
        String attemptDatetime = "2024-11-26 10:00:00";
        Quiz quiz = new Quiz();  // Assuming you have a valid Quiz object

        QuizResult quizResult = new QuizResult(1L, userId, totalObtainedMarks, attemptDatetime, quiz);

        // Get the string representation of the QuizResult object
        String toStringResult = quizResult.toString();

        // Print the toString output for debugging (if needed)
        System.out.println(toStringResult);

        // Assert that the toString includes all the fields of QuizResult
        assertTrue(toStringResult.contains("quizResId=1")); // Assuming quizResId is not set in this case
        assertTrue(toStringResult.contains("userId=101"));
        assertTrue(toStringResult.contains("totalObtainedMarks=85.5"));
        assertTrue(toStringResult.contains("attemptDatetime=2024-11-26 10:00:00"));
        assertTrue(toStringResult.contains("quiz="));  // This should represent the Quiz object
    }
}

