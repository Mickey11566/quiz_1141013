package com.example.quiz_1141013.request;

import com.example.quiz_1141013.constants.ValidationMsg;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserLoginReq {
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = ValidationMsg.EMAIL_ERROR)
	@NotBlank(message = ValidationMsg.EMAIL_ERROR)
	private String email;

	@NotBlank(message = ValidationMsg.PASSWORD_ERROR)
	private String password;

	public UserLoginReq(
			@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email error!") @NotBlank(message = "Email error!") String email,
			@NotBlank(message = "Password error!") String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public UserLoginReq() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
