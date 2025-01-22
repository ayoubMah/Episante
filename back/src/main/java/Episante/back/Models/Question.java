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
    private String questionType; // "number", "select", "text"
    private String options; // Comma-separated options for select questions (e.g., "Male,Female,Other")
    private String unit; // Unit for numeric input if any (e.g., "kg", "cm", "years")

    public Question() {
    }

    public Question(String questionText, String questionType, String options,String unit) {
        this.questionText = questionText;
        this.questionType = questionType;
        this.options = options;
        this.unit = unit;
    }

    public Long getId() {
        return id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
