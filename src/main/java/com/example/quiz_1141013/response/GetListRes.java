package com.example.quiz_1141013.response;

import java.util.List;

import com.example.quiz_1141013.entity.Quiz;

public class GetListRes extends BasicRes {
	private List<Quiz> quizList;

	public GetListRes() {
		super();
	}

	public GetListRes(int code, String message) {
		super(code, message);
	}

	public GetListRes(int code, String message, List<Quiz> quizList) {
		super(code, message);
		this.quizList = quizList;
	}

	public List<Quiz> getQuizList() {
		return quizList;
	}

	public void setQuizList(List<Quiz> quizList) {
		this.quizList = quizList;
	}

}
