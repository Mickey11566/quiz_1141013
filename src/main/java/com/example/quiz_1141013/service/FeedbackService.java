package com.example.quiz_1141013.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

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

	public StatisticsRes statistics(int quizId) throws Exception {

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
//			v 為 Map<code-optionName, count>
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

//	public StatisticsRes statistics_test(int quizId) throws Exception {
//		/* res 包含了多位使用者(email)的填答 */
//		List<Fillin> res = fillinDao.getByQuizId(quizId);
//		/* Map<questionId, List<OptionsCount>> */
//		Map<Integer, List<OptionsCount>> map = new HashMap<>();
//		for (Fillin item : res) {
//			try {
//				/*
//				 * 把字串 answer 轉換成物件 List<AnswerVo> 
//				 * 這邊一個 List<AnswerVo> 只包含了一個問題的 所有編號-選項
//				 */
//				List<AnswerVo> voList = mapper.readValue(item.getAnswer(), new TypeReference<>() {
//				});
//				/* voList 轉成 List<OptionsCount> */
//				List<OptionsCount> opCountList = CollectionUtils.isEmpty(map.get(item.getQuestionId()))
//						? new ArrayList<>()
//						: map.get(item.getQuestionId());
//				voList.forEach(vo -> {
//					/* 有選 */
//					if (vo.isCheck()) {
//						/*
//						 * 第一筆資料 --> opCountList 是空的，不用 CollectionUtilsE.isEmpty() 
//						 * 判斷是因為前面已經把其設定為 new ArrayList<>()， 要使用也可以
//						 */
//						if (opCountList.isEmpty()) {
//							/* 因為是第一筆資料，所以有選的次數直接變成1 */
//							opCountList.add(new OptionsCount(vo.getCode(), vo.getOptionName(), 1));
//						} else {
//							/* 遍歷並比對相同編號 */
//							opCountList.forEach(op -> {
//								/* 比對相同編號 --> 取出 op 中的次數 --> +1 --> set 回去 */
//								if (op.getCode() == vo.getCode()) {
//									op.setCount(op.getCount() + 1);
//								}
//							});
//						}
//					}
//				});
//				map.put(item.getQuestionId(), opCountList);
//			} catch (Exception e) {
//				throw e;
//			}
//		}
//		/* 把 map 轉成 List<Statistics> */
//		List<Statistics> list = new ArrayList<>();
//		map.forEach((k, v) -> {
//			list.add(new Statistics(k, v));
//		});
//		return new StatisticsRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), list);
//	}

	public StatisticsRes statistics_test(int quizId) throws Exception {
		/* res 包含了多位使用者(email)的填答 */
		List<Fillin> res = fillinDao.getByQuizId(quizId);
		/* Map<questionId, List<OptionsCount>> */
		Map<Integer, List<OptionsCount>> map = new HashMap<>();
		for (Fillin item : res) {
			try {
				/*
				 * 把字串 answer 轉換成物件 List<AnswerVo> 這邊一個 List<AnswerVo> 只包含了一個問題的 所有編號-選項
				 */
				List<AnswerVo> voList = mapper.readValue(item.getAnswer(), new TypeReference<>() {
				});
				/* voList 轉成 List<OptionsCount> */
				List<OptionsCount> opCountList = CollectionUtils.isEmpty(map.get(item.getQuestionId()))
						? new ArrayList<>()
						: map.get(item.getQuestionId());
				voList.forEach(vo -> {
					OptionsCount opCount = new OptionsCount(vo.getCode(), vo.getOptionName(), 0);
					/* 判斷 vo 中的編號是否有存在於 opCountList 中 */
					if (!isIncludeCode.test(vo, opCountList)) {
						/* opCountList 不存在相同編號的 vo --> 新增 */
						opCountList.add(opCount);
					} else {
						opCount = opCountList.stream()
								/* 使用 filter 篩選出 code 相同的物件 */
								.filter(opItem -> opItem.getCode() == vo.getCode())
								/* 取出第一個符合條件的 */
								.findFirst()
								/* 如果沒找到，則回傳新建立的物件(正常應該都會有，因為上面的 if 已經先判斷過了) */
								.orElse(new OptionsCount());
					}
					/* 有選 */
					if (vo.isCheck()) {
						/* 遍歷並比對相同編號 */
						opCountList.forEach(op -> {
							/* 比對相同編號 --> 取出 op 中的次數 --> +1 --> set 回去 */
							if (op.getCode() == vo.getCode()) {
								op.setCount(op.getCount() + 1);
							}
						});
					}
				});
				map.put(item.getQuestionId(), opCountList);
			} catch (Exception e) {
				throw e;
			}
		}
		/* 把 map 轉成 List<Statistics> */
		List<Statistics> list = new ArrayList<>();
		map.forEach((k, v) -> {
			list.add(new Statistics(k, v));
		});
		return new StatisticsRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), list);
	}

	private BiPredicate<AnswerVo, List<OptionsCount>> isIncludeCode = (vo, opCountList) -> {
		if (vo == null || CollectionUtils.isEmpty(opCountList)) {
			return false;
		}
		/* 比對選項編號一樣時，選項是否一樣 */
		for (OptionsCount opCount : opCountList) {
			if (vo.getCode() == opCount.getCode()) {
				return true;
			}
		}
		return false;
	};

}
