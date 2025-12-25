package com.example.quiz_1141013.response;

import java.util.List;

import com.example.quiz_1141013.entity.RespondentDTO;

public class RespondentRes extends BasicRes {
	List<RespondentDTO> respondentDTOList;

	public RespondentRes() {
		super();
	}

	public RespondentRes(int code, String message) {
		super(code, message);
	}

	public RespondentRes(int code, String message, List<RespondentDTO> respondentDTOList) {
		super(code, message);
		this.respondentDTOList = respondentDTOList;
	}

	public List<RespondentDTO> getRespondentDTOList() {
		return respondentDTOList;
	}

	public void setRespondentDTOList(List<RespondentDTO> respondentDTOList) {
		this.respondentDTOList = respondentDTOList;
	}

}
