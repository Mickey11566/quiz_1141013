package com.example.quiz_1141013.response;

import java.util.List;

import com.example.quiz_1141013.request.FillinReq;
import com.example.quiz_1141013.vo.Answers;

public class Feedback extends FillinReq {

	public Feedback() {
		super();
	}

	public Feedback(int quizId, String email, List<Answers> answersList) {
		super(quizId, email, answersList);
	}

}
