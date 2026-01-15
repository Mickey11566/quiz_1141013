	package com.example.quiz_1141013.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.quiz_1141013.constants.ResMessage;
import com.example.quiz_1141013.dao.UserDao;
import com.example.quiz_1141013.entity.User;
import com.example.quiz_1141013.request.UserAddReq;
import com.example.quiz_1141013.request.UserLoginReq;
import com.example.quiz_1141013.response.BasicRes;

@EnableScheduling
@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	public BasicRes addUser(UserAddReq req) {
		String email = req.getEmail();
		String password = req.getPassword();
		String name = req.getName();
		String phone = req.getPhone();
		int res = userDao.addUser(email, encoder.encode(password), name, phone);
		if (res == 1) {
			return new BasicRes(ResMessage.SUCCESS.getCode(), //
					ResMessage.SUCCESS.getMessage());
		} else {
			return new BasicRes(ResMessage.REGISTRATION_ERROR.getCode(), //
					ResMessage.REGISTRATION_ERROR.getMessage());
		}
	}

	public BasicRes login(UserLoginReq req) {
		String email = req.getEmail();
		String password = req.getPassword();

		User user = userDao.getUser(email);

		if (user == null) {
			return new BasicRes(ResMessage.USER_NOT_FOUND.getCode(), //
					ResMessage.USER_NOT_FOUND.getMessage());
		}
//		比對密碼:
//		比對輸入的密碼與資料庫中加密過的密碼是否相同
		if (!encoder.matches(password, user.getPassword())) {
			return new BasicRes(ResMessage.PASSWORD_ERROR.getCode(), //
					ResMessage.PASSWORD_ERROR.getMessage());
		}

		return new BasicRes(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage());
	}
	
//	單位:           秒 分 時 日 月 週
//	@Scheduled(cron = "* * * * * *")
//	public void test() {
//		System.out.println(LocalDateTime.now());	
//	}
	

	public BasicRes updateInfo(String email, String oldPwd, String newPwd, String oldName, String newName,String oldPhone, String newPhone) {
//		比對帳號	
		User user = userDao.getUser(email);
		if (user == null) {
			return new BasicRes(ResMessage.USER_NOT_FOUND.getCode(), //
					ResMessage.USER_NOT_FOUND.getMessage());
		}
//		比對密碼:
//		比對輸入的密碼與資料庫中加密過的密碼是否相同
		if (!encoder.matches(oldPwd, user.getPassword())) {
			return new BasicRes(ResMessage.PASSWORD_ERROR.getCode(), //
					ResMessage.PASSWORD_ERROR.getMessage());
		}
//		更新加密後的密碼
		int res = userDao.updateInfo(email, encoder.encode(newPwd), newName, newPhone);
		return res == 1 ? //
				new BasicRes(ResMessage.SUCCESS.getCode(), //
						ResMessage.SUCCESS.getMessage())
				: new BasicRes(ResMessage.UPDATE_INFO_FAILED.getCode(),
						ResMessage.UPDATE_INFO_FAILED.getMessage());
	}

}
