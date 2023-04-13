package com.gameberry.sample.recommendation.utils;

import com.gameberry.sample.recommendation.models.User;

public interface UserListener {
	void onUserChange(User user);
}