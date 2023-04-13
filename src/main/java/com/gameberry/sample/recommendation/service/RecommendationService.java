package com.gameberry.sample.recommendation.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import com.gameberry.sample.recommendation.models.CostTracking;
import com.gameberry.sample.recommendation.models.CuisineTracking;
import com.gameberry.sample.recommendation.models.Restaurant;
import com.gameberry.sample.recommendation.models.User;

@Service
public class RecommendationService {

	public String[] getRecommendations(User user, Restaurant[] availableRestaurants) {

		CuisineTracking[] userCuisines = user.getCuisines();

		/*
		 * Pre-computing the top cuisines and cost bracket so that we can do constant
		 * lookups on the array in our Comparator logic
		 */
		Arrays.sort(userCuisines, (a, b) -> b.getNoOfOrders() - a.getNoOfOrders());
		user.setCuisinesNoUpdate(userCuisines);
		CostTracking[] costTracking = user.getCostBracket();
		Arrays.sort(costTracking, (a, b) -> b.getNoOfOrders() - a.getNoOfOrders());
		user.setCostBracketNoUpdate(costTracking);

		// We are taking a set to do constant time lookups
		Set<Restaurant> top4NewlycreatedRestaurants = Arrays.stream(availableRestaurants)
				.filter(a -> a.getOnboardedTime().after(DateUtils.addHours(new Date(), -48)))
				.sorted(Comparator.comparingDouble(Restaurant::getRating).reversed()).limit(4)
				.collect(Collectors.toSet());

		String[] restaurantIDs = Arrays.stream(availableRestaurants)
				.sorted(new RestaurantComparator(user, top4NewlycreatedRestaurants)).map(Restaurant::getRestaurantId)
				.toArray(String[]::new);

		return restaurantIDs;
	}

	private class RestaurantComparator implements Comparator<Restaurant> {

		User user;
		Set<Restaurant> top4NewlycreatedRestaurants;

		RestaurantComparator(User user, Set<Restaurant> top4NewlycreatedRestaurants) {
			this.user = user;
			this.top4NewlycreatedRestaurants = top4NewlycreatedRestaurants;
		}

		@Override
		public int compare(Restaurant r1, Restaurant r2) {
			// compare as per the conditions

			// CONDITION 1:
			/*
			 * Featured restaurants of primary cuisine and primary cost bracket. If none,
			 * then all featured restaurants of primary cuisine, secondary cost and
			 * secondary cuisine, primary cost
			 */

			if ((r1.getCuisine() == this.user.getCuisines()[0].getType()
					&& r1.getCostBracket() == this.user.getCostBracket()[0].getType() && r1.isRecommended())) {
				return -1;
			}
			if ((r2.getCuisine() == this.user.getCuisines()[0].getType()
					&& r2.getCostBracket() == this.user.getCostBracket()[0].getType() && r2.isRecommended())) {
				return 1;
			}

			// primary cusine secondary cost - featured
			if (r1.getCuisine() == this.user.getCuisines()[0].getType()
					&& r1.getCostBracket() == this.user.getCostBracket()[1].getType() && r1.isRecommended()) {
				return -1;
			}
			if ((r2.getCuisine() == this.user.getCuisines()[0].getType()
					&& r2.getCostBracket() == this.user.getCostBracket()[1].getType() && r2.isRecommended())) {
				return 1;
			}

			// secondary cuisine primary cost - featured

			if (r1.getCuisine() == this.user.getCuisines()[1].getType()
					&& r1.getCostBracket() == this.user.getCostBracket()[0].getType() && r1.isRecommended()) {
				return -1;
			}
			if ((r2.getCuisine() == this.user.getCuisines()[1].getType()
					&& r2.getCostBracket() == this.user.getCostBracket()[0].getType() && r2.isRecommended())) {
				return 1;
			}

			// CONDITION 2:
			// primary cuisine & primary cost bracket with rating >= 4
			if ((r1.getCuisine() == this.user.getCuisines()[0].getType()
					&& r1.getCostBracket() == this.user.getCostBracket()[0].getType() && r1.getRating() >= 4)) {
				return -1;
			}
			if ((r2.getCuisine() == this.user.getCuisines()[0].getType()
					&& r2.getCostBracket() == this.user.getCostBracket()[0].getType() && r2.getRating() >= 4)) {
				return 1;
			}

			// CONDITION 3:
			// primary cuisine & secondary cost bracket with rating >= 4.5
			if ((r1.getCuisine() == this.user.getCuisines()[0].getType()
					&& r1.getCostBracket() == this.user.getCostBracket()[1].getType() && r1.getRating() >= 4.5)) {
				return -1;
			}
			if ((r2.getCuisine() == this.user.getCuisines()[0].getType()
					&& r2.getCostBracket() == this.user.getCostBracket()[1].getType() && r2.getRating() >= 4.5)) {
				return 1;
			}

			// CONDITION 4:
			// secondary cuisine, primary cost bracket with rating >= 4.5
			if ((r1.getCuisine() == this.user.getCuisines()[1].getType()
					&& r1.getCostBracket() == this.user.getCostBracket()[0].getType() && r1.getRating() >= 4.5)) {
				return -1;
			}
			if ((r2.getCuisine() == this.user.getCuisines()[1].getType()
					&& r2.getCostBracket() == this.user.getCostBracket()[0].getType() && r2.getRating() >= 4.5)) {
				return 1;
			}

			// CONDITION 5:

			if (top4NewlycreatedRestaurants != null && top4NewlycreatedRestaurants.contains(r1)) {
				return -1;
			}
			if (top4NewlycreatedRestaurants != null && top4NewlycreatedRestaurants.contains(r2)) {
				return 1;
			}

			// CONDITION 6:
			// primary cuisine & primary cost bracket with rating < 4
			if ((r1.getCuisine() == this.user.getCuisines()[0].getType()
					&& r1.getCostBracket() == this.user.getCostBracket()[0].getType() && r1.getRating() < 4)) {
				return -1;
			}
			if ((r2.getCuisine() == this.user.getCuisines()[0].getType()
					&& r2.getCostBracket() == this.user.getCostBracket()[0].getType() && r2.getRating() < 4)) {
				return 1;
			}

			// CONDITION 7:
			// All restaurants of Primary cuisine, secondary cost bracket with rating < 4.5
			if ((r1.getCuisine() == this.user.getCuisines()[0].getType()
					&& r1.getCostBracket() == this.user.getCostBracket()[1].getType() && r1.getRating() < 4.5)) {
				return -1;
			}
			if ((r2.getCuisine() == this.user.getCuisines()[0].getType()
					&& r2.getCostBracket() == this.user.getCostBracket()[1].getType() && r2.getRating() < 4.5)) {
				return 1;
			}

			// CONDITION 8:
			// All restaurants of secondary cuisine, primary cost bracket with rating < 4.5
			if ((r1.getCuisine() == this.user.getCuisines()[1].getType()
					&& r1.getCostBracket() == this.user.getCostBracket()[0].getType() && r1.getRating() < 4.5)) {
				return -1;
			}
			if ((r2.getCuisine() == this.user.getCuisines()[1].getType()
					&& r2.getCostBracket() == this.user.getCostBracket()[0].getType() && r2.getRating() < 4.5)) {
				return 1;
			}

			return 0;

		}

	}

}