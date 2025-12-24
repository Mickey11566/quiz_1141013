package com.example.quiz_1141013.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.quiz_1141013.constants.ResMessage;
import com.example.quiz_1141013.constants.Type;
import com.example.quiz_1141013.dao.QuestionDao;
import com.example.quiz_1141013.dao.QuizDao;
import com.example.quiz_1141013.entity.Question;
import com.example.quiz_1141013.entity.Quiz;
import com.example.quiz_1141013.request.QuizCreateReq;
import com.example.quiz_1141013.request.QuizUpdateReq;
import com.example.quiz_1141013.response.BasicRes;
import com.example.quiz_1141013.response.GetListRes;
import com.example.quiz_1141013.response.GetQuestionRes;
import com.example.quiz_1141013.vo.Options;
import com.example.quiz_1141013.vo.QuestionVo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
public class QuizService {

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private QuizDao quizDao;

	@Autowired
	private QuestionDao questionDao;

//	rollbackOn(rollbackFor) = Exception.class 表示此方法發生Exception
//	寫一半的資料都會回溯(rollback)
	@Transactional(rollbackOn = Exception.class)
	public BasicRes create(QuizCreateReq req) throws Exception {
		BasicRes checkRes = check(req);
//		方法 check() 的結果只有2種， null 和 非 null(BasicRes)
//		非 null 的結果表示檢查有錯
		if (checkRes != null) {
//			把檢查錯誤的結果回傳
			return checkRes;
		}
//		新增問卷
		quizDao.addQuiz(req.getTitle(), req.getDescription(), req.getStartDate(), req.getEndDate(), req.isPublished());
//		取得最新quiz_id編號
		int quizId = quizDao.getMaxId();
//		將 question 寫進DB
		for (QuestionVo vo : req.getQuestionVoList()) {
//			將 vo 中 List<Options> 轉換成字串
			try {
				String optionsListStr = mapper.writeValueAsString(vo.getOptionsList());
				questionDao.addQuestion(quizId, vo.getQuestionId(), vo.getQuestion(), //
						vo.getType(), vo.isRequired(), optionsListStr);
			} catch (Exception e) {
				throw e;
			}
		}
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	};

	private BasicRes check(QuizCreateReq req) {
//		排除開始時間比結束時間晚 或 開始時間比當天早
		if (req.getStartDate().isAfter(req.getEndDate()) || req.getStartDate().isBefore(LocalDate.now())) {
			return new BasicRes(ResMessage.DATE_ERROR.getCode(), ResMessage.DATE_ERROR.getMessage());
		}
		List<QuestionVo> voList = req.getQuestionVoList();
		for (QuestionVo vo : voList) {
//			排除非固定3種type
			if (!Type.checkType(vo.getType())) {
				return new BasicRes(ResMessage.TYPE_ERROR.getCode(), ResMessage.TYPE_ERROR.getMessage());
			}
//			type 是選擇題時 選項至少要有一個
			if (Type.isChosenType(vo.getType())) {
				if (vo.getOptionsList().size() < 1) {
					return new BasicRes(ResMessage.OPTIONS_SIZE_ERROR.getCode(),
							ResMessage.OPTIONS_SIZE_ERROR.getMessage());
				}
			} else {
//				Type 為簡答題 不能有選項
				if (!vo.getOptionsList().isEmpty()) {
					return new BasicRes(ResMessage.OPTIONS_SIZE_ERROR.getCode(),
							ResMessage.OPTIONS_SIZE_ERROR.getMessage());
				}
			}
		}
		return null;
	}

