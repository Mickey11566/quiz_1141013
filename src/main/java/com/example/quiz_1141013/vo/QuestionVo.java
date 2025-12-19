package com.example.quiz_1141013.vo;

import java.util.ArrayList;
import java.util.List;

import com.example.quiz_1141013.constants.ValidationMsg;

import jakarta.validation.constraints.NotBlank;

public class QuestionVo {

	private int quizId;

	private int questionId;

	@NotBlank(message = ValidationMsg.QUESTION_ERROR)
	private String question;

	@NotBlank(message = ValidationMsg.TYPE_ERROR)
	private String type;

	private boolean required;

//	不限制，因簡答題沒有選項
//	賦予空的 List 可預防該屬性沒有mapping 到時會是 null
//	預設值會從 null 變成空的 List
	private List<Options> optionsList = new ArrayList<>();

	public QuestionVo() {
		super();
	}

	public QuestionVo(int quizId, int questionId, String question, String type, boolean required,
			List<Options> optionsList) {
		super();
		this.quizId = quizId;
		this.questionId = questionId;
		this.question = question;
		this.type = type;
		this.required = required;
		this.optionsList = optionsList;
	}

	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public List<Options> getOptionsList() {
		return optionsList;
	}

	public void setOptionsList(List<Options> optionsList) {
		this.optionsList = optionsList;
	}

}
