package com.neuroplay.NeuroPlay.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neuroplay.NeuroPlay.Entities.Quizes.QuizAnswer;

public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, Long> {}