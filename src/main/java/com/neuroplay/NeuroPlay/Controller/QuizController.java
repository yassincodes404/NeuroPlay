package com.neuroplay.NeuroPlay.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.neuroplay.NeuroPlay.Entities.QuizAnswer;
import com.neuroplay.NeuroPlay.Repository.QuizAnswerRepository;
import com.neuroplay.NeuroPlay.Repository.QuizQuestionRepository;

import java.util.List;

@Controller
public class QuizController {

    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizAnswerRepository quizAnswerRepository;

    public QuizController(QuizQuestionRepository quizQuestionRepository, QuizAnswerRepository quizAnswerRepository) {
        this.quizQuestionRepository = quizQuestionRepository;
        this.quizAnswerRepository = quizAnswerRepository;
    }

    @GetMapping("/quiz")
    public String quizPage(Model model) {
        List<QuizAnswer> questions = quizAnswerRepository.findAll();
        model.addAttribute("questions", questions);
        return "quiz-template";
    }
}