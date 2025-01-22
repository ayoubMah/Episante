package Episante.back.Service;


import Episante.back.Repository.QuestionRepository;
import Episante.back.Models.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuizService {

    @Autowired
    private QuestionRepository questionRepository;

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private Map<Long, String> userAnswers = new HashMap<>();

    // Initialize questions (can be loaded from DB or hardcoded)
    public void initializeQuestions() {
        // Example of hardcoding questions
        questions = new ArrayList<>();
        questions.add(new Question("How old are you?"));
        questions.add(new Question("What is your IQ?"));
        questions.add(new Question("What is your favorite color?"));
        // Or load from the database
        // questions = questionRepository.findAll();
    }

    public Question getNextQuestion() {
        if (questions == null || questions.isEmpty()) {
            initializeQuestions(); // Initialize if not already done
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
        // Implement your logic to determine the result based on userAnswers
        // Example:
        int smartPoints = 0;
        int simplePoints = 0;
        int stupidPoints = 0;

        for (Map.Entry<Long, String> entry : userAnswers.entrySet()) {
            Long questionId = entry.getKey();
            String answer = entry.getValue().toLowerCase(); // Example: lowercase for comparison

            // Example scoring logic (you'll need to customize this)
            if (questionId == 2 && answer.contains("above 120")) { // Assuming question with id 2 is about IQ
                smartPoints++;
            } else if (answer.contains("yes") || answer.contains("no")) {
                simplePoints++;
            } else {
                stupidPoints++;
            }
        }

        if (smartPoints > simplePoints && smartPoints > stupidPoints) {
            return "You are smart!";
        } else if (simplePoints > smartPoints && simplePoints > stupidPoints) {
            return "You are simple.";
        } else {
            return "You are... interesting."; // Or "stupid" if you prefer
        }
    }

    public boolean hasMoreQuestions() {
        return currentQuestionIndex < questions.size();
    }

    public void resetQuiz() {
        currentQuestionIndex = 0;
        userAnswers.clear();
    }
}
