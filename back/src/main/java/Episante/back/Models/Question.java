package Episante.back.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String questionText;
    private String answer; // add the correct answer for testing purposes


    public Question() {}

    public Question(String questionText, String answer) {
        this.questionText = questionText;
        this.answer = answer ;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswer(){
        return answer ;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
