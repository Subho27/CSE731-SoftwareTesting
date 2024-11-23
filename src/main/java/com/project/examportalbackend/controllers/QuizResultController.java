package com.project.examportalbackend.controllers;

import com.project.examportalbackend.DTO.QuizResultDTO;
import com.project.examportalbackend.models.Question;
import com.project.examportalbackend.models.Quiz;
import com.project.examportalbackend.models.QuizResult;
import com.project.examportalbackend.models.User;
import com.project.examportalbackend.repository.UserRepository;
import com.project.examportalbackend.services.QuestionService;
import com.project.examportalbackend.services.QuizResultService;
import com.project.examportalbackend.services.QuizService;
import com.project.examportalbackend.services.implementation.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/api/quizResult")
public class QuizResultController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuizService quizService;

    @Autowired
    private QuizResultService quizResultService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/submit", params = {"userId", "quizId"})
    public ResponseEntity<?> submitQuiz(@RequestParam Long userId, @RequestParam Long quizId, @RequestBody HashMap<String,String> answers) {
        try {
            Quiz quiz = quizService.getQuiz(quizId);
            int totalQuestions = quiz.getQuestions().size();
            int totalMarks = quiz.getQuestions().size() * 10;
            float marksPerQuestion = 5;

            Question question = null;
            int numCorrectAnswers = 0;
            for(Map.Entry<String, String> m : answers.entrySet()){
                Long quesId = Long.valueOf(m.getKey());
                question = questionService.getQuestion(Long.valueOf(m.getKey()));
                if(question != null) {
                    if(question.getAnswer().equals(m.getValue())){
                        numCorrectAnswers++;
                    }
                }
            }
            float totalObtainedMarks = numCorrectAnswers*marksPerQuestion;

            QuizResult quizResult = new QuizResult();
            quizResult.setUserId(userId);
            quizResult.setQuiz(quizService.getQuiz(quizId));
            quizResult.setTotalObtainedMarks(totalObtainedMarks);
            final ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
            quizResult.setAttemptDatetime(now.toLocalDate().toString() + " " + now.toLocalTime().toString().substring(0,8));

            quizResultService.addQuizResult(quizResult);
            return ResponseEntity.ok(quizResult);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("There is an Exception");
        }
    }

    @GetMapping(value = "/", params = "userId")
    public ResponseEntity<?> getQuizResults(@RequestParam Long userId){
        try {
            List<QuizResult> quizResultsList =  quizResultService.getQuizResultsByUser(userId);
            Collections.reverse(quizResultsList);
            return ResponseEntity.ok(quizResultsList);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("There is an Exception");
        }
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getQuizResults(){
        try {
            List<QuizResult> quizResultsList =  quizResultService.getQuizResults();
            List<QuizResultDTO> students = new ArrayList<>();
            for(QuizResult quizResults : quizResultsList) {
                User user = userRepository.findUserByUserId(quizResults.getUserId());
                QuizResultDTO student = new QuizResultDTO();
                student.setName(user.getFirstName() + " " + user.getLastName());
                student.setAttemptDatetime(quizResults.getAttemptDatetime());
                student.setQuiz(quizResults.getQuiz());
                student.setQuizResId(quizResults.getQuizResId());
                student.setTotalObtainedMarks(quizResults.getTotalObtainedMarks());
                students.add(student);
            }
            Collections.reverse(students);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("There is an Exception");
        }
    }
}