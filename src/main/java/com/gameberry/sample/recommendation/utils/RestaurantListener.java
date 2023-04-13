package com.gameberry.sample.recommendation.utils;

import com.gameberry.sample.recommendation.models.Restaurant;

public interface RestaurantListener {
    void onRestaurantChanged(Restaurant restaurant);
}