package com.project.examportalbackend.IntegrationTest;

import com.project.examportalbackend.models.Question;
import com.project.examportalbackend.models.Quiz;
import com.project.examportalbackend.services.QuestionService;
import com.project.examportalbackend.services.QuizService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Controller Layer Testing - These tests focus on the HTTP request/response and
// verify if the controller layer works as expected by making actual HTTP requests
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;

    @MockBean
    private QuizService quizService;

    @Test
    public void testAddQuestion() throws Exception {
        Question question = new Question();
        question.setQuesId(1L);
        question.setContent("Sample Question?");
        when(questionService.addQuestion(Mockito.any(Question.class))).thenReturn(question);

        mockMvc.perform(post("/api/question/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"quesId\":1,\"content\":\"Sample Question?\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quesId").value(1))
                .andExpect(jsonPath("$.content").value("Sample Question?"));
    }

    @Test
    public void testGetQuestions() throws Exception {
        Question question = new Question();
        question.setQuesId(1L);
        question.setContent("Sample Question?");
        when(questionService.getQuestions()).thenReturn(Collections.singletonList(question));

        mockMvc.perform(get("/api/question/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].quesId").value(1))
                .andExpect(jsonPath("$[0].content").value("Sample Question?"));
    }

    @Test
    public void testGetQuestionById() throws Exception {
        Question question = new Question();
        question.setQuesId(1L);
        question.setContent("Sample Question?");
        when(questionService.getQuestion(1L)).thenReturn(question);

        mockMvc.perform(get("/api/question/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quesId").value(1))
                .andExpect(jsonPath("$.content").value("Sample Question?"));
    }

    @Test
    public void testGetQuestionsByQuiz() throws Exception {
        Question question = new Question();
        question.setQuesId(1L);
        question.setContent("Sample Question?");
        Set<Question> questions = new HashSet<>();
        questions.add(question);
        Quiz quiz = new Quiz();
        quiz.setQuizId(1L);
        quiz.setTitle("Sample Quiz");
        quiz.setQuestions(questions);
        when(quizService.getQuiz(1L)).thenReturn(quiz);

        mockMvc.perform(get("/api/question/").param("quizId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].quesId").value(1))
                .andExpect(jsonPath("$[0].content").value("Sample Question?"));
    }

    @Test
    public void testUpdateQuestion() throws Exception {
        Question question = new Question();
        question.setQuesId(1L);
        question.setContent("Updated Question?");
        when(questionService.getQuestion(1L)).thenReturn(question);
        when(questionService.updateQuestion(Mockito.any(Question.class))).thenReturn(question);

        mockMvc.perform(put("/api/question/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"quesId\":1,\"content\":\"Updated Question?\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quesId").value(1))
                .andExpect(jsonPath("$.content").value("Updated Question?"));
    }

    @Test
    public void testDeleteQuestion() throws Exception {
        Mockito.doNothing().when(questionService).deleteQuestion(1L);

        mockMvc.perform(delete("/api/question/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}

