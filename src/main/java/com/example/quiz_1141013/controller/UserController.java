package com.example.quiz_1141013.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz_1141013.constants.ResMessage;
import com.example.quiz_1141013.request.UserAddReq;
import com.example.quiz_1141013.request.UserLoginReq;
import com.example.quiz_1141013.request.UserUpdateInfoReq;
import com.example.quiz_1141013.response.BasicRes;
import com.example.quiz_1141013.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("quiz/addUser")
	public BasicRes create(@Valid @RequestBody UserAddReq req) throws Exception {
		return userService.addUser(req);
	}

	@PostMapping("quiz/login")
	public BasicRes login(@Valid @RequestBody UserLoginReq req, HttpSession session) throws Exception {
		BasicRes res = userService.login(req);
		if (res.getCode() == 200) {
//			setting session's attribute when login success
			session.setAttribute("account", req.getEmail());
		}
		return res;
	}

	@PostMapping("quiz/logout")
	public BasicRes logout(HttpSession session) {
//		讓session 失效
//		一個 session 的有效時間為 30 分
//		在有效時間內做任何操作，都會再延長30分
		session.invalidate();
		return new BasicRes(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage());
	}

	@PostMapping("/quiz/updateInfo")
	public BasicRes updateInfo(@RequestBody UserUpdateInfoReq req) {
		return userService.updateInfo(req.getEmail(), //
				req.getOldPassword(), req.getNewPassword(), //
				req.getOldName(), req.getNewName(), //
				req.getOldPhone(), req.getNewPhone());
	}

}
