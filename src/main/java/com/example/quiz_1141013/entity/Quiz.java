package com.example.quiz_1141013.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Table(name = "quiz")
@Entity
public class Quiz {

	@Column(name = "id")
	@Id
	private int id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "startDate")
	private LocalDate startDate;

	@Column(name = "endDate")
	private LocalDate endDate;

//	屬性變數名稱即使資料型態為 boolean，也別用 is 開頭
//	因為會影響getter 方法命名方式
//	published 資料型態為 boolean，所以 getter 的方法名稱預設為 isPublished
//	若變數名稱一開始設定為isPublished
//	則IDE自動產生的正確getter方法名稱應該為 isIsPublished
//	但IDE產生的為 isPublished ，所以會影響資料庫中該欄位的值無法被傳遞到變數容器中
//	會導致該變數永遠為預設值 false(0)
	
	@Column(name = "published")
	private boolean published;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

}
