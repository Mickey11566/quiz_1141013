package com.example.quiz_1141013.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.quiz_1141013.constants.ResMessage;
import com.example.quiz_1141013.dao.FillinDao;
import com.example.quiz_1141013.dao.UserDao;
import com.example.quiz_1141013.entity.Fillin;
import com.example.quiz_1141013.entity.User;
import com.example.quiz_1141013.response.Feedback;
import com.example.quiz_1141013.response.FeedbackRes;
import com.example.quiz_1141013.vo.AnswerVo;
import com.example.quiz_1141013.vo.Answers;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FeedbackService {

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private FillinDao fillinDao;

	@Autowired
	private UserDao userDao;

	public FeedbackRes feedback(int quizId) throws Exception {
//		res 包含多位使用者(email)的填答
		List<Fillin> res = fillinDao.getByQuizId(quizId);
//		Map<email, List<Answers>>
		Map<String, List<Answers>> map = new HashMap<>();
		List<Answers> ansList = new ArrayList<>();
		for (Fillin item : res) {
			try {
//				把字串 answer 轉換成 物件 List<AnswerVo>
//				一個List<AnswerVo> 等同一個問題的所有編號-選項
				List<AnswerVo> voList = mapper.readValue(item.getAnswer(), new TypeReference<>() {
				});
				Answers ans = new Answers(item.getQuestionId(), voList);
				ansList.add(ans);
//				把相同email對應 List<Answers> 取出
				ansList = map.get(item.getEmail());
				if (CollectionUtils.isEmpty(ansList)) {
//					如果判斷為 true  => 代表 map 中沒有該使用者 email
//					清空原本的 ansList 內容
					ansList = new ArrayList<>();

				}
			} catch (Exception e) {
				throw e;
			}
		}
		List<Feedback> feedbackList = new ArrayList<>();
		for (String email : map.keySet()) {
			User user = userDao.getUser(email);
			feedbackList.add(new Feedback(quizId, email, map.get(email)));
		}
		return new FeedbackRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), feedbackList);
	}

	/* 一次性撈取所有 email 對應的 User 資訊 --> 不管 email 有多少，就只會使用 userDao 一次 */
	private List<Feedback> getFeedbackList(List<Fillin> fillinList, int quizId, //
			Map<String, List<Answers>> map) {
		/* 蒐集同一張問卷下的所有 email */
		List<String> emailList = new ArrayList<>();
		fillinList.forEach(item -> {
			emailList.add(item.getEmail());
		});
		/* 一次性的撈取包含所有 email 的 User 資訊 */
		List<User> userList = userDao.getUsersIn(emailList);
		/* 生成所有 FeedbackRes */
		List<Feedback> feedbackList = new ArrayList<>();
		userList.forEach(item -> {
			feedbackList.add(new Feedback(quizId, item.getEmail(), map.get(item.getEmail())));
		});
		return feedbackList;
	}

}
