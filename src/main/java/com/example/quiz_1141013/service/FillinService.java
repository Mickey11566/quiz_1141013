package com.example.quiz_1141013.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.quiz_1141013.constants.ResMessage;
import com.example.quiz_1141013.dao.FillinDao;
import com.example.quiz_1141013.dao.QuestionDao;
import com.example.quiz_1141013.entity.Question;
import com.example.quiz_1141013.request.FillinReq;
import com.example.quiz_1141013.response.BasicRes;
import com.example.quiz_1141013.vo.AnswerVo;
import com.example.quiz_1141013.vo.Options;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FillinService {

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private FillinDao fillinDao;

	@Autowired
	private QuestionDao questionDao;

	public BasicRes fillin(FillinReq req) throws Exception {
//		TODO 透過email取得User資料
//		透過 quizId 取得所有問題
		List<Question> questionList = questionDao.getOptionByQuizId(req.getQuizId());
//		CollectionUtils.isEmpty()
//		檢查 List 是否為 null 
		if (CollectionUtils.isEmpty(questionList)) {
			return new BasicRes(ResMessage.QUESTION_NOT_FOUND.getCode(), ResMessage.QUESTION_NOT_FOUND.getMessage());
		}

//		Map<問題編號, 所有選項編號和選項>
		Map<Integer, List<Options>> questionsMap = new HashMap<>();
		for (Question item : questionList) {
//			把字串 options 轉成物件 Options
			try {
				List<Options> opList = mapper.readValue(item.getOptions(), new TypeReference<>() {
				});
//				將 問題編號 and List<Options> 放入map
				questionsMap.put(item.getQuestionId(), opList);
			} catch (Exception e) {
				throw e;
			}
		}

//		比對選項和答案是否一樣 
		List<AnswerVo> anserVoList = req.getAnswerVoList();
		for(AnswerVo vo : anserVoList) {
			
		}
		
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}
}
