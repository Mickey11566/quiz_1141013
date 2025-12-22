package com.example.quiz_1141013.constants;

public enum ResMessage {

	SUCCESS(200, "Success!"), //
	PLEASE_LOGIN_FIRST(400, "Please login first!"),
	TYPE_ERROR(400, "Type error!"),
	OPTIONS_SIZE_ERROR(400, "Option size error!"),
	DATE_ERROR(400, "Date error!"),
	REGISTRATION_ERROR(400, "Registration error"),
	PASSWORD_ERROR(400, "Password error"),
	UPDATE_INFO_FAILED(400, "Update info failed!"),
	QUIZ_NOT_FOUND(404, "Quiz not found!"),
	QUESTION_NOT_FOUND(404, "Question not found!"),
	QUIZ_ID_MISMATCH(400, "Quiz id mismatch!"),
	OPTION_NAME_DISMATCH(400, "Option name dismatch"),
	ANSWER_REQUIRED(400, "Answer required"),
	USER_NOT_FOUND(404, "User not found");
	
	private int code;

	private String message;

	private ResMessage(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
