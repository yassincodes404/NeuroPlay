package com.neuroplay.NeuroPlay.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neuroplay.NeuroPlay.Entities.*;

public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, Long> {}