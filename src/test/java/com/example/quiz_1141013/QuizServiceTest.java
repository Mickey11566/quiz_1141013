package com.example.quiz_1141013;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.quiz_1141013.request.QuizCreateReq;
import com.example.quiz_1141013.service.QuizService;
import com.example.quiz_1141013.vo.QuestionVo;

@SpringBootTest
public class QuizServiceTest {

	@Autowired
	private QuizService quizService;
	
	public void createQuizTest() {
		QuestionVo vo = new QuestionVo(1,1, "test123", "single", false, new ArrayList<>());
	}
}
