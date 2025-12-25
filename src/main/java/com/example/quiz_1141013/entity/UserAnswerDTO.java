package com.example.quiz_1141013.entity;

public class UserAnswerDTO {
	private int questionId;
	private String questionTitle;
	private String type;
	private String answer;

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public UserAnswerDTO() {
		super();
	}

	public UserAnswerDTO(int questionId, String questionTitle, String type, String answer) {
		super();
		this.questionId = questionId;
		this.questionTitle = questionTitle;
		this.type = type;
		this.answer = answer;
	}

}
