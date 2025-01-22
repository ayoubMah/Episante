package Episante.back.Service;


import Episante.back.Repository.QuestionRepository;
import Episante.back.Models.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizService {

    @Autowired
    private QuestionRepository questionRepository;

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private Map<Long, String> userAnswers = new HashMap<>();

    // Initialize questions (can be loaded from DB or hardcoded) me i prefer the DB
    // i can make a func initialized to initialize the questions but no need, cuz will be initialized from DB by findAll() method
    public Question getNextQuestion() {
        if (questions == null || questions.isEmpty()) {
            questions = questionRepository.findAll(); // Load from DB only if not initialized
        }
        if (currentQuestionIndex < questions.size()) {
            return questions.get(currentQuestionIndex++);
        }
        return null; // No more questions
    }

    public void submitAnswer(Long questionId, String answer) {
        userAnswers.put(questionId, answer);
    }


    public String calculateResult() {
        if (questions == null || questions.isEmpty()) {
            questions = questionRepository.findAll(); // Load from DB if not initialized
        }
        int correctAnswers = 0;
        for (Map.Entry<Long, String> entry : userAnswers.entrySet()) {
            Long questionId = entry.getKey();
            String userAnswer = entry.getValue().toLowerCase().trim(); // the answers should be in lower case

            Optional<Question> question = questionRepository.findById(questionId);
            if(question.isPresent())
            {
                String correctAnswer = question.get().getAnswer().toLowerCase().trim();
                if (userAnswer.equals(correctAnswer)) {
                    correctAnswers++;
                }

            }

        }

        double percentageCorrect = (double) correctAnswers / questions.size() * 100;

        if (percentageCorrect >= 80) {
            return "You are quite smart!, m3alm!";
        } else if (percentageCorrect >= 50) {
            return "You have a decent level of knowledge. zayar m3ana";
        } else {
            return "You might need to brush up on your knowledge. tu es un hmar ";
        }
    }

    public boolean hasMoreQuestions() {
        if (questions == null || questions.isEmpty()) {
            questions = questionRepository.findAll(); // Load from DB if not initialized
        }
        return currentQuestionIndex < questions.size();
    }

    public void resetQuiz() {
        currentQuestionIndex = 0;
        userAnswers.clear();
    }
}
