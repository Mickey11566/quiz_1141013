package com.example.quiz_1141013.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.quiz_1141013.constants.ResMessage;
import com.example.quiz_1141013.constants.Type;
import com.example.quiz_1141013.dao.FillinDao;
import com.example.quiz_1141013.dao.QuestionDao;
import com.example.quiz_1141013.entity.Fillin;
import com.example.quiz_1141013.entity.Question;
import com.example.quiz_1141013.request.FillinReq;
import com.example.quiz_1141013.response.BasicRes;
import com.example.quiz_1141013.response.StatisticsRes;
import com.example.quiz_1141013.vo.AnswerVo;
import com.example.quiz_1141013.vo.Answers;
import com.example.quiz_1141013.vo.Options;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
public class FillinService {

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private FillinDao fillinDao;

	@Autowired
	private QuestionDao questionDao;

	@Transactional(rollbackOn = Exception.class)
	public BasicRes fillin(FillinReq req) throws Exception {
		// 使用 email 取得 User 資料
		/* 使用 quizId 取得所有問題 */
		List<Question> questionList = questionDao.getOptionByQuizId(req.getQuizId());
		/* CollectionUtils.isEmpty(): 有檢查 list 是否為 null */
		if (CollectionUtils.isEmpty(questionList)) {
			return new BasicRes(ResMessage.QUESTION_NOT_FOUND.getCode(), //
					ResMessage.QUESTION_NOT_FOUND.getMessage());
		}
		/* 把 answersList 轉成 Map<QuestionId, List<AnswerVo>> */
		Map<Integer, List<AnswerVo>> quesIdAnsMap = new HashMap<>();
		for (Answers item : req.getAnswersList()) {
			quesIdAnsMap.put(item.getQuestionId(), item.getAnswerVoList());
		}
		for (Question question : questionList) {
			List<AnswerVo> voList = quesIdAnsMap.get(question.getQuestionId());
			/* 必填但沒答案或選項 */
			if (question.isRequired() && CollectionUtils.isEmpty(voList)) {
				return new BasicRes(ResMessage.ANSWER_REQUIRED.getCode(), //
						ResMessage.ANSWER_REQUIRED.getMessage());
			}
			/* 跳過簡答題 */
			if (question.getType().equals(Type.SHORT_ANSWER.getType())) {
				continue;
			}
			/* 把 字串 options 轉成物件 Options */
			try {
				List<Options> opList = mapper.readValue(question.getOptions(), new TypeReference<>() {
				});

				boolean isMatch = voList != null && voList.stream().allMatch(vo -> isSameOption.test(vo, opList));
				if (!isMatch) {
					return new BasicRes(ResMessage.OPTION_NAME_DISMATCH.getCode(), //
							ResMessage.OPTION_NAME_DISMATCH.getMessage());
				}
			} catch (Exception e) {
				throw e;
			}
		}
//		寫資料
		for (int questionId : quesIdAnsMap.keySet()) {
			try {
				fillinDao.insert(req.getQuizId(), questionId, req.getEmail(),
						mapper.writeValueAsString(quesIdAnsMap.get(questionId)));
			} catch (Exception e) {
				throw e;
			}
		}

		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

	private BiPredicate<AnswerVo, List<Options>> isSameOption = (ans, opList) -> {
		if (ans == null || CollectionUtils.isEmpty(opList)) {
			return false;
		}
		/* 比對選項編號一樣時，選項是否一樣 */
		for (Options op : opList) {
			if (ans.getCode() == op.getCode() && !ans.getOptionName().equals(op.getOptionName())) {
				return false;
			}
		}
		return true;
	};
	

}
