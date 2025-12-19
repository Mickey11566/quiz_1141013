package com.example.quiz_1141013.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quiz_1141013.entity.Fillin;
import com.example.quiz_1141013.entity.FillinId;

@Repository
public interface FillinDao extends JpaRepository<Fillin, FillinId> {

}
