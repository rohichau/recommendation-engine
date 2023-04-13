package com.gameberry.sample.recommendation.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.gameberry.sample.recommendation.enums.Cuisine;
import com.gameberry.sample.recommendation.utils.RestaurantListener;

public class Restaurant {
	
	private String restaurantId;
	private Cuisine cuisine;
	private int costBracket;
	private float rating;
	private boolean isRecommended;
	private Date onboardedTime;
	private List<RestaurantListener> listeners;

	public Restaurant(String restaurantId, Cuisine cuisine, int costBracket, float rating, boolean isRecommended,
			Date onboardedTime) {
		super();
		this.restaurantId = restaurantId;
		this.cuisine = cuisine;
		this.costBracket = costBracket;
		this.rating = rating;
		this.isRecommended = isRecommended;
		this.onboardedTime = onboardedTime;
		this.listeners = new ArrayList<>();
	}
	
    public void addListener(RestaurantListener listener) {
        listeners.add(listener);
    }

    public void removeListener(RestaurantListener listener) {
        listeners.remove(listener);
    }

	/**
	 * @return the restaurantId
	 */
	public String getRestaurantId() {
		return restaurantId;
	}

	/**
	 * @param restaurantId the restaurantId to set
	 */
	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}

	/**
	 * @return the cuisine
	 */
	public Cuisine getCuisine() {
		return cuisine;
	}

	/**
	 * @param cuisine the cuisine to set
	 */
	public void setCuisine(Cuisine cuisine) {
		this.cuisine = cuisine;
		notifyListeners();
	}

	/**
	 * @return the costBracket
	 */
	public int getCostBracket() {
		return costBracket;
	}

	/**
	 * @param costBracket the costBracket to set
	 */
	public void setCostBracket(int costBracket) {
		this.costBracket = costBracket;
		notifyListeners();
	}

	/**
	 * @return the rating
	 */
	public float getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(float rating) {
		this.rating = rating;
		notifyListeners();
	}

	/**
	 * @return the isRecommended
	 */
	public boolean isRecommended() {
		return isRecommended;
	}

	/**
	 * @param isRecommended the isRecommended to set
	 */
	public void setRecommended(boolean isRecommended) {
		this.isRecommended = isRecommended;
		notifyListeners();
	}

	/**
	 * @return the onboardedTime
	 */
	public Date getOnboardedTime() {
		return onboardedTime;
	}

	/**
	 * @param onboardedTime the onboardedTime to set
	 */
	public void setOnboardedTime(Date onboardedTime) {
		this.onboardedTime = onboardedTime;
	}
	
	private void notifyListeners() {
		 for (RestaurantListener listener : listeners) {
	            listener.onRestaurantChanged(this);
	     }
    }

	@Override
	public int hashCode() {
		return Objects.hash(costBracket, cuisine, isRecommended, onboardedTime, rating, restaurantId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Restaurant other = (Restaurant) obj;
		return costBracket == other.costBracket && cuisine == other.cuisine && isRecommended == other.isRecommended
				&& Objects.equals(onboardedTime, other.onboardedTime)
				&& Float.floatToIntBits(rating) == Float.floatToIntBits(other.rating)
				&& Objects.equals(restaurantId, other.restaurantId);
	}
}