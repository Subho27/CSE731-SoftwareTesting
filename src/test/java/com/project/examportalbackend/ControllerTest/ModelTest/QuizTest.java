package com.project.examportalbackend.ControllerTest.ModelTest;

import com.project.examportalbackend.models.Category;
import com.project.examportalbackend.models.Quiz;
import com.project.examportalbackend.models.QuizResult;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuizTest {

    @Test
    public void testToString() {
        // Create QuizResult objects
        QuizResult quizResult1 = new QuizResult();
        quizResult1.setQuizResId(1L);
        quizResult1.setTotalObtainedMarks(85);

        QuizResult quizResult2 = new QuizResult();
        quizResult2.setQuizResId(2L);
        quizResult2.setTotalObtainedMarks(90);

        // Create a Category object (assuming it's already implemented)
        Category category = new Category();
        category.setCatId(1L);
        category.setTitle("General Knowledge");

        // Create a Quiz object with sample data
        Quiz quiz = new Quiz();
        quiz.setQuizId(1L);
        quiz.setTitle("Sample Quiz");
        quiz.setDescription("This is a sample quiz.");
        quiz.setIActive(true);
        quiz.setNumOfQuestions(10);
        quiz.setCategory(category);

        // Add QuizResults to the quiz
        quiz.getQuizResults().add(quizResult1);
        quiz.getQuizResults().add(quizResult2);

        // Get the string representation of the Quiz object
        String toStringResult = quiz.toString();

        // Print the toString output for debugging (if needed)
        System.out.println(toStringResult);

        // Assert that the toString method contains the quizResults
        assertTrue(toStringResult.contains("quizResults=[QuizResult(quizResId=1, userId=null, " +
                "totalObtainedMarks=85.0, attemptDatetime=null, quiz=null), QuizResult(quizResId=2, " +
                "userId=null, totalObtainedMarks=90.0, attemptDatetime=null, quiz=null)]"));
        assertTrue(toStringResult.contains("quizId=1"));
        assertTrue(toStringResult.contains("title=Sample Quiz"));
        assertTrue(toStringResult.contains("description=This is a sample quiz."));
        assertTrue(toStringResult.contains("iActive=true"));
        assertTrue(toStringResult.contains("numOfQuestions=10"));
        assertTrue(toStringResult.contains("category=Category(catId=1, title=General Knowledge, " +
                "description=null, quizzes=[])"));
    }
}