	@Transactional(rollbackOn = Exception.class)
	public BasicRes updateQuiz(QuizUpdateReq req) throws Exception {
//		方法 check() 的參數資料型態是 QuizCreateReq
//		對 QuizUpdateReq 來說是父類別
//		若把子類別 QuizUpdateReq 當參數放到 check 中
//		資料型態會自動轉型成 QuizCreateReq
//		即 check((QuizCreateReq) req)
//		這樣結果差別只在於子類別中的屬性 quizId 都會預設值為0
//		但不影響方法 check2 的檢查， 因沒用到 quizId
		BasicRes checkRes = check(req);
//		方法 check() 的結果只有2種， null 和 非 null(BasicRes)
//		非 null 的結果表示檢查有錯
		if (checkRes != null) {
//			把檢查錯誤的結果回傳
			return checkRes;
		}
//		檢查 quizId 和 QuestionVo 中的 quizId是否一樣
		for (QuestionVo vo : req.getQuestionVoList()) {
			if (req.getQuizId() != vo.getQuizId()) {
				return new BasicRes(ResMessage.QUIZ_ID_MISMATCH.getCode(), ResMessage.QUIZ_ID_MISMATCH.getMessage());
			}
		}
//		更新quiz
		int updateRes = quizDao.updateQuiz(req.getQuizId(), req.getTitle(), req.getDescription(), req.getStartDate(),
				req.getEndDate(), req.isPublished());
//		有找到 quizId並更新成功(即使要更新的資料與DB中的資料都一樣)
//		也會回傳 1 (where 條件帶的是PK)
		if (updateRes != 1) {
			return new BasicRes(ResMessage.QUIZ_NOT_FOUND.getCode(), ResMessage.QUIZ_NOT_FOUND.getMessage());
		}
//		確認 quizId 存在 > 先刪除舊問題 => 新增問題
		questionDao.deleteQuestionByQuizId(req.getQuizId());
//		新增問題
//		將 question 寫進DB
		for (QuestionVo vo : req.getQuestionVoList()) {
//			將 vo 中 List<Options> 轉換成字串
			try {
				String optionsListStr = mapper.writeValueAsString(vo.getOptionsList());
				questionDao.addQuestion(vo.getQuizId(), vo.getQuestionId(), vo.getQuestion(), //
						vo.getType(), vo.isRequired(), optionsListStr);
			} catch (Exception e) {
				throw e;
			}
		}
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	};

	public GetListRes getAll() {
		return new GetListRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), quizDao.getAll());
	};

	public GetListRes getAll(String keyword, LocalDate startDate, LocalDate endDate) {
//		將 keyword 是 null(沒有輸入值) 或 空字串 或 全空白字串 轉換成空字串
//		可在取資料時 使用 like %% 
//		%%中間是空字串時，會撈取所有資料
		if (!StringUtils.hasText(keyword)) {
			keyword = "";
		}
		if (startDate == null) {
			startDate = LocalDate.of(1970, 1, 1);
		}
		if (endDate == null) {
			endDate = LocalDate.of(2070, 1, 1);
		}

		return new GetListRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(),
				quizDao.getAll(keyword, startDate, endDate));
	};

	public GetQuestionRes getQuestionByQuizId(int quizId) throws Exception {
		List<Question> list = questionDao.getOptionByQuizId(quizId);
		List<QuestionVo> questionVoList = new ArrayList<>();
//		把 Question 中每個 String options 轉換成自定義物件 Options
		for (Question item : list) {
//			轉換物件
			try {
				List<Options> opList = mapper.readValue(item.getOptions(), new TypeReference<>() {
				});
//				把 Question 中每個屬性值以及 opList set 到 QuestionVo 對應屬性位置
				QuestionVo vo = new QuestionVo(quizId, item.getQuestionId(), item.getQuestion(), //
						item.getType(), item.isRequired(), opList);
//				將每個 vo 加到 questionVoList
				questionVoList.add(vo);
			} catch (Exception e) {
				throw e;
			}
		}
		return new GetQuestionRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), questionVoList);
	};

	@Transactional(rollbackOn = Exception.class)
	public BasicRes removeQuiz(List<Integer> quizId) {
		// 執行更新，並取得實際被修改的筆數
		int deletedCount = quizDao.deleteQuizById(quizId);

		// 如果受影響的筆數大於 0，表示至少有一份問卷被成功軟刪除
		if (deletedCount > 0) {
			return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
		} else {
			// 如果一筆都沒改到，表示傳入的 ID 在資料庫都找不到（或是已被刪除）
			return new BasicRes(ResMessage.QUIZ_NOT_FOUND.getCode(), ResMessage.QUIZ_NOT_FOUND.getMessage());
		}
	}
}
