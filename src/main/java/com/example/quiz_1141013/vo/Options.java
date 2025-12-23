package com.example.quiz_1141013.vo;

public class Options {

	private int code;

	private String optionName;

	public Options(int code, String optionName) {
		super();
		this.code = code;
		this.optionName = optionName;
	}

	public Options() {
		super();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

}
