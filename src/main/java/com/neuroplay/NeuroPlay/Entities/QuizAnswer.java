package com.neuroplay.NeuroPlay.Entities;

import jakarta.persistence.*;

@Entity
public class QuizAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String answerText;
    private boolean correct;

    @ManyToOne
    @JoinColumn(name = "quiz_question_id")
    private QuizQuestion quizQuestion;

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAnswerText() { return answerText; }
    public void setAnswerText(String answerText) { this.answerText = answerText; }
    public boolean isCorrect() { return correct; }
    public void setCorrect(boolean correct) { this.correct = correct; }
    public QuizQuestion getQuizQuestion() { return quizQuestion; }
    public void setQuizQuestion(QuizQuestion quizQuestion) { this.quizQuestion = quizQuestion; }
}