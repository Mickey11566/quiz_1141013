package com.example.quiz_1141013.response;

import java.util.List;

public class FeedbackRes extends BasicRes {
	private List<Feedback> feedbackList;

	public FeedbackRes() {
		super();
	}

	public FeedbackRes(int code, String message) {
		super(code, message);
	}

	public FeedbackRes(int code, String message, List<Feedback> feedbackList) {
		super(code, message);
		this.feedbackList = feedbackList;
	}

	public List<Feedback> getFeedbackList() {
		return feedbackList;
	}

	public void setFeedbackList(List<Feedback> feedbackList) {
		this.feedbackList = feedbackList;
	}

}
