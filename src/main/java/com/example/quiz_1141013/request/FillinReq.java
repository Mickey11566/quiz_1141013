package com.example.quiz_1141013.request;

import java.util.List;

import com.example.quiz_1141013.constants.ValidationMsg;
import com.example.quiz_1141013.vo.Answers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class FillinReq {

	@Min(value = 1, message = ValidationMsg.QUIZ_ID_ERROR)
	private int quizId;

	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = ValidationMsg.EMAIL_ERROR)
	@NotBlank(message = ValidationMsg.EMAIL_ERROR)
	private String email;

	@Valid
	private List<Answers> answersList;

	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	public List<Answers> getAnswersList() {
		return answersList;
	}

	public void setAnswersList(List<Answers> answersList) {
		this.answersList = answersList;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public FillinReq() {
		super();
	}

	public FillinReq(int quizId, String email, List<Answers> answersList) {
		super();
		this.quizId = quizId;
		this.email = email;
		this.answersList = answersList;
	}

}
