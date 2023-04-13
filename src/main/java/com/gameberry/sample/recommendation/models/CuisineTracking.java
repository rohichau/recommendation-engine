package com.gameberry.sample.recommendation.models;

import java.util.Objects;

import com.gameberry.sample.recommendation.enums.Cuisine;

public class CuisineTracking {

	public CuisineTracking(Cuisine type, int noOfOrders) {
		super();
		this.type = type;
		this.noOfOrders = noOfOrders;
	}

	private Cuisine type;
	private int noOfOrders;

	/**
	 * @return the type
	 */
	public Cuisine getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Cuisine type) {
		this.type = type;
	}

	/**
	 * @return the noOfOrders
	 */
	public int getNoOfOrders() {
		return noOfOrders;
	}

	/**
	 * @param noOfOrders the noOfOrders to set
	 */
	public void setNoOfOrders(int noOfOrders) {
		this.noOfOrders = noOfOrders;
	}

	@Override
	public int hashCode() {
		return Objects.hash(noOfOrders, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CuisineTracking other = (CuisineTracking) obj;
		return noOfOrders == other.noOfOrders && type == other.type;
	}
}