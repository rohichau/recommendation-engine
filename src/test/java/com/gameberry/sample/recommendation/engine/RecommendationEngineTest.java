package com.gameberry.sample.recommendation.engine;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;

import com.gameberry.sample.recommendation.enums.Cuisine;
import com.gameberry.sample.recommendation.models.CostTracking;
import com.gameberry.sample.recommendation.models.CuisineTracking;
import com.gameberry.sample.recommendation.models.Restaurant;
import com.gameberry.sample.recommendation.models.User;
import com.gameberry.sample.recommendation.service.RecommendationService;

public class RecommendationEngineTest {

	@Test
	public void testGetRecommendations() {
		// Create test data

		CuisineTracking[] cuisines = { new CuisineTracking(Cuisine.NorthIndian, 10),
				new CuisineTracking(Cuisine.SouthIndian, 5) };
		CostTracking[] costBracket = { new CostTracking(5, 7), new CostTracking(3, 5) };
		User user = new User(cuisines, costBracket, "DAVID");

		Restaurant[] restaurants = {
				new Restaurant("1", Cuisine.NorthIndian, 5, (float) 4.20, true, DateUtils.addHours(new Date(), -96)),
				new Restaurant("2", Cuisine.SouthIndian, 5, (float) 4.8, true, DateUtils.addHours(new Date(), -25)),
				new Restaurant("3", Cuisine.Chinese, 5, (float) 4.5, true, DateUtils.addHours(new Date(), -15)),
				new Restaurant("4", Cuisine.NorthIndian, 3, (float) 3.9, true, DateUtils.addHours(new Date(), -100)),
				new Restaurant("5", Cuisine.SouthIndian, 3, (float) 4.6, false, DateUtils.addHours(new Date(), -90)),
				new Restaurant("6", Cuisine.NorthIndian, 5, (float) 4, false, DateUtils.addHours(new Date(), -26)),
				new Restaurant("7", Cuisine.NorthIndian, 3, (float) 4.6, false, DateUtils.addHours(new Date(), -20)),
				new Restaurant("8", Cuisine.SouthIndian, 5, (float) 4.7, false, DateUtils.addHours(new Date(), -200)),
				new Restaurant("9", Cuisine.Chinese, 5, (float) 4.9, true, DateUtils.addHours(new Date(), -10)),
				new Restaurant("10", Cuisine.NorthIndian, 5, (float) 3.5, false, DateUtils.addHours(new Date(), -9)),
				new Restaurant("11", Cuisine.NorthIndian, 3, (float) 3.5, false, DateUtils.addHours(new Date(), -8)),
				new Restaurant("12", Cuisine.SouthIndian, 5, (float) 4.1, false, DateUtils.addHours(new Date(), -7)), };

		RecommendationEngine recoEngine = new RecommendationEngine(new RecommendationService());
		recoEngine.addUser(user);

		for (Restaurant restaurant : restaurants) {
			recoEngine.addRestaurant(restaurant);
		}

		recoEngine.precomputeRecommendations();

		Map<User, String[]> recos = recoEngine.getRecommendations();

		// Assert the expected output
		String[] expectedOutput = { "1", "4", "2", "6", "7", "8", "9", "3", "10", "11", "12", "5" };
		assertArrayEquals(expectedOutput, recos.get(user));
	}

}