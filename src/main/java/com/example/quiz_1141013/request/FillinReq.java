package com.example.quiz_1141013.request;

import java.util.List;

import com.example.quiz_1141013.constants.ValidationMsg;
import com.example.quiz_1141013.vo.AnswerVo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class FillinReq {

	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = ValidationMsg.EMAIL_ERROR)
	@NotBlank(message = ValidationMsg.EMAIL_ERROR)
	private String email;

	@Min(value = 1, message = ValidationMsg.QUIZ_ID_ERROR)
	private int quizId;

	@Min(value = 1, message = ValidationMsg.QUESTION_ID_ERROR)
	private int questionId;
	
	@NotEmpty(message = ValidationMsg.ANSWERVO_IS_EMPTY)
	private List<AnswerVo> answerVoList;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public List<AnswerVo> getAnswerVoList() {
		return answerVoList;
	}

	public void setAnswerVoList(List<AnswerVo> answerVoList) {
		this.answerVoList = answerVoList;
	}

}
