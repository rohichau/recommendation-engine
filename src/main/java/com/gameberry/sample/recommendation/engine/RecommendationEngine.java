package com.gameberry.sample.recommendation.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gameberry.sample.recommendation.models.Restaurant;
import com.gameberry.sample.recommendation.models.User;
import com.gameberry.sample.recommendation.service.RecommendationService;
import com.gameberry.sample.recommendation.utils.RestaurantListener;
import com.gameberry.sample.recommendation.utils.UserListener;

/*
 * This class acts like a cache where we have pre-computed values of recommendations 
 * which can be quickly returned if required for any user.
 * */

@Component
class RecommendationEngine implements UserListener, RestaurantListener {

	private List<User> users;
	private List<Restaurant> restaurants;
	private Map<User, String[]> recommendations;

	/**
	 * @return the recommendations
	 */
	public Map<User, String[]> getRecommendations() {
		return recommendations;
	}

	public RecommendationService recoService;

	@Autowired
	public RecommendationEngine(RecommendationService recoService) {
		users = new ArrayList<>();
		restaurants = new ArrayList<>();
		recommendations = new HashMap<>();
		this.recoService = recoService;
	}

	public void precomputeRecommendations() {
		// precompute the recommendations for each user
		for (User user : users) {
			String[] recos = recoService.getRecommendations(user, convertRestoList(restaurants));
			recommendations.put(user, recos);
		}
	}

	public void addUser(User user) {
		users.add(user);
		user.addListener(this);
	}

	public void removeUser(User user) {
		users.remove(user);
		user.removeListener(this);
	}

	public void addRestaurant(Restaurant restaurant) {
		restaurants.add(restaurant);
		restaurant.addListener(this);
	}

	public void removeRestaurant(Restaurant restaurant) {
		restaurants.remove(restaurant);
		restaurant.removeListener(this);
	}

	private Restaurant[] convertRestoList(List<Restaurant> restoList) {
		Restaurant[] restoArr = new Restaurant[restoList.size()];
		for (int i = 0; i < restoList.size(); i++) {
			restoArr[i] = restoList.get(i);
		}
		return restoArr;
	}

	@Override
	public void onUserChange(User user) {
		// When a user changes, recompute their recommendations
		String[] recos = recoService.getRecommendations(user, convertRestoList(restaurants));
		recommendations.put(user, recos);
	}

	@Override
	public void onRestaurantChanged(Restaurant restaurant) {
		// When a restaurant changes, recompute recommendations for all users
		precomputeRecommendations();
	}
}