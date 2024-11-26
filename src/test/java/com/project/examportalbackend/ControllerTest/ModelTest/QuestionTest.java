package com.project.examportalbackend.ControllerTest.ModelTest;

import com.project.examportalbackend.models.Question;
import com.project.examportalbackend.models.Quiz;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuestionTest {

    @Test
    public void testToString() {
        Quiz quiz = new Quiz();
        quiz.setQuizId(1L);
        quiz.setTitle("Sample Quiz");

        // Create a Question object with sample data
        Question question = new Question();
        question.setQuesId(1L);
        question.setContent("What is the capital of France?");
        question.setImage("image_url");
        question.setOption1("Berlin");
        question.setOption2("Madrid");
        question.setOption3("Paris");
        question.setOption4("Rome");
        question.setAnswer("Paris");
        question.setQuiz(quiz);

        // Get the string representation of the Question object
        String toStringResult = question.toString();

        // Verify that the toString method contains all the fields
        assertTrue(toStringResult.contains("quesId=1"));
        assertTrue(toStringResult.contains("content=What is the capital of France?"));
        assertTrue(toStringResult.contains("image=image_url"));
        assertTrue(toStringResult.contains("option1=Berlin"));
        assertTrue(toStringResult.contains("option2=Madrid"));
        assertTrue(toStringResult.contains("option3=Paris"));
        assertTrue(toStringResult.contains("option4=Rome"));
        assertTrue(toStringResult.contains("answer=Paris"));
        assertTrue(toStringResult.contains(
                "quiz=Quiz(quizId=1, title=Sample Quiz, description=null, iActive=false, " +
                        "numOfQuestions=0, category=null, questions=[], quizResults=[])"));  
    }
}
