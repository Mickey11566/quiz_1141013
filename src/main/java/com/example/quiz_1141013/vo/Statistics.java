package com.example.quiz_1141013.vo;

import java.util.List;

public class Statistics {

	private int questionId;

	private List<OptionsCount> opCountList;

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public List<OptionsCount> getOpCountList() {
		return opCountList;
	}

	public void setOpCountList(List<OptionsCount> opCountList) {
		this.opCountList = opCountList;
	}

	public Statistics() {
		super();
	}

	public Statistics(int questionId, List<OptionsCount> opCountList) {
		super();
		this.questionId = questionId;
		this.opCountList = opCountList;
	}

}
