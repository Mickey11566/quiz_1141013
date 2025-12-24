package com.example.quiz_1141013.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz_1141013.response.StatisticsRes;
import com.example.quiz_1141013.service.FeedbackService;

@CrossOrigin
@RestController
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;

	@GetMapping("quiz/stat")
	public StatisticsRes statistics(@RequestParam("quizId") int quizId) throws Exception {
		return feedbackService.statistics_test(quizId);

	}
}
