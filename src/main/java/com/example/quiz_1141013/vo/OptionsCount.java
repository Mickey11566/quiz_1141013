package com.example.quiz_1141013.vo;

public class OptionsCount extends Options {

	private int count;

	public OptionsCount(int code, String optionName, int count) {
		super(code, optionName);
		this.count = count;
	}

	public OptionsCount() {
		super();
	}

	public OptionsCount(int code, String optionName) {
		super(code, optionName);
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
