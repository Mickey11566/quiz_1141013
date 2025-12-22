package com.example.quiz_1141013.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.quiz_1141013.entity.Fillin;
import com.example.quiz_1141013.entity.FillinId;

import jakarta.transaction.Transactional;

@Repository
public interface FillinDao extends JpaRepository<Fillin, FillinId> {

	@Transactional
	@Modifying
	@Query(value = "insert into fillin(quiz_id, question_id,email, answer) " //
			+ " values(?1, ?2, ?3, ?4)", nativeQuery = true)
	public void insert(int quizId, int questionId, String email, String answer);

	@Query(value = "select * from fillin where quiz_id = ?", nativeQuery = true)
	public List<Fillin> getByQuizId(int quizId);
}
