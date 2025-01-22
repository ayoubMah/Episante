package Episante.back;

import Episante.back.Models.Question;
import Episante.back.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public void run(String... args) throws Exception {
        // Add basic intelligence test questions
        List<Question> questions = Arrays.asList(
                new Question("What is 3 + 4?", "7"),
                new Question("Which planet is closest to the Sun?", "Mercury"),
                new Question("How many continents are there?", "7"),
                new Question("What is the capital of France?", "Paris"),
                new Question("What is the square root of 64?", "8"),
                new Question("Which gas do plants absorb from the atmosphere?", "Carbon dioxide"),
                new Question("How many sides does a triangle have?", "3"),
                new Question("What is the chemical symbol for gold?", "Au"),
                new Question("Which direction does the Sun rise from?", "East"),
                new Question("What is the largest mammal in the world?", "Blue whale")
        );

        questionRepository.saveAll(questions);
    }
}