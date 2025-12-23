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
import com.example.quiz_1141013.response.StatisticsRes;
import com.example.quiz_1141013.vo.AnswerVo;
import com.example.quiz_1141013.vo.Answers;
import com.example.quiz_1141013.vo.OptionsCount;
import com.example.quiz_1141013.vo.Statistics;
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

	private StatisticsRes statistics(int quizId) throws Exception {

//		res 包含多位使用者(email)的填答
		List<Fillin> res = fillinDao.getByQuizId(quizId);
//		Map<questionId, Map<code-optionName, count>>
		Map<Integer, Map<String, Integer>> map = new HashMap<>();
		for (Fillin item : res) {
			try {
//				把字串 answer 轉換成 物件 List<AnswerVo>
//				一個List<AnswerVo> 等同一個問題的所有編號-選項
				List<AnswerVo> voList = mapper.readValue(item.getAnswer(), new TypeReference<>() {
				});

//				從voList 蒐集 code(選項編號) 對應的check
//				遍歷完之後 一個 codeCountMap 會有x筆資料:
//				如：編號:1, count = 0, 編號:2, count = 1,..... 
				Map<String, Integer> codeCountMap = CollectionUtils.isEmpty(map.get(item.getQuestionId()))
						? new HashMap<>()
						: map.get(item.getQuestionId());

				voList.forEach(vo -> {
//					第一筆資料 codeCountMap 用了 code 當 key 取出對應的 value 肯定是 null
//					而沒資料會 null 的原因為 codeCountMap 的資料型態為 Integer
					String str = String.valueOf(vo.getCode() + "-" + vo.getOptionName());
					int count = codeCountMap.get(str) == null ? 0 : codeCountMap.get(str);
					if (vo.isCheck()) {
						count++;
					}
					codeCountMap.put(str, count);
				});
				map.put(item.getQuestionId(), codeCountMap);
			} catch (Exception e) {
				throw e;
			}
		}
//		將 map List<Statistics>
		List<Statistics> list = new ArrayList<>();
		map.forEach((k, v) -> {
			List<OptionsCount> opCountList = new ArrayList<>();
//			v 就是 Map<code-optionName, count>
			v.forEach((k1, v1) -> {
//				array = [code, optionName]
				String[] array = k1.split("-");
//				array[0] 是選項編號(code)，需要將資料型態轉回 int
				OptionsCount opCount = new OptionsCount(Integer.valueOf(array[0]), array[1], v1);
				opCountList.add(opCount);
			});
			Statistics stat = new Statistics(k, opCountList);
			list.add(stat);
		});
		return new StatisticsRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), list);
	};

	private StatisticsRes statistics_test(int quizId) throws Exception {

//		res 包含多位使用者(email)的填答
		List<Fillin> res = fillinDao.getByQuizId(quizId);
		Map<Integer, List<OptionsCount>> map = new HashMap<>();
		for (Fillin item : res) {
			try {
//				把字串 answer 轉換成 物件 List<AnswerVo>
//				一個List<AnswerVo> 等同一個問題的所有編號-選項
				List<AnswerVo> voList = mapper.readValue(item.getAnswer(), new TypeReference<>() {
				});
				List<OptionsCount> opCountList = CollectionUtils.isEmpty(map.get(item.getQuestionId()))
						? new ArrayList<>()
						: map.get(item.getQuestionId());

				voList.forEach(vo -> {
					OptionsCount opCount = new OptionsCount(vo.getCode(), vo.getOptionName(), vo.isCheck() ? 1 : 0);
					opCountList.add(opCount);
				});
				map.put(item.getQuestionId(), opCountList);
			} catch (Exception e) {
				throw e;
			}
		}

		return null;
	}

}
