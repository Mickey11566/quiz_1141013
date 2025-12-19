package com.example.quiz_1141013.constants;

public enum Type {

	SINGLE("single"), //
	MULTIPLE("multiple"), //
	SHORT_ANSWER("short-answer");

	private String type;

	private Type(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static boolean checkType(String input) {
//		if (input.equalsIgnoreCase(Type.SINGLE.getType()) //
//				|| input.equalsIgnoreCase(Type.MULTIPLE.getType()) //
//				|| input.equalsIgnoreCase(Type.SHORT_ANSWER.getType())) {
//			return true;
//		}

//		values()
//		是在Type.java最上面列舉的所有項目 (5:7)
		for (Type type : values()) {
			if (input.equalsIgnoreCase(type.getType())) {
				return true;
			}
		}
		return false;
	}

	public static boolean isChosenType(String input) {
		if (input.equalsIgnoreCase(Type.SINGLE.getType()) //
				|| input.equalsIgnoreCase(Type.MULTIPLE.getType())) {
			return true;
		}
		return false;
	}

}
