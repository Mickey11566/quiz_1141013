package com.example.quiz_1141013.entity;
import com.example.quiz_1141013.constants.ValidationMsg;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Table(name = "user")
@Entity
public class User {

	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = ValidationMsg.EMAIL_ERROR)
	@NotBlank(message = ValidationMsg.EMAIL_ERROR)
	@Column(name = "email")
	@Id
	private String email;

	@NotBlank(message = ValidationMsg.PASSWORD_ERROR)
	@Column(name = "password")
	private String password;

	@NotBlank(message = ValidationMsg.NAME_ERROR)
	@Column(name = "name")
	private String name;

	@Pattern(regexp = "^09\\d{2}[\\s\\-]?\\d{3}[\\s\\-]?\\d{3}$", message = ValidationMsg.PHONE_ERROR)
	@NotBlank(message = ValidationMsg.PHONE_ERROR)
	@Column(name = "phone")
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

}
