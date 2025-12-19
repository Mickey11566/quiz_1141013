package com.example.quiz_1141013.request;

import com.example.quiz_1141013.constants.ValidationMsg;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class UserAddReq {
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = ValidationMsg.EMAIL_ERROR)
	@NotBlank(message = ValidationMsg.EMAIL_ERROR)
	private String email;

	@NotBlank(message = ValidationMsg.PASSWORD_ERROR)
	private String password;

	@NotNull(message = ValidationMsg.NAME_ERROR)
	private String name;

	@Pattern(regexp = "^09\\d{2}[\\s\\-]?\\d{3}[\\s\\-]?\\d{3}$", message = ValidationMsg.PHONE_ERROR)
	@NotNull(message = ValidationMsg.PHONE_ERROR)
	private String phone;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public UserAddReq() {
		super();
	}

	public UserAddReq(@NotBlank(message = "Email error!") String email, //
			@NotBlank(message = "Password error!") String password, //
			@NotNull(message = "Name error!") String name, //
			@NotNull(message = "Phone error!") String phone) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.phone = phone;
	}

}
