package com.example.quiz_1141013.request;

import com.example.quiz_1141013.constants.ValidationMsg;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class UserUpdateInfoReq {

	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = ValidationMsg.EMAIL_ERROR)
	@NotBlank(message = ValidationMsg.EMAIL_ERROR)
	private String email;

	@NotBlank(message = ValidationMsg.PASSWORD_ERROR)
	private String oldPassword;
	@NotBlank(message = ValidationMsg.PASSWORD_ERROR)
	private String newPassword;

	@NotNull(message = ValidationMsg.NAME_ERROR)
	private String oldName;
	@NotNull(message = ValidationMsg.NAME_ERROR)
	private String NewName;

	@Pattern(regexp = "^09\\d{2}[\\s\\-]?\\d{3}[\\s\\-]?\\d{3}$", message = ValidationMsg.PHONE_ERROR)
	@NotNull(message = ValidationMsg.PHONE_ERROR)
	private String oldPhone;
	@Pattern(regexp = "^09\\d{2}[\\s\\-]?\\d{3}[\\s\\-]?\\d{3}$", message = ValidationMsg.PHONE_ERROR)
	@NotNull(message = ValidationMsg.PHONE_ERROR)
	private String newPhone;

	public UserUpdateInfoReq(
			@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email error!") @NotBlank(message = "Email error!") String email,
			@NotBlank(message = "Password error!") String oldPassword,
			@NotBlank(message = "Password error!") String newPassword, @NotNull(message = "Name error!") String oldName,
			@NotNull(message = "Name error!") String newName,
			@Pattern(regexp = "^09\\d{2}[\\s\\-]?\\d{3}[\\s\\-]?\\d{3}$", message = "Phone error!") @NotNull(message = "Phone error!") String oldPhone,
			@Pattern(regexp = "^09\\d{2}[\\s\\-]?\\d{3}[\\s\\-]?\\d{3}$", message = "Phone error!") @NotNull(message = "Phone error!") String newPhone) {
		super();
		this.email = email;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.oldName = oldName;
		NewName = newName;
		this.oldPhone = oldPhone;
		this.newPhone = newPhone;
	}

	public UserUpdateInfoReq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public String getNewName() {
		return NewName;
	}

	public void setNewName(String newName) {
		NewName = newName;
	}

	public String getOldPhone() {
		return oldPhone;
	}

	public void setOldPhone(String oldPhone) {
		this.oldPhone = oldPhone;
	}

	public String getNewPhone() {
		return newPhone;
	}

	public void setNewPhone(String newPhone) {
		this.newPhone = newPhone;
	}

}
