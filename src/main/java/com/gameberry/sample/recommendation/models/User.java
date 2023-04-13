package com.gameberry.sample.recommendation.models;

import java.util.ArrayList;
import java.util.List;

import com.gameberry.sample.recommendation.utils.UserListener;

public class User {

	private CuisineTracking[] cuisines;
	private CostTracking[] costBracket;
	private String name;
	private List<UserListener> listeners;
	
	public User(CuisineTracking[] cuisines, CostTracking[] costBracket, String name) {
		super();
		this.cuisines = cuisines;
		this.costBracket = costBracket;
		this.name = name;
		this.listeners = new ArrayList<>();
	}

	public User() {}
	
	public void addListener(UserListener listener) {
        listeners.add(listener);
    }

    public void removeListener(UserListener listener) {
        listeners.remove(listener);
    }

	/**
	 * @return the cuisines
	 */
	public CuisineTracking[] getCuisines() {
		return cuisines;
	}

	/**
	 * @param cuisines the cuisines to set
	 */
	public void setCuisines(CuisineTracking[] cuisines) {
		this.cuisines = cuisines;
		notifyListeners();
	}
	
	/**
	 * @param cuisines the cuisines to set
	 */
	public void setCuisinesNoUpdate(CuisineTracking[] cuisines) {
		this.cuisines = cuisines;
	}

	/**
	 * @return the costBracket
	 */
	public CostTracking[] getCostBracket() {
		return costBracket;
	}

	/**
	 * @param costBracket the costBracket to set
	 */
	public void setCostBracket(CostTracking[] costBracket) {
		this.costBracket = costBracket;
		notifyListeners();
	}
	
	public void setCostBracketNoUpdate(CostTracking[] costBracket) {
		this.costBracket = costBracket;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	private void notifyListeners() {
        for (UserListener listener : listeners) {
            listener.onUserChange(this);
        }
    }
}