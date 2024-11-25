package com.project.examportalbackend.IntegrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.examportalbackend.models.Category;
import com.project.examportalbackend.models.Quiz;
import com.project.examportalbackend.repository.CategoryRepository;
import com.project.examportalbackend.repository.QuizRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//End-to-End (Full) Integration Testing - We have combined the controller, service, and
// repository layer in a single end-to-end test to verify that everything works together.
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class QuizIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Quiz testQuiz;

    @Autowired
    private WebTestClient client;

    @BeforeEach
    void setUp() {

        quizRepository.deleteAll();

        Category category = new Category();
        category.setCatId(1L);
        category.setTitle("General Knowledge");
        category = categoryRepository.save(category);

        testQuiz = new Quiz();
        testQuiz.setTitle("Sample Quiz");
        testQuiz.setDescription("A sample quiz description.");
        testQuiz.setCategory(category);
        testQuiz.setNumOfQuestions(5);
        testQuiz.setIActive(true);

        quizRepository.save(testQuiz);
    }

    @Test
    void testGetAllQuizzes() throws Exception {
        mockMvc.perform(get("/api/quiz/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value(testQuiz.getTitle()));
    }

    @Test
    void testAddQuiz() throws Exception {
        Quiz newQuiz = new Quiz();
        newQuiz.setTitle("New Quiz");
        newQuiz.setDescription("A new quiz description.");
        newQuiz.setNumOfQuestions(10);
        newQuiz.setIActive(true);

        mockMvc.perform(post("/api/quiz/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newQuiz)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(newQuiz.getTitle()));
    }

    @Test
    void testUpdateQuiz() throws Exception {
        testQuiz.setTitle("Updated Quiz Title");

        mockMvc.perform(put("/api/quiz/" + testQuiz.getQuizId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testQuiz)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Quiz Title"));
    }

    @Test
    void testDeleteQuiz() throws Exception {
        mockMvc.perform(delete("/api/quiz/" + testQuiz.getQuizId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/quiz/" + testQuiz.getQuizId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }
}



