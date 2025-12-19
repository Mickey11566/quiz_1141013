package com.example.quiz_1141013.response;

import java.util.List;

import com.example.quiz_1141013.vo.QuestionVo;

public class GetQuestionRes extends BasicRes {
	List<QuestionVo> questionVoList;

	public List<QuestionVo> getQuestionVoList() {
		return questionVoList;
	}

	public void setQuestionVoList(List<QuestionVo> questionVoList) {
		this.questionVoList = questionVoList;
	}

	public GetQuestionRes() {
		super();
	}

	public GetQuestionRes(int code, String message) {
		super(code, message);
	}

	public GetQuestionRes(int code, String message, List<QuestionVo> questionVoList) {
		super(code, message);
		this.questionVoList = questionVoList;
	}

}
