package com.example.quiz_1141013.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz_1141013.request.QuizCreateReq;
import com.example.quiz_1141013.request.QuizUpdateReq;
import com.example.quiz_1141013.response.BasicRes;
import com.example.quiz_1141013.response.GetListRes;
import com.example.quiz_1141013.response.GetQuestionRes;
import com.example.quiz_1141013.service.QuizService;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
public class QuizController {

	@Autowired
	private QuizService quizService;

	@PostMapping("quiz/create")
	public BasicRes create(@Valid @RequestBody QuizCreateReq req) throws Exception {
		return quizService.create(req);
	}

	@GetMapping("quiz/getall")
	public BasicRes getAll() {
		return quizService.getAll();
	}

	@GetMapping("quiz/get_filter_data")
	public BasicRes getAll(//
			@RequestParam("keyword") String keyword, //
			@RequestParam("startDate") LocalDate startDate, //
			@RequestParam("endDate") LocalDate endDate) {
		return quizService.getAll(keyword, startDate, endDate);

	}

//	http://localhost:8080/quiz/getquestion?quizId=3
	@GetMapping("quiz/getquestion")
	public GetQuestionRes getQuestionByQuizId(@RequestParam("quizId") int quizId) throws Exception {
		return quizService.getQuestionByQuizId(quizId);
	}

	@PostMapping("quiz/update")
	public BasicRes updateQuiz(@RequestBody QuizUpdateReq req) throws Exception {
		return quizService.updateQuiz(req);
	}

}
