package com.example.quiz_1141013.request;

import com.example.quiz_1141013.constants.ValidationMsg;

import jakarta.validation.constraints.Min;

public class QuizUpdateReq extends QuizCreateReq {

//	因是更新已存在問卷
//	quiz id 至少是 1
	@Min(value = 1, message = ValidationMsg.QUIZ_ID_ERROR)
	private int quizId;

	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

}
