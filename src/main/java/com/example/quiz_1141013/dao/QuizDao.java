package com.example.quiz_1141013.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.quiz_1141013.entity.Fillin;
import com.example.quiz_1141013.entity.Quiz;
import com.example.quiz_1141013.entity.RespondentDTO;
import com.example.quiz_1141013.entity.UserAnswerDTO;

@Repository
public interface QuizDao extends JpaRepository<Quiz, Integer> {

	@Transactional
	@Modifying
	@Query(value = "insert into quiz(title, description, start_date, end_date, published) "//
			+ " values (?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
	public void addQuiz(String title, String description, //
			LocalDate startDate, LocalDate endDate, boolean published);

	@Transactional
	@Modifying
	@Query(value = "update quiz set title = ?2, description =?3, start_date = ?4," //
			+ " end_date = ?5, published = ?6 where id = ?1", nativeQuery = true)
	public int updateQuiz(int quizId, String title, String description, //
			LocalDate startDate, LocalDate endDate, boolean published);

	@Query(value = "select max(id) from quiz", nativeQuery = true)
	public int getMaxId();

	@Query(value = "select * from quiz where is_deleted = 0", nativeQuery = true)
	public List<Quiz> getAll();

	@Query(value = "select * from quiz where id IN ? AND is_deleted = 0", nativeQuery = true)
	public List<Quiz> getQuizById(List<Integer> quizId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE quiz SET is_deleted = 1 WHERE id IN ?", nativeQuery = true)
	public int deleteQuizById(List<Integer> quizId);

	@Query(value = "select * from quiz where title like %?1% and start_date >= ?2 and end_date <= ?3 and is_deleted = 0", nativeQuery = true)
	public List<Quiz> getAll(String keyword, LocalDate startDate, LocalDate endDate);

	@Query("SELECT DISTINCT new com.example.quiz_1141013.entity.RespondentDTO(f.email, f.fillinDate) "
			+ "FROM Fillin f " + "WHERE f.quizId = ?1 " + "ORDER BY f.fillinDate DESC")
	public List<RespondentDTO> findDistinctRespondents(int quizId);


}
