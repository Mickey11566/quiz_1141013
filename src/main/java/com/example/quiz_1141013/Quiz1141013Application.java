package com.example.quiz_1141013;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


/**
 * 因為有使用 spring-boot-starter-security 此依賴，
 * 要排除預設的基本安全性設定(帳密登入驗證)， 排除帳密登入驗證就是加上
 * exclude = {SecurityAutoConfiguration.class}， 等號後面若有多個 class時
 * ，就要用大括號，一個時大括號可有可無，多個 class 是用逗號(,)區隔
 * =====================================================================================
 * 以下是 spring boot 4.0.0 版本的排除方式:
 * 
 * @SpringBootApplication(exclude = {//
 *                                UserDetailsServiceAutoConfiguration.class,
 *                                ServletWebSecurityAutoConfiguration.class})
 */

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class Quiz1141013Application {

	public static void main(String[] args) {
		SpringApplication.run(Quiz1141013Application.class, args);
	}

}
