package com.example.quiz_1141013.entity;

import java.time.LocalDate;

public class RespondentDTO {
	private String email;
	private LocalDate fillinDate;

	public RespondentDTO() {
		super();
	}

	public RespondentDTO(String email, LocalDate fillinDate) {
		super();
		this.email = email;
		this.fillinDate = fillinDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getFillinDate() {
		return fillinDate;
	}

	public void setFillinDate(LocalDate fillinDate) {
		this.fillinDate = fillinDate;
	}

}
