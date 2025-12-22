package com.example.quiz_1141013.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz_1141013.request.FillinReq;
import com.example.quiz_1141013.response.BasicRes;
import com.example.quiz_1141013.service.FillinService;

import jakarta.validation.Valid;

@RestController
public class FillinController {

	@Autowired
	private FillinService fillinService;

	@PostMapping(value = "quiz/fillin")
	public BasicRes fillin(@Valid @RequestBody FillinReq req) throws Exception {
		return fillinService.fillin(req);
	}
}
