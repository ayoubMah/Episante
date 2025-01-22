package Episante.back;

import Episante.back.Models.Question;
import Episante.back.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final QuestionRepository questionRepository;

    @Autowired
    public DataLoader(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Delete all existing data
        questionRepository.deleteAll();

        // Load new questions
        List<Question> questions = Arrays.asList(
                new Question("What is your age?", "number", null, "years"),
                new Question("What is your weight?", "number", null, "kg"),
                new Question("What is your height?", "number", null, "cm"),
                new Question("What is your sex?", "select", "Male,Female,Other",null),
                new Question("How many miles do you walk/run per day?", "number", null,"miles"),
                new Question("Do you smoke?", "select", "Yes,No",null),
                new Question("How many hours do you sleep per day?", "number", null, "hours"),
                new Question("Do you have a history of heart disease in your family?", "select", "Yes,No",null),
                new Question("How many liters of water do you drink per day?", "number",null,"liters"),
                new Question("Do you have any known allergies?", "select", "Yes,No", null)
        );
        questionRepository.saveAll(questions);
    }
}